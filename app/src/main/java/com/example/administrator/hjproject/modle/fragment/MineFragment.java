package com.example.administrator.hjproject.modle.fragment;

import android.os.Bundle;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseFrag;
import com.example.administrator.hjproject.databinding.FragmentMineBinding;
import com.example.administrator.hjproject.modle.modelview.MineModelView;

/**
 * 微智全景源代码，版权@北京微智全景信息技术有限公司
 * 项目: 易宝支付通道插件
 * 类名: com.example.administrator.hjproject.modle.fragment$
 * 作者: created by 熊凯 on $data$ $hour$
 * 标记:
 * 电话: 15323410416
 * 描述: $Description$
 */

public class MineFragment extends BaseFrag<FragmentMineBinding, MineModelView> {
    public static final String TYPE = "TYPE";

    public static MineFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {

    }
}
