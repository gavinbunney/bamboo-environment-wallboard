/*
 * Copyright (C) 2010 JFrog Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package au.net.bunney.bamboo.plugins.envwallboard.admin;

import com.atlassian.bamboo.ww2.BambooActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Gavin Bunney
 */
public class ConfigureEnvironmentAction extends BambooActionSupport {

    private transient Logger log = Logger.getLogger(ConfigureEnvironmentAction.class);

    private String mode;
    private String testing;

    private long id;
    private String name;
    private String url;
    private String auth;

    private transient EnvironmentConfigManager environmentConfigManager;

    public ConfigureEnvironmentAction(EnvironmentConfigManager environmentConfigManager) {
        this.environmentConfigManager =  environmentConfigManager;
        mode = "add";
    }

    @Override
    public void validate() {
        clearErrorsAndMessages();

        if (StringUtils.isBlank(name)) {
            addFieldError("name", "Please specify a name for the environment.");
        }

        if (StringUtils.isBlank(url)) {
            addFieldError("url", "Please specify a URL of the environment.");
        } else {
            try {
                new URL(url);
            } catch (MalformedURLException mue) {
                addFieldError("url", "Please specify a valid URL of the environment.");
            }
        }

    }

    public String doAdd() throws Exception {
        return "input";
    }

    public String doCreate() throws Exception {
        if (isTesting()) {
            return "input";
        }

        environmentConfigManager.addEnvironmentConfiguration(
                new EnvironmentConfig(-1, getName(), getUrl(), getAuth()));
        return "success";
    }

    public String doEdit() throws Exception {
        EnvironmentConfig environmentConfig = environmentConfigManager.getEnvironmentConfigById(id);
        if (environmentConfig == null) {
            throw new IllegalArgumentException("Could not find environment configuration by the ID " + id);
        }
        setName(environmentConfig.getName());
        setUrl(environmentConfig.getUrl());
        setAuth(environmentConfig.getAuth());

        return "input";
    }

    public String doUpdate() throws Exception {
        if (isTesting()) {
            return "input";
        }

        environmentConfigManager.updateEnvironmentConfiguration(
                new EnvironmentConfig(getId(), getName(), getUrl(), getAuth()));
        return "success";
    }

    public String doDelete() throws Exception {
        environmentConfigManager.deleteEnvironmentConfiguration(getId());

        return "success";
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTesting() {
        return testing;
    }

    private boolean isTesting() {
        return "Test".equals(getTesting());
    }

    public void setTesting(String testing) {
        this.testing = testing;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
