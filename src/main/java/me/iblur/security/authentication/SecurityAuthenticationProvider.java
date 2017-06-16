package me.iblur.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author 秦欣
 * @since 2017年06月16日 9:41.
 */
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    @Value(value = "${security.rolePrefix}")
    private String rolePrefix;

    private final AuthenticationService<Authentication> authenticationService;

    @Autowired
    public SecurityAuthenticationProvider(final AuthenticationService<Authentication> authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UserDetails userDetails = authenticationService.attemptAuthentication(authentication);
        final UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(), authentication
                .getCredentials(), userDetails.getAuthorities());
        authenticationToken.setDetails(userDetails);
        return authentication;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
