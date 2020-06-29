package com.diankong.sexstory.mobile.modle.modelview;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityFeedbackBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.utils.UIUtils;
import com.diankong.sexstory.mobile.widget.LoadingDialog;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/17.
 * 描述：
 * =============================================
 */

public class FeedBackModelView extends BaseViewModle<ActivityFeedbackBinding> implements TextWatcher {


    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        b.etContent.addTextChangedListener(this);
    }


    @Override
    public void initNet() {

    }

    @Override
    public void onDestory() {
        super.onDestory();
        LoadingDialog.cancelDialogForLoading();
    }

    public void commitClick() {
        if (TextUtils.isEmpty(b.etContent.getText())) {
            ToastUtils.showShort("请输入内容");
            return;
        }
        LoadingDialog.showDialogForLoading(act);
        EasyHttp.post(Api.apiurl + "/userSet/comment")
                .params("userId", String.valueOf(UIUtils.getUserInfo().id))
                .params("comment", b.etContent.getText().toString())
                .params("token", UIUtils.getUserInfo().token)
                .params("uid", String.valueOf(UIUtils.getUserInfo().id))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        LoadingDialog.cancelDialogForLoading();
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo _userInfoPojo) {
                        LoadingDialog.cancelDialogForLoading();
                        ToastUtils.showShort("提交成功");
                        b.etContent.setText("");
                        act.finish();
                    }

                }) {
                });

    }

    @Override
    public void beforeTextChanged(CharSequence _charSequence, int _i, int _i1, int _i2) {
        b.tvCounter.setText(_charSequence.length() + "/300");
    }

    @Override
    public void onTextChanged(CharSequence _charSequence, int _i, int _i1, int _i2) {

    }

    @Override
    public void afterTextChanged(Editable _editable) {
        b.tvCounter.setText(_editable.length() + "/300");
    }

}
