package com.example.administrator.hjproject.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.App;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by apanda on 2017/5/19.
 */

public class AndroidSystemUtils {


    public static String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return "";
    }


    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isLocationServiceOpen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }


    public static final void openGPSSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }


    public static boolean judgePhoneNumber(final Context mContext, final EditText mEditText) {
        String regex = "[0-9]{11,11}";
        final Pattern mPattern = Pattern.compile(regex);
        Matcher mMatcher = mPattern.matcher(mEditText.getText().toString());
        return mMatcher.matches();
    }


    /**
     * 弹出键盘
     *
     * @param v 获取焦点的view
     */
    public static void showInputBroad(View v) {
        try {
            if (v == null) return;
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏键盘
     *
     * @param v 获取焦点的view
     */
    public static void hideInputBroad(View v) {
        try {
            if (v == null) return;
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideInputBroad(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 获取设备的序列号
     *
     * @param context
     * @return 设备的序列号 String
     */
    public static String getDeviceId(Context context) {
        String deviceId = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != telephonyManager) {
            deviceId = telephonyManager.getDeviceId();
        }
        return deviceId;
    }

    public static void phoneDial(String phoneNumber, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    public static String getSystemInfo() {
        String phoneInfo = "Product: " + Build.PRODUCT;
        phoneInfo += ",CPU_ABI: " + Build.CPU_ABI;
        phoneInfo += ",TAGS: " + Build.TAGS;
        phoneInfo += ",VERSION_CODES.BASE: " + Build.VERSION_CODES.BASE;
        phoneInfo += ",MODEL: " + Build.MODEL;
        phoneInfo += ",SDK: " + Build.VERSION.SDK_INT;
        phoneInfo += ",VERSION.RELEASE: " + Build.VERSION.RELEASE;
        phoneInfo += ",DEVICE: " + Build.DEVICE;
        phoneInfo += ",DISPLAY: " + Build.DISPLAY;
        phoneInfo += ",BRAND: " + Build.BRAND;
        phoneInfo += ",BOARD: " + Build.BOARD;
        phoneInfo += ",FINGERPRINT: " + Build.FINGERPRINT;
        phoneInfo += ",ID: " + Build.ID;
        phoneInfo += ",MANUFACTURER: " + Build.MANUFACTURER;
        return phoneInfo;
    }

    /**
     * @return 手机型号
     */
    public static String getDeviceInfo() {
        return Build.DEVICE;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return context.getString(R.string.can_not_find_version_name);
        }
    }

    public static List<String> getInstallAppPackageNames(Context context) {
        PackageManager pm = context.getPackageManager();
        List<String> appPackageNames = new ArrayList<String>();
        //获取手机中所有安装的应用集合
        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo info : applicationInfos) {
            String packageName = info.packageName;
            appPackageNames.add(packageName);
        }
        return appPackageNames;
    }

    /**
     * 获取当前栈顶的Activity对象
     *
     * @return
     */
    public static Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final static int NETWORK_TYPE_WIFI = 1;
    private final static int NETWORK_TYPE_4G = 2;
    private final static int NETWORK_TYPE_3G = 3;
    private final static int NETWORK_TYPE_2G = 4;

    /**
     * 根据NetWorkTypeId来获取networktype
     * 返回类型 2G 3G 4G WiFi这样的类型
     * 不是具体的GPRS WSCDMA LTE等类型
     *
     * @return
     */
    public static String getNetWorkType() {
        int netWorkId = getNetWorkTypeId();
        String netWorkType = "";
        switch (netWorkId) {
            case NETWORK_TYPE_2G:
                netWorkType = "2G";
                break;
            case NETWORK_TYPE_3G:
                netWorkType = "3G";
                break;
            case NETWORK_TYPE_4G:
                netWorkType = "4G";
                break;
            case NETWORK_TYPE_WIFI:
                netWorkType = "WiFi";
                break;
            default:
                break;
        }
        return netWorkType;
    }

    /**
     * 获取网络类型ID（这个ID是与后端商议的ID，非系统网络类型ID）
     *
     * @return
     */
    public static int getNetWorkTypeId() {
        int netWorkType = 0;
        NetworkInfo networkInfo = ((ConnectivityManager) App.getContext().getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (null != networkInfo && networkInfo.isConnected()) {
            if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                netWorkType = NETWORK_TYPE_WIFI;
            } else if (ConnectivityManager.TYPE_MOBILE == networkInfo.getType()) {
                int type = networkInfo.getSubtype();
                String name = networkInfo.getSubtypeName();
                switch (type) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netWorkType = NETWORK_TYPE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        netWorkType = NETWORK_TYPE_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netWorkType = NETWORK_TYPE_4G;
                        break;
                    default:
                        if (name.equalsIgnoreCase("TD-SCDMA") || name.equalsIgnoreCase("WCDMA") || name.equalsIgnoreCase("CDMA2000")) {
                            netWorkType = NETWORK_TYPE_3G;
                        }
                        break;
                }
            }
        }
        return netWorkType;
    }

    /**
     * 获取当前系统时间戳
     * 精确到毫秒
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前系统时间戳
     * 精确到秒
     *
     * @return
     */
    public static long getCurrentTimeSecond() {
        return getCurrentTimeMillis() / 1000;
    }

    public static boolean isMIUI() {
        String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
        String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
        String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }


    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }


    public static boolean isMeizuFlymeOS() {
/* 获取魅族系统操作版本标识*/
        String meizuFlymeOSFlag = getSystemProperty("ro.build.display.id", "");
        if (TextUtils.isEmpty(meizuFlymeOSFlag)) {
            return false;
        } else if (meizuFlymeOSFlag.contains("flyme") || meizuFlymeOSFlag.toLowerCase().contains("flyme")) {
            return true;
        } else {
            return false;
        }
    }


    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (ClassNotFoundException e) {
            L.d("SystemUtil=================>" + e.getMessage());
            return null;
        } catch (NoSuchMethodException e) {
            L.d("SystemUtil=================>" + e.getMessage());
            return null;
        } catch (IllegalAccessException e) {
            L.d("SystemUtil=================>" + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            L.d("SystemUtil=================>" + e.getMessage());
            return null;
        } catch (InvocationTargetException e) {
            L.d("SystemUtil=================>" + e.getMessage());
            return null;
        }
    }


}
