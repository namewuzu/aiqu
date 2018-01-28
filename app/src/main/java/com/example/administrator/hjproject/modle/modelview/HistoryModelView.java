package com.example.administrator.hjproject.modle.modelview;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseViewLoadingDataModle;
import com.example.administrator.hjproject.bean.Results;
import com.example.administrator.hjproject.bean.SuperBean;
import com.example.administrator.hjproject.bean.TestPojo;
import com.example.administrator.hjproject.databinding.FragmentHistoryBinding;
import com.example.administrator.hjproject.http.ServiceApi;
import com.example.administrator.hjproject.utils.SpUtlis;
import com.example.administrator.hjproject.widget.recyclerview.BaseBindingItemPresenter;
import com.example.administrator.hjproject.widget.recyclerview.BaseDataBindingAdapter;
import com.example.administrator.hjproject.widget.recyclerview.BindingViewHolder;
import com.example.administrator.hjproject.widget.recyclerview.PullRecyclerView;
import com.example.administrator.hjproject.widget.recyclerview.SingleTypeBindingAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/17.
 * 描述：
 * =============================================
 */

public class HistoryModelView extends BaseViewLoadingDataModle<TestPojo.EntrustPojo,FragmentHistoryBinding>implements BaseBindingItemPresenter<TestPojo.EntrustPojo> {
    private SingleTypeBindingAdapter _adapter;
    private int type;

    @Override
    public boolean showLoading() {
        return true;
    }

    @NotNull
    @Override
    public Object getPageManagerView() {
        return b.recycview;
    }

    @NotNull
    @Override
    public PullRecyclerView getPullRecyclerView() {
        return b.recycview;
    }

    @NotNull
    @Override
    public BaseDataBindingAdapter<?> getBaseDataBindingAdapter() {
        return _adapter;
    }

    @NotNull
    @Override
    public Observable<SuperBean<Results<List<TestPojo.EntrustPojo>>>> getServiceApi() {
        return ServiceApi
                .Factory
                .create()
                .postOrderSearch(SpUtlis.getToken(),  getPage(), 2, type);
    }

    @Override
    public void initRecyclerView() {
        b.setViewmodle(this);
        startAction();
        // List<TestPojo.EntrustPojo> entrustPojo = TestPojo.EntrustPojo.getEntrustPojo();
        _adapter = new SingleTypeBindingAdapter(act, null, R.layout.item_history);
        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<TestPojo.EntrustPojo>() {
            @Override
            public void decorator(BindingViewHolder holder, int position, int viewType, List<TestPojo.EntrustPojo> mData) {

            }
        });
        _adapter.setItemPresenter(this);
        b.recycview.setAdapter(_adapter);


    }

    @Override
    public void onDataResult(List<? extends TestPojo.EntrustPojo> t) {
        super.onDataResult(t);
    }

    @Override
    public void onItemClick(int position, TestPojo.EntrustPojo itemData) {

    }


}
