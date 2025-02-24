package ru.spshop.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utilities parse the token into components and give an answer to 2 questions, whether the temporary
 * buffer of the token has expired and whether the token is valid at the moment.
 */
@Component
public class JwtUtil {
    //final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Whether the token is valid.
     *
     * @return
     */
    public boolean isInvalid(String token) {
        Claims claims;
        try {
            claims = getAllClaimsFromToken(token);
        }catch (Exception ignored){
            return true;
        }
        Date dateToken = claims.getExpiration();
        Date dateNow = new Date();
        boolean result = dateToken.before(dateNow);
        long resultDate = dateNow.getTime()-dateToken.getTime();
        //log.info("JwtUtil/isInvalid/ Date now - DateToken in sec: " + resultDate/1000);
        //log.info("JwtUtil/isInvalid/ result: " + result);
        return result;
    }

}
