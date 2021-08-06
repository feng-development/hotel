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
package com.feng.hotel.base.exception;

import java.util.Arrays;

/**
 * @author Administrator
 * @since 2021/7/19
 */
public enum EnumReturnStatus {
    SUCCESS("200", "sucess"),

    SYS_BAD_REQUEST("400", "发送的请求不符合要求"),
    SYS_BAD_PARAMTER("401", "发送的请求参数未验证通过"),
    SYS_NO_DATA("402", "未查询到对应的数据信息"),
    ACCOUNT_AUTHCODE_ERROR("403", "验证码错误"),
    ACCOUNT_USER_NOT_EXIST("405", "用户不存在"),
    ACCOUNT_USER_PASSWORD_ERROR("406", "用户密码错误"),
    SYS_FUN_UNOPENED("407", "该功能暂时未开放"),
    SYS_BAD_DATA_STATUS("408", "数据状态错误"),
    SYS_FAIL_MAKE_ORDER_STATUS("409", "未成功生成订单"),

    SYS_SERVER_ERROR("500", "服务端错误"),
    SYS_UNKNOW_ERROR("501", "未知的软件异常"),


    SECONDS_KILL_ERROR("408", "秒杀商品已抢光，请等待下次秒杀"),
    SECONDS_KILL_NUM_ERROR("409", "特价秒杀商品只能购买一件"),
    SECONDS_KILL_NUM_EXCEED_ERROR("409", "所选秒杀商品库存不足，请确认购买数量"),

    ACCOUNT_REGISTERED("410", "该手机号已经注册，如忘记密码请使用快捷登录或找回密码"),
    ACCOUNT_FINGER_REGISTERED("410", "该手机号指纹已经注册过"),
    ACCOUNT_PHONE_NOT_REGISTER("411", "该手机号没有注册过"),
    ACCOUNT_FINGER_AUTHCODE_ERROR("411", "验证码验证错误"),
    ACCOUNT_AUTHCODE_EXCEED("412", "验证码超过10分钟的有效时间，请重新获取"),
    ACCOUNT_REQUEST_FREQUENT("413", "发送请求太频繁，请稍后再试"),
    ACCOUNT_FINGER_MATCH_ERROR("416", "指纹匹配失败"),
    ACCOUNT_FINGER_IDCODE_ERROR("417", "识别码错误，请输入正确的识别码"),
    ACCOUNT_NOT_EXIST_AUTHCODE("419", "该手机号未获取验证码"),

    ACCOUNT_NOT_EXIST_USER("421", "用户不存在"),
    ACCOUNT_MERCHANT_NOT_EXIST("422", "请求的邀请人商家不存在"),
    ACCOUNT_NOT_EXIST_FINGER("425", "用户未录入指纹"),
    ACCOUNT_DEVICE_BINDDED("426", "设备已经绑定过其他商户"),
    ACCOUNT_USER_PASSWORD_SETTED("426", "用户已设置过密码"),
    ACCOUNT_DEVICE_NO_BINDDED("426", "设备还未绑定"),
    ACCOUNT_DEVICE_BINDDED_ERROR("427", "商户还未审核，不能绑定设备"),
    ACCOUNT_USER_TOKEN_EXCEED("429", "您的账号已在其他地方登录，请重新登录"),

    ACCOUNT_PHONE_ERROR("430", "手机号码不正确，请输入正确的手机号"),
    OFFLINE_NOT_MERCHANT_ERROR("430", "您还不是商户，不能获取收款码"),
    OFFLINE_STORE_NOT_MERCHANT_ERROR("430", "您还不是商户，不能新建店铺"),
    OFFLINE_STORE_NAME_CHANGE_ERROR("431", "店铺名称不能自助修改，如有需要，请联系客服"),
    OFFLINE_STORE_DISCOUNT_FREQUENT("432", "每天只能修改一次折扣"),
    ACCOUNT_SCORE_ERROR("432", "积分不足"),
    ACCOUNT_MERCHANT_EXIST("433", "用户已经是商户"),
    ACCOUNT_CODE_LONG_TIME("434", "验证码错误或已过期"),
    ACCOUNT_DEVICE_TYPE_ERROR("435", "设备类型错误"),

