package com.bnzy.filter;

import com.alibaba.fastjson.JSON;
import com.bnzy.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 是否已经登录的过滤器
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Component
@ConfigurationProperties(prefix = "test")
public class LoginCheckFilter implements Filter {


    private String[] filterURL;

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();//工具类

    public void setFilterURL(String[] filterURL) {
        this.filterURL = filterURL;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        log.error("过滤器");
//
//        //1.获取url
//        String requestURI = request.getRequestURI();
//
//        //2.判断本次请求是否需要处理，若不需要则直接放行
//        boolean check = check(filterURL, requestURI);
//        if (check) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //3.判断登录状态，如果已经登录则直接放行（后台）
//        if (request.getSession().getAttribute("id") != null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //4.如果未登录则返回未登录结果
//        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//        return;

        filterChain.doFilter(request, response);

    }

    /**
     * 进行路径匹配，查看是否直接放行还是。
     *
     * @param filterURL
     * @param requestURI
     * @return
     */
    private boolean check(String[] filterURL, String requestURI) {
        for (String s : filterURL) {
            boolean match = PATH_MATCHER.match(s, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }


}
