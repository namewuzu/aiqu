package com.diankong.sexstory.mobile.modle.modelview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.GoodsOrderResultPojo;
import com.diankong.sexstory.mobile.databinding.ActivityGoodOrderListBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.adapter.GoodOrderListAdapter;
import com.diankong.sexstory.mobile.utils.SpUtlis;
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
 * 作者：Created by 胡清 on 2019/11/12.
 * 描述：
 * =============================================
 */

public class GoodOrderListViewModle extends BaseViewModle<ActivityGoodOrderListBinding> implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout mErrorView;
    private TextView mTvError;
    private int mPage = 1;
    private GoodOrderListAdapter mMyAdapter;
    private int goodTypeId;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        goodTypeId = act.getIntent().getIntExtra("goodTypeId", 0);

        b.recyclerView.setErrorView(R.layout.view_error);
        b.recyclerView.setEmptyView(R.layout.view_empty);
        mErrorView = (LinearLayout) b.recyclerView.getErrorView();
        mTvError = mErrorView.findViewById(R.id.tv_error);

        mMyAdapter = new GoodOrderListAdapter(act);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));

        b.recyclerView.setRefreshingColorResources(R.color.red23);
        b.recyclerView.setAdapterWithProgress(mMyAdapter);
        getListData();
        b.recyclerView.setRefreshListener(this);

        mMyAdapter.setNoMore(R.layout.view_nomore);
        mMyAdapter.setMore(R.layout.view_more, this);

    }

    private void getListData() {

        EasyHttp.post(Api.apiurl + "order/getPageByParam")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("orderStatus", String.valueOf(2))
                .params("pageNo", String.valueOf(mPage))
                .params("pageSize", String.valueOf(10))
                .execute(new CallBackProxy<BaseResult<GoodsOrderResultPojo>, GoodsOrderResultPojo>(new SimpleCallBack<GoodsOrderResultPojo>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final GoodsOrderResultPojo mTestPojo) {
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
