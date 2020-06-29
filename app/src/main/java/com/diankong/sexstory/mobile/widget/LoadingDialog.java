package com.diankong.sexstory.mobile.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.utils.Toasty;


/**
 * description:弹窗浮动加载进度条
 * Created by xsf
 * on 2016.07.17:22
 */
public class LoadingDialog {
    /**
     * 加载数据对话框
     */
    private static Toasty toast;

    /**
     * 显示加载对话框
     *
     * @param context 上下文
     */
    public static Toasty showDialogForLoading(Context context) {
        if (toast == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.show_loading_dialog, null);
            view.setOnClickListener(null);
            toast = new Toasty(context);
            toast.setGravityFill();
            toast.setView(view);
        }
        toast.show();
        return toast;
    }


    /**
     * 关闭加载对话框
     */
    public static void cancelDialogForLoading() {
        if (toast != null && toast.isShowing()) {
            toast.cancel();
            toast = null;
        }
//
      /*  TimerUtils.countdown(1)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });

*/
    }

    public static void cancelDialogNow() {

        if (toast != null && toast.isShowing()) {
            toast.cancel();
            toast = null;
        }
    }
}
