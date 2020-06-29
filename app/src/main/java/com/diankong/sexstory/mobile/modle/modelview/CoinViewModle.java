package com.diankong.sexstory.mobile.modle.modelview;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.databinding.ActivityCoinBinding;
import com.diankong.sexstory.mobile.modle.fragment.CoinFragment;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;

import java.util.ArrayList;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/20.
 * 描述：
 * =============================================
 */

public class CoinViewModle extends BaseViewModle<ActivityCoinBinding> {

    private ArrayList<Fragment> _fragments;
    private String[] titles = {"全部记录", "金币添加记录", "金币减少记录"};
    private int money;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBarWhiteNav(b.toolbar,act);
        money = act.getIntent().getIntExtra("MONEY",0);

        b.tvMoney.setText(money+"");
        _fragments = new ArrayList<>();

        _fragments.add(CoinFragment.newInstance(0));
        _fragments.add(CoinFragment.newInstance(1));
        _fragments.add(CoinFragment.newInstance(2));


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
    }

    @Override
    public void initNet() {

    }
}
