package com.example.administrator.hjproject.modle.fragment;

import android.os.Bundle;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseFrag;
import com.example.administrator.hjproject.databinding.FragmentHistoryBinding;
import com.example.administrator.hjproject.modle.modelview.HistoryModelView;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/17.
 * 描述：
 * =============================================
 */

public class HistoryFragment extends BaseFrag<FragmentHistoryBinding,HistoryModelView> {

    public static final String TYPE = "TYPE";

    public static HistoryFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_history;
    }

    @Override
    public void initView() {

    }
}
