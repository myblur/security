package me.iblur.study.security.authentication;

import me.iblur.study.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
        final me.iblur.study.security.model.User user = securityService.loadUserByUsername(username);
        final User.UserBuilder userBuilder = User.withUsername(username)
                .password("");
        userBuilder.disabled(!user.getEnabled());
        userBuilder.accountExpired(user.getExpired());
        userBuilder.accountLocked(user.getLocked());
        userBuilder.authorities(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        return userBuilder.build();
    }

}
