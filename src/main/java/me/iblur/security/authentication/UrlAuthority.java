package me.iblur.security.authentication;

/**
 * @author 秦欣
 * @since 2017年06月16日 10:44.
 */
public interface UrlAuthority {

    /**
     * 获取当前url权限的资源地址
     *
     * @return 资源地址URL
     */
    String getUrl();

    /**
     * 获取当前url权限的请求方法
     *
     * @return 请求方法 {@link org.springframework.web.bind.annotation.RequestMethod}
     */
    String getMethod();

    /**
     * 获取当前url权限角色名称
     *
     * @return 角色名称
     */
    String getRoleName();

}
