package com.diankong.sexstory.mobile.modle.modelview;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.GoodsPojo;
import com.diankong.sexstory.mobile.bean.TestPojo;
import com.diankong.sexstory.mobile.databinding.FragmentBookstoryBinding;
import com.diankong.sexstory.mobile.modle.activity.SearchActivity;
import com.diankong.sexstory.mobile.modle.activity.TypeActivity;
import com.diankong.sexstory.mobile.modle.adapter.AutoSwitchAdapter;
import com.diankong.sexstory.mobile.modle.fragment.HomeFragment;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/20.
 * 描述：
 * =============================================
 */

public class BookStoryViewModle extends BaseViewModle<FragmentBookstoryBinding> {

    private SingleTypeBindingAdapter _adapter;

    private ArrayList<Fragment> _fragments;
    private String[] titles = {"精选", "男频", "女频"};

    @Override
    public void initUI() {

        _fragments = new ArrayList<>();

        _fragments.add(HomeFragment.newInstance(1));
        _fragments.add(HomeFragment.newInstance(2));
        _fragments.add(HomeFragment.newInstance(3));


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

        b.vpView.setOffscreenPageLimit(0);
        b.tabs.setupWithViewPager(b.vpView);
        b.tabs.setTabMode(TabLayout.MODE_FIXED);

        _adapter = new SingleTypeBindingAdapter(act, null, R.layout.item_issue);
        getData();
        b.recyclerView.setAdapter(_adapter);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));
        List<GoodsPojo> mData = new ArrayList<>();
//        mData.add(new LoopViewPojo("http://bmob-cdn-10899.b0.upaiyun.com/2017/05/09/1add12e3407aa2ac80899838f5e5a097.jpg"));
//        mData.add(new LoopViewPojo("http://bmob-cdn-10899.b0.upaiyun.com/2017/05/09/1add12e3407aa2ac80899838f5e5a097.jpg"));
//        mData.add(new LoopViewPojo("http://bmob-cdn-10899.b0.upaiyun.com/2017/05/09/1add12e3407aa2ac80899838f5e5a097.jpg"));
        AutoSwitchAdapter mAdapter = new AutoSwitchAdapter(act, mData);
        b.vp.setAdapter(mAdapter);

        b.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(TypeActivity.class);
            }
        });

        b.flSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(SearchActivity.class);
            }
        });
    }

    private void getData() {
        List<TestPojo> testPojos = new ArrayList<>();
        testPojos.add(new TestPojo("123"));
        testPojos.add(new TestPojo("456"));
        testPojos.add(new TestPojo("789"));
        _adapter.addAll(testPojos);
    }

    @Override
    public void initNet() {

    }
//
//    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
//        Class<?> tabLayout = tabs.getClass();
//        Field tabStrip = null;
//        try {
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        tabStrip.setAccessible(true);
//        LinearLayout llTab = null;
//        try {
//            llTab = (LinearLayout) tabStrip.get(tabs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
//        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
//
//        for (int i = 0; i < llTab.getChildCount(); i++) {
//            View child = llTab.getChildAt(i);
//            child.setPadding(0, 0, 0, 0);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.leftMargin = left;
//            params.rightMargin = right;
//            child.setLayoutParams(params);
//            child.invalidate();
//        }
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        b.tabs.post(new Runnable() {
//
//            @Override
//
//            public void run() {
//
//                setIndicator(b.tabs, 50, 50);
//
//            }
//
//        });
//    }


}
