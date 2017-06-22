package me.iblur.study.security.service;

import me.iblur.study.security.mapper.SecurityMapper;
import me.iblur.study.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 秦欣
 * @since 2017年06月22日 18:19.
 */
@Service
public class SecurityService {

    @Autowired
    private SecurityMapper securityMapper;

    public User loadUserByUsername(final String username) {
        final User user = securityMapper.loadUserByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

}
