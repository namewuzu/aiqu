//package com.diankong.sexstory.mobile.kotlin.base
//
//import android.content.Context
//import com.diankong.sexstory.mobile.R
//import com.diankong.sexstory.mobile.base.App
//import com.diankong.sexstory.mobile.base.BaseDialogConfig
//import com.diankong.sexstory.mobile.kotlin.http.rxexception.DefaultErrorBundle
//import com.diankong.sexstory.mobile.kotlin.http.rxexception.ErrorManager
//import com.diankong.sexstory.mobile.widget.LoadingDialog
//import rx.Subscriber
//
//
///**
// * des:订阅封装
// * Created by xsf
// * on 2016.09.10:16
// */
//
///********************
// * 使用例子
// */
//
//abstract class RxSubscriber<T> : Subscriber<T> {
//
//    private var mContext: Context? = null
//    private var msg: String? = null
//    private var showDialog = true
//
//    /**
//     * 是否显示浮动dialog
//     */
//    fun showDialog() {
//        this.showDialog = true
//    }
//
//    fun hideDialog() {
//        this.showDialog = true
//    }
//
//    @JvmOverloads constructor(context: Context, msg: String = App.getInstance().getString(R.string.loading), showDialog: Boolean = true) {
//        this.mContext = context
//        this.msg = msg
//        this.showDialog = showDialog
//    }
//
//    constructor(context: Context, showDialog: Boolean) : this(context, App.getInstance().getString(R.string.loading), showDialog) {}
//
//
//    constructor(context: Context, _config: BaseDialogConfig?) {
//        this.mContext = context
//
//        if (_config != null) {
//            this.msg = _config.showText()
//            this.showDialog = _config.showAnim()
//        }
//    }
//
//    override fun onCompleted() {
//        if (showDialog)
//            LoadingDialog.cancelDialogForLoading()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (showDialog) {
//            try {
//                LoadingDialog.showDialogForLoading(mContext)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
//    }
//
//
//    override fun onNext(t: T?) {
//        if (t != null) {
//            _onNext(t)
//        }
//        onCompleted()
//    }
//
//    override fun onError(e: Throwable) {
//        if (showDialog)
//            LoadingDialog.cancelDialogForLoading()
//        _onError(ErrorManager.handleError(DefaultErrorBundle(e as Exception)))
//
//    }
//
//    protected abstract fun _onNext(t: T)
//
//    protected abstract fun _onError(message: String)
//
//
//
//}
