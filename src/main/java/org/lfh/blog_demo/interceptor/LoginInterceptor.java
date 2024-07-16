package org.lfh.blog_demo.interceptor;

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

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        Map<String, Integer> map = JWTUtil.verifyJWT(token);
        if (map != null && map.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        Integer userID = map.get("userID");
        UserHolder.setUser(userID);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }

}
