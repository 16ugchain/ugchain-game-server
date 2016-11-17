package com.fiveonechain.digitasset.config;

import com.fiveonechain.digitasset.auth.JwtAuthenticationFilter;
import com.fiveonechain.digitasset.auth.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by yuanshichao on 2016/11/7.
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/auth/**")
        ;
        web.ignoring()
                .antMatchers("/user/**")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationEntryPoint entryPoint = new Http401AuthenticationEntryPoint("CHC");

        http
                .authorizeRequests().antMatchers("/admin/**").hasRole("USER")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), entryPoint), BasicAuthenticationFilter.class)
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new JwtAuthenticationProvider(jwtConfig.getTokenSigningKey()));
    }

}
