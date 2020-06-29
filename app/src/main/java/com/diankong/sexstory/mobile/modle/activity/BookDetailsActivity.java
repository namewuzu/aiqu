package com.diankong.sexstory.mobile.modle.activity;

import android.content.Intent;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseAct;
import com.diankong.sexstory.mobile.databinding.ActivityBookDetailsBinding;
import com.diankong.sexstory.mobile.modle.modelview.BookDetailsViewModle;
import com.umeng.socialize.UMShareAPI;

import org.jetbrains.annotations.Nullable;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/23.
 * 描述：
 * =============================================
 */

public class BookDetailsActivity extends BaseAct<ActivityBookDetailsBinding,BookDetailsViewModle> {
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_book_details;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
