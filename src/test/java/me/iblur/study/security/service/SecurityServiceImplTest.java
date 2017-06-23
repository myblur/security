package me.iblur.study.security.service;

import me.iblur.study.security.mapper.SecurityMapper;
import me.iblur.study.security.model.AuthUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 秦欣
 * @since 2017年06月23日 11:04.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(SecurityServiceImplTest.class);

    @Autowired
    private SecurityMapper securityMapper;

    @Test
    public void loadUserByUsername() throws Exception {
        final AuthUser authUser = securityMapper.loadUserByUsername("iblur");
        logger.info("查询出的用户：{}", authUser);
    }

    @Test
    public void loadAllAuthority() throws Exception {
    }

}