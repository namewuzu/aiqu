package com.diankong.sexstory.mobile.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author chenqiang
 */
public class Config {

    public static final LinkedHashMap<String,String> INFOS;
    public static String sharePkg;
    public static String shareAppId;

    static {
        INFOS = new LinkedHashMap<>();
        INFOS.put("com.tencent.mobileqq","wxf0a80d0ac2e82aa7");
        INFOS.put("com.tencent.mtt","wx64f9cf5b17af074d");
        INFOS.put("com.UCMobile","wx020a535dccd46c11");
    }

    public static void checkIfNoneShowIntall(Context context) {
        int i = 0;
        for(HashMap.Entry<String,String> entry : INFOS.entrySet()){
            try {
                context.getPackageManager().getPackageInfo(entry.getKey(), 0);
                sharePkg = entry.getKey();
                shareAppId = entry.getValue();
                return;
            } catch (PackageManager.NameNotFoundException e) {
                if(i == INFOS.size()-1){
                    sharePkg = "";
                    shareAppId = "";
                    showInstallDialog(context);
                }
            }
            i++;
        }
    }

    private static void showInstallDialog(final Context context) {
        final int[] appIndex = {0};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("安装其中一款后即可快速分享(只需安装),您也可以自己去应用商店下一款");
        builder.setSingleChoiceItems(new String[]{"QQ(完整版)-推荐", "QQ浏览器", "UC浏览器"}, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                appIndex[0] = which;
            }
        });
        builder.setNegativeButton("放弃分享", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(context instanceof Activity){
                    ((Activity) context).finish();
                }
            }
        });
        builder.setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String uri = "";
                if (appIndex[0] == 0) {
                    uri = "http://im.qq.com/download/";
                } else if (appIndex[0] == 1) {
                    uri = "http://mdc.html5.qq.com/?channel_id=22579";
                } else if (appIndex[0] == 2) {
                    uri = "http://wap.uc.cn/packinfo/chinese_999/ucbrowser/pf/145?uc_param_str=vepffrbiupladsdnni&r=main&from=wap-atp-mobile&plang=";
                }
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(uri)));
                dialog.dismiss();
                if(context instanceof Activity){
                    ((Activity) context).finish();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}
