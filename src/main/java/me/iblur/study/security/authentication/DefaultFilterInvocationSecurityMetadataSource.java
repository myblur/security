package me.iblur.study.security.authentication;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.Jsr250SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 秦欣
 * @since 2017年06月23日 9:13.
 */
public class DefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource,
        InitializingBean {

    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    private SecurityService securityService;

    private String rolePrefix = "ROLE_";

    // ~ Methods
    // ========================================================================================================

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        final Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (final Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    public Collection<ConfigAttribute> getAttributes(final Object object) {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (final Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean supports(final Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void setSecurityService(final SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.securityService, "A SecurityService must be set");
        this.requestMap = buildRequestMap();
    }

    private Map<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() throws Exception {
        final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
        final List<UrlAuthority> urlAuthorityList = securityService.loadAllAuthority();
        if (!CollectionUtils.isEmpty(urlAuthorityList)) {
            for (final UrlAuthority urlAuthority : urlAuthorityList) {
                final Jsr250SecurityConfig securityConfig = new Jsr250SecurityConfig(
                        rolePrefix + urlAuthority.getRoleName());
                buildIpRequestMatcher(requestMap, urlAuthority, securityConfig);
                buildPathRequestMatcher(requestMap, urlAuthority, securityConfig);
            }
        }
        return requestMap;
    }

    private void buildIpRequestMatcher(final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap,
                                       final UrlAuthority urlAuthority,
                                       final Jsr250SecurityConfig securityConfig) {
        if (StringUtils.hasLength(urlAuthority.getIpAddress())) {
            addConfigAttribute(requestMap, securityConfig, new IpAddressMatcher(urlAuthority.getIpAddress()));
        }
    }

    private void buildPathRequestMatcher(final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap,
                                         final UrlAuthority urlAuthority,
                                         final Jsr250SecurityConfig securityConfig) {
        final String method = urlAuthority.getMethod();

        final String url = urlAuthority.getUrl();
        addConfigAttribute(requestMap, securityConfig, new AntPathRequestMatcher(url));
        if (url.indexOf(46) == -1 && !url.endsWith("/")) {
            addConfigAttribute(requestMap, securityConfig, new AntPathRequestMatcher(url + "" +
                    ".", method));
            addConfigAttribute(requestMap, securityConfig, new AntPathRequestMatcher(url + "" +
                    "/", method));
        }
        if (url.endsWith(".*")) {
            addConfigAttribute(requestMap, securityConfig, new RegexRequestMatcher(url, method));
        }
    }

    private void addConfigAttribute(final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap,
                                    final Jsr250SecurityConfig securityConfig,
                                    final RequestMatcher requestMatcher) {
        final Collection<ConfigAttribute> configAttributes = requestMap.get(requestMatcher);
        if (CollectionUtils.isEmpty(configAttributes)) {
            final LinkedList<ConfigAttribute> configAttributeList = new LinkedList<>();
            configAttributeList.add(securityConfig);
            requestMap.put(requestMatcher, configAttributeList);
        } else {
            configAttributes.add(securityConfig);
        }
    }

    public void setRolePrefix(final String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }
}
