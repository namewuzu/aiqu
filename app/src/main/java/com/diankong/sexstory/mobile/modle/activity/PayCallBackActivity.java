package com.diankong.sexstory.mobile.modle.activity;

import android.content.Context;
import android.content.Intent;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseAct;
import com.diankong.sexstory.mobile.databinding.ActivityPaycallbackBinding;
import com.diankong.sexstory.mobile.modle.modelview.PayCallBackViewModle;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/12.
 * 描述：
 * =============================================
 */

public class PayCallBackActivity extends BaseAct<ActivityPaycallbackBinding,PayCallBackViewModle> {

    public static void startActivity(Context context, int type) {
        Intent starter = new Intent(context, PayCallBackActivity.class);
        starter.putExtra("TYPE", type);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_paycallback;
    }
}
