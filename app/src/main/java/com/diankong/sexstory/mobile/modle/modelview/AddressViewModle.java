package com.diankong.sexstory.mobile.modle.modelview;

import android.text.TextUtils;
import android.view.View;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.LinkPojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityAddressBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
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

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/8.
 * 描述：
 * =============================================
 */

public class AddressViewModle extends BaseViewModle<ActivityAddressBinding> {

    CityPickerView mPicker;
    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        requestInfo();
        mPicker =new CityPickerView();
        mPicker.init(act);
        b.tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (TextUtils.isEmpty(b.etName.getText().toString())) {
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

                if (TextUtils.isEmpty(b.etAddresses.getText().toString())) {
                    ToastUtils.showShort("请输入地区");
                    return;
                }

                if (TextUtils.isEmpty(b.etAddress.getText().toString())) {
                    ToastUtils.showShort("请输入详细收货地址");
                    return;
                }

                commitAddress();
            }
        });

        b.llAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                //配置
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
                        if(!TextUtils.isEmpty(String.valueOf(district))){
                            b.etAddresses.setText(province + "-" + city + "-" + district);
                        }else{
                            b.etAddresses.setText(province + "-" + city);
                        }
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                    }
                });

            }
        });

    }

    private void requestInfo() {
        EasyHttp.post(Api.apiurl + "user/getUserInfor")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo _userInfoPojo) {
                        b.etAddress.setText( _userInfoPojo.address);
                        b.etPhone.setText( _userInfoPojo.phone);
                        b.etName.setText( _userInfoPojo.name);
                        b.etAddresses.setText(_userInfoPojo.city);
                    }

                }) {
                });
    }

    private void commitAddress() {
        EasyHttp.post(Api.apiurl + "order/setAddress")
                .params("id", String.valueOf(SpUtlis.getId()))
                .params("name", String.valueOf(b.etName.getText().toString()))
                .params("phone", String.valueOf(b.etPhone.getText().toString()))
                .params("city", String.valueOf(b.etAddresses.getText().toString()))
                .params("address", String.valueOf(b.etAddress.getText().toString()))
                .execute(new CallBackProxy<BaseResult<LinkPojo>, LinkPojo>(new SimpleCallBack<LinkPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final LinkPojo _linkPojo) {
                        ToastUtils.showShort("提交成功!");
                        act.finish();
                    }

                }) {
                });
    }

    @Override
    public void initNet() {

    }
}