    THIRD_PARTY_SERVICE_ERROR("440", "调用第三方服务出错"),
    PAYMENT_CODE_INVALID("441", "付款码已失效，请刷新重试"),

    AUTH_ERROR("450", "添加银行卡失败，请确认银行卡信息"),
    AUTH_CARD_ERROR("451", "您输入的银行卡暂不支持，请用其他银行卡绑定"),
    AUTH_CARD_SIGNED("452", "该银行卡已经签约过，请添加新的银行卡"),
    AUTH_ID_SIGNED("452", "该身份证已在其他账户认证过，请使用其他身份证操作"),
    AUTH_ADD_CARD_ERROR("453", "您输入的银行卡已添加过，请换张银行卡操作"),
    ACCOUNT_DEVICENO_ERROR("455", "输入的设备号码有误，请输入正确的设备号"),
    AUTH_NO_CARD("456", "您还未绑定银行卡，请先绑定银行卡"),
    AUTH_BIND_AGAIN("457", "您的银行卡需要重新绑定，请重新绑定"),
    AUTH_CODE_ERROR("458", "验证码验证失败，请重新获取验证码"),
    AUTH_PAY_ERROR("458", "信用卡扣款失败，请确认信用卡信息"),
    AUTH_UNBINDING_ERROR("458", "默认支付卡不能解约"),
    AUTH_DEFAULT_CARD_ERROR("458", "设置银行卡指纹支付失败"),
    ACCOUNT_BINDED_ERROR("459", "绑定关系不存在"),

    SCORE_ALREADY_SIGN("460", "今天已签到"),
    SCORE_NONE_ENOUGH("461", "纹豆不足"),
    SCORE_RAFFLE_FAILED("462", "没有抽中"),
    STOCKS_NONE_ENOUGH("463", "库存不足"),
    ADDRESS_NONE("464", "收货地址不存在"),
    SCORE_TIMES_ENOUGH("465", "活动还未开始，不能抽奖"),
    LOTTERY_TIMES_ERROR("466", "抽奖次数不足，不能抽奖"),

    ACCOUNT_USER_FINGER_NOT_EXIST("470", "用户未注册指纹，不能设置识别码"),
    GET_CASH_NUM_ERROR("470", "提现金额超过可提金额错误"),
    ACCOUNT_PHONE_ALREADY_EXIST("471", "该手机号码已经被注册过了"),
    GET_CASH_TYPE_ERROR("471", "提现类型错误"),
    ACCOUNT_APP_WEB_BINDING("472", "该手机号已绑定过，或用户已绑定其他手机，不能重复绑定"),
    GET_CASH_HANDLE_ERROR("472", "您有一笔正在处理的提现，请稍后操作"),
    SERVICE_LANDLIN_ERROR("472", "暂不支持该号码"),
    ACCOUNT_NEW_USER_ORDER_ERROR("473", "您已领取过新人红包，不能重复领取"),
    GET_CASH_VERIFY_ERROR("473", "审核的提现订单不存在"),
    GET_MERCHANT_ERROR("474", "您还不是商户，请先申请成为商户"),
    GET_INCOME_CASH_NUM_ERROR("475", "提现金额不能低于3元"),
    GET_CASH_CLEAR_ERROR("476", "清算失败"),
    PAY_REPETE_ERROR("476", "订单已支付成功，请等待处理结果"),
    GET_CASH_PROCESS_ERROR("477", "提现请求已提交银行处理，请稍后查看提现结果"),
    WALLET_REFUND_ERROR("478", "该订单已返现过，不能重复返现"),
    COUPON_GET_ERROR("701", "领取次数超限"),
    COUPON_NOT_ENOUGH("702", "优惠券已领完"),
    COUPON_ERROR("703", "优惠券暂不可用"),
    SHOP_ERROR("479", "指呗暂时只支持在恋指团商城购物消费，请您挑选合适的商品"),

