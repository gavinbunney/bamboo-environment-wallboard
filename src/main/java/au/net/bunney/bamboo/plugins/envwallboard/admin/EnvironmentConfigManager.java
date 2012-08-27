package au.net.bunney.bamboo.plugins.envwallboard.admin;

import com.atlassian.bamboo.bandana.PlanAwareBandanaContext;
import com.atlassian.bamboo.security.StringEncrypter;
import com.atlassian.bandana.BandanaManager;
import com.atlassian.spring.container.ContainerManager;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Gavin Bunney
 */
public class EnvironmentConfigManager implements Serializable {

    private transient Logger log = Logger.getLogger(EnvironmentConfigManager.class);

    private transient BandanaManager bandanaManager;
    private static final String CONFIG_KEY = "au.net.bunney.bamboo.plugins.envwallboard.config";
    private final List<EnvironmentConfig> configuredEnvironments = new CopyOnWriteArrayList<EnvironmentConfig>();
    private AtomicLong nextAvailableId = new AtomicLong(0);

    public static EnvironmentConfigManager getInstance() {
        EnvironmentConfigManager EnvironmentConfigManager = new EnvironmentConfigManager();
        ContainerManager.autowireComponent(EnvironmentConfigManager);
        return EnvironmentConfigManager;
    }

    public List<EnvironmentConfig> getAllEnvironmentConfigs() {
        return Lists.newArrayList(configuredEnvironments);
    }

    public EnvironmentConfig getEnvironmentConfigById(long id) {
        for (EnvironmentConfig configuredServer : configuredEnvironments) {
            if (configuredServer.getId() == id) {
                return configuredServer;
            }
        }

        return null;
    }

    public void addEnvironmentConfiguration(EnvironmentConfig environmentConfig) {
        environmentConfig.setId(nextAvailableId.getAndIncrement());
        configuredEnvironments.add(environmentConfig);
        persist();
    }

    public void deleteEnvironmentConfiguration(final long id) {
        Iterator<EnvironmentConfig> configIterator = configuredEnvironments.iterator();
        while (configIterator.hasNext()) {
            EnvironmentConfig environmentConfig = configIterator.next();
            if (environmentConfig.getId() == id) {
                configuredEnvironments.remove(environmentConfig);
                persist();
                break;
            }
        }
    }

    public void updateEnvironmentConfiguration(EnvironmentConfig updated) {
        for (EnvironmentConfig configuredServer : configuredEnvironments) {
            if (configuredServer.getId() == updated.getId()) {
                configuredServer.setName(updated.getName());
                configuredServer.setUrl(updated.getUrl());
                configuredServer.setAuth(updated.getAuth());
                persist();
                break;
            }
        }
    }

    public void setBandanaManager(BandanaManager bandanaManager) {
        this.bandanaManager = bandanaManager;

        Object existingConfigs = bandanaManager.getValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, CONFIG_KEY);
        if (existingConfigs != null) {

            List<EnvironmentConfig> environmentConfigList = (List<EnvironmentConfig>) existingConfigs;
            StringEncrypter stringEncrypter = new StringEncrypter();

            for (EnvironmentConfig environmentConfig : environmentConfigList) {
                if (nextAvailableId.get() <= environmentConfig.getId()) {
                    nextAvailableId.set(environmentConfig.getId() + 1);
                }

                configuredEnvironments.add(new EnvironmentConfig(environmentConfig.getId(),
                                                                 environmentConfig.getName(),
                                                                 environmentConfig.getUrl(),
                                                                 stringEncrypter.decrypt(environmentConfig.getAuth())));
            }
        }
    }

    private synchronized void persist() {
        StringEncrypter stringEncrypter = new StringEncrypter();
        List<EnvironmentConfig> environmentConfigs = Lists.newArrayList();

        for (EnvironmentConfig environmentConfig : configuredEnvironments) {
            environmentConfigs.add(new EnvironmentConfig(environmentConfig.getId(),
                                                         environmentConfig.getName(),
                                                         environmentConfig.getUrl(),
                                                         stringEncrypter.encrypt(environmentConfig.getAuth())));
        }

        bandanaManager.setValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, CONFIG_KEY, environmentConfigs);
    }
}