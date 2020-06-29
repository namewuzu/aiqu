package com.diankong.sexstory.mobile.modle.modelview;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.BookInfoPojo;
import com.diankong.sexstory.mobile.databinding.FragmentBookcaseBinding;
import com.diankong.sexstory.mobile.databinding.ItemGeneralBinding;
import com.diankong.sexstory.mobile.event.BookInfoEvent;
import com.diankong.sexstory.mobile.event.RefreshEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.SearchActivity;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.IntentUtils;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class BookCaseViewModle extends BaseViewModle<FragmentBookcaseBinding> {

    private SingleTypeBindingAdapter _adapter;

    @Override
    public void initUI() {
        EventBus.getDefault().register(this);
        _adapter = new SingleTypeBindingAdapter(act, null, R.layout.item_general);
        b.recyclerView.setAdapter(_adapter);
        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<BookInfoPojo>() {
            @Override
            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<BookInfoPojo> mData) {
                ItemGeneralBinding binding2 = (ItemGeneralBinding) holder.getBinding();
                binding2.tvTitle.setText(mData.get(position).articlename);
                binding2.tvAuthor.setText(mData.get(position).intro);
                GlideImageLoader.onDisplayImage(getContext(), binding2.ivIcon, Api.IMGURL + mData.get(position).articleid + "/" + mData.get(position).articleid + Api.IMGURL_FOOT1);
                binding2.llContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View _view) {
                        requestCount(mData.get(position).articleid);
                        CommonInterface.getHtml(act, 1, SpUtlis.getId(), mData.get(position).articleid, mData.get(position).chapterorder, mData.get(position).chapterId, mData.get(position).upload ? 1 : 0);
                    }
                });
                binding2.tvDuration.setVisibility(View.GONE);
            }
        });
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));
        getListData();

        b.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(SearchActivity.class);
            }
        });
    }

    private void getListData() {

        EasyHttp.post(Api.apiurl + "book/getUserBooks")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("token", SpUtlis.getToken())
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("userType", SpUtlis.getUserType())
                .execute(new CallBackProxy<BaseResult<List<BookInfoPojo>>, List<BookInfoPojo>>(new SimpleCallBack<List<BookInfoPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        if(e.getCode()==800){
                            IntentUtils.toSignActivity();
                        }
                    }

                    @Override
                    public void onSuccess(final List<BookInfoPojo> mTestPojo) {
                        _adapter.clear();
                        _adapter.addAll(mTestPojo);
                    }

                }) {
                });

    }

    @Override
    public void onDestory() {
        super.onDestory();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initNet() {
    }

    public void onHiddenChanged(boolean visibleHint) {
        if (visibleHint) {
            getListData();
        }
    }


    private void requestCount(int id) {
        EasyHttp.post(Api.apiurl + "book/setBookRedRecord")
                .params("bookId", String.valueOf(id))
                .params("token", SpUtlis.getToken())
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("userType", SpUtlis.getUserType())
                .execute(new CallBackProxy<BaseResult<String>, String>(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        if(e.getCode()==800){
                            IntentUtils.toSignActivity();
                        }
                    }

                    @Override
                    public void onSuccess(final String mTestPojo) {

                    }

                }) {
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BookInfoEvent(BookInfoEvent event) {

        getListData();
        _adapter.notifyDataSetChanged();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshEvent(RefreshEvent event) {
         if(event.refresh){
             getListData();
         }
    }

    @Override
    public void onResume() {
        super.onResume();
        getListData();
        _adapter.notifyDataSetChanged();
    }
}
