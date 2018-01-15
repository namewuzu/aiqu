package com.example.administrator.hjproject.bean;

import android.databinding.BaseObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/18.
 * 描述：
 * =============================================
 */

public class TestPojo extends BaseObservable {
    public String test;

    public TestPojo(String test) {
        this.test = test;
    }

    public static List<TestPojo> gettest() {
        List<TestPojo> list = new ArrayList<>();

        for (int i = 0; i <= 8; i++) {
            list.add(new TestPojo("哈哈"));
        }

        return list;
    }
}
