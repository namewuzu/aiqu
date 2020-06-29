package com.diankong.sexstory.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.Consts;
import com.diankong.sexstory.mobile.bean.AppListPojo;
import com.diankong.sexstory.mobile.bean.TestPojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;

import java.io.Serializable;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/20.
 * 描述：
 * =============================================
 */

public class SpUtlis {

    public static String PREFERENCE_NAME = "loveVedio";

    public static void saveToken(String token) {
        ACache.SharedPrefsUtils.setStringPreference(Consts.Token, token);
    }

    public static String getToken() {
        return ACache.SharedPrefsUtils.getStringPreference(Consts.Token);
    }

    public static void saveFirstPlay(int firstPlay) {
        ACache.SharedPrefsUtils.setIntegerPreference(Consts.FIRSTPLAY, firstPlay);
    }

    public static void saveDeviceId(String DeviceId) {
        ACache.SharedPrefsUtils.setStringPreference("DEVICEID", DeviceId);
    }

    public static String getDeviceId() {
        return ACache.SharedPrefsUtils.getStringPreference("DEVICEID");
    }

    public static int getFirstPlay() {
        return ACache.SharedPrefsUtils.getIntegerPreference(Consts.FIRSTPLAY, 0);
    }

    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static int getId() {
        return ACache.SharedPrefsUtils.getIntegerPreference("ID",0);
    }

    public static void setId(int _id) {
        ACache.SharedPrefsUtils.setIntegerPreference("ID", _id);
    }


    public static String getlinkString() {
        return ACache.SharedPrefsUtils.getStringPreference("linkString");
    }

    public static void setlinkString(String _s) {
        ACache.SharedPrefsUtils.setStringPreference("linkString",_s);
    }

    public static String getVisitor() {
        return ACache.SharedPrefsUtils.getStringPreference("Visitor");
    }

    public static void setVisitor(String _id) {
        ACache.SharedPrefsUtils.setStringPreference("Visitor",_id);
    }

    public static String getUserType() {
        return ACache.SharedPrefsUtils.getStringPreference("UserType");
    }

    public static void setUserType(String _id) {
        ACache.SharedPrefsUtils.setStringPreference("UserType",_id);
    }

    public static String getGuid() {
        return ACache.SharedPrefsUtils.getStringPreference(Consts.USER_GUID);
    }

