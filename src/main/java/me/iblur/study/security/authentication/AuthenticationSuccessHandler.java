package me.iblur.study.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 秦欣
 * @since 2017年06月22日 12:28.
 */
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws ServletException, IOException {
        //获得授权后可得到用户信息   可使用OperatorInfoService进行数据库操作
        final UserDetails userDetails = (UserDetails) authentication.getDetails();
        //输出登录提示信息
        logger.info("用户[{}]登陆成功", userDetails.getUsername());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
