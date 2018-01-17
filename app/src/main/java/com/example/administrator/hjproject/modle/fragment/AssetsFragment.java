package com.example.administrator.hjproject.modle.fragment;

import android.os.Bundle;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseFrag;
import com.example.administrator.hjproject.databinding.FragmentAssetsBinding;
import com.example.administrator.hjproject.modle.modelview.AssetsModelView;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/17.
 * 描述：
 * =============================================
 */

public class AssetsFragment extends BaseFrag<FragmentAssetsBinding,AssetsModelView> {

    public static final String TYPE = "TYPE";

    public static AssetsFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        AssetsFragment fragment = new AssetsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_assets;
    }

    @Override
    public void initView() {

    }
}
