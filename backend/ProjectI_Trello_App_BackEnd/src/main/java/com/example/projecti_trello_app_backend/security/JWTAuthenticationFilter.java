package com.example.projecti_trello_app_backend.security;

import com.auth0.jwt.JWT;
import com.example.projecti_trello_app_backend.constants.SecurityConstants;
import com.example.projecti_trello_app_backend.serviceImpls.user.UserServiceImpl;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.sun.istack.NotNull;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* JWT Custom Authentication Filter
*/
@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  CustomUserDetailService userDetailsService;

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String token = getTokenFromRequest(request);
            if(StringUtils.hasText(token) && jwtProvider.validateToken(token))
            {
                String userName = jwtProvider.getUserNameFromJwt(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if(userDetails!=null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch (Exception ex)
        {
         ex.printStackTrace();
         log.error("Unable authenticated for user");
        }
       filterChain.doFilter(request,response);
    }

    /*
    * Get JWT from request, token has type
    */
    private String getTokenFromRequest(HttpServletRequest request)
    {
        String authHeader = request.getHeader(SecurityConstants.AUTH_HEADER);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith(SecurityConstants.TOKEN_TYPE))
            return authHeader.substring(7);
        return null;
    }


}
