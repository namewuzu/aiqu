package com.example.administrator.hjproject.utils;

import android.app.Dialog;
import android.text.TextUtils;

import com.example.administrator.hjproject.bean.HttpLogBean;
import com.example.administrator.hjproject.http.Api;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;


//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃   神兽保佑
//    ┃　　　┃   代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 * 高信网络--文明社区源代码，版权@归而然网络科技所有。
 * 项目: 文明社区
 * 包名: HttpLogManager
 * 作者: created by 熊凯 on 2017/2/16 14:30
 * 电话: 15323410416
 * 描述: 网络请求log管理类
 */

/**
 * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 * ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
 * │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
 * ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
 * │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
 * ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
 * │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
 * ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
 * │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
 * ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
 * │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
 * └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘
 */
public class HttpLogManager {
    public static boolean isRecordHttpLog = true;
    private ArrayList<HttpLogBean> logs;

    private HttpLogManager() {
        logs = new ArrayList<>();
        String log = ACache.get().getAsString("app_all_log");
        if (!TextUtils.isEmpty(log)) {
            Type type = new TypeToken<ArrayList<HttpLogBean>>() {
            }.getType();
            ArrayList<HttpLogBean> oldLogs = Convert.fromJson(log, type);
            logs.addAll(oldLogs);
        }
    }

    private static HttpLogManager manager;

    public static HttpLogManager getInstance() {
        if (null == manager) {
            manager = new HttpLogManager();
        }
        return manager;
    }


    public void save2Local() {
        if (logs != null && logs.size() > 0) {
            String value = JsonUtils.toJson(logs);
            ACache.get().put("app_all_log", value);
            if (!L.isDebug) {
                return;
            }
        /* 准备错误日志文件 */


            File logFile = new File(Api.LOG_PATH + "log.txt");
            if (!logFile.getParentFile().exists()) {
                logFile.getParentFile().mkdirs();
            }

            if (logFile.exists()) {
                logFile.delete();
            }

        /* 写入错误日志 */
            FileWriter fw = null;
            try {
                fw = new FileWriter(logFile, true);
                int i = 1;
                for (HttpLogBean log : logs) {
//                    public int type;//1 --网络访问 2- 推送 3--错误信息
                    fw.write("*******************" + i++ + "开始*********************\n*");
                    fw.write(log.toString() + "\r\n");
                    fw.write("*******************" + i++ + "结束*********************\n\n\n*");
                }
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fw)
                        fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 监听程序错误信息
     *
     * @param _errorInfo
     */
    public void recordErrorLog(String _errorInfo) {
        if (!L.isDebug) return;
        HttpLogBean httpLogBean = new HttpLogBean();
        httpLogBean.content = _errorInfo;// JsonUtils.formatJson(body);
        httpLogBean.time = TimeUtils.getSystemDateToSecond();
        httpLogBean.type = 3;
        logs.add(httpLogBean);
        updateUIAfterDataChanged();
    }


    private void setErrorLog(StringBuffer _buffer) {
        _buffer.append("接口访问错误信息===============================================================================\n");
        HttpLogBean httpLogBean = new HttpLogBean();
        httpLogBean.content = _buffer.toString();// JsonUtils.formatJson(body);
        httpLogBean.time = TimeUtils.getSystemDateToSecond();
        httpLogBean.type = 3;
        logs.add(httpLogBean);
        updateUIAfterDataChanged();
        L.d(_buffer.toString());
    }


    /**
     * 记录 rx retfriot 的网络请求信息
     *
     * @param message
     */
    public void recordLog(String message) {
        if (!L.isDebug) return;
        HttpLogBean httpLogBean = new HttpLogBean();
        httpLogBean.content = message;// JsonUtils.formatJson(body);
        httpLogBean.time = TimeUtils.getSystemDateToSecond();
        httpLogBean.type = 1;
        logs.add(httpLogBean);
        updateUIAfterDataChanged();
    }





    public static final String TYPE = "type";

    private void updateUIAfterDataChanged() {
        if (null != onDataChangedListener) {
            onDataChangedListener.onDataChanged();
        }
    }

    public void remove(HttpLogBean log) {
        logs.remove(log);
        updateUIAfterDataChanged();
    }

    public void clear() {
        logs.clear();
        updateUIAfterDataChanged();
    }

    public ArrayList<HttpLogBean> getLogs() {
        return logs;
    }


    private static Dialog dialog = null;

    private OnDataChangedListener onDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener l) {
        onDataChangedListener = l;
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }


}
