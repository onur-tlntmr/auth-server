package com.example.springjwt.security;

import com.example.springjwt.exception.CustomAccessDeniedHandler;
import com.example.springjwt.exception.CustomAuthenticationExceptionHandler;
import com.example.springjwt.filter.CustomAuthenticationFilter;
import com.example.springjwt.filter.CustomAuthorizationFilter;
import com.example.springjwt.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthFilter =
                new CustomAuthenticationFilter(tokenService);

        customAuthFilter.setAuthenticationManager(authenticationManagerBean());

        http.formLogin().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers(POST, "/auth/**").permitAll();

        http.authorizeRequests().antMatchers(POST, "/users").permitAll();
        http.authorizeRequests().antMatchers(GET, "/users").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/users/*/").hasAuthority("ROLE_USER");


        http.authorizeRequests().antMatchers("/roles/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.exceptionHandling().authenticationEntryPoint(authenticationExceptionHandler());
        http.addFilterAt(customAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomAuthorizationFilter(tokenService),
                UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();

    }

    @Bean
    public CustomAuthenticationExceptionHandler authenticationExceptionHandler() {
        return new CustomAuthenticationExceptionHandler();
    }


}