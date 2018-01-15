package com.example.administrator.hjproject.modle.modelview;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseViewModle;
import com.example.administrator.hjproject.databinding.ActivityMainBinding;
import com.example.administrator.hjproject.modle.fragment.BusinessFragment;
import com.example.administrator.hjproject.modle.fragment.CircleFragment;
import com.example.administrator.hjproject.modle.fragment.GuestFragment;
import com.example.administrator.hjproject.modle.fragment.MineFragment;
import com.example.administrator.hjproject.modle.fragment.MsgFragment;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/12.
 * 描述：
 * =============================================
 */

public class MainModelView extends BaseViewModle<ActivityMainBinding> {


    public String _danqian;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;

    @Override
    public void initUI() {
        b.setViewmodle(this);
        //设置底部（可放两张图片进行点击切换）
        b.bottomTabBar.init(act.getSupportFragmentManager())
                .addTabItem("商城", R.mipmap.ic_launcher, BusinessFragment.class)
                .addTabItem("麦圈", R.mipmap.ic_launcher, CircleFragment.class)
                .addTabItem("消息", R.mipmap.ic_launcher, MsgFragment.class)
                .addTabItem("创客", R.mipmap.ic_launcher, GuestFragment.class)
                .addTabItem("我的", R.mipmap.ic_launcher, MineFragment.class);

    }

    @Override
    public void initNet() {

    }

}
