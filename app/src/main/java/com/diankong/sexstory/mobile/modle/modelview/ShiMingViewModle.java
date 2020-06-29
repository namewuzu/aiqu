package com.diankong.sexstory.mobile.modle.modelview;

import android.content.Intent;
import android.text.TextUtils;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityShimingBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.MainActivity;
import com.diankong.sexstory.mobile.utils.CustomClickListener;
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
 * 作者：Created by 胡清 on 2020/6/15.
 * 描述：
 * =============================================
 */

public class ShiMingViewModle extends BaseViewModle<ActivityShimingBinding> {
    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);


        b.btCommit.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {

                if (TextUtils.isEmpty(b.et1.getText().toString())) {
                    ToastUtils.showShort("请输入姓名");
                }

                if (TextUtils.isEmpty(b.et2.getText().toString())) {
                    ToastUtils.showShort("请输入身份证号码");
                }

                isOrder();

            }

            @Override
            protected void onFastClick() {
                ToastUtils.showShort("请等待...");
            }
        });


    }

    private void isOrder() {
        EasyHttp.get(Api.apiurl + "user/identifyUser")
                .params("id", String.valueOf(SpUtlis.getId()))
                .params("idNo", b.et2.getText().toString())
                .params("name", b.et1.getText().toString())
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo _userInfoPojo) {

                            SpUtlis.savaIdNo(_userInfoPojo.flag);

                            if(_userInfoPojo.flag==2){
                                //新增
                                Intent intent = new Intent(act, MainActivity.class);
                                intent.putExtra("ISNEEDLOGIN",true);
                                act.startActivity(intent);
                                ToastUtils.showShort(_userInfoPojo.respMessage);

                            }else{
                                ToastUtils.showShort(_userInfoPojo.respMessage);
                                //新增
                                Intent intent = new Intent(act, MainActivity.class);
                                intent.putExtra("ISNEEDLOGIN",true);
                                act.startActivity(intent);
                            }

                        act.finish();

                    }

                }) {
                });
    }

    @Override
    public void initNet() {

    }


}
