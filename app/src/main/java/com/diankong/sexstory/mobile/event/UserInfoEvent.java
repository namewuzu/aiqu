package com.diankong.sexstory.mobile.event;

import com.diankong.sexstory.mobile.bean.UserInfoPojo;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/27.
 * 描述：
 * =============================================
 */

public class UserInfoEvent {
    public UserInfoPojo pojo;

    public UserInfoEvent(UserInfoPojo _pojo) {
        pojo = _pojo;
    }
}