package com.tarena.lbs.common.security.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.common.security.utils.LbsSecurityContenxt;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
public class UserPrincipleFilter implements Filter {

    private JwtEncoder jwtEncoder;

    public UserPrincipleFilter() {
        this.jwtEncoder = new JwtEncoder();
        this.jwtEncoder.setSecret("tarena2024Up;");
    }

    private static final String AUTH_HEADER="Authorization";

    /**
     *业务逻辑
     *1 从头获取Authorization的值(拿到jwt或者拿不到)
     *2 如果拿到继续 解析程userPrinciple(解析成功或者解析失败)
     *3 如果解析成功 把userPrinciple 绑定到SecurityContext中
     *4 执行filter
     *5 执行结束然后清除SecurityContext的principle
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        UserPrinciple userPrinciple= null;
        //确定是http请求
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String jwt = req.getHeader(AUTH_HEADER);
            //确定jwt有值
            if (StringUtils.isNotEmpty(jwt)){
                log.info("get jwt from request; jwt:{}",jwt);
                try{
                    userPrinciple= (UserPrinciple) jwtEncoder.getLoginFromToken(jwt, UserPrinciple.class);
                }catch (Exception e){
                    log.error("parse jwt failed,reason:{}",e.getMessage());
                }
            }
            LbsSecurityContenxt.bindLoginToken(userPrinciple);
        }
        filterChain.doFilter(servletRequest, servletResponse);
        //处理一下 线程复用的问题
        LbsSecurityContenxt.clearLoginToken();
    }

    @Override public void destroy() {
        log.info(" loginUserFilter destroies");
    }
}