    public static void setGuid(String _guid) {
        ACache.SharedPrefsUtils.setStringPreference(Consts.USER_GUID, _guid);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    public static void savaPhone(String _psd) {
        ACache.SharedPrefsUtils.setStringPreference(Consts.USER_PHONE, _psd);
    }

    public static String getPhone() {
        return ACache.SharedPrefsUtils.getStringPreference(Consts.USER_PHONE);
    }

    public static void setWebOpenId(String _webOpenid) {
        ACache.SharedPrefsUtils.setStringPreference(Consts.WEB_OPENID, _webOpenid);
    }

    public static String getWebOpenId() {
        return ACache.SharedPrefsUtils.getStringPreference(Consts.WEB_OPENID);
    }

    public static void savaOpenId(String _openid) {
        ACache.SharedPrefsUtils.setStringPreference(Consts.OPENID, _openid);
    }

    public static String getOpenId() {
        return ACache.SharedPrefsUtils.getStringPreference(Consts.OPENID);
    }

    public static void savaIdNo(int idNo) {
        ACache.SharedPrefsUtils.setIntegerPreference(Consts.IDNO, idNo);
    }

    public static int getIdNo() {
        return ACache.SharedPrefsUtils.getIntegerPreference(Consts.IDNO,0);
    }

    public static void saveNickName(String nickname) {
        ACache.SharedPrefsUtils.setStringPreference("nickname", nickname);
    }

    public static String getNickName() {
        return ACache.SharedPrefsUtils.getStringPreference("nickname");
    }

    public static void saveAvatar(String avatar) {
        ACache.SharedPrefsUtils.setStringPreference("avatar", avatar);
    }

    public static String getAvatar() {
        return ACache.SharedPrefsUtils.getStringPreference("avatar");
    }

    public static void saveAddress(String address) {
        ACache.SharedPrefsUtils.setStringPreference("address", address);
    }

    public static String getAddress() {
        return ACache.SharedPrefsUtils.getStringPreference("address");
    }

    public static void saveIMToken(String IMToken) {
        ACache.SharedPrefsUtils.setStringPreference("IMToken", IMToken);
    }

    public static String getIMToken() {
        return ACache.SharedPrefsUtils.getStringPreference("IMToken");
    }


    public static void setShareCount(int count) {
        ACache.SharedPrefsUtils.setIntegerPreference(Consts.COUNT, count);
    }

    public static int getShareCount() {
        return ACache.SharedPrefsUtils.getIntegerPreference(Consts.COUNT, 0);
    }


    public static class UserInfo {
        public static void saveUserInfo(String userInfoStr) {
            ACache.SharedPrefsUtils.setStringPreference(new StringBuilder("UserInfo").append(getGuid()).toString(), userInfoStr);
        }

        public static void saveGuid(String guid) {
            ACache.SharedPrefsUtils.setStringPreference(Consts.USER_GUID, guid);
        }

        public static String getUserInfo() {
            return ACache.SharedPrefsUtils.getStringPreference(new StringBuilder("UserInfo").append(getGuid()).toString());
        }


        public static void savePhone(String phone) {
            ACache.SharedPrefsUtils.setStringPreference(Consts.USER_PHONE, phone);
        }

        public static String getPhone() {
            return ACache.SharedPrefsUtils.getStringPreference(Consts.USER_PHONE);
        }
    }


    public static void setPojo(TestPojo mTestPojo) {
        ACache.get(App.getInstance()).put("TESTPOJO", (Serializable) mTestPojo);
    }

    public static TestPojo getPojo() {
        return (TestPojo) ACache.get(App.getInstance()).getAsObject("TESTPOJO");
    }

    public static void setUserMsgPojo(UserInfoPojo mTestPojo) {
        ACache.get(App.getInstance()).put("USERPOJO", (Serializable) mTestPojo);
    }

    public static UserInfoPojo getUserMsgPojo() {
        return (UserInfoPojo) ACache.get(App.getInstance()).getAsObject("USERPOJO");
    }

    public static void setMineTemplete(int mineTemplete){
        ACache.SharedPrefsUtils.setIntegerPreference("mineTemplete", mineTemplete);
    }

    public static int getMineTemplete(){
        return ACache.SharedPrefsUtils.getIntegerPreference("mineTemplete",0);
    }

    public static void setAppListPojo(AppListPojo mListPojo) {
        ACache.get(App.getInstance()).put("AppListPojo", (Serializable) mListPojo);
    }

    public static AppListPojo getAppListPojo() {
        return (AppListPojo) ACache.get(App.getInstance()).getAsObject("AppListPojo");
    }

    public static void setAccessKey(String accessKey) {
        ACache.SharedPrefsUtils.setStringPreference("accessKey", accessKey);
    }

    public static String getAccessKey() {
        return ACache.SharedPrefsUtils.getStringPreference("accessKey");
    }

    public static void setSecret(String secret) {
        ACache.SharedPrefsUtils.setStringPreference("secret", secret);
    }

    public static String getSecret() {
        return ACache.SharedPrefsUtils.getStringPreference("secret");
    }

    public static void setOssToken(String osstoken) {
        ACache.SharedPrefsUtils.setStringPreference("osstoken", osstoken);
    }

    public static String getOssToken() {
        return ACache.SharedPrefsUtils.getStringPreference("osstoken");
    }

    public static void setReplyedName(String replyedName) {
        ACache.SharedPrefsUtils.setStringPreference("replyedName", replyedName);
    }

    public static String getReplyedName() {
        return ACache.SharedPrefsUtils.getStringPreference("replyedName");
    }

}
