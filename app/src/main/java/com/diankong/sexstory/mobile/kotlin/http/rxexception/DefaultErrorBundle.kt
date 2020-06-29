package com.diankong.sexstory.mobile.kotlin.http.rxexception
import android.text.TextUtils

/**
 * Created by Administrator on 2016/11/24 0024.
 */
class DefaultErrorBundle(private val e: Exception) : ErrorBundle {

    override fun getStatus(): String {
        val serverException = e as? ServerException
        return serverException?.status?: DEFAULT_ERROR_STATUS_CODE
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
        private val DEFAULT_ERROR_STATUS_CODE ="000"
    }

}
