package com.diankong.sexstory.mobile.modle.fragment;

import android.os.Bundle;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseFrag;
import com.diankong.sexstory.mobile.databinding.FragmentGoodstypeBinding;
import com.diankong.sexstory.mobile.modle.modelview.GoodstypeViewModle;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/15.
 * 描述：
 * =============================================
 */

public class GoodsTypeFragment extends BaseFrag<FragmentGoodstypeBinding,GoodstypeViewModle> {

    public static GoodsTypeFragment newInstance(int id,int goodTypeId) {
        Bundle args = new Bundle();
        args.putInt("TYPE",id);
        args.putInt("goodTypeId",goodTypeId);
        GoodsTypeFragment fragment = new GoodsTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_goodstype;
    }

    @Override
    public void initView() {

    }
}