    NOT_UPDATE_ANY_DATA("480", "未成功更新任何数据"),
    FINGER_PAY_AUTH_ERROR("480", "您的身份认证失败，请填写正确的卡信息"),
    FINGER_PAY_PWD_ERROR("481", "您的支付密码有误，请输入正确的支付密码"),
    ACCOUNT_USER_FINGER_NUM_ERROR("481", "该识别码使用用户过多，请换一个识别码"),
    FINGER_PAY_PWD_NOT_EXIST("482", "您的支付密码还未设置，请先设置支付密码"),
    FINGER_PAY_PWD_SETTING_ERROR("483", "您的认证没有通过，请先通过认证在设置密码"),
    FINGER_PAY_BANK_CARD_ERROR("483", "银行卡指纹支付单笔限额1000元"),
    PAY_LIMIT_ERROR("484", "支付金额超过限制"),
    PAY_ERROR("485", "支付失败"),
    PAY_UPDATE("485", "渠道维护中，请使用其他方式支付"),
    COIN_PAY_ERROR("485", "纹豆目前只支持移动充值和纹豆商品兑换！"),
    COIN_PAY_EXCHARGE_ERROR("485", "纹豆话费充值暂不可用，可使用其他支付方式充值！"),
    PAY_LIMIT_5_ERROR("486", "单卡每天限支付5次，请换个卡支付"),
    PAY_AMOUNT_ERROR("486", "支付卡余额不足"),
    PAY_PROCESS_ERROR("487", "支付请求已提交银行处理，请稍后查看支付结果"),

    WALLET_WITHDRAW_ERROR("490", "提现金额错误，不能小于3元，不能大于最大可提现额度"),
    PAY_SMS_AUTH("491", "指爱付需要验证码验证，请输入验证码"),
    AUTH_DEFAULT_CARD_SMS("491", "设置银行卡指纹支付需要验证码，请输入验证码"),
    PAY_SMS_AUTH_ERROR("492", "验证码错误，请重新获取验证码"),
    PAY_METHOD_LIMIT_ERROR("493", "设备端银行卡支付维护中，可使用钱包和指呗支付"),
    AUTH_DEFAULT_CARD_NEED_SIGN("498", "银行卡需要先激活才能设置为指纹支付卡"),
    AUTH_DEFAULT_CARD_TYPE_ERROR("498", "暂只支持储蓄卡为指纹支付银行卡，请选择可用的储蓄卡"),
    HOME_BUY_OVER_LIMIT("499", "当前商品已达到活动时间内限购次数"),

    WALLET_RECHARGE_LIMIT_ERROR("4841", "钱包充值单笔最大限额为1000元"),
    WALLET_RECHARGE_AMOUNT_ERROR("4842", "钱包余额不能大于1000元，请确认充值金额"),
    WALLET_RECHARGE_LIMIT_10_ERROR("4842", "通道升级中，充值限额10元以下"),
    WALLET_WITHDRAW_SERVICE_ERROR("4844", "钱包提现服务维护中，请稍后再试"),

    YST_BINDING_PHONE_ERROR("600", "用于已在云商通绑定手机，不可重复绑定！"),
    YST_CREATE_MEMBER_ERROR("601", "云商通创建会员失败，请稍后再试！"),
    YST_GET_PHONE_CODE_ERROR("602", "云商通获取手机验证码失败，请稍后再试！"),
    YST_BINDING_CARD_ERROR("603", "云商通绑卡失败，请确认银行卡信息！"),
    YST_REALNAME_ERROR("604", "云商通实名认证失败，请确认身份信息！"),
    YST_REALNAME_CARDNAME_ERROR("605", "云商通实名信息与绑卡姓名不一致，请确认姓名信息！"),
    YST_GET_URL_ERROR("606", "云商通获取签约url失败！"),
    YST_SIGN_ERROR("607", "用户已签约过云商通，不能重复签约！"),
    YST_QUERY_BALANCE_ERROR("608", "获取用户云商通余额失败，请稍后再试！"),
    NOT_GET_USER_CATINFO("609", "未获取到该用户的养喵信息"),

