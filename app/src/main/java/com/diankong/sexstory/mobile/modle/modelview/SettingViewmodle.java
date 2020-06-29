package com.diankong.sexstory.mobile.modle.modelview;

import android.view.View;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.databinding.ActivitySettingBinding;
import com.diankong.sexstory.mobile.modle.activity.FeedBackActivity;
import com.diankong.sexstory.mobile.utils.AndroidSystemUtils;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/7/5.
 * 描述：
 * =============================================
 */

public class SettingViewmodle extends BaseViewModle<ActivitySettingBinding> {
    @Override
    public void initUI() {

        ToolbarUtils.initToolBar(b.toolbar, act);

        b.tvYijian.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(FeedBackActivity.class);
            }
        });
        b.tvPsd.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
//                RegisteredActivity.startActivity(act, 1);
            }
        });

        b.tvClern.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                SpUtlis.savaOpenId("");
                ToastUtils.showShort("取消成功");
            }
        });

        b.tvVersionCode.setSubtitle(AndroidSystemUtils.getVersion(act));


    }

    @Override
    public void initNet() {

    }

}