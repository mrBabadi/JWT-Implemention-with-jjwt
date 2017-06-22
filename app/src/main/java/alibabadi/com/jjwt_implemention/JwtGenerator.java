package alibabadi.com.jjwt_implemention;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Ali Babadi
 */

public class JwtGenerator {

    String JWT_KEY = "this-is-a-sample";

    public JwtGenerator() {
    }

    /**
     * @param header  jwt type like HS256
     * @param claims jwt payloads(data)
     * @return jwt String
     * @throws UnsupportedEncodingException
     */

    String jwtCompact(Map<String, Object> header, Map<String, Object> claims) throws UnsupportedEncodingException {
        byte[] data = JWT_KEY.getBytes("UTF-8");
//        String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);

        return Jwts.builder()
                .setHeader(header)
                .signWith(SignatureAlgorithm.HS256, data)
                .setClaims(claims)
                .compact();
    }

    String jwtParser(String jwtString) {
        try {
            byte[] data = JWT_KEY.getBytes("UTF-8");
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(data).parseClaimsJws(jwtString);
            Claims body = claimsJws.getBody();

            return "UserName : " + body.get("username") + "\n"
                    + "Password : " + body.get("password") + "\n"
                    + "Time : " + body.get("time");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

    }

}
