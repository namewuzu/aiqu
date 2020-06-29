package com.diankong.sexstory.mobile.bean.lisener;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/7.
 * 描述：
 * =============================================
 */

public interface ImgLisenter<T> {
     void onSucceed(T data);

     void onFail(String msg);
}
