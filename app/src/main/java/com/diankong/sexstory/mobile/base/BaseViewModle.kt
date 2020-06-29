package com.diankong.sexstory.mobile.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import com.diankong.sexstory.mobile.utils.ToastUtils
import com.diankong.sexstory.mobile.widget.pagemanage.PageManager


//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃   神兽保佑
//    ┃　　　┃   代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 *
 * 包名:
 * 作者: created by 小胡哥 on 2020/04/20 13:18
 * 描述:
 *
 */


abstract class BaseViewModle<B : ViewDataBinding> : ViewModleRecycle {
    protected lateinit var b: B
    protected lateinit var act: AppCompatActivity
    protected lateinit var context: Context
    protected lateinit var frag: android.support.v4.app.Fragment
    var mPageManager: PageManager? = null


    companion object

    open fun finishActivity() {
        AppManager.getAppManager().finishActivity()
    }


    /**
     *  加载动画替换的布局
     */
    open fun getPageManagerView(): Any {
        return act.window.decorView.findViewById(android.R.id.content)
    }

    /**
     * 是否显示加载动画
     */
    open fun showLoading(): Boolean {
        return false
    }


    open fun showPageManagerStartLoading() {
        if (showLoading()) {
            initPageManager()
            if (!PageManager.isNetWorkAvailable(act)) {
                PageManager.showNoNetWorkDlg(act)
                mPageManager!!.showNoNetError()
            } else {
                mPageManager!!.showLoading()
            }
        }
    }

    open fun pageManagerStartLoading() {
        if (!PageManager.isNetWorkAvailable(act)) {
            PageManager.showNoNetWorkDlg(act)
            mPageManager!!.showNoNetError()
        } else {
            mPageManager!!.showLoading()
        }
    }


    open fun showPageManagerEmpty() {
        mPageManager!!.showEmpty()
    }

    open fun showPageManagerContent() {
        mPageManager!!.showContent()
    }

    open fun showPageManagerError() {
        mPageManager!!.showError()
    }


    /**
     * 初始化加载的错误 成功 动画信息
     */
    open fun initPageManager(): PageManager {
        if (mPageManager == null) {
            val empty = "搜寻不到您的内容哦"
            val retry = "出错了,请点击重试"
            val loading = "加载中,请稍后"
            mPageManager = PageManager.init(getPageManagerView(), retry, empty, loading, getRetryAction())
        }
        mPageManager!!.showContent()
        return mPageManager!!
    }


    /**
     * 重试操作
     */
    open fun getRetryAction(): Runnable? {
        return Runnable {
            showPageManagerStartLoading()
            initNet()
        }
    }


    protected fun toast(text: String) {
        ToastUtils.showShort(context, text)
    }

    protected fun toast(text: Int) {
        ToastUtils.showShort(text)
    }


    fun initToolBar(toolbar: Toolbar) {
        act.setSupportActionBar(toolbar)
        act.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }


//    fun <T> RxManager(x: Observable<SuperBean<T>>): Observable<T> {
//        return x.compose(RxJavaHttpHelper.handleResult<T>())
//                .compose(RxSchedulersHelper.applyIoTransformer())
//    }
//
//    inline fun <reified T : Any> RxManagerArray(x: Observable<SuperBean<Results<T>>>): Observable<T> {
//        return x.compose(RxJavaHttpHelper.handleResultArray<T>())
//                .compose(RxSchedulersHelper.applyIoTransformer())
//    }
//
//
//    fun <T> RxManagerArrays(x: Observable<SuperBean<Results<T>>>): Observable<T> {
//        return x.compose(RxJavaHttpHelper.handleResultArrays<T>())
//                .compose(RxSchedulersHelper.applyIoTransformer())
//    }


    fun init(act: AppCompatActivity, b: B, c: Context) {
        context = c
    }
    fun init(act: AppCompatActivity, b: B) {
        this.b = b
        this.act = act
        initUI()
        initNet()
    }

    fun initFrag(frag: android.support.v4.app.Fragment) {
        this.frag = frag
    }


    inline fun <reified T : Activity> Activity.gotoActivity() {
        var intent = Intent(this, T::class.java)
        act.startActivity(intent)
    }


    abstract fun initUI()
    abstract fun initNet()


    fun clear() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }


    override fun onDestory() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    override fun onStart() {

    }

    override fun onCreateSavedInstanceState(savedInstanceState: Bundle?) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }


    /**
     * 通过Class跳转界面
     */
    fun startActivity(cls: Class<*>) {
        startActivity(cls, null)
    }

    /**
     * 通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?,
                               requestCode: Int) {
        val intent = Intent()
        intent.setClass(act, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        act.startActivityForResult(intent, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivity(cls: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(act, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        act.startActivity(intent)
    }

   open fun onSaveInstanceState(outState: Bundle?) {

    }


}