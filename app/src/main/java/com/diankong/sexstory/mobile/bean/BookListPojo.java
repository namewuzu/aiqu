package com.diankong.sexstory.mobile.bean;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/21.
 * 描述：
 * =============================================
 */

public class BookListPojo {

    public int id;
    public String typeName;
    public String imgUrl;
    public int bookCount;

    public BookListPojo(int _id, String _typeName, String _imgUrl,int bookCount) {
        id = _id;
        typeName = _typeName;
        imgUrl = _imgUrl;
        this.bookCount = bookCount;
    }
}
