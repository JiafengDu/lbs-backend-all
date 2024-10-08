package com.tarena.lbs.common.passport.encoder;

import com.alibaba.fastjson2.JSON;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.common.passport.enums.PassportResultEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
@Slf4j
public class JwtEncoder<T> {
    private String secret;
    private static final String CLAIM_PAYLOAD = "payload";
    public String generateToken(T payload) throws BusinessException {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_PAYLOAD, JSON.toJSONString(payload));
        JwtBuilder builder = null;
        try{
            builder = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512,secret.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            log.error("failed to create jwt builders,error message:{}",e.getMessage());
            throw new BusinessException(PassportResultEnum.TOKEN_GENERATE_FAILED);
        }
        return builder.compact();
    }
    public T getLoginFromToken(String token, Class<? extends T> clazz) throws BusinessException {
        Jws<Claims> jws = null;
        if (StringUtils.isEmpty(token)){
            return null;
        }
        try {
            jws = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
        } catch(Exception e){
            log.error("jwt parse error:{}",e.getMessage());
            throw new BusinessException(PassportResultEnum.JWT_PARSE_ERROR);
        }
        Claims claims = (Claims)jws.getBody();
        String loginTokenJson = (String)claims.get(CLAIM_PAYLOAD);
        return JSON.parseObject(loginTokenJson, clazz);
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
