package me.iblur.study.security.config;

import me.iblur.study.security.authentication.*;
import me.iblur.study.security.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Arrays;

/**
 * @author 秦欣
 * @since 2017年06月22日 8:44.
 */
@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityServiceImpl securityService;

    @Autowired
    public WebSecurityConfig(final SecurityServiceImpl securityService) {
        this.securityService = securityService;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/manage**", "/manage/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .permitAll();
        http.sessionManagement()
                .sessionCreationPolicy
                        (SessionCreationPolicy.IF_REQUIRED).sessionFixation().migrateSession()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false).expiredUrl("/expire").sessionRegistry(sessionRegistry());
        http.csrf().disable();
        http.rememberMe().alwaysRemember(true).tokenValiditySeconds(7 * 24 * 60 * 60).rememberMeCookieName("pwid")
                .tokenRepository(new InMemoryTokenRepositoryImpl());
        //http.addFilterBefore(captchaAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)),
        //        UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(
                roleAuthenticationFilterSecurityInterceptor(http.getSharedObject(AuthenticationManager.class)),
                FilterSecurityInterceptor.class);
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        final PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(
                "pwm_token",
                userDetailsService(), persistentTokenRepository());
        rememberMeServices.setTokenValiditySeconds(30 * 24 * 3600);
        rememberMeServices.setUseSecureCookie(true);
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setAuthoritiesMapper(grantedAuthoritiesMapper());
        rememberMeServices.setCookieDomain("localhost");
        rememberMeServices.setCookieName("r_t");
        return rememberMeServices;
    }

    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        return new SimpleAuthorityMapper();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider()).authenticationEventPublisher
                (authenticationEventPublisher());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    public CaptchaAuthenticationFilter captchaAuthenticationFilter(final AuthenticationManager authenticationManager) {
        final CaptchaAuthenticationFilter captchaAuthenticationFilter = new CaptchaAuthenticationFilter();
        captchaAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return captchaAuthenticationFilter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DefaultAuthenticationProvider authenticationProvider = new DefaultAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setAuthoritiesMapper(grantedAuthoritiesMapper());
        return authenticationProvider;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService(securityService);
    }

    @Bean
    public RoleAuthenticationFilterSecurityInterceptor roleAuthenticationFilterSecurityInterceptor(
            final AuthenticationManager authenticationManager) {
        final RoleAuthenticationFilterSecurityInterceptor roleAuthenticationFilterSecurityInterceptor = new
                RoleAuthenticationFilterSecurityInterceptor();
        roleAuthenticationFilterSecurityInterceptor.setRejectPublicInvocations(false);
        roleAuthenticationFilterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
        roleAuthenticationFilterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource());
        roleAuthenticationFilterSecurityInterceptor.setPublishAuthorizationSuccess(true);
        roleAuthenticationFilterSecurityInterceptor.setAuthenticationManager(authenticationManager);
        return roleAuthenticationFilterSecurityInterceptor;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        final AffirmativeBased affirmativeBased = new AffirmativeBased(Arrays.asList(new RoleVoter(), new
                AuthenticatedVoter()));
        affirmativeBased.setAllowIfAllAbstainDecisions(false);
        return affirmativeBased;
    }

    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        final DefaultFilterInvocationSecurityMetadataSource securityMetadataSource = new
                DefaultFilterInvocationSecurityMetadataSource();
        securityMetadataSource.setSecurityService(securityService);
        return securityMetadataSource;
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }

}

