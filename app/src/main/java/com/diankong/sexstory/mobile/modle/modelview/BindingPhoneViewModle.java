package com.diankong.sexstory.mobile.modle.modelview;

import android.text.TextUtils;
import android.view.View;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityBindingPhoneBinding;
import com.diankong.sexstory.mobile.event.PhoneEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/25.
 * 描述：
 * =============================================
 */

public class BindingPhoneViewModle extends BaseViewModle<ActivityBindingPhoneBinding> {
    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
        b.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                final String phone = b.editUsername.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort("请输入手机号码");
                    return;
                }

                if (!phone.startsWith("1")||phone.length()!=11) {
                    ToastUtils.showShort("手机号码格式有误");
                    return;
                }

                EasyHttp.post(Api.apiurl + "user/bandingPhone")
                        .params("phone", phone)
                        .params("id", String.valueOf(SpUtlis.getId()))
                        .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                            @Override
                            public void onError(ApiException e) {
                                ToastUtils.showShort(e.getMessage());
                            }

                            @Override
                            public void onSuccess(final UserInfoPojo _userInfoPojo) {
                                ToastUtils.showShort("绑定成功！");
                                EventBus.getDefault().post(new PhoneEvent(phone));
                                act.finish();
                            }

                        }) {
                        });
            }
        });

    }

    @Override
    public void initNet() {

    }
}
