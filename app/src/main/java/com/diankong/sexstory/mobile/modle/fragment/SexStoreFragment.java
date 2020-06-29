package com.diankong.sexstory.mobile.modle.fragment;

import android.os.Bundle;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseFrag;
import com.diankong.sexstory.mobile.databinding.FragmentSexStoreBinding;
import com.diankong.sexstory.mobile.modle.modelview.SexStoreViewModle;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/6.
 * 描述：
 * =============================================
 */

public class SexStoreFragment extends BaseFrag<FragmentSexStoreBinding,SexStoreViewModle> {

    public static SexStoreFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("TYPE", type);
        SexStoreFragment fragment = new SexStoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sex_store;
    }

    @Override
    public void initView() {

    }
}
