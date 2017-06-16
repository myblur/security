package me.iblur.security.authentication;

import java.util.List;

/**
 * @author 秦欣
 * @since 2017年06月16日 10:44.
 */
public interface SecurityMetadataSourceService {

    List<UrlAuthority> getAllUrlAuthorities();

    void evictAllUrlAuthoritiesCache();

}
