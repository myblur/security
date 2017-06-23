package me.iblur.study.security.model;

import java.util.List;

/**
 * @author 秦欣
 * @since 2017年06月23日 10:22.
 */
public class AuthUser {

    private Long id;

    private String username;

    private String password;

    private Boolean enabled;

    private Boolean locked;

    private Boolean expired;

    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(final Boolean locked) {
        this.locked = locked;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(final Boolean expired) {
        this.expired = expired;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(final List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthUser{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append(", locked=").append(locked);
        sb.append(", expired=").append(expired);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
