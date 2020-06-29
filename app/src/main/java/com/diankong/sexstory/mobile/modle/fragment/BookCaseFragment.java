package com.diankong.sexstory.mobile.modle.fragment;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseFrag;
import com.diankong.sexstory.mobile.databinding.FragmentBookcaseBinding;
import com.diankong.sexstory.mobile.modle.modelview.BookCaseViewModle;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class BookCaseFragment extends BaseFrag<FragmentBookcaseBinding, BookCaseViewModle> {
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_bookcase;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        viewModle.onHiddenChanged(hidden);
    }
}
