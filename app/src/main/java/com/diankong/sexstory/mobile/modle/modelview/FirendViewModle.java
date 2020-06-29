package com.diankong.sexstory.mobile.modle.modelview;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.GroupTagsPojo;
import com.diankong.sexstory.mobile.databinding.FirendFragmentBinding;
import com.diankong.sexstory.mobile.databinding.ItemHotBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.HotActivity;
import com.diankong.sexstory.mobile.modle.activity.HuatiActivity;
import com.diankong.sexstory.mobile.modle.activity.SearchActivity;
import com.diankong.sexstory.mobile.modle.activity.ZhuboActivity;
import com.diankong.sexstory.mobile.modle.fragment.CircleFragment;
import com.diankong.sexstory.mobile.modle.fragment.CityFragment;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.widget.GlideRoundTransform;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/5.
 * 描述：
 * =============================================
 */

public class FirendViewModle extends BaseViewModle<FirendFragmentBinding> {

    private ArrayList<Fragment> _fragments;
    private String[] titles = {"热门动态", "最新动态", "同城的人"};
    private SingleTypeBindingAdapter _adapter;
    private List<GroupTagsPojo> _pojos;

    @Override
    public void initUI() {
        _fragments = new ArrayList<>();
        _pojos = new ArrayList<>();
        _fragments.add(CircleFragment.newInstance(1));
        _fragments.add(CircleFragment.newInstance(2));
        _fragments.add(CityFragment.newInstance(1));


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

        b.vpView.setOffscreenPageLimit(0);
        b.tabs.setupWithViewPager(b.vpView);
        b.tabs.setTabMode(TabLayout.MODE_FIXED);


        requestheadPic();

        b.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(HuatiActivity.class);
            }
        });

        b.llJiaoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(act, ZhuboActivity.class);
                intent.putExtra("TYPE",2);
                act.startActivity(intent);
            }
        });

        b.llLiaozhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(act, ZhuboActivity.class);
                intent.putExtra("TYPE",3);
                act.startActivity(intent);
            }
        });

        b.llLianren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(act, ZhuboActivity.class);
                intent.putExtra("TYPE",4);
                act.startActivity(intent);
            }
        });

        b.ivMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                CommonInterface.GoChatList(act);
            }
        });

        b.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(SearchActivity.class);
            }
        });


    }

    private void requestheadPic() {
        EasyHttp.get(Api.apiurl2 + "topic/getAllTopics")
                .execute(new CallBackProxy<BaseResult<List<GroupTagsPojo>>, List<GroupTagsPojo>>(new SimpleCallBack<List<GroupTagsPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<GroupTagsPojo> _groupTagsPojos) {

                        _pojos = _groupTagsPojos.subList(1,7);

                        _adapter= new SingleTypeBindingAdapter(act,_pojos, R.layout.item_hot);

                        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<GroupTagsPojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<GroupTagsPojo> mData) {
                                ItemHotBinding hotBinding = (ItemHotBinding) holder.getBinding();
                                String replace = mData.get(position).img.replace("\\", "");
//                                GlideImageLoader.onDisplayImage(act,hotBinding.iv, replace);
                                Glide.with(act)
                                        .load(replace)
                                        .transform(new CenterCrop(act),new GlideRoundTransform(act))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .placeholder(R.mipmap.ic_mrlg)
                                        .dontAnimate()
                                        .crossFade()
                                        .into(hotBinding.iv);

                                hotBinding.iv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        Intent intent = new Intent(act,HotActivity.class);
                                        intent.putExtra("POJO",mData.get(position));
                                        act.startActivity(intent);
                                    }
                                });

                            }
                        });

                        b.recyclerView.setAdapter(_adapter);
                        b.recyclerView.setLayoutManager(new GridLayoutManager(act,3));
                    }

                }) {
                });

    }

    @Override
    public void initNet() {

    }
}
