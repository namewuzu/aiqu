package com.diankong.sexstory.mobile.modle.modelview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.PagePojo;
import com.diankong.sexstory.mobile.databinding.ActivityHistoryBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.adapter.BookHisListAdapter;
import com.diankong.sexstory.mobile.utils.IntentUtils;
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
 * 作者：Created by 胡清 on 2019/3/23.
 * 描述：
 * =============================================
 */

public class HistoryViewModle extends BaseViewModle<ActivityHistoryBinding>  implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout mErrorView;
    private TextView mTvError;
    private int mPage = 1;
    private BookHisListAdapter mMyAdapter;
    private int sortId;
    private String typeName;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        sortId = act.getIntent().getIntExtra("sortId", 0);
        b.recyclerView.setErrorView(R.layout.view_error);
        b.recyclerView.setEmptyView(R.layout.view_empty);
        mErrorView = (LinearLayout) b.recyclerView.getErrorView();
        mTvError = mErrorView.findViewById(R.id.tv_error);

        mMyAdapter = new BookHisListAdapter(act);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));

        b.recyclerView.setRefreshingColorResources(R.color.colorAccent);
        b.recyclerView.setAdapterWithProgress(mMyAdapter);
        getListData();
        b.recyclerView.setRefreshListener(this);

        mMyAdapter.setNoMore(R.layout.view_nomore);
        mMyAdapter.setMore(R.layout.view_more, this);

    }

    private void getListData() {
        EasyHttp.post(Api.apiurl + "user/getRecordpage")
                .params("pageNo", String.valueOf(mPage))
                .params("pageSize", String.valueOf(10))
                .params("token", SpUtlis.getToken())
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("userType", SpUtlis.getUserType())
                .execute(new CallBackProxy<BaseResult<PagePojo>, PagePojo>(new SimpleCallBack<PagePojo>() {
                    @Override
                    public void onError(ApiException e) {
                        if(e.getCode()==800){
                            IntentUtils.toSignActivity();
                        }
                    }

                    @Override
                    public void onSuccess(final PagePojo mTestPojo) {
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
