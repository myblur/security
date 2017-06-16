package me.iblur.security.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 秦欣
 * @since 2017年06月16日 11:01.
 */
@Component
public class DefaultLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    @Value(value = "${security.targetUrlParameter}")
    private String targetUrlParameter;

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public DefaultLoginUrlAuthenticationEntryPoint(final String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    protected String determineUrlToUseForThisRequest(final HttpServletRequest request,
                                                     final HttpServletResponse response,
                                                     final AuthenticationException exception) {
        final String determineUrlToUseForThisRequest = super
                .determineUrlToUseForThisRequest(request, response, exception);
        final StringBuilder sb = new StringBuilder();
        sb.append(determineUrlToUseForThisRequest);
        if (!determineUrlToUseForThisRequest.contains("?")) {
            sb.append("?");
        } else {
            sb.append("&");
        }
        // FIXME 需要拼接真实的绝对路径
        return sb.append(targetUrlParameter).append(request.getRequestURI()).toString();
    }
}
