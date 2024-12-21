package com.tarena.lbs.basic.web.filters;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import javafx.scene.canvas.GraphicsContext;
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
    private static final String AUTH_HEADER = "Authorization";
    @Autowired
    private JwtEncoder<UserPrinciple> jwtEncoder;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("MyFilter01.doFilter()");
        UserPrinciple userPrinciple = null;
        // 1. check if the servletRequest is an HTTP servletRequest
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            // get the request Authorization header
            String jwt = request.getHeader(AUTH_HEADER);
            // 2. check if the jwt is not null
            if (StringUtils.isNotBlank((jwt))) {
                // 3. try decode
                try {
                    userPrinciple = jwtEncoder.getLoginFromToken(jwt, UserPrinciple.class);
                    // TODO 4. pass the value to the controller
                    AuthenticationContextUtils.setPrinciple(userPrinciple);
                } catch (BusinessException e) {
                    log.error("failed to decode jwt token", e);
                }
            }
        }
        AuthenticationContextUtils.setPrinciple(userPrinciple);
        chain.doFilter(servletRequest, response);
        //before sending response back
        AuthenticationContextUtils.clearPrinciple();
    }
}
