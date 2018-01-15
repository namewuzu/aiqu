package com.example.administrator.hjproject.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.administrator.hjproject.R;
import com.meizu.flyme.reflect.StatusBarProxy;

import static android.content.DialogInterface.BUTTON_POSITIVE;


public class DialogUtils {

    public static void showDialogPrompt(Context context, String content) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("提示")
                .setMessage(content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        setPositiveButtonColor(alertDialog);
        alertDialog.show();
    }

    public static void showDialogPrompt(Context context, String title, String content) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        setPositiveButtonColor(alertDialog);
        alertDialog.show();
    }

    /**
     * 点击屏幕外不消失
     * @param context
     * @param title
     * @param content
     * @param _b
     */
    public static void showDialogPrompt(final AppCompatActivity context, String title, final String content, boolean _b) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        context.finish();
                    }
                })
                .create();
        setPositiveButtonColor(alertDialog);
        alertDialog.setCanceledOnTouchOutside(_b);
        alertDialog.show();
    }

    public static AlertDialog showDialogPrompt2(Context context, String title, String content) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        setPositiveButtonColor(alertDialog);
        alertDialog.show();
        return alertDialog;
    }


    public static void showDialogPrompt(Context context, String title, String content, DialogInterface.OnClickListener positiveListener) {
        if (title == null || title.length() == 0) {
            title = "提示";
        }
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton("确定", positiveListener)
                .create();

        setPositiveButtonColor(alertDialog);

        alertDialog.show();
    }


    public static void showDialogPrompt(Context context, String title, String content, String posBtn) {
        if (title == null || title.length() == 0) {
            title = "提示";
        }
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton(posBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();

        setPositiveButtonColor(alertDialog);
        alertDialog.show();
    }


    private static void setPositiveButtonColor(AlertDialog _alertDialog) {
        Window window = _alertDialog.getWindow();
        StatusBarProxy.setStatusBarDarkIcon(window, true);
        _alertDialog.setIcon(R.mipmap.ic_launcher);
        _alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog).getButton(BUTTON_POSITIVE);
                positiveButton.setTextColor(((AlertDialog) dialog).getContext().getResources().getColor(R.color.colorPrimaryRed));
                positiveButton.invalidate();
            }
        });
    }

    /**
     * 两个按钮
     *
     * @param context
     * @param title
     * @param content
     * @param positionStr
     * @param positiveListener
     */
    public static AlertDialog showDialogPrompt(Context context, String title, String content, String positionStr, DialogInterface.OnClickListener positiveListener) {
        if (title == null || title.length() == 0) {
            title = "提示";
        }


        final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton(positionStr, positiveListener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        //   alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setPositiveButtonColor(alertDialog);
        alertDialog.show();

        return alertDialog;

    }

    public static AlertDialog showDialogPromptV2(Context context, String title, String content, String positionStr, DialogInterface.OnClickListener positiveListener) {
        if (title == null || title.length() == 0) {
            title = "提示";
        }


        final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton(positionStr, positiveListener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        //   alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setPositiveButtonColor(alertDialog);
        return alertDialog;

    }

    /**
     * 两个按钮的额功能都可以定义的dialog
     *
     * @param context
     * @param title
     * @param content
     * @param positionStr
     * @param nagativeStr
     * @param positiveListener
     * @param negasitiveListener
     * @return
     */
    public static AlertDialog showDialogPrompt(Context context, String title,
                                               String content,
                                               String positionStr, String nagativeStr,
                                               DialogInterface.OnClickListener positiveListener,
                                               DialogInterface.OnClickListener negasitiveListener) {
        if (title == null || title.length() == 0) {
            title = "提示";
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton(positionStr, positiveListener)
                .setNegativeButton(nagativeStr, negasitiveListener)
                .create();
        setPositiveButtonColor(alertDialog);
        alertDialog.show();
        return alertDialog;

    }


    /**
     * 设置底部出现的Diaog
     *
     * @param id      布局文件
     * @param context 上下文
     * @return dialog
     */
    @NonNull
    public static Dialog showAlert(int id, Context context) {
        final Dialog dialog = new Dialog(context, com.example.administrator.hjproject.R.style.Dialog_FS_TAB);

        //////设置为true点击区域外消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        dialog.setContentView(id);

        //自定义dialog的属性
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT; // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度
        //lp.alpha = 0.7f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);
        //设置动画
        dialogWindow.setWindowAnimations(com.example.administrator.hjproject.R.style.PopupAnimation);

//        dialog.show();
        return dialog;
    }

    /**
     * 设置顶部出现的Diaog
     *
     * @param id      布局文件
     * @param context 上下文
     * @return dialog
     */
    @NonNull
    public static Dialog showTopAlert(int id, Context context) {
        final Dialog dialog = new Dialog(context, com.example.administrator.hjproject.R.style.Dialog_FS_TAB);

        //////设置为true点击区域外消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        dialog.setContentView(id);

        //自定义dialog的属性
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        lp.x = 0;
        lp.y = 190;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT; // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度
        //lp.alpha = 0.7f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);
        //设置动画
        dialogWindow.setWindowAnimations(com.example.administrator.hjproject.R.style.AnimationArrowUp);

//        dialog.show();
        return dialog;
    }


    public static Dialog showAlert(View view, Context context) {
        final Dialog dialog = new Dialog(context, com.example.administrator.hjproject.R.style.Dialog_FS_TAB);
        //////设置为true点击区域外消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        dialog.setContentView(view);

        //自定义dialog的属性
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT; // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度
        //lp.alpha = 0.7f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);
        //设置动画
        dialogWindow.setWindowAnimations(com.example.administrator.hjproject.R.style.PopupAnimation);

        dialog.show();
        return dialog;
    }

    /**
     * 设置中间出现的Diaog
     *
     * @param id      布局文件
     * @param context 上下文
     * @return dialog
     */
    @NonNull
    public static Dialog showAlertMid(int id, Context context) {

        final Dialog dialog = new Dialog(context);
        //设置为true点击区域外消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(id);

        //自定义dialog的属性

        //自定义dialog的属性
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT; // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度
        //lp.alpha = 0.7f; // 透明度

        dialog.show();
        return dialog;
    }


    /**
     * 设置中间出现的Diaog
     *
     * @param view    视图
     * @param context 上下文
     * @return dialog
     */
    @NonNull
    public static Dialog showAlertMid(View view, Context context) {

        final Dialog dialog = new Dialog(context);
        //设置为true点击区域外消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        //自定义dialog的属性
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT; // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度
        //lp.alpha = 0.7f; // 透明度
        return dialog;
    }

    /**
     * 当判断当前手机没有网络时选择是否打开网络设置
     */
    public static AlertDialog showNoNetWorkDlg(final Activity activity) {
        if (activity == null) {
            return null;
        }
/*
        if (activity==null){
            activity= (Activity) context;
        }
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }*/
        AlertDialog dialog = null;
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            final Activity finalActivity = activity;

            dialog = builder        //
                    .setTitle(R.string.app_name)            //
                    .setMessage("当前无网络").setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳转到系统的网络设置界面
                            Intent intent = null;
                            // 先判断当前系统版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
                                intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            } else {
                                intent = new Intent();
                                intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                            }
                            finalActivity.startActivity(intent);
                            dialog.dismiss();
                        }
                    }).setNegativeButton("知道了", null).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }


}