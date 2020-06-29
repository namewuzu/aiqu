package com.diankong.sexstory.mobile.modle.modelview;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.databinding.ActivityQianmingBinding;
import com.diankong.sexstory.mobile.event.QianMingEvent;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/23.
 * 描述：
 * =============================================
 */

public class QianMingViewModle extends BaseViewModle<ActivityQianmingBinding> implements TextWatcher {
    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        b.etContent.addTextChangedListener(this);

        b.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(!TextUtils.isEmpty(b.etContent.getText().toString())){
                    EventBus.getDefault().post(new QianMingEvent(b.etContent.getText().toString()));
                    act.finish();
                }else{
                    ToastUtils.showShort("昵称不能为空");
                }

            }
        });
    }

    @Override
    public void initNet() {

    }

    @Override
    public void beforeTextChanged(CharSequence _charSequence, int _i, int _i1, int _i2) {
        b.tvCounter.setText(_charSequence.length() + "/30");
    }

    @Override
    public void onTextChanged(CharSequence _charSequence, int _i, int _i1, int _i2) {

    }

    @Override
    public void afterTextChanged(Editable _editable) {
        b.tvCounter.setText(_editable.length() + "/30");
    }
}
