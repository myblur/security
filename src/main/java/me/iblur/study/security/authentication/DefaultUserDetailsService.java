package me.iblur.study.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author 秦欣
 * @since 2017年06月22日 11:00.
 */
public class DefaultUserDetailsService implements UserDetailsService {

    private final SecurityService securityService;

    @Autowired
    public DefaultUserDetailsService(final SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return securityService.loadUserByUsername(username);
    }

}
