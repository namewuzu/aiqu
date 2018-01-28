package com.example.administrator.hjproject.modle.modelview;

import android.view.View;

import com.example.administrator.hjproject.base.BaseViewModle;
import com.example.administrator.hjproject.bean.SuperBean;
import com.example.administrator.hjproject.bean.UserBean;
import com.example.administrator.hjproject.bean.UserInfoPojo;
import com.example.administrator.hjproject.databinding.FragmentAssetsBinding;
import com.example.administrator.hjproject.http.ServiceApi;
import com.example.administrator.hjproject.kotlin.base.RxSubscriber;
import com.example.administrator.hjproject.utils.L;
import com.example.administrator.hjproject.utils.SpUtlis;

import org.jetbrains.annotations.NotNull;

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

public class AssetsModelView extends BaseViewModle<FragmentAssetsBinding> {

    @Override
    public void initUI() {
        b.setViewmodle(this);
    }

    @NotNull
    @Override
    public View getPageManagerView() {
        return b.llContent;
    }

    @Override
    public boolean showLoading() {
        return true;
    }

    @Override
    public void initNet() {
        initPageManager();
        showPageManagerStartLoading();
        UserData();
    }

    private void UserData() {

        ServiceApi.Factory.create().postUserInfo(SpUtlis.getToken()).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new RxSubscriber<SuperBean<UserBean<UserInfoPojo>>>(act, false) {
                    @Override
                    protected void _onError(String message) {
                        showPageManagerError();
                        L.d(message);
                    }

                    @Override
                    protected void _onNext(SuperBean<UserBean<UserInfoPojo>> pojo) {
                        showPageManagerContent();
                        b.tvMaibaoTotal.setText(String.valueOf(pojo.data.user_info.maibao_total));
                        b.tvCanMoney.setText(String.valueOf(pojo.data.user_info.can_money));
                        b.tvIntegral.setText(String.valueOf(pojo.data.user_info.integral));
                    }

                });
    }
}
