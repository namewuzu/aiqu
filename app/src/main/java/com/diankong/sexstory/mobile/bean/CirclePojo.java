package com.diankong.sexstory.mobile.bean;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/5.
 * 描述：
 * =============================================
 */

public class CirclePojo {

   public List<Image> imageList;
   public int id;
   public int userId;
   public int showCount;
   public int replyCount;
   public int pointCount;
   public int replyedUserId;
   public int communityId;
   public int deleteStatus;
   public long createTime;
   public long publishTime;
   public long updateTime;
   public String operatorName;
   public String descs;
   public String userName;
   public String comment;
   public String avatar;
   public String userNickname;
   public String imageUrl;
   public String avatarUrl;
   public List<CirclePojo> replies;
   public int shareCount;
   public int sex;
   public int age;
   public String address;
   public int isReply;
   public String replyedUserName;
   public String nickName;
   public String wxCity;
   public String remark;
   public int flo;
   public int isConcert;
   public int states;
   public int coin;


    public CirclePojo() {
   }

   public CirclePojo(List<Image> imageList) {
      this.imageList = imageList;
   }
}
