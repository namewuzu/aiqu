package com.example.administrator.hjproject.modle.modelview;

import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseViewModle;
import com.example.administrator.hjproject.bean.CensusPojo;
import com.example.administrator.hjproject.bean.SuperBean;
import com.example.administrator.hjproject.bean.TestPojo;
import com.example.administrator.hjproject.bean.succeedPojo;
import com.example.administrator.hjproject.databinding.FragmentMineBinding;
import com.example.administrator.hjproject.http.ServiceApi;
import com.example.administrator.hjproject.kotlin.base.RxSubscriber;
import com.example.administrator.hjproject.utils.SpUtlis;
import com.example.administrator.hjproject.utils.ToastUtils;
import com.example.administrator.hjproject.widget.LoadingDialog;
import com.example.administrator.hjproject.widget.recyclerview.BaseBindingItemPresenter;
import com.example.administrator.hjproject.widget.recyclerview.MultiTypeBindingAdapter;
import com.example.administrator.hjproject.widget.recyclerview.SingleTypeBindingAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.administrator.hjproject.utils.SpUtlis.getToken;

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
    public int type;
    public int payment_type;

    @Override
    public void initUI() {
        b.setViewmodle(this);
    }

    @NotNull
    @Override
    public View getPageManagerView() {
        return b.sv;
    }

    @Override
    public boolean showLoading() {
        return true;
    }

    @Override
    public void initNet() {
        b.setViewmodle(this);
        initPageManager();
        showPageManagerStartLoading();
        dataStatus();
        tenData();
        buy2mai();
        b.mlrg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb3) {
                    payment_type = 1;
                } else {
                    payment_type = 2;
                }
            }
        });
    }


    private void dataStatus() {
        RxManager(ServiceApi.Factory.create().postCensus(SpUtlis.getToken()).observeOn(AndroidSchedulers.mainThread()))
                .subscribeOn(Schedulers.io())
                .subscribe(new RxSubscriber<CensusPojo>(act, false) {
                    @Override
                    protected void _onError(String message) {
                        showPageManagerError();
                        ToastUtils.showShort(message);
                    }

                    @Override
                    protected void _onNext(CensusPojo _pojo) {
                        showPageManagerContent();
                        if (_pojo == null) return;
                        initdata(_pojo);

                    }
                });
    }


    private void tenData() {
        RxManagerArrays(ServiceApi.Factory.create().postNewTrade(getToken()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new RxSubscriber<List<TestPojo>>(act, false) {
                    @Override
                    protected void _onError(String message) {
                        showPageManagerError();
                        ToastUtils.showShort(message);
                    }

                    @Override
                    protected void _onNext(List<TestPojo> _industryPojos) {
                        showPageManagerContent();
                        initRecyclerView(_industryPojos);
                    }

                });
    }

    private void initdata(CensusPojo _pojo) {
        b.tvDealMoney.setText(String.valueOf(_pojo.deal_money));
        b.tvDealNum.setText(String.valueOf(_pojo.deal_num));
        b.tvDifferencePrice.setText(String.valueOf(_pojo.difference_price));
        b.tvLowestPrice.setText(String.valueOf(_pojo.lowest_price));
        b.tvPercentage.setText(String.valueOf(_pojo.percentage));
        b.tvRealTimePrice.setText(String.valueOf(_pojo.real_time_price));
        b.tvTiptopPrice.setText(String.valueOf(_pojo.tiptop_price));
        b.tvTodayPrice.setText(String.valueOf(_pojo.today_price));
        b.tvYesterdayPrice.setText(String.valueOf(_pojo.yesterday_price));
        if (_pojo.buy_list != null && _pojo.buy_list.size() > 0) {
            b.recycview.setAdapter(new SingleTypeBindingAdapter(act, _pojo.buy_list, R.layout.item_trading_head_recycler));
            b.recycview.setLayoutManager(new LinearLayoutManager(act));
        }
        if (_pojo.getTrade_list() != null && _pojo.getTrade_list().size() > 0) {
            b.recycview2.setAdapter(new SingleTypeBindingAdapter(act, _pojo.getTrade_list(), R.layout.item_trading_head_recycler2));
            b.recycview2.setLayoutManager(new LinearLayoutManager(act));
        }
    }

    private void initRecyclerView(List<TestPojo> _industryPojos) {
        _adapter = new MultiTypeBindingAdapter(act, _industryPojos, R.layout.item_trading);
        _adapter.setItemPresenter(this);
        b.recycview3.setAdapter(_adapter);
        b.recycview3.setLayoutManager(new LinearLayoutManager(act));
    }


    private void buy2mai() {
        b.mlrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb1) {
                    type = 0;
                    b.llPay.setVisibility(View.VISIBLE);
                    b.tvPrice.setText(R.string.buyprice);
                    b.tvCount.setText(R.string.buycout);
                    b.etMoney.setHint(R.string.typebuyprice);
                    b.etCount.setHint(R.string.typebuycout);
                    b.tvBuy.setText(R.string.buy);
                } else {
                    type = 1;
                    b.llPay.setVisibility(View.GONE);
                    b.tvPrice.setText(R.string.maiprice);
                    b.tvCount.setText(R.string.maicout);
                    b.etMoney.setHint(R.string.typemaiprice);
                    b.etCount.setHint(R.string.typemaicout);
                    b.tvBuy.setText(R.string.mai);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, TestPojo itemData) {

    }

    public void buyClick() {
        String count = b.etCount.getText().toString();
        String price = b.etMoney.getText().toString();
        int intcount;
        int intprice;
        if (TextUtils.isEmpty(count)) {
            toast("请输入买入数量");
            return;
        } else {
            intcount = Integer.parseInt(count);
        }

        if (TextUtils.isEmpty(price)) {
            toast("请输入买入价格");
            return;
        } else {
            intprice = Integer.parseInt(price);
        }
        if (type == 0) {
            LoadingDialog.showDialogForLoading(act);
           /* RxManager(ServiceApi.Factory.create().postOrderBuy(getToken(), intprice, intcount, 1, payment_type))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new RxSubscriber<succeedPojo>(act, false) {
                        @Override
                        protected void _onError(String message) {
                            ToastUtils.showShort(message);
                            LoadingDialog.cancelDialogForLoading();
                        }

                        @Override
                        protected void _onNext(succeedPojo _industryPojos) {
                            toast(_industryPojos.msg);
                            LoadingDialog.cancelDialogForLoading();
                        }

                    });*/

            ServiceApi.Factory.create().postOrderBuy(getToken(), String.valueOf(intprice), String.valueOf(intcount), String.valueOf(1), String.valueOf(payment_type)).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new RxSubscriber<SuperBean>(act, false) {
                        @Override
                        protected void _onError(String message) {
                            LoadingDialog.cancelDialogForLoading();
                            ToastUtils.showShort(message);

                        }

                        @Override
                        protected void _onNext(SuperBean pojo) {
                            LoadingDialog.cancelDialogForLoading();
                            if(pojo.code==0){
                                toast("成功");
                            }else {
                                toast(pojo.msg);
                            }


                        }

                    });


        } else {
            RxManager(ServiceApi.Factory.create().postOrderTrade(getToken(), intprice, intcount, 2))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new RxSubscriber<succeedPojo>(act, false) {
                        @Override
                        protected void _onError(String message) {
                            ToastUtils.showShort(message);
                        }

                        @Override
                        protected void _onNext(succeedPojo _industryPojos) {
                            toast(_industryPojos.msg);
                        }

                    });
        }
    }
}
