package com.diankong.sexstory.mobile.bean;

import java.io.Serializable;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/6.
 * 描述：
 * =============================================
 */

public class GoodsPojo implements Serializable {

    public int id;
    public int type;
    public int isFree;
    public String goodTypeName;
    public String goodTypeUrl;


    public String operatorName;
    public String updateTime;
    public String createTime;
    public String goodName;
    public String goodSmallIcon;
    public String goodDes;
    public double goodPrice;
    public String title;
    public String city;
    public String goodDesUrl;
    public String goodUrl;
    public double totalPrice;
    public int operatorId;
    public int deleteStatus;
    public int goodStatus;
    public int goodTypeId;
    public int fare;
    public int sales;
    public int ifWx;
    public int num;

    public List<GoodsPojo> list;
    public List<GoodsPojo> goodList;
    public TwoListPojo twoList;
    public List<GoodsPojo> hotGoodTypeList;
    public List<GoodsPojo> hotList;
    public List<GoodsPojo> qingquList;

    public String address;
    public String phone;
    public String name;
    public String url;
    public String wechatImgUrl;
    public String desc;
    public String imageUrl;
    public String showImgUrl;
    public String imgUrl;
    public String wxNo;


    public GoodsPojo(int type,String desc,String goodName,double goodPrice,List<GoodsPojo> list){
        this.type = type;
        this.desc = desc;
        this.goodName = goodName;
        this.goodPrice = goodPrice;
        this.list = list;
    }

    public GoodsPojo(int type,String desc,String goodName,double goodPrice){
        this.type = type;
        this.desc = desc;
        this.goodName = goodName;
        this.goodPrice = goodPrice;
    }

    public GoodsPojo(String _goodName,String _goodSmallIcon,double _goodPrice,int num){
        this.goodName = _goodName;
        this.goodSmallIcon = _goodSmallIcon;
        this.goodPrice = _goodPrice;
        this.num = num;
    }
}
