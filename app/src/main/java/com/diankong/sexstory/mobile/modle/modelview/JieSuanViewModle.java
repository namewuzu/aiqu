package com.diankong.sexstory.mobile.modle.modelview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONObject;
import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CommitPojo;
import com.diankong.sexstory.mobile.bean.LinkPojo;
import com.diankong.sexstory.mobile.databinding.ActivityJiesuanBinding;
import com.diankong.sexstory.mobile.databinding.ItemGoodsBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.PayActivity;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
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
 * 作者：Created by 胡清 on 2019/11/8.
 * 描述：
 * =============================================
 */

public class JieSuanViewModle extends BaseViewModle<ActivityJiesuanBinding> {

    private int id;
    private int orderType = 1;
    private CommitPojo _pojo;
    private SingleTypeBindingAdapter _adapter;
    private String jsonstring;
    CityPickerView mPicker;
    private JSONObject jsonObject;

    public List<CommitPojo> goods;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        id = act.getIntent().getIntExtra("ID", 0);
        goods = new ArrayList<>();
        mPicker = new CityPickerView();
        mPicker.init(act);
        _pojo = (CommitPojo) act.getIntent().getSerializableExtra("BEAN");

        goods = _pojo.goods;

        b.tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                CityConfig cityConfig = new CityConfig.Builder()
                        .title("选择城市")//标题
                        .titleTextSize(16)//标题文字大小
                        .titleTextColor("#585858")//标题文字颜  色
                        .titleBackgroundColor("#E9E9E9")//标题栏背景色
                        .confirTextColor("#585858")//确认按钮文字颜色
                        .confirmText("确定")//确认按钮文字
                        .confirmTextSize(14)//确认按钮文字大小
                        .cancelTextColor("#585858")//取消按钮文字颜色
                        .cancelText("取消")//取消按钮文字
                        .cancelTextSize(14)//取消按钮文字大小
                        .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                        .showBackground(true)//是否显示半透明背景
                        .visibleItemsCount(5)//显示item的数量
                        .province("湖南省")//默认显示的省份
                        .city("长沙市")//默认显示省份下面的城市
                        .district("岳麓区")//默认显示省市下面的区县数据
                        .provinceCyclic(true)//省份滚轮是否可以循环滚动
                        .cityCyclic(true)//城市滚轮是否可以循环滚动
                        .districtCyclic(true)//区县滚轮是否循环滚动
                        .drawShadows(false)//滚轮不显示模糊效果
                        .setLineColor("#E22323")//中间横线的颜色
                        .setLineHeigh(1)//中间横线的高度
                        .setShowGAT(false)//是否显示港澳台数据，默认不显示
                        .build();
                //添加配置
                mPicker.setConfig(cityConfig);
                //显示
                mPicker.showCityPicker();
                //监听选择点击事件及返回结果
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        super.onSelected(province, city, district);

                        //为TextView赋值
                        if (!TextUtils.isEmpty(String.valueOf(district))) {
                            b.tvCity.setText(province + "-" + city + "-" + district);
                        } else {
                            b.tvCity.setText(province + "-" + city);
                        }
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                    }
                });
            }
        });

        L.d(_pojo.fee + "-----------------------");

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

        b.tvAllPrice.setText("¥" + String.valueOf(_pojo.fee));
        b.tvFare.setText("¥" + String.valueOf(_pojo.fare));
        b.tvMoney.setText("¥" + String.valueOf(_pojo.fee + _pojo.fare));

//        getGoodsInfo();
        _adapter = new SingleTypeBindingAdapter(act, goods, R.layout.item_goods);
        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<CommitPojo>() {
            @Override
            public void decorator(BindingViewHolder holder, int position, int viewType, List<CommitPojo> mData) {
                ItemGoodsBinding itemGoodsBinding = (ItemGoodsBinding) holder.getBinding();

                itemGoodsBinding.tvName.setText(mData.get(position).goodName);
                itemGoodsBinding.tvMoney.setText(String.valueOf(mData.get(position).goodPrice) + " X " + String.valueOf(mData.get(position).count));

                GlideImageLoader.onDisplayImage(act, itemGoodsBinding.ivPic, mData.get(position).goodSmallIcon);
            }
        });

        b.recyclerView.setAdapter(_adapter);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));

        b.rlHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (b.tvGone.getText().toString().equals("点击隐藏购物商品")) {
                    b.tvGone.setText("点击展开购物商品");
                    b.recyclerView.setVisibility(View.GONE);
                    b.ivZhankai.setImageResource(R.mipmap.ai_money_arrowr);
                } else if (b.tvGone.getText().toString().equals("点击展开购物商品")) {
                    b.tvGone.setText("点击隐藏购物商品");
                    b.recyclerView.setVisibility(View.VISIBLE);
                    b.ivZhankai.setImageResource(R.mipmap.ai_money_arrow);
                }
            }
        });


        b.tvCommit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                _pojo.userId = SpUtlis.getId();
                _pojo.orderType = orderType;
                _pojo.orderStatus = 4;
                _pojo.adress = b.etAddress.getText().toString();
                _pojo.name = b.etShouhuoren.getText().toString();
                _pojo.phone = b.etPhone.getText().toString();
                _pojo.city = b.tvCity.getText().toString();
                _pojo.remark = b.etBeizhu.getText().toString();
                jsonObject = new JSONObject();
                jsonstring = jsonObject.toJSONString(_pojo);
                if (TextUtils.isEmpty(b.etShouhuoren.getText().toString())) {
                    ToastUtils.showShort("请输入收货人姓名");
                    return;
                }

                if (TextUtils.isEmpty(b.etPhone.getText().toString())) {
                    ToastUtils.showShort("请输入收货人手机号");
                    return;
                }
                if (!b.etPhone.getText().toString().startsWith("1") || b.etPhone.getText().toString().length() != 11) {
                    ToastUtils.showShort("手机号码格式有误");
                    return;
                }

                if (b.tvCity.getText().toString().equals("请选择 省-市-区县")) {
                    ToastUtils.showShort("请输入地区");
                    return;
                }

                if (TextUtils.isEmpty(b.etAddress.getText().toString())) {
                    ToastUtils.showShort("请输入详细收货地址");
                    return;
                }

                EasyHttp.post(Api.apiurl + "order/userReOrder")
                        .upJson(jsonstring)
                        .execute(new CallBackProxy<BaseResult<LinkPojo>, LinkPojo>(new SimpleCallBack<LinkPojo>() {
                            @Override
                            public void onError(ApiException e) {
                                ToastUtils.showShort(e.getMessage());

                            }

                            @Override
                            public void onSuccess(final LinkPojo _linkPojo) {
//                                WBViewActivity.startActivity(act, "支付", _linkPojo.orderString, false);

                                Bundle bundle = new Bundle();
                                bundle.putInt("TYPE", orderType);
                                bundle.putString("ORDERID", _linkPojo.orderId);
                                bundle.putDouble("FEE", _pojo.fee+_pojo.fare);
                                startActivity(PayActivity.class, bundle);
                            }

                        }) {
                        });
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
//        getGoodsInfo();
    }

    @Override
    public void initNet() {

    }

}
