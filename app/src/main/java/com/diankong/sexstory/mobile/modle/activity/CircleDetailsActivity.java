package com.diankong.sexstory.mobile.modle.activity;

import android.view.KeyEvent;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseAct;
import com.diankong.sexstory.mobile.databinding.ActivityCircleDetailBinding;
import com.diankong.sexstory.mobile.modle.modelview.CircleDetailViewModle;
import com.diankong.sexstory.mobile.utils.L;

import org.jetbrains.annotations.Nullable;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/6.
 * 描述：
 * =============================================
 */

public class CircleDetailsActivity extends BaseAct<ActivityCircleDetailBinding,CircleDetailViewModle>{
    private long initTime1;
    private long initTime2;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_circle_detail;
    }


    @Override
    public void init() {
        super.init();
        initTime1 = System.currentTimeMillis();
        L.d("时长：" + (initTime1));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onKeyDown(int keyCode, @Nullable KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            initTime2 = System.currentTimeMillis();
            L.d("时长：" + (initTime2));
            long l = initTime2 - initTime1;
            L.d("时长：" + l);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
