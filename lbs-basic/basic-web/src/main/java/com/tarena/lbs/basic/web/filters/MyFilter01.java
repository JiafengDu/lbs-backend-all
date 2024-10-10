package com.tarena.lbs.basic.web.filters;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class MyFilter01 implements Filter {
    /**
     * 过滤器核心逻辑
     * @param servletRequest 请求对象
     * @param servletResponse 响应对象
     * @param filterChain 连接过滤器和后续流程的 重要对象
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("你好");
        //通过链对象(管子)向后流转请求
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
