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
import au.net.bunney.bamboo.plugins.envwallboard.admin.EnvironmentConfigManager;
import com.atlassian.bamboo.ww2.BambooActionSupport;
import org.apache.log4j.Logger;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ViewEnvironmentWallboard extends BambooActionSupport {

    private static final Logger log = Logger.getLogger(ViewEnvironmentWallboard.class);

    private static final String THREAD_GROUP_NAME = "ViewEnvironmentWallboardThreadGroup";

    private static final int DEFAULT_SECONDS_BEFORE_NEXT_REFRESH = 15;
    private int secondsBeforeNextRefresh = DEFAULT_SECONDS_BEFORE_NEXT_REFRESH;

    private EnvironmentConfigManager environmentConfigManager;
    private List<EnvironmentDetails> environments = new ArrayList<EnvironmentDetails>();
    private String wallboardName;
    private ThreadGroup threadGroup = new ThreadGroup(THREAD_GROUP_NAME);

    public ViewEnvironmentWallboard(EnvironmentConfigManager environmentConfigManager) {
        super();
        this.environmentConfigManager = environmentConfigManager;
    }

    @Override
    public String doDefault() throws Exception {

        for (EnvironmentConfig environmentConfig : environmentConfigManager.getAllEnvironmentConfigs(getWallboardName())) {
            environments.add(new EnvironmentDetails(environmentConfig));
        }

        for (final EnvironmentDetails environment : environments) {
            final String threadName = environment.getName();

            Runnable runnableBlock = new Runnable() {
                public void run() {
                connect(environment);
                log.debug(String.format("%s - Completed", threadName));
                }
            };

            Thread childRunner = new Thread(threadGroup, runnableBlock, "");
            log.debug(String.format("Start thread runner '%s'", threadName ));
            childRunner.start();
        }

        // wait for all the child threads to finish
        Thread[] childThreads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(childThreads);
        for (Thread childThread : childThreads) {
            childThread.join();
        }

        return SUCCESS;
    }

    public static void connect(EnvironmentDetails environment) {
        try {
            URL url = new URL(environment.getUrl());
            HttpURLConnection yc = (HttpURLConnection) url.openConnection();

            String usernamePassword = environment.getAuth();
            if (usernamePassword != null) {
                String encoded = new sun.misc.BASE64Encoder().encode(usernamePassword.getBytes());
                yc.setRequestProperty("Authorization", "Basic " + encoded);
            }

            yc.setConnectTimeout(5 * 1000);
            yc.setReadTimeout(10 * 1000);
            yc.connect();
            Scanner scanner = new Scanner(yc.getInputStream());

            try {
                HashMap<String, String> buildDetails = new HashMap<String, String>();

                while ( scanner.hasNextLine() ){
                    String line = scanner.nextLine();
                    if (line.equals("[build]"))
                        continue;

                    Scanner lineScanner = new Scanner(line);
                    lineScanner.useDelimiter("=");
                    if ( lineScanner.hasNext() ){
                        String name = lineScanner.next().trim();
                        String value = lineScanner.next().trim();

                        buildDetails.put(name, value);
                    }
                }

                environment.setStatus("alive");
                environment.setBuildNumber(buildDetails.get("buildNumber"));
                environment.setBuildTimeStamp(buildDetails.get("buildTimeStamp"));
                environment.setBuildRevision(buildDetails.get("buildRevision"));
            } finally {
                scanner.close();
            }

        } catch (Exception e) {
            log.info("Environment wallboard exception: " + e);
            environment.setStatus("dead");
        }
    }

    public String getWallboardName() {
        return wallboardName;
    }

    public void setWallboardName(String wallboardName) {
        this.wallboardName = wallboardName;
    }

    public List<EnvironmentDetails> getEnvironments() {
        return environments;
    }

    public int getSecondsBeforeNextRefresh() {
        if(secondsBeforeNextRefresh < DEFAULT_SECONDS_BEFORE_NEXT_REFRESH) {
            return DEFAULT_SECONDS_BEFORE_NEXT_REFRESH;
        }
        return secondsBeforeNextRefresh;
    }

    public void setSecondsBeforeNextRefresh(int secondsBeforeNextRefresh) {
        this.secondsBeforeNextRefresh = secondsBeforeNextRefresh;
    }
}
