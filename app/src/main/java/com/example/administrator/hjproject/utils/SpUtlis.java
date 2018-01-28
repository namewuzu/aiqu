package com.example.administrator.hjproject.utils;

import com.example.administrator.hjproject.base.Consts;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/20.
 * 描述：
 * =============================================
 */

public class SpUtlis {
    public static void saveToken(String token) {
        ACache.SharedPrefsUtils.setStringPreference(Consts.Token, token);
    }

    public static String getToken() {
        return ACache.SharedPrefsUtils.getStringPreference(Consts.Token);
    }
}
