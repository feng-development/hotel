/*
 *  Copyright 1999-2019 Evision Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.feng.hotel.utils;

import com.feng.hotel.base.Constants;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

/**
 * 加密工具类
 *
 * @author asheng
 * @since 2018/7/17
 */
public final class EncryptUtils {

    private static final String AES = "AES";

    private static final String MD5 = "MD5";

    private static final String AES_TYPE = "AES/ECB/PKCS5Padding";

    private static final String PADDING = "#";

    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private EncryptUtils() {
    }

    /**
     * 进行MD5加密
     *
     * @param content 加密内容
     * @return 加密后的内容
     */
    public static String md5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance(MD5);
            byte[] encryptBytes = digest.digest(content.getBytes(Constants.UTF_8_CHARSET));
            return new String(encodeHex(encryptBytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("unsupported such encrypt algorithm .", e);
        }
    }

    /**
     * 将加密的二进制数据转换成16进制
     *
     * @param bytes 二进制数据
     * @return 16进制
     */
    private static char[] encodeHex(byte[] bytes) {
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return chars;
    }

    /**
     * 对内容进行解密
     *
     * @param encrypt 加密内容
     * @param aesKey  密钥
     * @return 解密后的内容
     */
    public static String decrypt(String encrypt, String aesKey) {
        if (StringUtils.isBlank(encrypt) || StringUtils.isBlank(aesKey)) {
            return StringUtils.EMPTY;
        }
        try {
            return aesDecrypt(encrypt, paddingKey(aesKey));
        } catch (Exception e) {
            throw new RuntimeException(String.format("decrypt error. content: %s", encrypt), e);
        }
    }

    /**
     * 对加密的内容进行加密
     *
     * @param content 加密后的内容
     * @param aesKey  密钥
     * @return 加密后的内容
     */
    public static String encrypt(String content, String aesKey) {
        Assert.assertNotBlank(content, "encrypt content can not be empty");
        Assert.assertNotBlank(aesKey, "encrypt key can not be empty");
        try {
            return aesEncrypt(content, paddingKey(aesKey));
        } catch (Exception e) {
            throw new RuntimeException(String.format("encrypt error. content: %s", content), e);
        }
    }

    public static String paddingKey(String key) {
        int length = key.length();
        if (length >= 16) {
            return key.substring(0, 16);
        }
        int leftLength = 16 - length;
        StringBuilder keyBuilder = new StringBuilder(key);
        for (int i = 1; i <= leftLength; i++) {
            keyBuilder.append(PADDING);
        }
        return keyBuilder.toString();
    }

    /**
     * 对加密的内容进行加密(RSA)
     *
     * @param content   加密后的内容
     * @param publicKey 公钥
     * @return 加密后的内容
     */
    public static String rsaEncrypt(String content, String publicKey) {
        Assert.assertNotBlank(content, "rsaEncrypt content can not be empty");
        Assert.assertNotBlank(publicKey, "rsaEncrypt key can not be empty");
        try {
            //base64编码的公钥
            byte[] decoded = Base64.getDecoder().decode(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(Constants.RSA).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(Constants.RSA);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(content.getBytes(Constants.UTF_8_CHARSET)));
        } catch (Exception e) {
            throw new RuntimeException(String.format("encrypt error. content: %s", content), e);
        }
    }

    /**
     * 对加密的内容进行解密(RSA)
     *
     * @param content    加密字符串
     * @param privateKey 私钥
     * @return 铭文
     */
    public static String rsaDecrypt(String content, String privateKey) {
        Assert.assertNotBlank(content, "rsaEncrypt content can not be empty");
        Assert.assertNotBlank(privateKey, "rsaDecrypt key can not be empty");
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.getDecoder().decode(content.getBytes(Constants.UTF_8_CHARSET));
            //base64编码的私钥
            byte[] decoded = Base64.getDecoder().decode(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(Constants.RSA).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance(Constants.RSA);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            throw new RuntimeException(String.format("encrypt error. content: %s", content), e);
        }
    }

    /**
     * 使用Base64进行加密
     *
     * @param bytes 加密字节
     * @return 加密后的内容
     */
    public static String base64Encode(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    /**
     * 对Base64加密后的内容进行解密
     *
     * @param base64Code 需要解密的内容
     * @return 解密后的字节
     */
    public static byte[] base64Decode(String base64Code) {
        Base64.Decoder decoder = Base64.getDecoder();
        return StringUtils.isEmpty(base64Code) ? null : decoder.decode(base64Code);
    }


    /***
     * 使用AES进行加密
     *
     * @param content 加密内容
     * @param aesKey  密钥
     * @return 加密后的字节
     * @throws Exception GeneralSecurityException
     */
    public static byte[] aesEncryptToBytes(String content, String aesKey) throws Exception {
        KeyGenerator gen = KeyGenerator.getInstance(AES);
        gen.init(128);
        Cipher cipher = Cipher.getInstance(AES_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(aesKey.getBytes(), AES));
        return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
    }


    /***
     * 使用AES进行加密
     *
     * @param content 加密内容
     * @param aesKey  密钥
     * @return 加密后的字符串
     * @throws Exception GeneralSecurityException
     */
    private static String aesEncrypt(String content, String aesKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, aesKey));
    }

    /***
     * 使用AES进行解密
     *
     * @param encryptBytes 加密内容的字节
     * @param aesKey       密钥
     * @return 解密后的内容
     * @throws Exception GeneralSecurityException
     */
    private static String aesDecryptByBytes(byte[] encryptBytes, String aesKey) throws Exception {
        KeyGenerator gen = KeyGenerator.getInstance(AES);
        gen.init(128);
        Cipher cipher = Cipher.getInstance(AES_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(aesKey.getBytes(), AES));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }


    /**
     * 对加密的文本进行AES解密
     *
     * @param encryptStr 加密后的字符串
     * @param aesKey     aes密钥
     * @return 解密后的内容
     * @throws Exception GeneralSecurityException
     */
    private static String aesDecrypt(String encryptStr, String aesKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), aesKey);
    }

}
