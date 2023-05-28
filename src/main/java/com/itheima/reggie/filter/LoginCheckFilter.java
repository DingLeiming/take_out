package com.itheima.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PatchMapping;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
* 检查用户登录是否完成
* */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取本次请求URI
        String requestURI = request.getRequestURI();
        // 判断是否要处理
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };

        boolean check = check(urls,requestURI);

        if(check){
            filterChain.doFilter(request,response);
            return;
        }

        if(request.getSession().getAttribute("employee")!=null){

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//        return;

    }
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = pathMatcher.match(url,requestURI);
            if(match){
                return true;
            }

        }
        return false;
    }


}
