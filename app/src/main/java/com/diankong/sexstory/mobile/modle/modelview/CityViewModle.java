package com.diankong.sexstory.mobile.modle.modelview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CircleResultPojo;
import com.diankong.sexstory.mobile.databinding.CityFragmentBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.adapter.CityAdapter;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/5.
 * 描述：
 * =============================================
 */

public class CityViewModle extends BaseViewModle<CityFragmentBinding> implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener  {

    private LinearLayout mErrorView;
    private TextView mTvError;
    private int mPage = 1;
    private CityAdapter mMyAdapter;



    @Override
    public void initUI() {

        b.recyclerView.setErrorView(R.layout.view_error);
        b.recyclerView.setEmptyView(R.layout.view_empty);
        mErrorView = (LinearLayout) b.recyclerView.getErrorView();
        mTvError = mErrorView.findViewById(R.id.tv_error);

        mMyAdapter = new CityAdapter(act);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));

        b.recyclerView.setRefreshingColorResources(R.color.red23);
        b.recyclerView.setAdapterWithProgress(mMyAdapter);
        getListData();
        b.recyclerView.setRefreshListener(this);

        mMyAdapter.setNoMore(R.layout.view_nomore);
        mMyAdapter.setMore(R.layout.view_more, this);

    }

    @Override
    public void initNet() {

    }

    private void getListData() {
        EasyHttp.get(Api.apiurl2 + "community/selectTongUser")
                .params("pageNo", String.valueOf(mPage))
                .params("pageSize", String.valueOf(10))
                .params("type", String.valueOf(1))
                .execute(new CallBackProxy<BaseResult<CircleResultPojo>, CircleResultPojo>(new SimpleCallBack<CircleResultPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final CircleResultPojo mTestPojo) {
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
