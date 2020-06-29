package com.diankong.sexstory.mobile.modle.modelview;

import android.text.TextUtils;
import android.view.View;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.databinding.ActivityNicknameBinding;
import com.diankong.sexstory.mobile.event.NickNameEvent;
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

public class NickNameViewModle extends BaseViewModle<ActivityNicknameBinding> {
    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);

        b.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(!TextUtils.isEmpty(b.et.getText().toString())){
                    EventBus.getDefault().post(new NickNameEvent(b.et.getText().toString()));
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
}
