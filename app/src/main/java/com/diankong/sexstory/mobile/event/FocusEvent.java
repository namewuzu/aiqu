package com.diankong.sexstory.mobile.event;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/30.
 * 描述：
 * =============================================
 */

public class FocusEvent {
    public int concertUserId;
    public String nickName;

    public FocusEvent(int concertUserId, String nickName) {
        this.concertUserId = concertUserId;
        this.nickName = nickName;
    }
}