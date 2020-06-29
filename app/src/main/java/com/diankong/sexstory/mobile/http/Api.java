package com.diankong.sexstory.mobile.http;

import android.os.Environment;

public interface Api {
    String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    String IMG_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    String COO_PATH = SD_PATH + "/ivedio/qr_img/";
    String COO_IMG_PATH = SD_PATH + "/ivedio/img/";
    String LOG_PATH = SD_PATH + "/mbao/logs/";
    String COO_PATH_IMG = IMG_PATH + "/fqb/qr_img/";

    //配置App网络访问域名（正式）
    String apiurl = "http://www.feimiao.xin:8080/shop/";
    String apiurl2 = "http://www.habeili.xin:8082/shopBBS/";

    //配置App网络访问域名（测试）
//    String apiurl = "http://8.210.59.127:8080/shop/";
//    String apiurl2 = "http://8.210.59.127:8082/shopBBS/";

    String imgurl = "http://47.244.48.177";
//    String apiurl = "http://test.mgrtv.cn/";

    String IMGURL = imgurl + "/book/image/0/";
    String IMGURL_FOOT1 = "l.jpg";
    String IMGURL_FOOT2 = "s.jpg";
}