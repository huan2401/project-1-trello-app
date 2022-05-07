package com.example.projecti_trello_app_backend.security;

import com.example.projecti_trello_app_backend.security.SecurityConstants;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JWTProvider {

    @Autowired
    private UserRepo userRepo;


    // generate jwt
    public String generateToken(String userName)
    {
        Date now = new Date(System.currentTimeMillis());
        String jwt = Jwts.builder().setSubject(userName)
                                    .setIssuedAt(now)
                                    .setExpiration(new Date (now.getTime() +SecurityConstants.EXPIRE_TIME))
                                    .signWith(SignatureAlgorithm.HS256,SecurityConstants.SECURITY_KEY)
                                    .compact();
        return jwt;
    }

    //get claims
    public Claims getClaims (String token)
    {
        return Jwts.parser().setSigningKey(SecurityConstants.SECURITY_KEY).parseClaimsJws(token).getBody();
    }

    // get userName from jwt
    public String getUserNameFromJwt(String token)
    {
        try{
            Claims claims = getClaims(token);
            return claims.getSubject();
        } catch (Exception ex) {
            log.error("error :", ex);
            return null;
        }
    }

    // check if jwt was expired
    public boolean isExpired(String token){
        try{
          Claims claims =getClaims(token);
          Date now = new Date(System.currentTimeMillis());
          return claims.getExpiration().after(now)?false:true;
        } catch (Exception ex)
        {
            log.error("check expired ? jwt error :",ex);
            return false;
        }
    }

    //validate token
    public boolean validateToken(String token)
    {
        try {
            Claims claims =getClaims(token);
            return isExpired(token)?false:true;
        } catch (Exception ex)
        {
            log.error("validate jwt error",ex);
            return false;
        }
    }





}
