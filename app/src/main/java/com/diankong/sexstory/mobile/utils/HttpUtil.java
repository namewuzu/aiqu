package com.diankong.sexstory.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.diankong.sexstory.mobile.base.AppManager;
import com.diankong.sexstory.mobile.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



public class HttpUtil {
	private static final String TAG = "HttpUtil";


	/**
	 * 当判断当前手机没有网络时选择是否打开网络设置
	 * @param context
	 */
	public static AlertDialog showNoNetWorkDlg(final Activity context) {

		/*if (context == null || !(context instanceof Activity)){
			return null;
		}*/


		Activity activity = AppManager.getAppManager().currentActivity();
		if (activity==null){
			activity= (Activity) context;
		}
		while (activity.getParent() != null) {
			activity = activity.getParent();
		}
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
							if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上

								//intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
							}else{
								intent = new Intent();
								intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
							}
							finalActivity.startActivity(intent);
							dialog.dismiss();
						}
					}).setNegativeButton("知道了", null).show();
		}catch (Exception e){
			e.printStackTrace();
		}

		return dialog;
	}


	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */

	public static boolean isNetWorkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			if (info == null) {
				return false;
			} else {
				if (info.isAvailable()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否是wifi连接
	 */
	public static boolean isWifiStatus(Context context) {
		boolean isWifiConnect = true;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
		for (int i = 0; i < networkInfos.length; i++) {
			if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
				if (networkInfos[i].getType() == cm.TYPE_MOBILE) {
					isWifiConnect = false;
				}
				if (networkInfos[i].getType() == cm.TYPE_WIFI) {
					isWifiConnect = true;
				}
			}
		}
		return isWifiConnect;
	}

	/**
	 * 获取网络html代码
	 * 
	 * @param url
	 * @return
	 */
	public String getHtmlCode(String url) {
		String html = null;
		try {
			URL htmlURL = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) htmlURL.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);

			InputStream inStream = conn.getInputStream();

			ByteArrayOutputStream outStream = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];

			int len = 0;

			while ((len = inStream.read(buffer)) != -1) {

				outStream.write(buffer, 0, len);

			}

			inStream.close();

			byte[] data = outStream.toByteArray();

			html = new String(data, "gbk");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}

	/**
	 * 将url参数转换成map
	 * 
	 * @param param
	 *            aa=11&bb=22&cc=33
	 * @return
	 */
	public static Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		if ("".equals(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	/**
	 * 将map转换成url
	 * 
	 * @param map
	 * @return
	 */
	public static String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}
}