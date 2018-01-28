package com.example.administrator.hjproject.http;

import android.os.Environment;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/18.
 * 描述：
 * =============================================
 */

public interface Api {
    String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    String LOG_PATH = SD_PATH + "/mbao/logs/";
    //配置App网络访问域名
    String apiurl = "http://mai.minedaily.cn/";
}
