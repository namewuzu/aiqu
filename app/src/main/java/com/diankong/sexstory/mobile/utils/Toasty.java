package com.diankong.sexstory.mobile.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by apanda on 2017/4/21.
 */

public class Toasty {

    public static final int LENGTH_MAX = -1;
    private boolean mCanceled = true;
    /**
     * @category 判断runnable 是否正在运行
     */
    private Handler mHandler;
    private Context mContext;
    private Toast mToast;

    public Toasty(Context context) {
        this(context, new Handler());
    }

    public Toasty(Context context, Handler h) {
        mContext = context;
        mHandler = h;
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
    }

    public void show(int resId, int duration) {
        mToast.setText(resId);
        if (duration != LENGTH_MAX) {
            mToast.setDuration(duration);
            mToast.show();
        } else if (mCanceled) {
            mToast.setDuration(Toast.LENGTH_LONG);
            mCanceled = false;
            showUntilCancel();
        }
    }

    /**
     * @param text     要显示的内容
     * @param duration 显示的时间长 根据LENGTH_MAX进行判断 如果不匹配，进行系统显示 如果匹配，永久显示，直到调用hide()
     */
    public void show(String text, int duration) {
        mToast.setText(text);
        if (duration != LENGTH_MAX) {
            mToast.setDuration(duration);
            mToast.show();

        } else {
            if (mCanceled) {
                mToast.setDuration(Toast.LENGTH_LONG);
                mCanceled = false;
                showUntilCancel();
            }
        }
    }


    public void show() {
        //   setView(view);
        if (mCanceled) {
            mToast.setDuration(Toast.LENGTH_LONG);
            mCanceled = false;
            showUntilCancel();
        }
    }

    public void setView(View view) {
        mToast.setView(view);
    }


    /**
     * 隐藏Toast
     */
    public void cancel() {
        mToast.cancel();
        mCanceled = true;
    }

    public boolean isShowing() {
        return !mCanceled;
    }


    //如果mCanceled为false,一直弹吐司
    private void showUntilCancel() {
        if (mCanceled)
            return;
        mToast.show();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                showUntilCancel();
            }
        }, 1000);

    }

    public void setGravityFill() {
        mToast.setGravity(Gravity.FILL, 0, 0);
    }
}
