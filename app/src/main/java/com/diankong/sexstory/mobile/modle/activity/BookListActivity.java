package com.diankong.sexstory.mobile.modle.activity;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseAct;
import com.diankong.sexstory.mobile.databinding.ActivityBookListBinding;
import com.diankong.sexstory.mobile.modle.modelview.BookListViewModle;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/21.
 * 描述：
 * =============================================
 */

public class BookListActivity extends BaseAct<ActivityBookListBinding,BookListViewModle> {
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_book_list;
    }
}