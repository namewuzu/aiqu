package com.diankong.sexstory.mobile.utils;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.umeng.weixin.umengwx.e;
import com.umeng.weixin.umengwx.i;
import com.umeng.weixin.umengwx.j;
import com.umeng.weixin.umengwx.k;


public class WeChat {
    private Context a;
    private String b;
    private boolean c = false;

    public WeChat(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public final boolean handleIntent(Intent intent, e eVar) {
        try {
            switch (intent.getIntExtra("_wxapi_command_type", 0)) {
                case 1:
                    eVar.a(new i(intent.getExtras()));
                    return true;
                case 2:
                    eVar.a(new k(intent.getExtras()));
                    return true;
                case 3:
                case 4:
                case 5:
                    return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public final boolean isWXAppInstalled() {
        try {
            return this.a.getPackageManager().getPackageInfo("com.tencent.mm", 64) != null;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public final boolean isWXAppSupportAPI() {
        return true;
    }

    public final boolean launchShare(Bundle bundle) {
        if (this.a == null) {
            return false;
        }
        String appId = Config.shareAppId;
        String appPackage = Config.sharePkg;
        Intent intent = new Intent();
        intent.setClassName("com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity");
        intent.putExtras(bundle);
        intent.putExtra("_mmessage_sdkVersion", 587268097);
        intent.putExtra("_mmessage_appPackage", appPackage);
        intent.putExtra("_mmessage_content", "weixin://sendreq?appid=" + appId);
        intent.putExtra("_mmessage_checksum", j.a("weixin://sendreq?appid=" + appId, 587268097, appPackage));
        intent.addFlags(268435456).addFlags(134217728);
        try {
            this.a.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public final void pushare(Bundle bundle) {
        launchShare(bundle);
    }

    public final boolean registerApp(String str) {
        if (this.c) {
            throw new IllegalStateException("registerApp fail, WXMsgImpl has been detached");
        }
        if (str != null) {
            this.b = str;
        }
        if (this.a == null) {
            return false;
        }
        String appId = Config.shareAppId;
        String appPackage = Config.sharePkg;
        Intent intent = new Intent("com.tencent.mm.plugin.openapi.Intent.ACTION_HANDLE_APP_REGISTER");
        intent.putExtra("_mmessage_sdkVersion", 587268097);
        intent.putExtra("_mmessage_appPackage", appPackage);
        intent.putExtra("_mmessage_content", "weixin://registerapp?appid=" + appId);
        intent.putExtra("_mmessage_checksum", j.a("weixin://registerapp?appid=" + appId, 587268097, appPackage));
        this.a.sendBroadcast(intent, "com.tencent.mm.permission.MM_MESSAGE");
        return true;
    }

    public final boolean sendReq(com.umeng.weixin.umengwx.a aVar) {
        if (!aVar.b()) {
            return false;
        }
        Bundle bundle = new Bundle();
        aVar.a(bundle);
        launchShare(bundle);
        return true;
    }
}