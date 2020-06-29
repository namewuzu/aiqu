package com.diankong.sexstory.mobile.modle.modelview;

import android.view.View;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.LinkPojo;
import com.diankong.sexstory.mobile.bean.PayPojo;
import com.diankong.sexstory.mobile.databinding.ActivityPayBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.utils.AliPayUtils;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/28.
 * 描述：
 * =============================================
 */

public class payViewModle extends BaseViewModle<ActivityPayBinding> {

    private int type;//1微信，2支付宝
    private double fee;
    private String orderId;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
        type = act.getIntent().getIntExtra("TYPE",0);
        orderId = act.getIntent().getStringExtra("ORDERID");
        fee = act.getIntent().getDoubleExtra("FEE",0.0);

        b.tvMoney.setText("￥"+fee);

        if(type==1){
            b.ivIcon.setImageResource(R.mipmap.pay_wexin);
            b.tvCommit.setBackgroundResource(R.drawable.shape_text_weixin_shadow_select);
            b.tvCommit.setText("使用微信支付￥" + fee);
        }else{
            b.ivIcon.setImageResource(R.mipmap.pay_zhifubao);
            b.tvCommit.setBackgroundResource(R.drawable.shape_text_zhifubao_shadow_select);
            b.tvCommit.setText("使用支付宝支付￥" + fee);
        }

        b.tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (type == 2) {
                    zhifubao();
                } else {
                    wexin();
                }
            }
        });


    }

    private void wexin() {
        EasyHttp.post(Api.apiurl + "order/reOrder")
                .params("orderId", orderId)
                .execute(new CallBackProxy<BaseResult<PayPojo>, PayPojo>(new SimpleCallBack<PayPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final PayPojo _payPojo) {
                        weChatPay(new PayPojo(_payPojo.partnerId, _payPojo.prepayId, _payPojo.sign, _payPojo.nonceStr, _payPojo.pack_age, _payPojo.appid, _payPojo.timestamp,_payPojo.extData));
                    }

                }) {
                });
    }

    private void zhifubao() {
        EasyHttp.post(Api.apiurl + "order/testOrder")
                .params("orderId", orderId)
                .execute(new CallBackProxy<BaseResult<LinkPojo>, LinkPojo>(new SimpleCallBack<LinkPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final LinkPojo _linkPojo) {
//                        WBViewActivity.startActivity(act, "支付", _linkPojo.orderString, false);
                        AliPayUtils.alipay(_linkPojo.orderString,act);
                    }

                }) {
                });
    }

    private void weChatPay(PayPojo _payPojo) {

        PayReq req = new PayReq();
        req.appId = _payPojo.appid;
        req.prepayId = _payPojo.prepayId;
        req.partnerId = _payPojo.partnerId;
        req.packageValue = _payPojo.pack_age;
        req.nonceStr = _payPojo.nonceStr;
        req.timeStamp = _payPojo.timestamp;
        req.sign = _payPojo.sign;

        App.getmWxApi().sendReq(req);


    }

    @Override
    public void initNet() {

    }
}
