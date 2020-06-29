package com.diankong.sexstory.mobile.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/18.
 * 描述：
 * =============================================
 */

public class TestPojo extends BaseObservable implements Serializable {
    public String test;
    public String token;
    public int ranking;
    public String author;
    public String category;
    public List<String> imgs;
    public int nameType;
    public int tabType;
    public int resourceID;
    public String apiUrl;
    public String name;
    public String adTrack;
    public String createdAt;
    public String publishedAt;
    public String type;
    public String url;
    public String contentURL;
    public String contentText;
    public String pageUrl;
    public String contentsUrl;
    public long goodLastTime;


    public TestPojo() {
    }

    public TestPojo(String test) {
        this.test = test;
    }

    public static List<TestPojo> gettest() {
        List<TestPojo> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            list.add(new TestPojo("中国推介" + i));
        }

        return list;
    }

    public static List<TestPojo> gettesttitle() {
        List<TestPojo> list = new ArrayList<>();

        for (int i = 0; i <= 2; i++) {
            list.add(new TestPojo("日常任务"));
        }

        return list;
    }





    public static class EntrustPojo extends BaseObservable {
        public int id;
        public String type;
        public String price;
        public String num;
        public String deal_num;
        public String times;

        public EntrustPojo() {

        }

        /*public EntrustPojo(String id, String type, String price, String num, String deal_num,String times) {
            this.id = id;
            this.two = two;
            this.three = three;
            this.four = four;
            this.five = five;
        }*/

   /*     public static List<EntrustPojo> getEntrustPojo() {
            List<EntrustPojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new EntrustPojo("买入", "2018-12-12 15:36", "总额麦宝1000个", "0.90元", "剩余麦宝800个"));
            }
            return list;
        }*/
    }

    public static class HistoryPojo {
        public String one;
        public String two;
        public String three;
        public String four;
        public String five;
        public String six;

        public HistoryPojo(String one, String two, String three, String four, String five, String six) {
            this.one = one;
            this.two = two;
            this.three = three;
            this.four = four;
            this.five = five;
            this.six = six;
        }

        public static List<HistoryPojo> getHistoryPojo() {
            List<HistoryPojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new HistoryPojo("成交价格", "845.56元", "交易单价", "0.90元", "卖出麦宝4879个", "12-12 15:45"));
            }
            return list;
        }
    }

    public static class TradingPojo {
        public int one;
        public String two;
        public String three;
        public String four;

        public TradingPojo(int one, String two, String three, String four) {
            this.one = one;
            this.two = two;
            this.three = three;
            this.four = four;
        }

        public static List<TradingPojo> getTradingPojo() {
            List<TradingPojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new TradingPojo(0, "卖八", "43.45", "2456"));
            }
            for (int i = 0; i <= 8; i++) {
                list.add(new TradingPojo(1, "买一", "43.45", "2456"));
            }
            return list;
        }
    }


    public static class MsgPojo extends BaseObservable {
        public String touxiang;
        public String nicheng;
        public String biaoti;

        public String img1;
        public String img2;
        public String img3;

        public MsgPojo(String touxiang, String nicheng, String biaoti, String img1, String img2, String img3) {
            this.touxiang = touxiang;
            this.nicheng = nicheng;
            this.biaoti = biaoti;
            this.img1 = img1;
            this.img2 = img2;
            this.img3 = img3;
        }

        public static List<MsgPojo> getMsgPojo() {
            List<MsgPojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new MsgPojo("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "我的小可爱", "今天你过来了吗我的小可爱哈哈哈哈哈", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));
            }
            return list;
        }
    }

    public static class UserDetailsPojo extends BaseObservable {
        public String tupian;
        public String biaoti;
        public String shijian;

        public String bofang;

        public UserDetailsPojo(String tupian, String biaoti, String shijian, String bofang) {
            this.tupian = tupian;
            this.biaoti = biaoti;
            this.shijian = shijian;
            this.bofang = bofang;
        }

        public static List<UserDetailsPojo> getUserDetailsPojo() {
            List<UserDetailsPojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new UserDetailsPojo("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "我的小可爱我的小可爱我的小可爱", "04-05", "580萬次播放"));
            }
            return list;
        }
    }

    public static class IncomePojo extends BaseObservable {
        public String title;
        public String desc;
        public String money;


        public IncomePojo(String title, String desc, String money) {
            this.title = title;
            this.desc = desc;
            this.money = money;
        }

        public static List<IncomePojo> getIncomePojo() {
            List<IncomePojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new IncomePojo("提现","2018-04-14 16:39","+20元"));
            }
            return list;
        }
    }



    public static class shituPojo extends BaseObservable {
        public String idcard;
        public String phone;
        public String money;
        public String status;


        public shituPojo(String idcard, String phone, String money,String status) {
            this.idcard = idcard;
            this.phone = phone;
            this.money = money;
            this.status = status;
        }

        public static List<shituPojo> getshituPojo() {
            List<shituPojo> list = new ArrayList<>();
            for (int i = 0; i <= 8; i++) {
                list.add(new shituPojo("12580","152**382962","20元","已发放"));
            }
            return list;
        }
    }


    public String price;
    public String deal_num;
    public String payment;
    public String times;

    public String desc;
    public String money;
    public String go;
    public String authors;
    public String content;

    public TestPojo(String title,String desc,String money,String go){
        this.title = title;
        this.desc = desc;
        this.money = money;
        this.go = go;
    }


    public static List<TestPojo> getitem() {
        List<TestPojo> list = new ArrayList<>();

        for (int i = 0; i <= 1; i++) {
            list.add(new TestPojo("绑定手机号","新用户绑定手机号奖励500金币，1元现金","+500金币 +1元","去绑定"));
        }

        return list;
    }


    public List<AppListPojo> data;


    public int id;
    public String title;
    public String thumbHorizontal1;
    public String thumbHorizontal2;
    public String thumbHorizontal3;
    public String thumbPortrait;
    public String tag;
    public String keyword;
    public String summary;
    public String introText;
    public String introURL;
    public String origin;
    public String creator;
    public String createTime;
    public String pv;
    public String pv2;
    public String share;
    public String share2;
    public String like;
    public String like2;
    public String collect;
    public String collect2;
    public String dataType;
    public int template;
    public int displayType;
    public int uniqueID;
    public String password;
    public String fee;
    public String order;
    public int parentID;
    public int status;
    public String reserve1;
    public String reserve2;
    public String reserve3;
    public String reserve4;
    public String reserve5;
    public String reserve6;
    public String reserve7;
    public String reserve8;
    public String reserve9;
    public String reserve10;
    public String thumb;
    public String sdURL;
    public String hdURL;
    public String duration;
    public String freeTime;
    public List<TestPojo> second_level;
    public List<TestPojo> relation;

}
