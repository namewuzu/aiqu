package com.diankong.sexstory.mobile.modle.modelview;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.databinding.ActivityAboutBinding;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/4/16.
 * 描述：
 * =============================================
 */

public class AboutViewModle extends BaseViewModle<ActivityAboutBinding> {
    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
    }

    @Override
    public void initNet() {

    }
}
