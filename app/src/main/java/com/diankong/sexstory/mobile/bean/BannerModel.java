package com.diankong.sexstory.mobile.bean;

/**
 * =============================================
 * 芒果瑞集团有限公司，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/1/17.
 * 描述：
 * =============================================
 */

public class BannerModel {
    private int id;
    private String imgurl;

    public BannerModel() {
    }

    public BannerModel(int id, String imgurl) {
        this.id = id;
        this.imgurl = imgurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "BannerModel{" +
                "id=" + id +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}
