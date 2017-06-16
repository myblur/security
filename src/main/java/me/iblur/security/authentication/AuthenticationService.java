package me.iblur.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author 秦欣
 * @since 2017年06月16日 9:43.
 */
public interface AuthenticationService<T> {

    UserDetails attemptAuthentication(final T authentication);
}
