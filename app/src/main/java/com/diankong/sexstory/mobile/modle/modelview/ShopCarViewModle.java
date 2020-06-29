package com.diankong.sexstory.mobile.modle.modelview;

import android.support.v7.widget.LinearLayoutManager;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.ShopPojo;
import com.diankong.sexstory.mobile.databinding.FragmentShopBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.adapter.ShoppingCartAdapter;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/18.
 * 描述：
 * =============================================
 */

public class ShopCarViewModle extends BaseViewModle<FragmentShopBinding> {

    private ShoppingCartAdapter shoppingCartAdapter;
    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
//        shopCarInfo();

    }

    @Override
    public void onResume() {
        super.onResume();
        shopCarInfo();
    }


    private void shopCarInfo() {

        EasyHttp.get(Api.apiurl + "shopCar/getShopToShopCar")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<ShopPojo>, ShopPojo>(new SimpleCallBack<ShopPojo>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final ShopPojo shopPojo) {
                        shoppingCartAdapter = new ShoppingCartAdapter(act,shopPojo.cartMap,b.tvMoney,b.ck,b.recyclerView,b.tvCommit);
                        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));
                        b.recyclerView.setAdapter(shoppingCartAdapter);
                        b.recyclerView.setNestedScrollingEnabled(false);
                    }


                }) {
                });

    }





    @Override
    public void initNet() {

    }

}
