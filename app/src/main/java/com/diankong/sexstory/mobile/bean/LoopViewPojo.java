package com.diankong.sexstory.mobile.bean;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class LoopViewPojo {
    public String bannerpicurl;// (string, optional): bannner地址 ,
    public String bannerdesc;//(string, optional): banner说明 ,
    public String bannerid;// (string, optional): 广告编号 ,
    public String bannerlinkurl;//(string, optional): 广告地址 ,
    public String bannertitle;//(string, optional): 标题

    public int id;
    public String url;
    public int bookId;
    public int chapters;
    public String bannerUrl;
    public String bookName;

    public LoopViewPojo(String url) {
        this.url = url;
    }
}
