package com.feng.hotel.manager.impl;

import com.feng.hotel.common.enums.HotelEnum;
import com.feng.hotel.domain.User;
import com.feng.hotel.manager.IUserManager;
import com.feng.hotel.request.AuthRequest;
import com.feng.hotel.service.IUserService;
import com.feng.hotel.utils.Assert;
import com.feng.hotel.utils.EncryptUtils;
import com.feng.hotel.utils.JwtBean;
import com.feng.hotel.utils.JwtUtils;
import com.feng.hotel.utils.date.DateUtils;
import com.feng.hotel.utils.json.JsonUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @since 2021/8/6
 */
@Service
public class IUserManagerImpl implements IUserManager {

    private final IUserService userService;

    public IUserManagerImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String auth(AuthRequest authRequest) {

        //获取用户
        User user = userService.getByLoginName(authRequest.getLoginName());
        Assert.assertNotNull(user, HotelEnum.AUTH_ERROR);

        //校验密码
        String pwd = EncryptUtils.md5(authRequest.getPwd().concat(user.getSalt()));
        Assert.assertNotBlank(pwd, user.getPwd());

        //成成token
        JwtBean jwtBean = new JwtBean();
        jwtBean.setUserNo(user.getId());
        jwtBean.setTimestamp(DateUtils.atEndOfDay(new Date()).getTime());
        jwtBean.setClient(authRequest.getClient());

        String jwtBeanStr = JsonUtils.serialize(jwtBean);
        jwtBean.setBase64Secret(Base64.getEncoder().encodeToString(jwtBeanStr.getBytes()));

        return JwtUtils.generateToken(jwtBean);
    }

    public static void main(String[] args) {
        JwtBean jwtBean = new JwtBean();
        jwtBean.setClient("pc");
        jwtBean.setUserNo(123L);
        long l = System.currentTimeMillis();
        System.out.println(l);
        jwtBean.setTimestamp(l);

        String jwtBeanStr = JsonUtils.serialize(jwtBean);
        jwtBean.setBase64Secret(Base64.getEncoder().encodeToString(jwtBeanStr.getBytes()));
        // 生成token
        String token = JwtUtils.generateToken(jwtBean);
        System.out.println(token);
        token = "eyJiYXNlNjRTZWNyZXQiOiJleUpqYkdsbGJuUWlPaUp3WXlJc0luUnBiV1Z6ZEdGdGNDSTZNVFl5T0RJeU1UazBOelkwTkN3aWRYTmxjazV2SWpveE1qTjkiLCJjbGllbnQiOiJwYyIsInRpbWVTdGFtcCI6MTYyODIyMTk0NzY0NCwidXNlcklkIjoxMjN9";
        JwtBean jwtBean1 = JwtUtils.generateJwt(token);
        System.out.println(JsonUtils.serialize(jwtBean1));

    }


}


