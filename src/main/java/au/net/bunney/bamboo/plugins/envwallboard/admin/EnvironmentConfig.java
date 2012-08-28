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

import java.io.Serializable;

/**
 * @author Gavin Bunney
 */
public class EnvironmentConfig implements Serializable {

    private long id;
    private String name;
    private String url;
    private String auth;
    private String wallboardName;

    private Integer displayPriority;
    private Double displayWidth;

    public EnvironmentConfig() {
    }

    public EnvironmentConfig(long id, String name, String url, String auth, String wallboardName, Integer displayPriority, Double displayWidth) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.auth = auth;
        this.wallboardName = wallboardName;
        this.displayPriority = displayPriority;
        this.displayWidth = displayWidth;
    }

    public EnvironmentConfig(EnvironmentConfig environmentConfig) {
        this.id = environmentConfig.id;
        this.name = environmentConfig.name;
        this.url = environmentConfig.url;
        this.auth = environmentConfig.auth;
        this.wallboardName = environmentConfig.wallboardName;
        this.displayPriority = environmentConfig.displayPriority;
        this.displayWidth = environmentConfig.displayWidth;
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