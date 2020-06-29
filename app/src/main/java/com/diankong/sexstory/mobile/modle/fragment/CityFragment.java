package com.diankong.sexstory.mobile.modle.fragment;

import android.os.Bundle;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseFrag;
import com.diankong.sexstory.mobile.databinding.CityFragmentBinding;
import com.diankong.sexstory.mobile.modle.modelview.CityViewModle;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/5.
 * 描述：
 * =============================================
 */

public class CityFragment extends BaseFrag<CityFragmentBinding,CityViewModle> {

    public static CityFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("TYPE", type);
        CityFragment fragment = new CityFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.city_fragment;
    }

    @Override
    public void initView() {

    }
}
