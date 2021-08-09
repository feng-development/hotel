package com.feng.hotel.service.impl;

import com.baidu.aip.ocr.AipOcr;
import com.feng.hotel.common.enums.IdCardStatusEnum;
import com.feng.hotel.response.IdCardResult;
import com.feng.hotel.service.IIdCardService;
import java.util.HashMap;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @since 2021/8/9
 */
@Service
public class IdCardServiceImpl implements IIdCardService {

  private final AipOcr aipOcr;
  //身份证正面(头像面)
  private final static String ID_CARD_SIDE = "front";
  private final static String ID_CARD_TYPE = "idcard_type";
  private final static String WORDS_RESULT = "words_result";
  private final static String ADDRESS = "住址";
  private final static String WORDS = "words";

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
    JSONObject jsonObject = aipOcr.idcard(path, "ID_CARD_SIDE", new HashMap<>());
    //验证结果
    IdCardStatusEnum.check(jsonObject.getString(ID_CARD_TYPE));

    //获取成功之后的数据
    JSONObject wordsResult = jsonObject.getJSONObject(WORDS_RESULT);

    //获取身份证信息
    IdCardResult idCardResult = new IdCardResult();
    //设置地址
    idCardResult.setAddress(wordsResult.getJSONObject(ADDRESS).getString(WORDS));
    //设置地址
    idCardResult.setAddress(wordsResult.getJSONObject(ADDRESS).getString(WORDS));
    //设置地址
    idCardResult.setIdNum(wordsResult.getJSONObject(ADDRESS).getString(WORDS));
    return idCardResult;
  }
}
