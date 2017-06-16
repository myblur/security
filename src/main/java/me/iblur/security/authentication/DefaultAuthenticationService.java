package me.iblur.security.authentication;

import me.iblur.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author 秦欣
 * @since 2017年06月16日 10:06.
 */
@Service
public class DefaultAuthenticationService implements AuthenticationService<Authentication> {

    private final SecurityService securityService;

    @Autowired
    public DefaultAuthenticationService(final SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public UserDetails attemptAuthentication(final Authentication authentication) {
        final User.UserBuilder userBuilder = User.withUsername(authentication.getPrincipal().toString());

        return userBuilder.build();
    }
}
