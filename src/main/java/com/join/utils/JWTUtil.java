package com.join.utils;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.HashMap;
import java.util.Map;

/**
 * @author join
 * @Description
 * @date 2022/8/25 21:42
 */
public class JWTUtil {
    //密钥
    private static final String SIGN="join";

    /**
     * 创建token
     * @param userId
     * @return
     */
    public static String createToken(Long userId){
//        WT由3部分组成：标头(Header)、有效载荷(Payload)和签名(Signature)。在传输的时候，
//        会将JWT的3部分分别进行Base64编码后用.进行连接形成最终传输的字符串
        Map<String,Object> map=new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");
        JWTCreator.Builder builder = JWT.create();
        builder.withHeader(map);
        builder.withClaim("userId",userId);
//        builder.withIssuedAt(new Date(System.currentTimeMillis()));//设置创建时间
//        builder.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60));//设置过期时间
        String token = builder.sign(Algorithm.HMAC256(SIGN));
        return token;
    }



    /**
       验证token
     * @param token
     */

    public static DecodedJWT verification(String token){
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    /**
     * 执行验证，返回验证后的结果
     * @return
     */
    public static boolean exeVerification(String token){
        try {
            JWTUtil.verification(token);
        }catch (Exception e){
            return  false;
        }
        return true;
    }

}
