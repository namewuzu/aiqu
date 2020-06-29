package com.diankong.sexstory.mobile.modle.modelview;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.GroupTagsPojo;
import com.diankong.sexstory.mobile.databinding.ActivityHuatiBinding;
import com.diankong.sexstory.mobile.databinding.ItemHuatiBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.HotActivity;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/11.
 * 描述：
 * =============================================
 */

public class HuatiViewModle extends BaseViewModle<ActivityHuatiBinding> {

    private SingleTypeBindingAdapter _adapter;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);

        requestList();
    }

    private void requestList() {
        EasyHttp.get(Api.apiurl2 + "topic/getAllTopics")
                .execute(new CallBackProxy<BaseResult<List<GroupTagsPojo>>, List<GroupTagsPojo>>(new SimpleCallBack<List<GroupTagsPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<GroupTagsPojo> _groupTagsPojos) {


                        _adapter = new SingleTypeBindingAdapter(act, _groupTagsPojos, R.layout.item_huati);

                        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<GroupTagsPojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<GroupTagsPojo> mData) {
                                ItemHuatiBinding itemHuatiBinding = (ItemHuatiBinding) holder.getBinding();

                                String replace = mData.get(position).img.replace("\\", "");
                                GlideImageLoader.onDisplayImage(act, itemHuatiBinding.ivIcon, replace);

                                itemHuatiBinding.tvTitle.setText(mData.get(position).topicTitle);
                                itemHuatiBinding.tvDesc.setText(mData.get(position).topicDesc);
                                itemHuatiBinding.tvCount.setText(mData.get(position).zoneCount + "");
                                itemHuatiBinding.tvNum.setText(mData.get(position).peopleCount + "");

                                itemHuatiBinding.llContent.setOnClickListener(new View.OnClickListener() {
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
                        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));
                    }

                }) {
                });
    }

    @Override
    public void initNet() {

    }
}
