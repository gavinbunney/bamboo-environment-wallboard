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
    private List<HashMap<String, String>> environments = new ArrayList<HashMap<String, String>>();
    private String wallboardName;
    private ThreadGroup threadGroup = new ThreadGroup(THREAD_GROUP_NAME);

    public ViewEnvironmentWallboard(EnvironmentConfigManager environmentConfigManager) {
        super();
        this.environmentConfigManager = environmentConfigManager;
    }

    @Override
    public String doDefault() throws Exception {

        // copy the environments into a friendly map for display
        for (EnvironmentConfig environmentConfig : environmentConfigManager.getAllEnvironmentConfigs(getWallboardName())) {
            HashMap<String, String> env = new HashMap<String, String>();
            env.put("name", environmentConfig.getName());
            env.put("url", environmentConfig.getUrl());
            env.put("auth", environmentConfig.getAuth());
            environments.add(env);
        }

        for (final HashMap<String, String> environment : environments) {
            final String threadName = environment.get("name");

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

    public static void connect(HashMap<String, String> environment) {
        try {
            URL url = new URL(environment.get("url"));
            HttpURLConnection yc = (HttpURLConnection) url.openConnection();

            String usernamePassword = environment.get("auth");
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

                environment.put("status", "alive");
                environment.put("buildNumber", buildDetails.get("buildNumber"));
                environment.put("buildTimeStamp", buildDetails.get("buildTimeStamp"));
                environment.put("buildRevision", buildDetails.get("buildRevision"));
            } finally {
                scanner.close();
            }

        } catch (Exception e) {
            log.info("Environment wallboard exception: " + e);
            environment.put("status", "dead");
        }
    }

    public String getWallboardName() {
        return wallboardName;
    }

    public void setWallboardName(String wallboardName) {
        this.wallboardName = wallboardName;
    }

    public List<HashMap<String, String>> getEnvironments() {
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
