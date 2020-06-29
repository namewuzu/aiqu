package com.diankong.sexstory.mobile.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.AppManager;
import com.diankong.sexstory.mobile.event.RefreshEvent;
import com.diankong.sexstory.mobile.modle.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/4/9.
 * 描述：
 * =============================================
 */

public class IntentUtils {

    public static void toSignActivity(){
        handlerSingleSign("您的账号信息已过期,请重新登录");
    }
    public static void toSignActivity(String _s){
        handlerSingleSign(_s);
    }


    public static void handlerSingleSign(String s) {
        SpUtlis.setUserType("1");
        EventBus.getDefault().post(new RefreshEvent(true));
        final AppCompatActivity appCompatActivity = AppManager.getAppManager().currentActivity();
        if (appCompatActivity == null) {
            return;
        }
        if (AppUtils.isAppBackground(appCompatActivity)) {
            showSingleSignNotification(appCompatActivity, s);
        } else {
            showSingleSignDialog(appCompatActivity, s);
        }

    }

    public static void showSingleSignDialog(final AppCompatActivity context, String msg) {
//        Observable.just(msg).compose(RxSchedulersHelper.<String>applyIoTransformer()).subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//                AppCompatActivity appCompatActivity = AppManager.getAppManager().currentActivity();
//                if (appCompatActivity == null) {
//                    return;
//                }
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                       // CommonUtils.clearUserInfo();
//                        App.clearUserInfo();
//                    }
//                }).start();
//
//              //  EventBus.getDefault().post(new Logout(true));
//                final AlertDialog loginDialog = DialogUtils.showDialogPrompt(appCompatActivity, "提示", s, "现在登录", "取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
////                        IntentUtils.toSignActivity(context);
//
//                    }
//                }, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                        AppManager appManager = AppManager.getAppManager();
//                        dialog.dismiss();
//                        if (!context.equals(MainActivity.class)) {
//                            appManager.returnToActivity(MainActivity.class);
//
//                        }
//                    }
//                });
//                loginDialog.setCancelable(false);
//                loginDialog.show();
//            }
//        });
    }

    private static void showSingleSignNotification(AppCompatActivity context, String msg) {

        NotificationManager mn = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // create and send notificaiton
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(context.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);


        Intent msgIntent = new Intent(context, MainActivity.class);
      //  CommonUtils.clearUserInfo();
        App.clearUserInfo();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentTitle("提醒");
        mBuilder.setTicker(msg);
     //   mBuilder.setSmallIcon(R.mipmap.ic_notification_icon);
        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        mn.notify(0, notification);

    }
}
