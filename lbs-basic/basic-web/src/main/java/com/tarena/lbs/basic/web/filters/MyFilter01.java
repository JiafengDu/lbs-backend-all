package com.tarena.lbs.basic.web.filters;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
@Component
public class MyFilter01 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("MyFilter01.doFilter()");
        chain.doFilter(request, response);
    }
}
