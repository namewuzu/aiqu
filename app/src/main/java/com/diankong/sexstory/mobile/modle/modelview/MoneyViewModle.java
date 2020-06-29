package com.diankong.sexstory.mobile.modle.modelview;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RadioGroup;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.LinkPojo;
import com.diankong.sexstory.mobile.bean.MoneyPojo;
import com.diankong.sexstory.mobile.bean.PayPojo;
import com.diankong.sexstory.mobile.databinding.ActivityMoneyBinding;
import com.diankong.sexstory.mobile.databinding.ItemMoneyBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.utils.AliPayUtils;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.widget.WBViewActivity;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/16.
 * 描述：
 * =============================================
 */

public class MoneyViewModle extends BaseViewModle<ActivityMoneyBinding> {

    private SingleTypeBindingAdapter _adapter;
    private int orderType = 1;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);

        b.radiogroupItcSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup _radioGroup, int _i) {

                if (b.radioButton1.getId() == _i) {
                    orderType = 1;
                } else {
                    orderType = 2;
                }

                L.d("打印------------" + orderType);
            }
        });

        getListData();

    }

    private void getListData() {


        EasyHttp.post(Api.apiurl + "coin/coinBaseInfo")
                .execute(new CallBackProxy<BaseResult<List<MoneyPojo>>, List<MoneyPojo>>(new SimpleCallBack<List<MoneyPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<MoneyPojo> moneyPojos) {
                        _adapter =  new SingleTypeBindingAdapter(act, moneyPojos, R.layout.item_money);
                        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<MoneyPojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, int position, int viewType, List<MoneyPojo> mData) {
                                ItemMoneyBinding itemMoneyBinding = (ItemMoneyBinding) holder.getBinding();
                                final MoneyPojo moneyPojo = mData.get(position);
                                itemMoneyBinding.tvNum.setText(moneyPojo.coinCount + "金币");
                                itemMoneyBinding.tvPrice.setText("￥"+moneyPojo.money );

                                itemMoneyBinding.rl.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        if(orderType==1){
                                            wexin(moneyPojo.money,moneyPojo.coinCount);

                                        }else{
//                                            zhifubao(moneyPojo.money,moneyPojo.coinCount);
                                            AliPayUtils.pay(act,moneyPojo.money,moneyPojo.coinCount);
                                        }
                                    }
                                });
                            }
                        });
                        b.recyclerView.setAdapter(_adapter);
                        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));

                    }

                }) {
                });



    }

    @Override
    public void initNet() {

    }

    private void wexin(double price,int coinCount) {
        EasyHttp.post(Api.apiurl + "coin/payforCoin")
                .params("userId",String.valueOf(SpUtlis.getId()))
                .params("fee",String.valueOf(price))
                .params("coinCount",String.valueOf(coinCount))
                .params("orderType",String.valueOf(orderType))
                .execute(new CallBackProxy<BaseResult<PayPojo>, PayPojo>(new SimpleCallBack<PayPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final PayPojo _payPojo) {

                            weChatPay(new PayPojo(_payPojo.partnerId, _payPojo.prepayId, _payPojo.sign, _payPojo.nonceStr, _payPojo.pack_age, _payPojo.appid, _payPojo.timestamp,_payPojo.extData));


                    }

                }) {
                });
    }

    private void weChatPay(PayPojo _payPojo) {

        PayReq req = new PayReq();
        req.appId = _payPojo.appid;
        req.prepayId = _payPojo.prepayId;
        req.partnerId = _payPojo.partnerId;
        req.packageValue = _payPojo.pack_age;
        req.nonceStr = _payPojo.nonceStr;
        req.timeStamp = _payPojo.timestamp;
        req.sign = _payPojo.sign;

        App.getmWxApi().sendReq(req);

    }

    private void zhifubao(double _money, int _coinCount) {
        EasyHttp.post(Api.apiurl + "coin/aliSiFangOrder")
                .params("userId",String.valueOf(SpUtlis.getId()))
                .params("fee",String.valueOf(_money))
                .params("coinCount",String.valueOf(_coinCount))
                .params("orderType",String.valueOf(orderType))
                .execute(new CallBackProxy<BaseResult<LinkPojo>, LinkPojo>(new SimpleCallBack<LinkPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final LinkPojo _linkPojo) {
                        WBViewActivity.startActivity(act, "支付", _linkPojo.orderString, false);
                    }

                }) {
                });
    }
}
