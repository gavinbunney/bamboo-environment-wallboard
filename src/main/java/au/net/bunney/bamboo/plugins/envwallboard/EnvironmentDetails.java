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

package au.net.bunney.bamboo.plugins.envwallboard;

import au.net.bunney.bamboo.plugins.envwallboard.admin.EnvironmentConfig;

/**
 * @author Gavin Bunney
 */
public class EnvironmentDetails extends EnvironmentConfig {

    private String status;
    private String buildNumber;
    private String buildTimeStamp;
    private String buildRevision;
    private String displayWidthPercentage;

    public EnvironmentDetails() {
        super();
    }

    public EnvironmentDetails(EnvironmentConfig environmentConfig) {
        super(environmentConfig);

        if (this.getDisplayWidth() != null) {
            this.displayWidthPercentage = getDisplayWidth() + "%";
        } else {
            this.displayWidthPercentage = "0%";
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getBuildTimeStamp() {
        return buildTimeStamp;
    }

    public void setBuildTimeStamp(String buildTimeStamp) {
        this.buildTimeStamp = buildTimeStamp;
    }

    public String getBuildRevision() {
        return buildRevision;
    }

    public void setBuildRevision(String buildRevision) {
        this.buildRevision = buildRevision;
    }

    public String getDisplayWidthPercentage() {
        return displayWidthPercentage;
    }

    public void setDisplayWidthPercentage(String displayWidthPercentage) {
        this.displayWidthPercentage = displayWidthPercentage;
    }
}
