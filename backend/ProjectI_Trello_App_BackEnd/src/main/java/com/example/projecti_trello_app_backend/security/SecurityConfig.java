package com.example.projecti_trello_app_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    // sercurity request config
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors();
        http.authorizeRequests().anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    // config cors to allow js call api use fetch or axios
    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        UrlBasedCorsConfigurationSource  source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
