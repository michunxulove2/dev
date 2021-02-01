package com.bxwl.admin.sys.security;

import com.bxwl.admin.sys.filter.ViewInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

@Configuration  
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired  
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CustomFilterSecurityInterceptor customFilterSecurityInterceptor;
    @Autowired
    private SessionRegistry sessionRegistry;
    private String rememberMeKey = "bxwl_manager";
    @Value("${logout_success_url}")
    public String logoutSuccessUrl;
    @Override
    protected void configure(HttpSecurity http) throws Exception {  
        http
        .formLogin().loginPage("/login").permitAll()
        .and()  
//        .logout().addLogoutHandler(new CustomLogoutHandler("remember-me")).logoutSuccessUrl("/login").permitAll().invalidateHttpSession(true)
                .logout().logoutSuccessUrl(logoutSuccessUrl).permitAll().invalidateHttpSession(true)
        .and()
        .authorizeRequests().anyRequest().authenticated()
        .and()
        .headers().frameOptions().sameOrigin()
        .and()
        .csrf().disable();
        TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices(rememberMeKey, customUserDetailsService);
        tokenBasedRememberMeServices.setTokenValiditySeconds(604800);//remember-me过期时间设置为7天
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(tokenBasedRememberMeServices,sessionRegistry);
        loginAuthenticationFilter.setAuthenticationManager(authenticationManager());
        loginAuthenticationFilter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        http.addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //session并发控制过滤器
        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry,sessionInformationExpiredStrategy()), ConcurrentSessionFilter.class);
//        RememberMeAuthenticationFilter rememberMeAuthenticationFilter = new CustomRememberMeAuthenticationFilter(authenticationManager(), tokenBasedRememberMeServices,sessionRegistry);
        //http.addFilterAfter(rememberMeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(customFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.addFilterBefore(new ViewInterceptor(), FilterSecurityInterceptor.class);
        //加入session过期过滤器
        http.addFilterBefore(new ExpiredSessionFilter(), BasicAuthenticationFilter.class);
       // http.sessionManagement().maximumSessions(1).expiredUrl("/logout").maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry);
    }

    //session失效跳转
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new SimpleRedirectSessionInformationExpiredStrategy("/login");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * 忽略静态文件
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/plugins/**","/js/**","/img/**","/verifyCode","/favicon.ico");
    }

    @Autowired  
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	authenticationProvider.setUserDetailsService(customUserDetailsService);
    	authenticationProvider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    	authenticationProvider.setHideUserNotFoundExceptions(false);
    	auth.authenticationProvider(authenticationProvider);
//    	RememberMeAuthenticationProvider rememberMeAuthenticationProvider = new RememberMeAuthenticationProvider(rememberMeKey);
//    	auth.authenticationProvider(rememberMeAuthenticationProvider);
    }  

}  