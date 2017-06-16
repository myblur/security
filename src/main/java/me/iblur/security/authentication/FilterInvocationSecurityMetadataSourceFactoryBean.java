package me.iblur.security.authentication;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author 秦欣
 * @since 2017年06月16日 10:41.
 */
public class FilterInvocationSecurityMetadataSourceFactoryBean implements FactoryBean<FilterInvocationSecurityMetadataSource> {

    @Override
    public FilterInvocationSecurityMetadataSource getObject() throws Exception {
        return new DefaultFilterInvocationSecurityMetadataSource(this.buildRequestMap());
    }

    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() {
        return null;
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
