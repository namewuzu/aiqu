package com.diankong.sexstory.mobile.kotlin.http.rxexception
import com.alibaba.fastjson.JSONException
import com.diankong.sexstory.mobile.base.CooException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by Administrator on 2016/11/24 0024.
 */
class ErrorManager {

    companion object {
        /**
         * 对错误数据进行处理，返回对应字符串提示信息

         * @param e 错误数据接口
         * *
         * @return 返回对应错误提示信息
         */
        fun handleError(e: ErrorBundle): String {
            var exception = e.getException()
            exception.printStackTrace()
         //   HttpLogManager.getInstance().recordErrorLog(exception.message)
            var message: String
            when (exception) {
                is SocketTimeoutException -> message = "网络连接超时，请检查您的网络状态"
                is ConnectException -> message = "连接异常，请检查您的网络状态"
                is NetworkConnectionException -> message = "网络中断，请检查您的网络状态"
                is JSONException -> {
                    message = ""
                }
                is ServerException -> {
                    message = e.getMessage()
                }
                is CooException -> {
                    message = exception.msgDes
                }

                else -> message = "连接服务器失败,请稍后再试"
            }
            return message
        }
    }
}
