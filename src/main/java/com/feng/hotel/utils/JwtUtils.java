package com.feng.hotel.utils;

import com.alibaba.fastjson.JSONObject;
import com.feng.hotel.base.Constants;
import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.common.enums.BizCodeEnum;
import com.google.common.base.Splitter;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * @author Administrator
 * @since 2021/8/6
 */
public class JwtUtils {
  private static final String BASE64_SECRET = "base64Secret";

  private static final String CLIENT = "client";

  private static final String TIMESTAMP = "timeStamp";

  private static final String USER_ID = "userId";

  /**
   * JWT Header: Type
   */
  protected static final String JWT_TYPE_KEY = "typ";

  /**
   * JWT Header: Type -> Value
   */
  protected static final String JWT_TYPE_VAL = "JWT";

  /**
   * JWT Header: Alg
   */
  protected static final String JWT_ALG_KEY = "alg";

  /**
   * JWT Header: Alg -> Value
   */
  protected static final String JWT_ALG_VAL = "HS256";


  public static JwtBean generateJwt(String content) {
    try {
      String jwtStr = Base64Codec.BASE64URL.decodeToString(content);
      JSONObject json = JSONObject.parseObject(jwtStr);
      JwtBean jwtBean = new JwtBean();
      jwtBean.setUserNo(json.getLong(USER_ID));
      jwtBean.setClient(json.getString(CLIENT));
      jwtBean.setTimestamp(json.getLong(TIMESTAMP));
      jwtBean.setBase64Secret(json.getString(BASE64_SECRET));
      return jwtBean;
    } catch (Exception e) {
      throw new BizException(BizCodeEnum.SIGNATURE_ERROR);
    }
  }


  public static String generateToken(JwtBean jwtBean) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtBean.getBase64Secret());
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    JwtBuilder builder = Jwts.builder()
        .setHeaderParam(JWT_TYPE_KEY, JWT_TYPE_VAL)
        .setHeaderParam(JWT_ALG_KEY, JWT_ALG_VAL)
        .setPayload(jwt2Json(jwtBean))
        .signWith(signatureAlgorithm, signingKey);

    return builder.compact();
  }


  public static String jwt2Json(JwtBean jwtBean) {
    JSONObject json = new JSONObject(true);
    json.put(BASE64_SECRET, jwtBean.getBase64Secret());
    json.put(CLIENT, jwtBean.getClient());
    json.put(TIMESTAMP, jwtBean.getTimestamp());
    json.put(USER_ID, jwtBean.getUserNo());
    return json.toString();
  }
}
