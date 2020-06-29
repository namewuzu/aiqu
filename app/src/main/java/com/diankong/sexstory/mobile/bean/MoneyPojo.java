package com.diankong.sexstory.mobile.bean;

import android.databinding.BaseObservable;

/**
 * =============================================
 * 长沙点控科技有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/6/7.
 * 描述：
 * =============================================
 */

public class MoneyPojo extends BaseObservable {

    public int todayApprentices;
    public int allApprentices;
    public double todayIncome;
    public double totalIncome;
    public String link;


    public int count;
    public int coin;
    public double canCansh;
    public double price;
    public int coinCount;
    public double money;

    public MoneyPojo() {

    }

    public MoneyPojo(int _count, double _price) {
        count = _count;
        price = _price;
    }
}
