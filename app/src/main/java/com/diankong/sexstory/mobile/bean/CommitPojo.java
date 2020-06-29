package com.diankong.sexstory.mobile.bean;

import java.io.Serializable;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/27.
 * 描述：
 * =============================================
 */

public class CommitPojo implements Serializable {
    public List<CommitPojo> goods;

    public int goodId;
    public int count;
    public int userId;
    public int orderType;
    public int orderStatus;
    public int fare;

    public double fee;
    public String goodName;
    public double goodPrice;
    public String goodSmallIcon;
    public String adress;
    public String name;
    public String phone;
    public String city;
    public String remark;

    public CommitPojo() {

    }

    public CommitPojo(int _goodId, int _count,String goodName,String goodSmallIcon,double goodPrice) {
        this.goodId = _goodId;
        this.count = _count;
        this.goodName = goodName;
        this.goodSmallIcon = goodSmallIcon;
        this.goodPrice = goodPrice;
    }

    public CommitPojo(int _goodId, int _count,String goodName,String goodSmallIcon,double goodPrice,int fare) {
        this.goodId = _goodId;
        this.count = _count;
        this.goodName = goodName;
        this.goodSmallIcon = goodSmallIcon;
        this.goodPrice = goodPrice;
        this.fare =fare;
    }
}
