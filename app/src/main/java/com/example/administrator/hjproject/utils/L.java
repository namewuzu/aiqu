package com.example.administrator.hjproject.utils;

import com.orhanobut.logger.Logger;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/14.
 * 描述：
 * =============================================
 */

public class L {

    public static boolean isDebug = true;

    private L() {
    }

    public static void log(int priority, String tag, String message, Throwable throwable) {
        if (isDebug)
            Logger.log(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        if (isDebug)
            Logger.d(message, args);
    }

    public static void d(Object object) {
        if (isDebug)
            Logger.d(object);
    }

    public static void e(String message, Object... args) {
        if (isDebug)
            Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (isDebug)
            Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        if (isDebug)
            Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        if (isDebug)
            Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        if (isDebug)
            Logger.w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(String message, Object... args) {
        if (isDebug)
            Logger.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(String json) {
        if (isDebug)
            Logger.json(json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(String xml) {
        if (isDebug)
            Logger.xml(xml);
    }

}
