package com.diankong.sexstory.mobile.kotlin.http.rxexception
/**
 * Created by Administrator on 2016/11/24 0024.
 */
class NetworkConnectionException : Exception {
    constructor() : super() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}
}
