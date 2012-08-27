package au.net.bunney.bamboo.plugins.envwallboard.admin;

import com.atlassian.bamboo.bandana.PlanAwareBandanaContext;
import com.atlassian.bamboo.security.StringEncrypter;
import com.atlassian.bandana.BandanaManager;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Gavin Bunney
 */
public class EnvironmentConfigManager implements Serializable {

    private transient BandanaManager bandanaManager;
    private static final String CONFIG_KEY = "au.net.bunney.bamboo.plugins.envwallboard.config";
    private final List<EnvironmentConfig> configuredEnvironments = new CopyOnWriteArrayList<EnvironmentConfig>();
    private AtomicLong nextAvailableId = new AtomicLong(0);

    public EnvironmentConfigManager(BandanaManager bandanaManager) {
        setBandanaManager(bandanaManager);
    }

    public List<EnvironmentConfig> getAllEnvironmentConfigs() {
        return Lists.newArrayList(configuredEnvironments);
    }

    public List<EnvironmentConfig> getAllEnvironmentConfigs(String wallboardName) {
        List<EnvironmentConfig> allConfigs = getAllEnvironmentConfigs();

        if (wallboardName == null)
            return allConfigs;

        List<EnvironmentConfig> filteredConfigs = new ArrayList<EnvironmentConfig>();
        for (EnvironmentConfig config : allConfigs) {
            if (   (config.getWallboardName() != null)
                && (config.getWallboardName().equals(wallboardName))) {
                filteredConfigs.add(config);
            }
        }

        return filteredConfigs;
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
        for (EnvironmentConfig environmentConfig : configuredEnvironments) {
            if (environmentConfig.getId() == id) {
                configuredEnvironments.remove(environmentConfig);
                persist();
                break;
            }
        }
    }

    public void updateEnvironmentConfiguration(EnvironmentConfig updated) {
        for (EnvironmentConfig configuredEnvironment : configuredEnvironments) {
            if (configuredEnvironment.getId() == updated.getId()) {
                configuredEnvironment.setName(updated.getName());
                configuredEnvironment.setUrl(updated.getUrl());
                configuredEnvironment.setAuth(updated.getAuth());
                configuredEnvironment.setWallboardName(updated.getWallboardName());
                persist();
                break;
            }
        }
    }

    public void setBandanaManager(BandanaManager bandanaManager) {
        this.bandanaManager = bandanaManager;

        Integer configCount = (Integer)bandanaManager.getValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, CONFIG_KEY + ".count");
        if (configCount == null)
            return;

        StringEncrypter stringEncrypter = new StringEncrypter();

        for (int idx = 0; idx < configCount; ++idx) {

            String environmentKey = CONFIG_KEY + "." + idx;

            Long configId = (Long)bandanaManager.getValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".id");
            if (nextAvailableId.get() <= configId) {
                nextAvailableId.set(configId + 1);
            }

            configuredEnvironments.add(new EnvironmentConfig(configId,
                    (String)bandanaManager.getValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".name"),
                    (String)bandanaManager.getValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".url"),
                    stringEncrypter.decrypt((String)bandanaManager.getValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".auth")),
                    (String)bandanaManager.getValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".wallboardName")));
        }
    }

    private synchronized void persist() {

        bandanaManager.setValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, CONFIG_KEY + ".count", configuredEnvironments.size());

        StringEncrypter stringEncrypter = new StringEncrypter();
        int idx = 0;
        for (EnvironmentConfig environmentConfig : configuredEnvironments) {
            String environmentKey = CONFIG_KEY + "." + idx;

            bandanaManager.setValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".id", environmentConfig.getId());
            bandanaManager.setValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".name", environmentConfig.getName());
            bandanaManager.setValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".url", environmentConfig.getUrl());
            bandanaManager.setValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".auth", stringEncrypter.encrypt(environmentConfig.getAuth()));
            bandanaManager.setValue(PlanAwareBandanaContext.GLOBAL_CONTEXT, environmentKey + ".wallboardName", environmentConfig.getWallboardName());
            idx++;
        }
    }
}