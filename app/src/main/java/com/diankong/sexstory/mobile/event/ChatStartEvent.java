package com.diankong.sexstory.mobile.event;

import com.diankong.sexstory.mobile.bean.GroupTagsPojo;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/23.
 * 描述：
 * =============================================
 */

public class ChatStartEvent {

    public List<GroupTagsPojo> _pojos1;

    public ChatStartEvent(List<GroupTagsPojo> _pojos1) {
        this._pojos1 = _pojos1;
    }
}
