/**
 * @Type com.swjt.community.config
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  21:06
 * @VERSION Version 1.0
 **/

package com.swjt.community.config;

import com.swjt.community.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableWebSecurity
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;


    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private static final String[] URL_WHITELIST = {

            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",
            "/swagger-ui.html",
            "/webjars/**",
            "/v2/**",
            "/swagger-resources/**",
            "/user/addUser",
            "/category/list",
            "/category/categoryInfo/{id}",
            "/upload",
            "/downloadPhoto/{id}",
            "/downloadVideo/{id}",
            "/article/articleInfo/{id}",
            "/article/listByDate",
            "/article/listByCategory/{categoryId}",
            "/article/add",
            "/user/fansUserList"

    };
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                // 登录配置
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)

                .and()
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler)

                // 禁用session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()

                // 异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 配置自定义的过滤器
                .and()
                .addFilter(jwtAuthenticationFilter())
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)

        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
