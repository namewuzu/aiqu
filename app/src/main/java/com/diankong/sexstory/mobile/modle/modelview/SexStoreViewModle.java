package com.diankong.sexstory.mobile.modle.modelview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.GoodTypePojo;
import com.diankong.sexstory.mobile.bean.GoodsPojo;
import com.diankong.sexstory.mobile.databinding.FragmentSexStoreBinding;
import com.diankong.sexstory.mobile.databinding.ItemGoodsTypeBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.GoodTypeActivity;
import com.diankong.sexstory.mobile.modle.activity.ShopCarActivity;
import com.diankong.sexstory.mobile.modle.adapter.AutoSwitchAdapter;
import com.diankong.sexstory.mobile.modle.adapter.sexStoreAdapter;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.widget.WBViewActivity;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class SexStoreViewModle extends BaseViewModle<FragmentSexStoreBinding> {


    public SingleTypeBindingAdapter _adapter;
    public SingleTypeBindingAdapter _adapter1;
    public SingleTypeBindingAdapter _adapter2;
    private sexStoreAdapter storeAdapter;

    @Override
    public void initUI() {

        requestBanner();

        requestGoodType();

        requestGoods();

        requestImg();

//        showDialog();

        b.ivShopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(ShopCarActivity.class);
            }
        });
    }

    private void requestImg() {
        EasyHttp.get(Api.apiurl + "common/getHotTypeGoodInfo")
                .execute(new CallBackProxy<BaseResult<GoodsPojo>, GoodsPojo>(new SimpleCallBack<GoodsPojo>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final GoodsPojo GoodsPojo) {
                        GlideImageLoader.onDisplayImage(act,b.iv1,GoodsPojo.hotList.get(0).imgUrl);
                        b.iv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                WBViewActivity.startActivity(getContext(),"商品详情",GoodsPojo.hotList.get(0).url+ "&userId="+SpUtlis.getId(),GoodsPojo.hotList.get(0).id,true);
                            }
                        });
                        GlideImageLoader.onDisplayImage(act,b.iv2,GoodsPojo.hotList.get(1).imgUrl);
                        b.iv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                WBViewActivity.startActivity(getContext(),"商品详情",GoodsPojo.hotList.get(1).url+ "&userId="+SpUtlis.getId(),GoodsPojo.hotList.get(1).id,true);
                            }
                        });
                        GlideImageLoader.onDisplayImage(act,b.iv3,GoodsPojo.hotList.get(2).imgUrl);
                        b.iv3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                WBViewActivity.startActivity(getContext(),"商品详情",GoodsPojo.hotList.get(2).url+ "&userId="+SpUtlis.getId(),GoodsPojo.hotList.get(2).id,true);
                            }
                        });
                        GlideImageLoader.onDisplayImage(act,b.iv4,GoodsPojo.qingquList.get(0).imgUrl);
                        b.iv4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                WBViewActivity.startActivity(getContext(),"商品详情",GoodsPojo.qingquList.get(0).url+ "&userId="+SpUtlis.getId(),GoodsPojo.qingquList.get(0).id,true);
                            }
                        });
                        GlideImageLoader.onDisplayImage(act,b.iv5,GoodsPojo.qingquList.get(1).imgUrl);
                        b.iv5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                WBViewActivity.startActivity(getContext(),"商品详情",GoodsPojo.qingquList.get(1).url+ "&userId="+SpUtlis.getId(),GoodsPojo.qingquList.get(1).id,true);
                            }
                        });
                        GlideImageLoader.onDisplayImage(act,b.iv6,GoodsPojo.qingquList.get(2).imgUrl);
                        b.iv6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                WBViewActivity.startActivity(getContext(),"商品详情",GoodsPojo.qingquList.get(2).url+ "&userId="+SpUtlis.getId(),GoodsPojo.qingquList.get(2).id,true);
                            }
                        });
                        GlideImageLoader.onDisplayImage(act,b.iv7,GoodsPojo.qingquList.get(3).imgUrl);
                        b.iv7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                WBViewActivity.startActivity(getContext(),"商品详情",GoodsPojo.qingquList.get(3).url+ "&userId="+SpUtlis.getId(),GoodsPojo.qingquList.get(3).id,true);
                            }
                        });

                    }

                }) {
                });
    }

    private void showDialog() {

        DialogUtils.showAlert2Mid(R.layout.dialog_show,act);

    }

    private void requestBanner() {
        EasyHttp.get(Api.apiurl + "good/getBanner")
                .execute(new CallBackProxy<BaseResult<List<GoodsPojo>>, List<GoodsPojo>>(new SimpleCallBack<List<GoodsPojo>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final List<GoodsPojo> GoodsPojo) {
                        b.vp.setAdapter(new AutoSwitchAdapter(act,GoodsPojo));

                    }

                }) {
                });

    }

    private void requestGoods() {
        EasyHttp.get(Api.apiurl + "common/getPageDate")
                .execute(new CallBackProxy<BaseResult<List<GoodsPojo>>, List<GoodsPojo>>(new SimpleCallBack<List<GoodsPojo>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final List<GoodsPojo> mTestPojo) {
                        storeAdapter = new sexStoreAdapter(act);
                        storeAdapter.addAll(mTestPojo);
                        b.recyclerView.setNestedScrollingEnabled(false);
                        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));
                        storeAdapter.notifyDataSetChanged();
                        b.recyclerView.setAdapter(storeAdapter);


                    }

                }) {
                });
    }

    private void requestGoodType() {
        EasyHttp.get(Api.apiurl + "good/getGoodType")
                .execute(new CallBackProxy<BaseResult<List<GoodTypePojo>>, List<GoodTypePojo>>(new SimpleCallBack<List<GoodTypePojo>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final List<GoodTypePojo> mTestPojo) {

                        _adapter1 = new SingleTypeBindingAdapter(act, mTestPojo, R.layout.item_goods_type);

                        _adapter1.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<GoodTypePojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<GoodTypePojo> mData) {
                                ItemGoodsTypeBinding binding = (ItemGoodsTypeBinding) holder.getBinding();
                                binding.tvName.setText(mData.get(position).goodTypeName);

                                GlideImageLoader.onDisplayImage(act, binding.ivPic, mData.get(position).goodTypeIcon);

                                final GoodTypePojo goodTypePojo = mData.get(position);


                                binding.ivPic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("goodTypeId", goodTypePojo.id);
                                        bundle.putString("TITLE",mData.get(position).goodTypeName);
                                        startActivity(GoodTypeActivity.class, bundle);
                                    }
                                });
                            }
                        });

                        b.recyclerView1.setAdapter(_adapter1);
                        b.recyclerView1.setLayoutManager(new GridLayoutManager(act, 5));

                    }

                }) {
                });
    }

    @Override
    public void initNet() {
    }


}
