package com.diankong.sexstory.mobile.bean;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/16.
 * 描述：
 * =============================================
 */

public class GiftPojo {
    public int id;
    public String title;
    public String img;
    public int count;
    public int price;
    public String giftUrl;
    public String giftName;
    public int giftPrice;
    public int giftCount;

    public GiftPojo(int _id, String _title, String _img, int _count,int _price) {
        id = _id;
        title = _title;
        img = _img;
        count = _count;
        price = _price;
    }
}
