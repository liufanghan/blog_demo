package org.lfh.blog_demo.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.lfh.blog_demo.util.JWTUtil;
import org.lfh.blog_demo.util.UserHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    //    @Resource
//    private StringRedisTemplate stringRedisTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //1.获取token
//        String token = request.getHeader("Authorization");
//        if (token == null || token.isEmpty()) {
//            response.setStatus(401);
//            return false;
//        }
//        //2.基于token获取redis中的用户
//        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(RedisConstants.LOGIN_CODE_KEY + token);
//        //3.判断用户是否存在
//        //4.不存在，拦截，返回401
//        if (map.isEmpty()) {
//            response.setStatus(401);
//            return false;
//        }
//        //5.将查询到的Hash数据转换为VO对象
//        User user = objectMapper.convertValue(map, User.class);
//        //4.若存在，保存到ThreadLocal中
//        UserHolder.setUser(user);
//        //5.刷新token有效期
//        stringRedisTemplate.expire(RedisConstants.LOGIN_CODE_KEY + token, JWTUtil.getExpiration(), TimeUnit.SECONDS);
//        //5.放行
//        return true;
//    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        UserHolder.removeUser();
//    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("请求地址:{}", uri);
        String method = request.getMethod();
        // 根据请求方法和URI进行处理
        if (uri.startsWith("/api/posts/") && "GET".equalsIgnoreCase(method)) {
            // 对于GET请求并且是/api/posts/，不进行拦截
            return true;
        }

        //1.获取token
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }
//        //2.基于token获取redis中的用户
//        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(RedisConstants.LOGIN_CODE_KEY + token);
//        //3.判断用户是否存在
//        //4.不存在，拦截，返回401
//        if (map.isEmpty()) {
//            response.setStatus(401);
//            return false;
//        }
//        //5.将查询到的Hash数据转换为VO对象
//        User user = objectMapper.convertValue(map, User.class);
        Map<String, Integer> map = JWTUtil.verifyJWT(token);
        if (map != null && map.isEmpty()) {
            response.setStatus(401);
            return false;
        }
//        User user = objectMapper.convertValue(map, User.class);
        Integer userID = map.get("userID");
        //4.若存在，保存到ThreadLocal中
        UserHolder.setUser(userID);

//        5.刷新token有效期
//        stringRedisTemplate.expire(RedisConstants.LOGIN_CODE_KEY + token, JWTUtil.getExpiration(), TimeUnit.SECONDS);
        //5.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }

}
