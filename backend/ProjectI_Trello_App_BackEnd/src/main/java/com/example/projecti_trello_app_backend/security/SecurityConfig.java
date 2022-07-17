package com.example.projecti_trello_app_backend.security;

import com.example.projecti_trello_app_backend.security.entry_point.AuthEntryPoint;
import com.example.projecti_trello_app_backend.security.jwt.JWTAuthenticationFilter;
import com.example.projecti_trello_app_backend.security.user_detail.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AccessDenyExceptionHandler accessDenyHandler;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService) // provide userDetailService  for Spring Security
                .passwordEncoder(passwordEncoder()); // provide password encoder
    }

    // sercurity request config
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String [] listURLPermitAll= {"/**/api/auth/**",
                                    "/**/api/user/signup",
                                    "/**/api/user/verify/**",
                                    "/**/api/user/reset-password/**",
                                    "/**/swagger-ui/**",
                                    "/**/v3/api-docs/**"};
        http.csrf().disable();
                http
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().cors()
                        .and()
                    .authorizeRequests()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .antMatchers(listURLPermitAll)
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(authEntryPoint)
                        .and()
                        .exceptionHandling().accessDeniedHandler(accessDenyHandler);
                http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

    }


    // config cors to allow front-end call api use fetch or axios
    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        UrlBasedCorsConfigurationSource  source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
