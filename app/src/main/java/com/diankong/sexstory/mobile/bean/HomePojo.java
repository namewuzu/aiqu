package com.diankong.sexstory.mobile.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/20.
 * 描述：
 * =============================================
 */

public class HomePojo {

    public String title;
    public String bookType;
    public List<BookInfoPojo> bookInfoList1;
    public List<BookInfoPojo> bookInfoList2;

    public HomePojo(String title, List<BookInfoPojo> bookInfoList1, List<BookInfoPojo> bookInfoList2) {
        this.title = title;
        this.bookInfoList1 = bookInfoList1;
        this.bookInfoList2 = bookInfoList2;
    }

    public static List<HomePojo> getData() {
        List<HomePojo> homePojos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            homePojos.add(new HomePojo("小编力推", getList1(), getList2()));
        }

        return homePojos;
    }

    public static List<BookInfoPojo> getList1() {
        List<BookInfoPojo> infoPojos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            infoPojos.add(new BookInfoPojo("http://static.zongheng.com/upload/cover/2011/12/1324038912358.JPG", "三国战神之吕布", "漂流岁月"));
        }

        return infoPojos;
    }

    public static List<BookInfoPojo> getList2() {
        List<BookInfoPojo> infoPojos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            infoPojos.add(new BookInfoPojo("http://static.zongheng.com/upload/cover/2011/12/1324038912358.JPG", "三国战神之吕布", "漂流岁月", "一个普通的少年，意外穿越到三国。且看他如何斗名将，泡美眉，玩转三国，成就一代战...", "历史军事", "389.2万字"));
        }

        return infoPojos;
    }

    public int id;
    public int bookId;
    public int chapters;
    public String bannerUrl;
    public String bookName;

}
