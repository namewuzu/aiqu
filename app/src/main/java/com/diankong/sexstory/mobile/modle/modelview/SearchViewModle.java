package com.diankong.sexstory.mobile.modle.modelview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.ZhuboListPojo;
import com.diankong.sexstory.mobile.databinding.ActivitySearchBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.adapter.SearchAdapter;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
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
 * 作者：Created by 胡清 on 2019/3/25.
 * 描述：
 * =============================================
 */

public class SearchViewModle extends BaseViewModle<ActivitySearchBinding> implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    CityPickerView mPicker;
    OptionsPickerView pvCustomOptions;
    private LinearLayout mErrorView;
    private TextView mTvError;
    private int mPage = 1;
    private SearchAdapter mMyAdapter;
    public String citys = "";
    public int sex;
    public int startAge;
    public int endAge;
    public int timeSort;
    private String nickName = "";


    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        mPicker = new CityPickerView();
        mPicker.init(act);

        b.llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                CityConfig cityConfig = new CityConfig.Builder()
                        .title("选择城市")//标题
                        .titleTextSize(16)//标题文字大小
                        .titleTextColor("#1183FF")//标题文字颜  色
                        .titleBackgroundColor("#E9E9E9")//标题栏背景色
                        .confirTextColor("#1183FF")//确认按钮文字颜色
                        .confirmText("确定")//确认按钮文字
                        .confirmTextSize(14)//确认按钮文字大小
                        .cancelTextColor("#1183FF")//取消按钮文字颜色
                        .cancelText("取消")//取消按钮文字
                        .cancelTextSize(14)//取消按钮文字大小
                        .setCityWheelType(CityConfig.WheelType.PRO_CITY)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                        .showBackground(true)//是否显示半透明背景
                        .visibleItemsCount(5)//显示item的数量
                        .province("湖南省")//默认显示的省份
                        .city("长沙市")//默认显示省份下面的城市
                        .district("岳麓区")//默认显示省市下面的区县数据
                        .provinceCyclic(true)//省份滚轮是否可以循环滚动
                        .cityCyclic(true)//城市滚轮是否可以循环滚动
                        .districtCyclic(true)//区县滚轮是否循环滚动
                        .drawShadows(false)//滚轮不显示模糊效果
                        .setLineColor("#1183FF")//中间横线的颜色
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
                            b.tvAddress.setText(province + "-" + city + "-" + district);
                        } else {
                            b.tvAddress.setText(province + "-" + city);
                        }
                        citys = String.valueOf(city);
                        mPage = 1;
                        getListData();
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                    }
                });
            }
        });

        b.llSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                initSex();
            }
        });

        b.llAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                initAge();
            }
        });

        b.llTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                initTime();
            }
        });

        b.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(!TextUtils.isEmpty(b.etTitle.getText().toString())){
                    initSearch();
                }
            }
        });

        b.recyclerView.setErrorView(R.layout.view_error);
        b.recyclerView.setEmptyView(R.layout.view_empty);
        mErrorView = (LinearLayout) b.recyclerView.getErrorView();
        mTvError = mErrorView.findViewById(R.id.tv_error);

        mMyAdapter = new SearchAdapter(act);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));
        getListData();
        b.recyclerView.setRefreshingColorResources(R.color.red23);
        b.recyclerView.setAdapterWithProgress(mMyAdapter);
        b.recyclerView.setRefreshListener(this);

        mMyAdapter.setNoMore(R.layout.view_nomore);
        mMyAdapter.setMore(R.layout.view_more, this);
    }

    private void initSearch() {
        nickName = b.etTitle.getText().toString();
        mPage = 1;
        getListData();
    }

    private void initAge() {
        final List<String> cardItem = new ArrayList<>();
        cardItem.add("20岁以下");
        cardItem.add("20-29");
        cardItem.add("30-39");
        cardItem.add("40-49");
        cardItem.add("50-59");
        cardItem.add("60-69");
        cardItem.add("70岁以上");

        pvCustomOptions = new OptionsPickerBuilder(act, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                ToastUtils.showShort(cardItem.get(options1));
                b.tvAge.setText(cardItem.get(options1));

                if(cardItem.get(options1).equals("20岁以下")){
                    startAge=0;
                    endAge=20;
                }else if(cardItem.get(options1).equals("20-29")){
                    startAge=20;
                    endAge=29;
                }else if(cardItem.get(options1).equals("30-39")){
                    startAge=30;
                    endAge=39;
                }else if(cardItem.get(options1).equals("40-49")){
                    startAge=40;
                    endAge=49;
                }else if(cardItem.get(options1).equals("50-59")){
                    startAge=50;
                    endAge=59;
                }else if(cardItem.get(options1).equals("60-69")){
                    startAge=60;
                    endAge=69;
                }else if(cardItem.get(options1).equals("70岁以上")){
                    startAge=70;
                    endAge=100;
                }
                mPage = 1;
                getListData();
            }
        }).build();
        pvCustomOptions.setPicker(cardItem);
        pvCustomOptions.show();
    }

    private void initSex() {
        final List<String> cardItem = new ArrayList<>();
        cardItem.add("不限");
        cardItem.add("男");
        cardItem.add("女");

        pvCustomOptions = new OptionsPickerBuilder(act, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                ToastUtils.showShort(cardItem.get(options1));
                b.tvSex.setText(cardItem.get(options1));

                if(cardItem.get(options1).equals("男")){
                    sex=1;
                }else if(cardItem.get(options1).equals("女")){
                    sex=2;
                }else{
                    sex=0;
                }
                mPage = 1;
                getListData();
            }
        }).build();
        pvCustomOptions.setPicker(cardItem);
        pvCustomOptions.show();
    }

    private void initTime() {
        final List<String> cardItem = new ArrayList<>();
        cardItem.add("不限");
        cardItem.add("最后登陆");
        cardItem.add("最新注册");

        pvCustomOptions = new OptionsPickerBuilder(act, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                ToastUtils.showShort(cardItem.get(options1));
                b.tvTime.setText(cardItem.get(options1));

                if(cardItem.get(options1).equals("最后登陆")){
                    timeSort=1;
                }else if(cardItem.get(options1).equals("最新注册")){
                    timeSort=2;
                }else{
                    timeSort=0;
                }
                mPage = 1;
                getListData();
            }
        }).build();
        pvCustomOptions.setPicker(cardItem);
        pvCustomOptions.show();
    }

    @Override
    public void initNet() {

    }

    private void getListData() {
        EasyHttp.post(Api.apiurl2 + "user/selectUserPageByParam")
                .params("pageNo", String.valueOf(mPage))
                .params("pageSize", String.valueOf(10))
                .params("city", String.valueOf(citys))
                .params("sex", String.valueOf(sex))
                .params("startAge", String.valueOf(startAge))
                .params("endAge", String.valueOf(endAge))
                .params("timeSort", String.valueOf(timeSort))
                .params("nickName", String.valueOf(nickName))
                .execute(new CallBackProxy<BaseResult<ZhuboListPojo>, ZhuboListPojo>(new SimpleCallBack<ZhuboListPojo>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final ZhuboListPojo mTestPojo) {
                        if (mPage == 1) {
                            mMyAdapter.removeAll();
                        }
                        mMyAdapter.addAll(mTestPojo.dataList);
                        mMyAdapter.notifyDataSetChanged();

//                        if(mTestPojo.dataList.size()<10){
//                            mPage = 1;
//                        }
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
