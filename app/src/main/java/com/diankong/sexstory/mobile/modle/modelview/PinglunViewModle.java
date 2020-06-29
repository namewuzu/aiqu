package com.diankong.sexstory.mobile.modle.modelview;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityPinglunBinding;
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
 * 作者：Created by 胡清 on 2019/11/14.
 * 描述：
 * =============================================
 */

public class PinglunViewModle extends BaseViewModle<ActivityPinglunBinding>  implements TextWatcher {

    private int goodid;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        b.etContent.addTextChangedListener(this);
        goodid = act.getIntent().getIntExtra("GOODID",0);
        b.btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                commitClick();
            }
        });
    }

    @Override
    public void onDestory() {
        super.onDestory();
        LoadingDialog.cancelDialogForLoading();

    }

    @Override
    public void initNet() {

    }

    public void commitClick() {
        if (TextUtils.isEmpty(b.etContent.getText())) {
            ToastUtils.showShort("请输入内容");
            return;
        }
        LoadingDialog.showDialogForLoading(act);
        EasyHttp.post(Api.apiurl +"good/comment")
                .params("userId", String.valueOf(UIUtils.getUserInfo().id))
                .params("content", b.etContent.getText().toString())
                .params("goodId", String.valueOf(goodid) )
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        LoadingDialog.cancelDialogForLoading();
//                        ToastUtils.showShort(e.getMessage());
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
