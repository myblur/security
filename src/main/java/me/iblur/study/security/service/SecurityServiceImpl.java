package me.iblur.study.security.service;

import me.iblur.study.security.authentication.SecurityService;
import me.iblur.study.security.authentication.UrlAuthority;
import me.iblur.study.security.mapper.SecurityMapper;
import me.iblur.study.security.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author 秦欣
 * @since 2017年06月22日 18:19.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private final SecurityMapper securityMapper;

    @Autowired
    public SecurityServiceImpl(final SecurityMapper securityMapper) {
        this.securityMapper = securityMapper;
    }


    public UserDetails loadUserByUsername(final String username) {
        final AuthUser authUser = securityMapper.loadUserByUsername(username);
        if (null == authUser) {
            throw new UsernameNotFoundException(username);
        }
        return User.withUsername(username).password(authUser.getPassword()).disabled(!authUser.getEnabled())
                .accountLocked(authUser.getLocked()).accountExpired(authUser.getExpired())
                .authorities(authUser.getRoles().<GrantedAuthority>stream().map(role -> new SimpleGrantedAuthority
                        (role.getName())).collect(toList())).build();
    }

    @Override
    public List<UrlAuthority> loadAllAuthority() throws Exception {
        return securityMapper.loadAllAuthority();
    }

}
