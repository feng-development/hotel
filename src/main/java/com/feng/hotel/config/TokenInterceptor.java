package com.feng.hotel.config;

import com.feng.hotel.base.Constants;
import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.common.enums.BizCodeEnum;
import com.feng.hotel.utils.JwtBean;
import com.feng.hotel.utils.JwtUtils;
import com.feng.hotel.utils.RequestContextUtils;
import com.google.common.base.Splitter;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Administrator
 * @since 2021/8/6
 */
public class TokenInterceptor implements HandlerInterceptor {
    /**
     * JWT分隔符
     */
    protected static final Splitter JWT_SPLITTER = Splitter.on(Constants.DOT);


    /**
     * JWT长度
     */
    protected static final Integer JWT_LENGTH = 3;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new BizException(BizCodeEnum.TOKEN_NOT_EXISTED_ERROR);
        }
        List<String> split = JWT_SPLITTER.splitToList(token);
        if (split.size() != JWT_LENGTH) {
            throw new BizException(BizCodeEnum.TOKEN_NOT_EXISTED_ERROR);
        }
        JwtBean jwtBean = JwtUtils.generateJwt(split.get(1));

        Long expiresSecond = jwtBean.getTimestamp();
        if (expiresSecond == null) {
            throw new BizException(BizCodeEnum.TOKEN_NOT_EXISTED_ERROR);
        }
        if (System.currentTimeMillis() > expiresSecond) {
            throw new BizException(BizCodeEnum.TOKEN_EXPIRED_ERROR);
        }
        String strJwt = JwtUtils.generateToken(jwtBean);
        String strSign = JWT_SPLITTER.splitToList(strJwt).get(2);
        if (!Objects.equals(split.get(2), strSign)) {
            throw new BizException(BizCodeEnum.TOKEN_NOT_EXISTED_ERROR);
        }
        RequestContextUtils.setRequest(request);
        return true;
    }
}
