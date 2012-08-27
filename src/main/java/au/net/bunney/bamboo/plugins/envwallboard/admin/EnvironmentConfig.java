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

    public EnvironmentConfig() {
    }

    public EnvironmentConfig(long id, String name, String url, String auth, String wallboardName) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.auth = auth;
        this.wallboardName = wallboardName;
    }

    public EnvironmentConfig(EnvironmentConfig environmentConfig) {
        this.id = environmentConfig.id;
        this.name = environmentConfig.name;
        this.url = environmentConfig.url;
        this.auth = environmentConfig.auth;
        this.wallboardName = environmentConfig.wallboardName;
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
}