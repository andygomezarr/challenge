package com.challenge.security;

import static com.challenge.security.Constants.LOGIN_URL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter{

    private UserDetailsService userDetailsService;

    public WebSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // arma la segurirad para los filtros
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        try {
            httpSecurity
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .cors().and()
                    .csrf().disable()
                    .authorizeRequests().antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
                    .anyRequest().authenticated().and()
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new JWTAuthorizationFilter(authenticationManager()));
        } catch (Exception exception) {
            throw exception;
        }
    }

    // setea el encoder para los password
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        try {
            auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        } catch (Exception exception) {
            throw exception;
        }
    }

    // configura las cors
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        try {
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
            return source;
        } catch (Exception exception) {
            throw exception;
        }
    }
}
