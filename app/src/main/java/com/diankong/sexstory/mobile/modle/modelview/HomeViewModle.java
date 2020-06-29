package com.diankong.sexstory.mobile.modle.modelview;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.databinding.FragmentHomeBinding;
import com.diankong.sexstory.mobile.modle.fragment.Free2Fragment;
import com.diankong.sexstory.mobile.modle.fragment.FreeFragment;
import com.diankong.sexstory.mobile.modle.fragment.SexStoreFragment;

import java.util.ArrayList;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/20.
 * 描述：
 * =============================================
 */

public class HomeViewModle extends BaseViewModle<FragmentHomeBinding> {

    private ArrayList<Fragment> _fragments;
    private String[] titles = {"商城首页", "免费专区", "抽奖专区"};

    @Override
    public void initUI() {

        _fragments = new ArrayList<>();

        _fragments.add(SexStoreFragment.newInstance(1));
        _fragments.add(FreeFragment.newInstance(1));
        _fragments.add(Free2Fragment.newInstance(1));


        b.vpView.setAdapter(new FragmentPagerAdapter(frag.getChildFragmentManager()) {
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
