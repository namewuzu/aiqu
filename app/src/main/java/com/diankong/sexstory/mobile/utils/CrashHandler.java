package com.diankong.sexstory.mobile.utils;

/**
 * Created by apanda on 2017/8/24.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;

import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.modle.activity.MainActivity;


/**
 * @brief 异常崩溃处理类
 * @details 当程序发生未捕获异常时，由该类来接管程序并记录发送错误报告。
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    App application;

    /**
     * @param application 上下文
     * @brief 构造函数
     * @details 获取系统默认的UncaughtException处理器，设置该CrashHandler为程序的默认处理器 。
     */
    public CrashHandler(App application) {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.application = application;
    }

    /**
     * @brief 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        saveCrashInfoToFile(ex);
        // 如果用户没有处理则让系统默认的异常处理器来处理
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // 等待会后结束程序
            try {
                Thread.sleep(3000);
               // PushController.getInstance().unBind();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);
                Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
                PendingIntent restartIntent = PendingIntent.getActivity(
                        application.getApplicationContext(), 0, intent,
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                //退出程序
                AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                        restartIntent); // 1秒钟后重启应用
                //  application.();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param ex 异常
     *           [url=home.php?mod=space&uid=7300]@return[/url] true：如果处理了该异常信息；否则返回false。
     * @brief 自定义错误处理，收集错误信息
     * @details 发送错误报告等操作均在此完成
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return true;
        }
        ex.printStackTrace();
        // 提示错误消息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                //ToastUtils.showShort("应用发生异常，即将退出！");
                Looper.loop();
            }
        }.start();
        // 保存错误报告文件

        return true;
    }

    /**
     * @param ex 异常
     * @brief 保存错误信息到文件中
     */
    private void saveCrashInfoToFile(Throwable ex) {
        final StackTraceElement[] stack = ex.getStackTrace();
        final String message = ex.getMessage();
        StringBuffer buffer = new StringBuffer();
        buffer.append("====================================程序错误信息===========================================\n");
        buffer.append("操作时间：" + TimeUtils.getSystemDateToSecond() + "\n");
        final String lineFeed = "\n";
        buffer.append("错误信息：" + message + lineFeed);
        for (int i = 0; i < stack.length; i++) {
            buffer.append("详细错误：" + stack[i].toString() + lineFeed);
        }
        buffer.append("====================================程序错误信息===========================================\n");
        L.d(buffer.toString());
       // HttpLogManager.getInstance().recordErrorLog(buffer.toString());
       // HttpLogManager.getInstance().save2Local();
    }
}