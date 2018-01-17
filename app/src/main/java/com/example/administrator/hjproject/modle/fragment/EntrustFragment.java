package com.example.administrator.hjproject.modle.fragment;

import android.os.Bundle;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseFrag;
import com.example.administrator.hjproject.databinding.FragmentEntrustBinding;
import com.example.administrator.hjproject.modle.modelview.EntrustModelView;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/17.
 * 描述：
 * =============================================
 */

public class EntrustFragment extends BaseFrag<FragmentEntrustBinding,EntrustModelView> {

    public static final String TYPE = "TYPE";

    public static EntrustFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        EntrustFragment fragment = new EntrustFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_entrust;
    }

    @Override
    public void initView() {

    }
}
