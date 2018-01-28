package com.example.administrator.hjproject.modle.modelview;

import android.support.annotation.IdRes;
import android.widget.RadioGroup;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseViewLoadingDataModle;
import com.example.administrator.hjproject.bean.Results;
import com.example.administrator.hjproject.bean.SuperBean;
import com.example.administrator.hjproject.bean.TestPojo;
import com.example.administrator.hjproject.databinding.FragmentEntrustBinding;
import com.example.administrator.hjproject.http.ServiceApi;
import com.example.administrator.hjproject.kotlin.base.RxSubscriber;
import com.example.administrator.hjproject.utils.SpUtlis;
import com.example.administrator.hjproject.utils.ToastUtils;
import com.example.administrator.hjproject.widget.recyclerview.BaseBindingItemPresenter;
import com.example.administrator.hjproject.widget.recyclerview.BaseDataBindingAdapter;
import com.example.administrator.hjproject.widget.recyclerview.PullRecyclerView;
import com.example.administrator.hjproject.widget.recyclerview.SingleTypeBindingAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/17.
 * 描述：
 * =============================================
 */

public class EntrustModelView extends BaseViewLoadingDataModle<TestPojo.EntrustPojo,FragmentEntrustBinding> implements BaseBindingItemPresenter<TestPojo.EntrustPojo>{

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
                .postOrderSearch(SpUtlis.getToken(),  getPage(), 0, type);
    }

    @Override
    public void initRecyclerView() {
        b.setViewmodle(this);
        startAction();
       // List<TestPojo.EntrustPojo> entrustPojo = TestPojo.EntrustPojo.getEntrustPojo();
        _adapter = new SingleTypeBindingAdapter(act, null, R.layout.item_entrust);
        _adapter.setItemPresenter(this);
        b.recycview.setAdapter(_adapter);


    }

    @Override
    public void onDataResult(List<? extends TestPojo.EntrustPojo> t) {
        super.onDataResult(t);
    }

    @Override
    public void initUI() {
        super.initUI();
        b.mlrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        type=0;
                        break;
                    case R.id.rb2:
                        type=1;
                        break;
                    case R.id.rb3:
                        type=2;
                        break;
                }
                onRefresh();
            }
        });

    }
    @Override
    public void onItemClick(int position, TestPojo.EntrustPojo itemData) {

    }
    public void ClearClick(int position,TestPojo.EntrustPojo itemData){
        showPageManagerLoading();
        ServiceApi api = ServiceApi.Factory.create();
        api.postRevokeTrade(SpUtlis.getToken(),itemData.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new RxSubscriber<SuperBean<Object>>(context, false) {
                    @Override
                    protected void _onError(String message) {
                        ToastUtils.showShort(message);
                    }

                    @Override
                    protected void _onNext(SuperBean<Object> pojo) {

                        if (pojo.code==401) {
                            showPageManagerError();
                            toast(pojo.msg);
                        }else{
                            toast("操作成功");
                        }
                    }
                });


    }


}
