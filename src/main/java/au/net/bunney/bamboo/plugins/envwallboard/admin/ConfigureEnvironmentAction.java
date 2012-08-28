/*
 * Copyright 2012 Bunney Apps, Brisbane, Australia.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package au.net.bunney.bamboo.plugins.envwallboard.admin;

import au.net.bunney.bamboo.plugins.envwallboard.EnvironmentDetails;
import au.net.bunney.bamboo.plugins.envwallboard.ViewEnvironmentWallboard;
import com.atlassian.bamboo.ww2.BambooActionSupport;
import org.apache.commons.lang.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * @author Gavin Bunney
 */
public class ConfigureEnvironmentAction extends BambooActionSupport {

    private String mode;
    private String testing;

    private long environmentId;
    private String name;
    private String url;
    private String auth;
    private String wallboardName;
    private Integer displayPriority;
    private Double displayWidth;

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

        if (   (displayWidth != null)
            && (   (displayWidth <= 0)
                || (displayWidth > 100))) {
            addFieldError("displayWidth", "Display width must be blank or between 0 and 100.");
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
            testConnection();
            return "input";
        }

        environmentConfigManager.addEnvironmentConfiguration(
                new EnvironmentConfig(-1, getName(), getUrl(), getAuth(), getWallboardName(), getDisplayPriority(), getDisplayWidth()));
        return "success";
    }

    public String doEdit() throws Exception {
        EnvironmentConfig environmentConfig = environmentConfigManager.getEnvironmentConfigById(environmentId);
        if (environmentConfig == null) {
            throw new IllegalArgumentException("Could not find environment configuration by the ID " + environmentId);
        }
        setEnvironmentId(environmentConfig.getId());
        setName(environmentConfig.getName());
        setUrl(environmentConfig.getUrl());
        setAuth(environmentConfig.getAuth());
        setWallboardName(environmentConfig.getWallboardName());
        setDisplayPriority(environmentConfig.getDisplayPriority());
        setDisplayWidth(environmentConfig.getDisplayWidth());

        return "input";
    }

    public String doUpdate() throws Exception {
        if (isTesting()) {
            testConnection();
            return "input";
        }

        environmentConfigManager.updateEnvironmentConfiguration(
                new EnvironmentConfig(getEnvironmentId(), getName(), getUrl(), getAuth(), getWallboardName(), getDisplayPriority(), getDisplayWidth()));
        return "success";
    }

    public String doDelete() throws Exception {
        environmentConfigManager.deleteEnvironmentConfiguration(getEnvironmentId());

        return "success";
    }

    private void testConnection() {

        EnvironmentDetails testDetails = new EnvironmentDetails();
        testDetails.setUrl(url);
        testDetails.setAuth(auth);
        ViewEnvironmentWallboard.connect(testDetails);

        if (testDetails.getStatus().equals("alive")) {
            addActionMessage("Connection successful!");
        } else {
            addActionError("Connection failed - check the url and the build file exists");
        }
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

    public long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(long environmentId) {
        this.environmentId = environmentId;
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

    public String getWallboardName() {
        return wallboardName;
    }

    public void setWallboardName(String wallboardName) {
        this.wallboardName = wallboardName;
    }

    public Integer getDisplayPriority() {
        return displayPriority;
    }

    public void setDisplayPriority(Integer displayPriority) {
        this.displayPriority = displayPriority;
    }

    public Double getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(Double displayWidth) {
        this.displayWidth = displayWidth;
    }

    public String getDisplayPriorityString() {
        return displayPriority != null ? displayPriority.toString() : "";
    }

    public String getDisplayWidthString() {
        return displayWidth != null ? displayWidth.toString() + "%" : "";
    }
}
