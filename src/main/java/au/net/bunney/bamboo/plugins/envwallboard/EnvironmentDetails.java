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
