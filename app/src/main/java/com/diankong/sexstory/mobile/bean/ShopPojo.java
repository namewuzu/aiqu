package com.diankong.sexstory.mobile.bean;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/26.
 * 描述：
 * =============================================
 */

public class ShopPojo {
    public List<ShopPojo> cartMap;


    public double  subtotal;
    public double  totalPrice;


    public boolean isCheck;
    public boolean isChoosed;
    public String  goodName;
    public String  goodSmallIcon;
    public double  goodPrice;
    public int  id;
    public int  count;

    public void setCount(int _count) {
        count = _count;
    }

    public int getCount() {
        return count;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }


    @Override
    public String toString() {
        return "ShopPojo{" +
                "cartMap=" + cartMap +
                ", subtotal=" + subtotal +
                ", totalPrice=" + totalPrice +
                ", isCheck=" + isCheck +
                ", isChoosed=" + isChoosed +
                ", goodName='" + goodName + '\'' +
                ", goodSmallIcon='" + goodSmallIcon + '\'' +
                ", goodPrice=" + goodPrice +
                ", id=" + id +
                ", count=" + count +
                '}';
    }
}
