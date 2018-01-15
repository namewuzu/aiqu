package com.example.administrator.hjproject.base

import android.databinding.BaseObservable
import android.databinding.ViewDataBinding
import com.example.administrator.hjproject.bean.Results
import com.example.administrator.hjproject.bean.SuperBean
import com.example.administrator.hjproject.kotlin.base.RxSubscriber
import com.example.administrator.hjproject.utils.L
import com.example.administrator.hjproject.utils.ToastUtils
import com.example.administrator.hjproject.widget.recyclerview.BaseDataBindingAdapter
import com.example.administrator.hjproject.widget.recyclerview.PullRecyclerView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


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
 * 作者: created by 熊凯 on 2017/6/27 13:18
 * 描述:
 *
 */

/**
 * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 * ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
 * │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
 * ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
 * │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
 * ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
 * │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
 * ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
 * │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
 * ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
 * │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
 * └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘
 */

abstract class BaseViewLoadingDataModle<M : BaseObservable, B : ViewDataBinding> : BaseViewModle<B>(), ViewModleRecycle, PullRecyclerView.OnLoadMoreListener {

    val ACTION_FIRSTLOAD = 1
    val ACTION_REFRSHING = 2
    val ACTION_LOADING = 3
    var action = ACTION_FIRSTLOAD
    open var count = 20
    open var page = 1
    lateinit var recyclerView: PullRecyclerView
    var isFirstIn = true

    /**
     * 刷新的recyclerview
     */
    abstract fun getPullRecyclerView(): PullRecyclerView;

    /**
     * 设配器
     */
    abstract fun getBaseDataBindingAdapter(): BaseDataBindingAdapter<*>

    /**
     * 获取网络访问api
     */
    abstract fun getServiceApi(): Observable<SuperBean<Results<List<M>>>>


    override fun initUI() {
        initRecyclerView()
        recyclerView = getPullRecyclerView()
        recyclerView.setOnLoadMoreListener(this)
        recyclerView.setAdapter(getBaseDataBindingAdapter())
    }

    override fun initNet() {

    }

    fun startAction() {
        when (action) {
            ACTION_REFRSHING -> page = 1
            ACTION_LOADING -> page++
            ACTION_FIRSTLOAD -> page = 1
        }
        initListNet()
    }


    abstract fun initRecyclerView()

    fun initListNet() {

        val api = getServiceApi()
        if (api == null) {
            return
        }
        RxManagerArray(api)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : RxSubscriber<List<M>>(context, false) {
                    override fun onStart() {
                        super.onStart()
                        setUpStart()
                    }

                    override fun _onNext(t: List<M>) {
                        isFirstIn = false
                        setUpListData(t)
                    }

                    override fun _onError(message: String) {
                        L.d(message)
                        ToastUtils.showShort(message)
                        isFirstIn = false
                        setUpError(message)
                    }

                })
    }

    /**
     * 初始化操作 网络动画
     */
    private fun setUpStart() {
        when (action) {
            ACTION_FIRSTLOAD -> {
                if (isRefreshDataNotShowLoading) return
                showPageManagerLoading()
            }

            ACTION_REFRSHING -> recyclerView.setRefreshState(true)
            ACTION_LOADING -> recyclerView.LoadMoreStart()
        }
    }

    /**
     * 获取数据后的操作
     */
    private fun setUpListData(listInfoPojos: List<M>) {

        onDataResult(listInfoPojos)
        when (action) {
            ACTION_FIRSTLOAD -> {
                if (listInfoPojos.isEmpty()) {
                    recyclerView.setRefreshLoaderMore(true, false)
                    if (headDataReady()) {
                        showPageManagerContent()
                    } else {
                        if (isRefreshDataNotShowLoading) return
                        showPageManagerEmpty()
                    }
                    return
                }
                if (listInfoPojos.size < count) {
                    recyclerView.setRefreshLoaderMore(true, false)
                } else if (listInfoPojos.size >= count)
                    recyclerView.setRefreshLoaderMore(true, true)
                showPageManagerContent()
                if (isAutoRefreshData)
                    getBaseDataBindingAdapter().refresh(listInfoPojos)
                onViewModelSuccess(action)

            }
            ACTION_REFRSHING -> {
                if (listInfoPojos.isNotEmpty()) {
                    showPageManagerContent()
                    if (isAutoRefreshData)
                        getBaseDataBindingAdapter().refresh(listInfoPojos)
                } else {
                    if (headDataReady()) {
                        showPageManagerContent()
                    } else
                        showPageManagerEmpty()
                }
                recyclerView.setRefreshState(false)
                onViewModelSuccess(action)
            }
            ACTION_LOADING -> {
                if (listInfoPojos.isNotEmpty()) {
                    onViewModelSuccess(action)
                    if (isAutoRefreshData)
                        getBaseDataBindingAdapter().addAll(listInfoPojos)
                    showPageManagerContent()
                } else {
                    page--
                }
            }
        }
        recyclerView.noMoreData(listInfoPojos, count)

    }


    /**
     * 获取错误的情况
     */
    private fun setUpError(errorInfo: String) {
        when (action) {
            ACTION_FIRSTLOAD -> {
                if (isRefreshDataNotShowLoading) return
                page = 1
                showPageManagerError()
            }
            ACTION_REFRSHING -> {
                page = 1
                recyclerView.refreshEnd()
            }
            ACTION_LOADING -> {
                page--
                recyclerView.loadMoreError()
            }
        }
        onViewModelError(action, errorInfo)
    }

    open fun onViewModelError(action: Int, errorInfo: String) {

    }

    open fun onViewModelEmpty(action: Int) {

    }

    open fun onViewModelSuccess(action: Int) {

    }


    protected fun showPageManagerLoading() {
        initPageManager()
        if (showLoading()) {
            mPageManager!!.showLoading()
        }
        onViewModelStart()
    }

    open fun onViewModelStart() {

    }


    open fun headDataReady(): Boolean {
        return false
    }


    override fun showPageManagerEmpty() {
        super.showPageManagerEmpty()
        onViewModelEmpty(action)
    }


    private var isAutoRefreshData = true

    fun setIsAutoRefreshData(isAutoRefreshData: Boolean) {
        this.isAutoRefreshData = isAutoRefreshData
    }


    /**
     * 重试操作
     */
    override fun getRetryAction(): Runnable? {
        return Runnable {
            showPageManagerLoading()
            initListNet()
        }
    }

    open fun onDataResult(t: List<M>) {

    }

    protected fun onEmpty() {}


    /**
     * 刷新
     */
    override fun onRefresh() {
        action = ACTION_REFRSHING
        startAction()
    }

    /**
     * 加载
     */
    override fun onLoadMore() {
        action = ACTION_LOADING
        startAction()
    }

    var isRefreshDataNotShowLoading = false
    fun refresh() {
        action = ACTION_FIRSTLOAD
        isRefreshDataNotShowLoading = true
        startAction()
    }

}