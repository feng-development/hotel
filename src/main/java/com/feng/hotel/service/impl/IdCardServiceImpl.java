package com.feng.hotel.service.impl;

import com.baidu.aip.ocr.AipOcr;
import com.feng.hotel.common.enums.IdCardStatusEnum;
import com.feng.hotel.response.IdCardResult;
import com.feng.hotel.service.IIdCardService;
import com.feng.hotel.utils.json.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @since 2021/8/9
 */
@Service
public class IdCardServiceImpl implements IIdCardService {

    Logger LOGGER = LoggerFactory.getLogger(IdCardServiceImpl.class);

    private final AipOcr aipOcr;
    //身份证正面(头像面)
    private final static String ID_CARD_SIDE = "front";
    private final static String WORDS_RESULT = "words_result";
    private final static String IMAGE_STATUS = "image_status";
    private final static String ADDRESS_KEY = "住址";
    private final static String WORDS = "words";
    private final static String ID_NO_KEY = "公民身份号码";
    private final static String NAME_KEY = "姓名";

    public IdCardServiceImpl(AipOcr aipOcr) {
        this.aipOcr = aipOcr;
    }

    /**
     * 身份证识别
     *
     * @param path 图片路径
     * @return 身份识别信息
     */
    public IdCardResult idCardRecognition(String path) {

        JSONObject jsonObject = aipOcr.idcard(path, ID_CARD_SIDE, new HashMap<>());
        LOGGER.info(JsonUtils.serialize(jsonObject));
        //验证结果
        IdCardStatusEnum.check(jsonObject.getString(IMAGE_STATUS));

        //获取成功之后的数据
        JSONObject wordsResult = jsonObject.getJSONObject(WORDS_RESULT);

        //获取身份证信息
        IdCardResult idCardResult = new IdCardResult();
        //设置地址
        idCardResult.setAddress(wordsResult.getJSONObject(ADDRESS_KEY).getString(WORDS));
        //设置名字
        idCardResult.setName(wordsResult.getJSONObject(NAME_KEY).getString(WORDS));
        //设置身份证
        idCardResult.setIdNum(wordsResult.getJSONObject(ID_NO_KEY).getString(WORDS));
        return idCardResult;
    }
}