    ACCOUNT_ALREADY_EXISTS("610", "用户信息已存在"),
    ACCOUNT_NO_APPINFO("611", "未在APP注册"),
    ACHIEVEMENT_INSUFFICIENT("612", "成就值不足"),
    MESSAGE_PUSH_TIME("613", "今日已提醒"),
    //集卡活动相关
    JIKA_INTEGRAL_NOT_ENOUGH("614", "积分不足"),
    JIKA_OVER_CONVERT_COUNT_LIMIT("615", "超过可兑换次数"),
    JIKA_PRIZE_PROHIBIT_CONVERT("616", "该奖品暂不参与兑换"),
    JIKA_PLAY_TIME_ERROR("617", "游戏次数不足"),
    JIKA_ENERGY_CARD_NUM_ERROR("618", "卡片不足，无法合成"),
    // 线下店铺会员卡
    OFFLINE_NO_VIP("619", "无会员卡信息"),

    OFFLINE_ALREADY_VIP("620", "已购买该店铺会员卡"),
    JIKA_PRIZE_STOCK_ERROR("621", "该奖品库存不足，请兑换其他奖品"),
    REGISTERIP_DAY_ERROR("623", "当前网络注册超限，建议更换4G尝试"),
    GET_ACHIEVEMENT_CASH_NUM_ERROR("625", "提现金额不能低于2元"),
    NOT_AVAILABLE_ACTIVATECODE_ERROR("626", "暂无可用的激活码，请联系客服"),
    NOT_FIND_OFFLINE_FIND_SHARE("627", "未查询到数据"),

    //直播相关
    LIVE_HAD_BEEN_OWNER("630", "此人不是主播"),
    LIVE_POST_TYPE("631", "发送类型错误"),

    GET_RAKEBACK_CASH_NUM_ERROR("642", "提现金额不能低于2元"),

    PAY_SUPPORT_ERROR("700", "该商品暂不支持微信、支付宝付款，请使用银行卡支付"),

    THIRD_LOGIN_NEED_BINDING("4000", "第三方登录账号需绑定手机！"),
    COLLECTION_REPEAT_ERROR("4100", "已收藏过，不能重复收藏！"),

    // --------------------------公共服务返回状态
    TIME_OUT("8001", "服务请求超时"),
    FINGER_EXIST("8004", "指纹已经存在"),
    FINGER_NO_8k("8003", "请重新注册指纹"),
    FACE_ADD_ERROR("8002", "添加用户人脸失败"),
    FACE_COUNT_MAX_ERROR("8001", "用户图片数量超限"),
    APPID_SECRET_ERROR("8009", "appId/appSecret错误，请检查"),
    APPID_SECRET_EXIST_ERROR("8010", "appId/appSecret 不存在"),
    APPID_SECRET_TIME_ERROR("8011", "appId/appSecret已过期"),
    APPID_SECRET_ILLEGAL("8010", "请联系重新分配appId/appSecret"),
    //payment
    BUSINESS_TYPE_ERROR("7001", "业务类型错误");
    //order
//  GOOS_STATUS_OFFLINE();

    private String status;
    private String msg;

    EnumReturnStatus(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static EnumReturnStatus getByStatus(String status) {
        return Arrays.stream(EnumReturnStatus.values())
            .filter(x -> x.status.equals(status))
            .findFirst().orElse(null);
    }
}
