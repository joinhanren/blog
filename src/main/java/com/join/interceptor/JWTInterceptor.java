package com.join.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.join.entity.Result;
import com.join.utils.JWTUtil;
import com.join.utils.ThreadLocalUser;
import com.join.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author join
 * @Description  JWT 验证拦截器
 * @date 2022/8/29 21:30
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截是否为controller 请求，不是controller 请求就放行，（可能为静态资源的请求）
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("Authorization");
        /**
         * 判断token是否为空
         */
        if ("".equals(token)){
            Result result = Result.fail(400, "未登录,kong");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(result);
            return false;
        }

        /**
         * 验证token是否正确
         */
        if (JWTUtil.verification(token)==null){
            Result result = Result.fail(400, "未登录,buzhengque");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(result);
            return false;
        }

        /**
         *  验证token在redis中是否存在，是否登录过期
         */
        System.out.println(redisTemplate);
        System.out.println(redisTemplate.opsForValue().get("TOKEN_" + token));
        if (redisTemplate.opsForValue().get("TOKEN_" + token)==null){
            Result result = Result.fail(400, "登录已过期，请重新登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(result);
            return false;
        }
        /**
         * 验证token通过，重新设置redis中token过期时间，给予放行
         */
        redisTemplate.expire("TOKEN_" + token,30, TimeUnit.MINUTES);
        String s = redisTemplate.opsForValue().get("TOKEN_" + token);
        UserVo userVo = JSON.parseObject(s, UserVo.class);
        ThreadLocalUser.put(userVo);
        System.out.println("线程名=============>"+Thread.currentThread().getName());
        return true;
    }

    /**
     * 响应结束
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    /**
     *  用完不删除会存在内存泄漏的风险
     */
       ThreadLocalUser.remove();
    }
}
