package com.diankong.sexstory.mobile.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.LinkPojo;
import com.diankong.sexstory.mobile.bean.PayResult;
import com.diankong.sexstory.mobile.http.Api;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.Map;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/19.
 * 描述：
 * =============================================
 */

public class AliPayUtils {

    private static final int SDK_PAY_FLAG = 1;

    private static String order_id;

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        // @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showShort("支付成功");

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
//                            Toast.makeText(ctx, "支付结果确认中", Toast.LENGTH_SHORT)
//                                    .show();
                            ToastUtils.showShort("支付结果确认中");

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            Toast.makeText(ctx, "支付失败", Toast.LENGTH_SHORT).show();
                            ToastUtils.showShort("支付失败");

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    //这个是 我们做项目的时候要上传服务器的数据，如果是你们用的话，可以把这些参数直接掠过
    public static void pay(final AppCompatActivity context, double _money, int _coinCount) {
        EasyHttp.get(Api.apiurl + "coin/aliSiFangOrder")
                .params("userId",String.valueOf(SpUtlis.getId()))
                .params("fee",String.valueOf(_money))
                .params("coinCount",String.valueOf(_coinCount))
                .execute(new CallBackProxy<BaseResult<LinkPojo>, LinkPojo>(new SimpleCallBack<LinkPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final LinkPojo _linkPojo) {
                        alipay(_linkPojo.orderString,context);
                    }

                }) {
                });

    }


    public static void alipay(String sign_order, final AppCompatActivity ctx) {
        L.e("wch", sign_order);
        final String payInfo = sign_order;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) ctx);

                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
