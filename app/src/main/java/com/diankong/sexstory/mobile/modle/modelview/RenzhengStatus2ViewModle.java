package com.diankong.sexstory.mobile.modle.modelview;

import android.text.TextUtils;
import android.view.View;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.OSSPojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityRenzhengStatus2Binding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2020/1/14.
 * 描述：
 * =============================================
 */

public class RenzhengStatus2ViewModle extends BaseViewModle<ActivityRenzhengStatus2Binding> {


    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
        getuserinfo();
        b.btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                updataUserInfo();
            }
        });

        b.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                act.finish();
            }
        });

    }

    private void getuserinfo() {
        EasyHttp.post(Api.apiurl + "user/getUserInfor")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo _userInfoPojo) {

                        b.coin.setText(_userInfoPojo.chatCoin + "金币/分钟");
                        b.coinIng.setText(_userInfoPojo.coinPayCount+"金币");
                    }

                }) {
                });

    }

    private void updataUserInfo() {
        if (TextUtils.isEmpty(b.etTitle.getText().toString())) {
            ToastUtils.showShort("请输入您要设置的金币数");
            return;
        }

        EasyHttp.get(Api.apiurl + "user/updateUserInfo")
                .params("chatCoin", b.etTitle.getText().toString())
                .params("id", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<OSSPojo>, OSSPojo>(new SimpleCallBack<OSSPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final OSSPojo _ossPojo) {
                        ToastUtils.showShort("设置成功");
                        act.finish();
                    }

                }) {
                });
    }

    @Override
    public void initNet() {

    }
}
