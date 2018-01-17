package com.example.administrator.hjproject.modle.modelview;

import android.support.v7.widget.LinearLayoutManager;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseViewModle;
import com.example.administrator.hjproject.bean.TestPojo;
import com.example.administrator.hjproject.databinding.FragmentHistoryBinding;
import com.example.administrator.hjproject.widget.recyclerview.SingleTypeBindingAdapter;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/17.
 * 描述：
 * =============================================
 */

public class HistoryModelView extends BaseViewModle<FragmentHistoryBinding> {
    private SingleTypeBindingAdapter _adapter;

    @Override
    public void initUI() {
        b.setViewmodle(this);
        initRecycView();
    }

    @Override
    public void initNet() {

    }

    private void initRecycView() {
        List<TestPojo.HistoryPojo> historyPojo = TestPojo.HistoryPojo.getHistoryPojo();
        _adapter = new SingleTypeBindingAdapter(act, historyPojo, R.layout.item_history);
        b.recycview.setAdapter(_adapter);
        b.recycview.setLayoutManager(new LinearLayoutManager(act));

    }
}
