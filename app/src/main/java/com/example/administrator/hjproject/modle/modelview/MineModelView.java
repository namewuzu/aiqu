package com.example.administrator.hjproject.modle.modelview;

import android.support.v7.widget.LinearLayoutManager;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseViewModle;
import com.example.administrator.hjproject.bean.TestPojo;
import com.example.administrator.hjproject.databinding.FragmentMineBinding;
import com.example.administrator.hjproject.databinding.ItemTradingHeadBinding;
import com.example.administrator.hjproject.widget.recyclerview.BaseBindingItemPresenter;
import com.example.administrator.hjproject.widget.recyclerview.BindingViewHolder;
import com.example.administrator.hjproject.widget.recyclerview.MultiTypeBindingAdapter;
import com.example.administrator.hjproject.widget.recyclerview.SingleTypeBindingAdapter;

import java.util.List;

/**
 * 项目:
 * 类名: com.example.administrator.hjproject.modle.modelview$
 * 作者: created by  on $data$ $hour$
 * 标记:
 * 电话:
 * 描述: $Description$
 */

public class MineModelView extends BaseViewModle<FragmentMineBinding> implements BaseBindingItemPresenter<TestPojo> {

    private MultiTypeBindingAdapter _adapter;

    @Override
    public void initUI() {
        b.setViewmodle(this);
        initRecyclerView();

    }

    @Override
    public void initNet() {

    }

    private void initRecyclerView() {
        List<TestPojo> gettest = TestPojo.gettest();
        _adapter = new MultiTypeBindingAdapter(act, gettest, R.layout.item_trading);
        _adapter.setItemPresenter(this);
        _adapter.addSingleHeadConfig(0, R.layout.item_trading_head, new Object());
        _adapter.setHeadDecorator(new MultiTypeBindingAdapter.HeadDecorator() {
            @Override
            public void headDecorator(BindingViewHolder holder, int position, int viewType, List mData) {
                ItemTradingHeadBinding binding = (ItemTradingHeadBinding) holder.getBinding();
                List<TestPojo.TradingPojo> tradingPojo = TestPojo.TradingPojo.getTradingPojo();
                binding.recycview.setAdapter(new SingleTypeBindingAdapter(act, tradingPojo, R.layout.item_trading_head_recycler));
                binding.recycview.setLayoutManager(new LinearLayoutManager(act));
            }
        });

        b.recycview.setAdapter(_adapter);
        b.recycview.setLayoutManager(new LinearLayoutManager(act));
    }

    @Override
    public void onItemClick(int position, TestPojo itemData) {

    }
}
