package com.example.administrator.hjproject.kotlin.http.rxexception

/**
 * Created by Administrator on 2016/11/24 0024.
 */
interface ErrorBundle {
    fun getException(): Exception
    fun getMessage(): String
    fun getStatus():Int

}
