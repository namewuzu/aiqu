package com.diankong.sexstory.mobile.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.diankong.sexstory.mobile.base.ZhanghaoConfig;
import com.diankong.sexstory.mobile.modle.activity.PayCallBackActivity;
import com.diankong.sexstory.mobile.modle.activity.ShiMingActivity;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/12.
 * 描述：
 * =============================================
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI mWxApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWxApi = WXAPIFactory.createWXAPI(this, ZhanghaoConfig.APP_ID2, true);
// 注册
        mWxApi.handleIntent(getIntent(),this );
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWxApi.handleIntent(intent,this );
    }

    @Override
    public void onReq(BaseReq _baseReq) {

    }

    @Override
    public void onResp(BaseResp _baseResp) {

//        ToastUtils.showShort("回调---------" + _baseResp.errCode);

        L.d("走了哪一步？" + _baseResp.errCode);

        PayResp resp = (PayResp) _baseResp;
        String all = resp.extData;

//        List<String> list= Arrays.asList(all.split(","));
//
//
//        for (int i = 0; i < list.size(); i++) {
//            st1 = list.get(0);
//            st2 = list.get(1);
//            st3 = list.get(2);
//        }

        switch (_baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK://发送成功
                ToastUtils.showShort("支付成功");
                if(all.equals("2")){
                    ShiMingActivity.startActivity(this);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://发送取消
                ToastUtils.showShort("支付取消");
                finish();
                PayCallBackActivity.startActivity(this, 2);

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://发送被拒绝
                ToastUtils.showShort("支付失败");
                finish();
                PayCallBackActivity.startActivity(this, 2);
                break;
            default:
                ToastUtils.showShort("支付失败");
                finish();
                PayCallBackActivity.startActivity(this, 2);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
