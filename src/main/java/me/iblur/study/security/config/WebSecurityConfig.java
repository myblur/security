package me.iblur.study.security.config;

import me.iblur.study.security.authentication.CaptchaAuthenticationFilter;
import me.iblur.study.security.authentication.DefaultAuthenticationProvider;
import me.iblur.study.security.authentication.DefaultUserDetailsService;
import me.iblur.study.security.authentication.FilterInvocationSecurityMetadataSourceFactoryBean;
import me.iblur.study.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

/**
 * @author 秦欣
 * @since 2017年06月22日 8:44.
 */
@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityService securityService;

    @Autowired
    public WebSecurityConfig(final SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/manage**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http.addFilterBefore(captchaAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(filterSecurityInterceptor(http.getSharedObject(AuthenticationManager.class)),
        FilterSecurityInterceptor.class);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public CaptchaAuthenticationFilter captchaAuthenticationFilter(final AuthenticationManager authenticationManager) {
        final CaptchaAuthenticationFilter captchaAuthenticationFilter = new CaptchaAuthenticationFilter();
        captchaAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return captchaAuthenticationFilter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DefaultAuthenticationProvider authenticationProvider = new DefaultAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService(securityService);
    }

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor(final AuthenticationManager authenticationManager) throws
            Exception {
        final FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setRejectPublicInvocations(false);
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
        filterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource());
        return filterSecurityInterceptor;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        final AffirmativeBased affirmativeBased = new AffirmativeBased(Arrays.asList(new RoleVoter(), new
                AuthenticatedVoter()));
        affirmativeBased.setAllowIfAllAbstainDecisions(false);
        return affirmativeBased;
    }

    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() throws Exception {
        return new FilterInvocationSecurityMetadataSourceFactoryBean().getObject();
    }

}

