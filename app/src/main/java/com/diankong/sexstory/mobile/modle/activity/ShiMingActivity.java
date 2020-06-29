package com.diankong.sexstory.mobile.modle.activity;

import android.content.Context;
import android.content.Intent;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseAct;
import com.diankong.sexstory.mobile.databinding.ActivityShimingBinding;
import com.diankong.sexstory.mobile.modle.modelview.ShiMingViewModle;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2020/6/15.
 * 描述：
 * =============================================
 */

public class ShiMingActivity extends BaseAct<ActivityShimingBinding,ShiMingViewModle> {

    public static void startActivity(Context context) {
        Intent starter = new Intent(context, ShiMingActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_shiming;
    }
}
