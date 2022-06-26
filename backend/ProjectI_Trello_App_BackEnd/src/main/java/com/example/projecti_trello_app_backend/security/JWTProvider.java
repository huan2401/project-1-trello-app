package com.example.projecti_trello_app_backend.security;

import com.example.projecti_trello_app_backend.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTProvider {

    // generate jwt
    public String generateToken(CustomUserDetails userDetails)
    {
        Date now = new Date(System.currentTimeMillis());
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",userDetails.getUsername());
        claims.put("userid",String.valueOf(userDetails.getUser().getUserId()));
        claims.put("email",userDetails.getUserEmail());
        String jwt = Jwts.builder().setSubject(userDetails.getUsername())
                                    .setClaims(claims)
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
            return claims.get("username").toString();
        } catch (Exception ex) {
            log.error("get username from jwt error :", ex);
            return null;
        }
    }

    public Integer getUserIdFromJwt(String token){
        try{
            Claims claims = getClaims(token);
            return (Integer) claims.get("userid");
        }catch (Exception ex)
        {
            log.error("get user id from jwt error:",ex);
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
            Claims claims = getClaims(token);
            return isExpired(token)?false:true;
        } catch (Exception ex)
        {
            log.error("validate jwt error",ex);
            return false;
        }
    }
}
