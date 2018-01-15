package com.example.administrator.hjproject.modle.modelview;

import com.example.administrator.hjproject.base.BaseViewModle;
import com.example.administrator.hjproject.databinding.FragmentGuestBinding;
import com.example.administrator.hjproject.modle.activity.secendActivity;
import com.example.administrator.hjproject.utils.L;

/**
 * 项目:
 * 类名: com.example.administrator.hjproject.modle.modelview$
 * 作者: created by  on $data$ $hour$
 * 标记:
 * 电话:
 * 描述: $Description$
 */

public class GuestModleView extends BaseViewModle<FragmentGuestBinding> {
    @Override
    public void initUI() {
        b.setViewmodle(this);
    }

    @Override
    public void initNet() {

    }

    public void mikeClick() {
        startActivity(secendActivity.class);
    }
}
