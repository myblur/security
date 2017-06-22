package me.iblur.study.security.authentication;

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

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User.UserBuilder userBuilder = User.withUsername(username)
                .password("$2a$10$AgOCOnU8vv0qsmpnO0fYNOqe1IOOXgK88E3ENsKsJUFXWmCVg8hP6");
        userBuilder.accountExpired(false);
        userBuilder.accountLocked(false);
        userBuilder.authorities(new SimpleGrantedAuthority("USER"));
        return userBuilder.build();
    }
}
