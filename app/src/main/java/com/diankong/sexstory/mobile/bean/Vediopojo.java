package com.diankong.sexstory.mobile.bean;

import android.databinding.BaseObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/4/3.
 * 描述：
 * =============================================
 */

public class Vediopojo extends BaseObservable {


    public String vediourl;
    public String imgurl;
    public String title;
    public int share;
    public int sharemoney;

    public Vediopojo() {
    }

    public Vediopojo(String vediourl, String imgurl, String title, int share) {
        this.imgurl = imgurl;
        this.vediourl = vediourl;
        this.title = title;
        this.share = share;
    }

    public static List<Vediopojo> getVedioList() {
        List<Vediopojo> list = new ArrayList<>();

        list.add(new Vediopojo("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "哈哈", 1));
        list.add(new Vediopojo("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "哈哈", 0));
        list.add(new Vediopojo("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "哈哈", 1));
        list.add(new Vediopojo("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "哈哈", 0));
        list.add(new Vediopojo("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "哈哈", 1));
        list.add(new Vediopojo("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "哈哈", 0));
        list.add(new Vediopojo("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "哈哈", 1));
        list.add(new Vediopojo("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4", "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640", "哈哈2", 0));

        return list;
    }




    public int id;
    public long createTime;
    public String vedioUrl;
    public String vedioTitle;
    public String imgageUrl;
    public String duration;
    public String authorId;
    public String vedioId;
    public String authorName;
    public String format;
    public double browsePrice;
    public String playCount;
    public String browseCount;
    public String shareCount;
    public String delStatus;
    public String authorImgUrl;
    public int vedioClassId;
}
