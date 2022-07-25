package com.example.projecti_trello_app_backend.security.jwt;

import com.example.projecti_trello_app_backend.constants.SecurityConstants;
import com.example.projecti_trello_app_backend.entities.user.User;
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
    public String generateAccessToken(User user)
    {
        Date now = new Date(System.currentTimeMillis());
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",user.getUserName());
        claims.put("userid",String.valueOf(user.getUserId()));
        claims.put("email",user.getEmail());
        String jwt = Jwts.builder().setSubject(user.getUserName())
                                    .setClaims(claims)
                                    .setIssuedAt(now)
                                    .setExpiration(new Date (now.getTime() +SecurityConstants.ACCESS_TOKEN_EXPIRATION_TIME))
                                    .signWith(SignatureAlgorithm.HS256,SecurityConstants.SECURITY_KEY)
                                    .compact();
        return jwt;
    }

    //get claims
    public Claims getClaims (String token)
    {
        try {
            return Jwts.parser().setSigningKey(SecurityConstants.SECURITY_KEY).parseClaimsJws(token).getBody();
        }catch (Exception ex)
        {
            log.error("parse claims error",ex);
            return null;
        }
    }

    // get userName from jwt
    public String getUserNameOrEmailFromJwt(String token)
    {
        try{
            Claims claims = getClaims(token);
            String loginAcc = claims.get("username").toString()!=null
                    ?claims.get("username").toString()
                    :claims.get("email").toString();
            return loginAcc;
        } catch (Exception ex) {
            log.error("get username from jwt error :", ex);
            return null;
        }
    }

    // get user from jwt
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

    //validate jwt
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
