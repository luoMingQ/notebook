package com.lsc.notebook.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: luosc
 * @Description:
 * @Date:created in 22:55 2020/3/30
 */
public class TokenProccessor {
    private TokenProccessor(){}
    private static final TokenProccessor INSTANCE = new TokenProccessor();

    public static TokenProccessor getInstance() {
        return INSTANCE;
    }
    //密钥
    public static final String SECRET = "sdjhakdhajdklsl;o653632";

    /**
     * 过期时间1小时
     */
    private static final int EXPIRE_TIME = 1000 * 60*60;

    /**
     * 生成Token
     * @return
     */
    public String makeToken(String userId,String userName) throws Exception {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, EXPIRE_TIME);
        Date expireDate = nowTime.getTime();
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("userId", userId)
                .withClaim("userName", userName)
                .withSubject("测试")//
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }

    /**
     * 验证Token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token)throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        }catch (Exception e){
            throw new RuntimeException("凭证已过期，请重新登录");
        }
        return jwt.getClaims();
    }

    /**
     * 解析Token
     * @param token
     * @return MAP
     */
    public static Map<String, Claim> parseToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }
}
