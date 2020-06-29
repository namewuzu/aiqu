package com.diankong.sexstory.mobile.bean;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/27.
 * 描述：
 * =============================================
 */

public class ShopListPojo  {
    public boolean isCheck = false;
    public boolean isChoosed;
    public String  goodName;
    public String  goodSmallIcon;
    public double  goodPrice;
    public int  id;
    public int  count;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}
