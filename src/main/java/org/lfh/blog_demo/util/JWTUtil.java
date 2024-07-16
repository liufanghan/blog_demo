package org.lfh.blog_demo.util;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    private static final String SECRET_KEY = "123456";
    @Getter
    private static final Integer Expiration = 1000 * 60 * 60;   //有效期1h

    public static String createJWT(Integer userID) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userID", userID);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setClaims(claims).setIssuedAt(new Date()) //设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + Expiration)); //有效时间，这里设置的为一天
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Integer> verifyJWT(String token) {
        try {
            Jwt parse = Jwts.parser().setSigningKey(SECRET_KEY).parse(token);
            return (Map<String, Integer>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        String jwt = JWTUtil.createJWT(1);
//        System.out.println(jwt);
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjEwNTExMzAsInVzZXJJRCI6MSwiaWF0IjoxNzIxMDQ3NTMwfQ.BQkzPz4apZhgNrlsJkv6PixjGdHWTwybbKehPS7ZQ10";
        Map<String, Integer> map = JWTUtil.verifyJWT(jwt);
        System.out.println(map.get("userID"));
    }
}
