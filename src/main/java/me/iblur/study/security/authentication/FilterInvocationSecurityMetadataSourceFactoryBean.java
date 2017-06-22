package me.iblur.study.security.authentication;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.Jsr250SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 秦欣
 * @since 2017年06月22日 14:41.
 */
public class FilterInvocationSecurityMetadataSourceFactoryBean implements
        FactoryBean<FilterInvocationSecurityMetadataSource> {

    @Override
    public FilterInvocationSecurityMetadataSource getObject() throws Exception {
        return new DefaultFilterInvocationSecurityMetadataSource(buildRequestMap());
    }

    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() {
        final LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
        requestMap
                .put(new AntPathRequestMatcher("/manage**"), Stream.of(new Jsr250SecurityConfig("ROLE_ADMIN")).collect(
                        Collectors.toList()));
        return requestMap;
    }

    @Override
    public Class<?> getObjectType() {
        return DefaultFilterInvocationSecurityMetadataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
