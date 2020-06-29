package com.diankong.sexstory.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/7/18.
 * 描述：
 * =============================================
 */

public class ShareUtil {
    public static final String invite_share_key = "com.UCMobile:wx020a535dccd46c11,com.tencent.mtt:wx64f9cf5b17af074d,com.baidu.browser.apps:wx0116bf301a92506b,com.ijinshan.browser_fast:wxc2ff198ba4a63f06,com.browser2345:wx66d367303dace3ad,sogou.mobile.explorer:wxf1d5d36b9ea492f8,com.qihoo.browser:wx60d9d5c44ca9386e,com.tencent.mobileqq:wxf0a80d0ac2e82aa7";


    public static void shareWxOrCircle(Context mContext, SendMessageToWX.Req paramReq) {
        try {
            Bundle bunlde = new Bundle();
            paramReq.toBundle(bunlde);
            String str2 = "weixin://sendreq?appid=" + "wxf0a80d0ac2e82aa7";
            Intent intent = new Intent().setClassName("com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity");
            intent.putExtras(bunlde);

            intent.putExtra("_mmessage_sdkVersion", 570490883);
            intent.putExtra("_mmessage_appPackage", "com.tencent.mobileqq");
            intent.putExtra("_mmessage_content", str2);
            String buffer = str2 +
                    570490883 +
                    "com.tencent.mobileqq" +
                    "mMcShCsTr";
            intent.putExtra("_mmessage_checksum", com.tencent.mm.opensdk.utils.b.c(buffer.substring(1, 9).getBytes()).getBytes());
            ((Activity) mContext).startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
