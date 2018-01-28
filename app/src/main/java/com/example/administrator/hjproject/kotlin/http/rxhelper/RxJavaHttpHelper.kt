package com.example.administrator.hjproject.kotlin.http.rxhelper


import com.example.administrator.hjproject.bean.Results
import com.example.administrator.hjproject.bean.SuperBean
import com.example.administrator.hjproject.kotlin.http.rxexception.NetworkConnectionException
import com.example.administrator.hjproject.kotlin.http.rxexception.ServerException
import com.example.administrator.hjproject.utils.ToastUtils
import rx.Observable
import rx.functions.Func1

/**
 * Created by Administrator on 2017/6/21 0021.
 */
class RxJavaHttpHelper {
    companion object Factory {
        /**
         * 默认 使用
         */
        fun <T> handleResult(): Observable.Transformer<SuperBean<T>, T> {
            val tTransformer = Observable.Transformer<SuperBean<T>, T> {
                baseRequestDataObservable ->
                val observable = baseRequestDataObservable.flatMap(Func1<SuperBean<T>, Observable<T>> { pojo ->
                    if (pojo == null) {
                        return@Func1 Observable.error(NetworkConnectionException("网络错误..."))
                    } else {
                      //  if(pojo.issuccess) {
                            when (pojo.code) {
                            //创建一个观察者
                                0 -> {
                                    if (pojo.data == null) {
                                        return@Func1 Observable.error(NetworkConnectionException("网络错误..."))
                                    } else {
                                        return@Func1 createObservable(pojo.data)
                                    }
                                }
                               // 203 -> IntentUtils.toSignActivity()
                                401 -> ToastUtils.showShort("錯誤")

                            }
                            Observable.error<T>(ServerException(pojo.code, pojo.msg))
                     //   }else {
                     //       Observable.error<T>(ServerException(pojo.status, pojo.message))
                     //   }

                    }
                })
                observable
            }
            return tTransformer

        }


        /**
         *  如果是返回数据 是 一个 数组,并且是在data 下使用了 result 包裹的情况下使用。
         */
        inline fun <reified T : Any> handleResultArray(): Observable.Transformer<SuperBean<Results<T>>, T> {
            val tTransformer = Observable.Transformer<SuperBean<Results<T>>, T> {
                baseRequestDataObservable ->
                val observable = baseRequestDataObservable.flatMap(Func1<SuperBean<Results<T>>, Observable<T>> { pojo ->
                    if (pojo == null) {
                        return@Func1 Observable.error<T>(NetworkConnectionException("网络错误..."))
                    } else {
                        when (pojo.code) {

                            0 -> {
                                if (pojo.data == null) {
                                    return@Func1 Observable.error<T>(NetworkConnectionException("网络错误..."))
                                } else {
                                    return@Func1 createObservable(pojo.data.list)
                                }
                            }
                          //  203 -> IntentUtils.toSignActivity()
                            else -> return@Func1 Observable.error<T>(ServerException(200, "客户端数据解析异常..."))
                        }
                        Observable.error(ServerException(pojo.code, pojo.msg))
                    }
                })
                observable
            }
            return tTransformer

        }


        fun <T> handleResultArrays(): Observable.Transformer<SuperBean<Results<T>>, T> {
            val tTransformer = Observable.Transformer<SuperBean<Results<T>>, T> {
                baseRequestDataObservable ->
                val observable = baseRequestDataObservable.flatMap(Func1<SuperBean<Results<T>>, Observable<T>> { pojo ->
                    if (pojo == null) {
                        return@Func1 Observable.error<T>(NetworkConnectionException("网络错误..."))
                    } else {
                        when (pojo.code) {

                            0 -> {
                                if (pojo.data == null) {
                                    return@Func1 Observable.error<T>(NetworkConnectionException("网络错误..."))
                                } else {
                                    return@Func1 createObservable(pojo.data.list)
                                }
                            }
                         //   203 -> IntentUtils.toSignActivity()
                            else -> return@Func1 Observable.error<T>(ServerException(200, "客户端数据解析异常..."))
                        }
                        Observable.error(ServerException(pojo.code, pojo.msg))
                    }
                })
                observable
            }
            return tTransformer
        }


        fun <T> createObservable(data: T): Observable<T> {
            return Observable.create {
                subscriber ->
                try {
                    subscriber.onNext(data)
                } catch (e: Exception) {
                    subscriber.onError(e)
                }
            }
        }


    }


}