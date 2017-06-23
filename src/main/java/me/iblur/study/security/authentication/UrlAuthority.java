package me.iblur.study.security.authentication;

/**
 * @author 秦欣
 * @since 2017年06月23日 9:18.
 */
public class UrlAuthority {

    private String url;

    private String ipAddress;

    private String method;

    private String roleName;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }
}
