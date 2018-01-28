package com.example.administrator.hjproject.modle.modelview;

import android.view.View;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseViewModle;
import com.example.administrator.hjproject.bean.SuperBean;
import com.example.administrator.hjproject.bean.TestPojo;
import com.example.administrator.hjproject.databinding.ActivityMainBinding;
import com.example.administrator.hjproject.http.ServiceApi;
import com.example.administrator.hjproject.kotlin.base.RxSubscriber;
import com.example.administrator.hjproject.modle.fragment.BusinessFragment;
import com.example.administrator.hjproject.modle.fragment.CircleFragment;
import com.example.administrator.hjproject.modle.fragment.GuestFragment;
import com.example.administrator.hjproject.modle.fragment.MsgFragment;
import com.example.administrator.hjproject.utils.L;
import com.example.administrator.hjproject.utils.SpUtlis;

import org.jetbrains.annotations.NotNull;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/12.
 * 描述：
 * =============================================
 */

public class MainModelView extends BaseViewModle<ActivityMainBinding> {


    @Override
    public void initUI() {
        b.setViewmodle(this);
        //设置底部（可放两张图片进行点击切换）
        b.bottomTabBar.init(act.getSupportFragmentManager())
                .addTabItem("商城", R.mipmap.ic_launcher, BusinessFragment.class)
                .addTabItem("麦圈", R.mipmap.ic_launcher, CircleFragment.class)
                .addTabItem("消息", R.mipmap.ic_launcher, MsgFragment.class)
                .addTabItem("创客", R.mipmap.ic_launcher, GuestFragment.class)
                .addTabItem("我的", R.mipmap.ic_launcher, BusinessFragment.class);

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
        ServiceApi.Factory.create().getToken().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new RxSubscriber<SuperBean<TestPojo>>(act, false) {
                    @Override
                    protected void _onError(String message) {
                        showPageManagerError();
                        L.d(message);
                    }

                    @Override
                    protected void _onNext(SuperBean<TestPojo> _testPojoSuperBean) {
                        showPageManagerContent();
                        SpUtlis.saveToken(_testPojoSuperBean.data.token);
                    }

                });
    }
}
