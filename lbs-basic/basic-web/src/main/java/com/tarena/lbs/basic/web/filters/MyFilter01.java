package com.tarena.lbs.basic.web.filters;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.param.UserParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class MyFilter01 implements Filter {
    @Autowired
    private JwtEncoder<UserPrinciple> jwtEncoder;
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
        //1.判断是否是http请求
        if (servletRequest instanceof HttpServletRequest){
            //转化
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            //2.获取头 Authorization
            String jwt = request.getHeader("Authorization");
            //判断空 "" null
            if (StringUtils.isNotBlank(jwt)){
                //3.try catch解析
                try{
                    UserPrinciple userPrinciple = jwtEncoder.getLoginFromToken(jwt, UserPrinciple.class);
                    //TODO 4.向后想办法传参
                    /*request.setAttribute("jwt",userPrinciple);*/
                }catch (BusinessException e){
                    //只打印日志
                    log.error("过滤器解析jwt失败",e);
                }
            }
        }
        //通过链对象(管子)向后流转请求
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
