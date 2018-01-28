package com.example.administrator.hjproject.bean;

import android.databinding.BaseObservable;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/1/22.
 * 描述：
 * =============================================
 */

public class CensusPojo extends BaseObservable {

    /**
     * today_price : 1800
     * real_time_price : 1650
     * yesterday_price : 1380
     * deal_num : 3
     * deal_money : 409500
     * lowest_price : 1650
     * tiptop_price : 1800
     * difference_price : 270
     * percentage : 9.57
     * buy_list : [{"price":"1600","num":500},{"price":"1550","num":100},{"price":"1350","num":200}]
     * trade_list : [{"price":"1650","num":40},{"price":"1800","num":60}]
     */

    public int today_price;
    public int real_time_price;
    public int yesterday_price;
    public int deal_num;
    public int deal_money;
    public int lowest_price;
    public int tiptop_price;
    public int difference_price;
    public int percentage;
    public List<BuyListBean> buy_list;
    public List<TradeListBean> trade_list;



    public List<TradeListBean> getTrade_list() {
        return trade_list;
    }

    public void setTrade_list(List<TradeListBean> trade_list) {
        this.trade_list = trade_list;
    }

    public static class BuyListBean extends BaseObservable {
        /**
         * price : 1600
         * num : 500
         */

        private String price;
        private int num;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class TradeListBean extends BaseObservable {
        /**
         * price : 1650
         * num : 40
         */

        private String price;
        private int num;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
