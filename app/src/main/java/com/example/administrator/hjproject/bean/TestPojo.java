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

    public static class EntrustPojo {
        public String one;
        public String two;
        public String three;
        public String four;
        public String five;

        public EntrustPojo(String one, String two, String three, String four, String five) {
            this.one = one;
            this.two = two;
            this.three = three;
            this.four = four;
            this.five = five;
        }

        public static List<EntrustPojo> getEntrustPojo() {
            List<EntrustPojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new EntrustPojo("买入", "2018-12-12 15:36", "总额麦宝1000个", "0.90元", "剩余麦宝800个"));
            }
            return list;
        }
    }

    public static class HistoryPojo {
        public String one;
        public String two;
        public String three;
        public String four;
        public String five;
        public String six;

        public HistoryPojo(String one, String two, String three, String four, String five, String six) {
            this.one = one;
            this.two = two;
            this.three = three;
            this.four = four;
            this.five = five;
            this.six = six;
        }

        public static List<HistoryPojo> getHistoryPojo() {
            List<HistoryPojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new HistoryPojo("成交价格", "845.56元", "交易单价", "0.90元", "卖出麦宝4879个", "12-12 15:45"));
            }
            return list;
        }
    }

    public static class TradingPojo {
        public int one;
        public String two;
        public String three;
        public String four;

        public TradingPojo(int one, String two, String three, String four) {
            this.one = one;
            this.two = two;
            this.three = three;
            this.four = four;
        }

        public static List<TradingPojo> getTradingPojo() {
            List<TradingPojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new TradingPojo(0, "卖八", "43.45", "2456"));
            }
            for (int i = 0; i <= 8; i++) {
                list.add(new TradingPojo(1, "买一", "43.45", "2456"));
            }
            return list;
        }
    }


}
