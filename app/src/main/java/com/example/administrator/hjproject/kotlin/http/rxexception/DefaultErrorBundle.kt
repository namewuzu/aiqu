package com.example.administrator.hjproject.kotlin.http.rxexception
import android.text.TextUtils

/**
 * Created by Administrator on 2016/11/24 0024.
 */
class DefaultErrorBundle(private val e: Exception) : ErrorBundle {

    override fun getStatus(): Int {
        val serverException = e as? ServerException
        return serverException?.code?:DEFAULT_ERROR_STATUS
    }

    override fun getException(): Exception {
        return e
    }

    override fun getMessage(): String {
        val message = e.message
        return if (message == null || TextUtils.isEmpty(message)) DEFAULT_ERROR_MSG else message
    }

    companion object {
        private val DEFAULT_ERROR_MSG = "Unknown error"
        private val DEFAULT_ERROR_STATUS =10000
    }

}
