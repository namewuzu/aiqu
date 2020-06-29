package com.diankong.sexstory.mobile.modle.modelview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.ZhuboListPojo;
import com.diankong.sexstory.mobile.databinding.ActivityZhuboBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.adapter.ZhuboAdapter;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2020/1/6.
 * 描述：
 * =============================================
 */

public class ZhuboViewModle extends BaseViewModle<ActivityZhuboBinding> implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    private LinearLayout mErrorView;
    private TextView mTvError;
    private int mPage = 1;
    private ZhuboAdapter mMyAdapter;
    private int type;
    @Override
    public void initUI() {
        type = act.getIntent().getIntExtra("TYPE",0);
        ToolbarUtils.initToolBar(b.toolbar,act);
        if(type==2){
            b.tvTitle.setText("真心交友");
        }else if(type==3){
            b.tvTitle.setText("颜值聊主");
        }else{
            b.tvTitle.setText("虚拟恋人");
        }

        b.recyclerView.setErrorView(R.layout.view_error);
        b.recyclerView.setEmptyView(R.layout.view_empty);
        mErrorView = (LinearLayout) b.recyclerView.getErrorView();
        mTvError = mErrorView.findViewById(R.id.tv_error);

        mMyAdapter = new ZhuboAdapter(act);
        b.recyclerView.setLayoutManager(new GridLayoutManager(act,2));

        b.recyclerView.setRefreshingColorResources(R.color.red23);
        b.recyclerView.setAdapterWithProgress(mMyAdapter);
        getListData();
        b.recyclerView.setRefreshListener(this);

        mMyAdapter.setNoMore(R.layout.view_nomore);
        mMyAdapter.setMore(R.layout.view_more, this);

    }

    private void getListData() {
        EasyHttp.get(Api.apiurl2 + "user/getRealFriendsInfo")
                .params("pageNo", String.valueOf(mPage))
                .params("pageSize", String.valueOf(10))
                .params("userType", String.valueOf(type))
                .execute(new CallBackProxy<BaseResult<ZhuboListPojo>, ZhuboListPojo>(new SimpleCallBack<ZhuboListPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final ZhuboListPojo mTestPojo) {
                        if (mPage == 1) {
                            mMyAdapter.removeAll();
                        }
                        mMyAdapter.addAll(mTestPojo.dataList);
                        mMyAdapter.notifyDataSetChanged();
                    }

                }) {
                });

    }


    @Override
    public void initNet() {

    }

    @Override
    public void onRefresh() {
        mPage = 1;
        getListData();
    }

    @Override
    public void onLoadMore() {
        mPage++;
        getListData();
    }
}
