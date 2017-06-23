package me.iblur.study.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author 秦欣
 * @since 2017年06月23日 9:17.
 */
public interface SecurityService {

    /**
     * 加载所有url权限
     *
     * @return url权限集合
     */
    List<UrlAuthority> loadAllAuthority() throws Exception;

    /**
     * 根据用户名加载用户信息
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 根据用户名没有查找到用户时抛出此异常
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
