package com.diankong.sexstory.mobile.event;

import com.diankong.sexstory.mobile.bean.BookInfoPojo;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/26.
 * 描述：
 * =============================================
 */

public class BookInfoEvent {
    public BookInfoPojo _pojo;
    public int type;//1为加入书架，2为移除书架

    public BookInfoEvent(BookInfoPojo _pojo,int type) {
        this._pojo = _pojo;
        this.type = type;
    }
}
