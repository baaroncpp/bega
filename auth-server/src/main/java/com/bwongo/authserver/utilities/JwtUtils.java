package com.bwongo.authserver.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.function.Function;


/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/21/23
 **/
public class JwtUtils {

    @Value("${app.secret-key}")
    private String secretKey;
    private JwtUtils() { }

    public String extractUsername(String token){
        return null;
    }

    public <T> extractClaim(Function<Claims, T> claimsResolver){

    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
