package io.oicp.yorick61c.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;

public final class JwtUtil {

    //密钥
    private final static String secretKey = "yor1ck61c";

    //过期时间
    private final static Duration expiration = Duration.ofHours(2);


    /*
    * @param username 用户名
    * @return JwtToken
    * */
    public static String generateToken(String username) {

        //过期时间
        Date expiryDate = new Date(System.currentTimeMillis() + expiration.toMillis());

        return Jwts.builder()
                .setSubject(username) // 将userName放进JWT
                .setIssuedAt(new Date()) // 设置JWT签发时间
                .setExpiration(expiryDate)  // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, secretKey) // 设置加密算法和秘钥
                .compact();

    }

    public static Claims parse(String token) {
        // 如果是空字符串直接返回null
        if (token == null) {
            return null;
        }

        // 这个Claims对象包含了许多属性，比如签发时间、过期时间以及存放的数据等
        Claims claims = null;
        // 解析失败了会抛出异常，所以我们要捕捉一下。token过期、token非法都会导致解析失败
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey) // 设置秘钥
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            // 这里应该用日志输出
            System.err.println("解析失败！");
        }
        return claims;
    }

}
