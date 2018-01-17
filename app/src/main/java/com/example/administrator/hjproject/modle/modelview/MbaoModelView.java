package com.example.administrator.hjproject.modle.modelview;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.example.administrator.hjproject.base.BaseViewModle;
import com.example.administrator.hjproject.databinding.ActivityMbaoBinding;
import com.example.administrator.hjproject.modle.fragment.AssetsFragment;
import com.example.administrator.hjproject.modle.fragment.EntrustFragment;
import com.example.administrator.hjproject.modle.fragment.HistoryFragment;
import com.example.administrator.hjproject.modle.fragment.MineFragment;
import com.example.administrator.hjproject.utils.ToolbarUtils;

import java.util.ArrayList;

/**
 * 项目:
 * 类名: com.example.administrator.hjproject.modle.modelview$
 * 作者: created by  on $data$ $hour$
 * 标记:
 * 电话:
 * 描述: $Description$
 */

public class MbaoModelView extends BaseViewModle<ActivityMbaoBinding> {
    private ArrayList<Fragment> _fragments;
    private String[] titles = {"麦宝交易", "麦宝委托", "交易历史", "个人资产"};


    @Override
    public void initUI() {
        b.setViewmodle(this);
        ToolbarUtils.initToolBarWhiteNav(b.toolbar, act);
        _fragments = new ArrayList<>();
        _fragments.add(MineFragment.newInstance(0));
        _fragments.add(EntrustFragment.newInstance(0));
        _fragments.add(HistoryFragment.newInstance(0));
        _fragments.add(AssetsFragment.newInstance(0));

        b.vpView.setAdapter(new FragmentPagerAdapter(act.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return _fragments.get(position);
            }

            @Override
            public int getCount() {
                return _fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        b.vpView.setOffscreenPageLimit(2);
        b.tabs.setupWithViewPager(b.vpView);
        b.tabs.setTabMode(TabLayout.MODE_FIXED);
        b.title.setSelected(true);
    }

    @Override
    public void initNet() {

    }

    public void ClearClick() {
        b.llTitle.setVisibility(View.GONE);
    }
}
