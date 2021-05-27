package io.oicp.yorick61c.interceptor;

import io.jsonwebtoken.Claims;
import io.oicp.yorick61c.utils.JwtUtil;
import io.oicp.yorick61c.utils.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestURI = request.getRequestURI();

        //登录请求直接放行
        if ("/user/login".equals(requestURI) || "/user/register".equals(requestURI)) {
            return true;
        }

        //字符串名称与前端的config.Headers['Authorization'] = getToken()保持一致
        Claims claims = JwtUtil.parse(request.getHeader("Authorization"));

        //登录过就存储用户名并放行
        if (claims != null) {
            // 将我们之前放到token中的username给存到上下文对象中
            UserContext.add(claims.getSubject());
            return true;
        }

        System.out.println("该请求已被拦截");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //请求结束后删除信息，否则可能造成内存泄漏
        UserContext.remove();
    }
}
