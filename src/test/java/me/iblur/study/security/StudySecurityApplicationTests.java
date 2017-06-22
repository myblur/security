package me.iblur.study.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudySecurityApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(StudySecurityApplicationTests.class);

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPasswordEncoder() {
        String text = "doubi";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String encode = passwordEncoder.encode(text);
        logger.info("{}", encode);
    }

}
