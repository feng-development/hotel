package com.feng.hotel.utils;

/**
 * @author Administrator
 * @since 2021/8/6
 */
public class JwtBean {
    public static final String TOKEN_PREFIX = "token";

    /**
     * 用户编号
     */
    protected Long userNo;

    /**
     * 时间戳
     */
    protected Long timestamp;

    /**
     * 客户端类型
     */
    private String client;

    /**
     * base64加密
     */
    private String base64Secret;

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }


    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public String getBase64Secret() {
        return base64Secret;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

}
