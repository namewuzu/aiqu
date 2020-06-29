package com.diankong.sexstory.mobile.bean;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/12.
 * 描述：
 * =============================================
 */

public class PayPojo {
    public String partnerId;//商户号
    public String prepayId;//支付ID
    public String sign;//下订单签名
    public String nonceStr;//下订单请求随机数
    public String pack_age;//扩展字段，api要求暂填固定值“Sign=WXPay”
    public String appid;//应用Id
    public String timestamp;//时间戳
    public String extData;


    public PayPojo(String _partnerId, String _prepayId, String _sign, String _noncestr, String _pack_age, String _appid, String _timestamp,String _extData) {
        partnerId = _partnerId;
        prepayId = _prepayId;
        sign = _sign;
        nonceStr = _noncestr;
        pack_age = _pack_age;
        appid = _appid;
        timestamp = _timestamp;
        extData = _extData;
    }
}
