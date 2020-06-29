package com.diankong.sexstory.mobile.modle.modelview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CircleResultPojo;
import com.diankong.sexstory.mobile.bean.Image;
import com.diankong.sexstory.mobile.databinding.ActivityMyLikeBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.adapter.CircleAdapter;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
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
 * 作者：Created by 胡清 on 2019/12/11.
 * 描述：
 * =============================================
 */

public class MyLikeViewModle extends BaseViewModle<ActivityMyLikeBinding>implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private int id;

    private LinearLayout mErrorView;
    private TextView mTvError;
    private int mPage = 1;
    private CircleAdapter mMyAdapter;
    private int type;
    private String url;

    private String[] split;
    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
        id = act.getIntent().getIntExtra("ID",0);
        type = act.getIntent().getIntExtra("TYPE",0);

        if(type==1){
            b.tvTitle.setText("我的点赞");
            url = Api.apiurl2 + "user/getPointCommunityInfo";
        }else{
            b.tvTitle.setText("我的分享");
            url = Api.apiurl2 + "user/getShareCommunityInfo";
        }

        b.recyclerView.setErrorView(R.layout.view_error);
        b.recyclerView.setEmptyView(R.layout.view_empty);
        mErrorView = (LinearLayout) b.recyclerView.getErrorView();
        mTvError = mErrorView.findViewById(R.id.tv_error);

        mMyAdapter = new CircleAdapter(act,1,act);
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
        EasyHttp.get(url)
                .params("pageNo", String.valueOf(mPage))
                .params("pageSize", String.valueOf(10))
                .params("userId", String.valueOf(SpUtlis.getId()))
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

                        for (int i = 0; i < mTestPojo.dataList.size(); i++) {
                            split = mTestPojo.dataList.get(i).imageUrl.split(",");
                            List<Image> _stringList = new ArrayList<>();
                            for (int j = 0; j < split.length; j++) {
                                _stringList.add(new Image(split[j], 320, 320));
                            }

                            mTestPojo.dataList.get(i).imageList = _stringList;
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
