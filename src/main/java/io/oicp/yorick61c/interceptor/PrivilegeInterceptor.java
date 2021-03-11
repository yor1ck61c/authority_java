package io.oicp.yorick61c.interceptor;

import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.utils.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PrivilegeInterceptor implements HandlerInterceptor {

    @Resource(description = "UserMapper")
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User userByUsername = userMapper.findUserByUsername(UserContext.getCurrentUserName());
        if (!userByUsername.getRole().equals("admin")){
            System.out.println("拦截成功");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
