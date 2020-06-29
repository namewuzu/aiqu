package com.diankong.sexstory.mobile.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.diankong.sexstory.mobile.widget.pagemanage.PageManager;
import com.diankong.sexstory.mobile.utils.ToastUtils;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/11.
 * 描述：
 * =============================================
 */

public abstract class BaseViewModleforJava<B extends ViewDataBinding> implements ViewModleRecycleforJava {

    protected B b;
    protected AppCompatActivity a;
    protected Fragment f;
    PageManager _pageManager;
    protected Context context;

    /**
     * 加载动画替换的布局
     */
    public View getPageManagerView() {
        return a.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * 是否显示加载动画
     */
    public boolean showLoading() {
        return false;
    }

    public void showPageManagerStartLoading() {
        if (showLoading()) {
            initPageManager();
            if (!PageManager.isNetWorkAvailable(a)) {
                PageManager.showNoNetWorkDlg(a);
                _pageManager.showNoNetError();
            } else {
                _pageManager.showLoading();
            }
        }
    }

    public void pageManagerStartLoading() {
        if (!PageManager.isNetWorkAvailable(a)) {
            PageManager.showNoNetWorkDlg(a);
            _pageManager.showNoNetError();
        } else {
            _pageManager.showLoading();
        }
    }

    public void showPageManagerEmpty() {
        _pageManager.showEmpty();
    }

    public void showPageManagerContent() {
        _pageManager.showContent();
    }

    public void showPageManagerError() {
        _pageManager.showError();
    }

    /**
     * 初始化加载的错误 成功 动画信息
     */
    public PageManager initPageManager() {
        if (_pageManager == null) {
            String empty = "搜寻不到您的内容哦";
            String retry = "出错了,请点击重试";
            String loading = "加载中,请稍后";
            _pageManager = PageManager.init(getPageManagerView(), retry, empty, loading, getRetryAction());
        }
        _pageManager.showContent();
        return _pageManager;
    }

    /**
     * 重试操作
     */
    public Runnable getRetryAction() {
        return new Runnable() {
            @Override
            public void run() {
                showPageManagerStartLoading();
                initNet();
            }
        };
    }

    protected void toast(String text) {
        ToastUtils.showShort(context, text);
    }

    protected void toast(int text) {
        ToastUtils.showShort(text);
    }

    public void initToolBar(Toolbar toolbar) {
        a.setSupportActionBar(toolbar);
        a.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    protected abstract void initUI();

    protected abstract void initNet();

    public void onSaveInstanceState(Bundle outState) {

    }
    public void onCreateSavedInstanceState(Bundle _savedInstanceState) {

    }

    protected void finishActivity() {
        AppManager.getAppManager().finishActivity();
    }

    public void init(BaseActivity _context, B _mBinding) {
        this.b = _mBinding;
        this.context = _context;
        initUI();
        initNet();
    }

    public void onResume() {

    }

    public void onPause() {

    }
    public void onDestory() {

    }

    public Boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public void onStart() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void onStop() {

    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(a, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        a.startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(a, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        a.startActivity(intent);
    }


}
