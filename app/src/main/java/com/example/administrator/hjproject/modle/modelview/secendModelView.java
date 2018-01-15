package com.example.administrator.hjproject.modle.modelview;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseViewLoadingDataModle;
import com.example.administrator.hjproject.bean.Results;
import com.example.administrator.hjproject.bean.SuperBean;
import com.example.administrator.hjproject.bean.TestPojo;
import com.example.administrator.hjproject.databinding.ActivitySecendBinding;
import com.example.administrator.hjproject.utils.ToolbarUtils;
import com.example.administrator.hjproject.widget.recyclerview.BaseDataBindingAdapter;
import com.example.administrator.hjproject.widget.recyclerview.PullRecyclerView;
import com.example.administrator.hjproject.widget.recyclerview.SingleTypeBindingAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/13.
 * 描述：
 * =============================================
 */

public class secendModelView extends BaseViewLoadingDataModle<TestPojo,ActivitySecendBinding> {

    private SingleTypeBindingAdapter _adapter;

    @Override
    public void initUI() {
        //initToolBar(b.toolbar);
        ToolbarUtils.initToolBar(b.toolbar,act);
        initRecyclerView();

    }

    @Override
    public void initNet() {

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
    public Observable<SuperBean<Results<List<TestPojo>>>> getServiceApi() {
        return null;
    }

    @Override
    public void initRecyclerView() {
        _adapter = new SingleTypeBindingAdapter(act, TestPojo.gettest(), R.layout.item_issue);
        b.recycview.setAdapter(_adapter);
    }
}
