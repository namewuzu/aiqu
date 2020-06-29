package com.diankong.sexstory.mobile.bean;

import java.io.Serializable;

/**
 * Created by apanda on 2017/7/24.
 */

public class UploadImgPojo implements Serializable {
    public String img;
    public int height;
    public int width;


    public UploadImgPojo() {
    }

    public UploadImgPojo(String _img, int _height, int _width) {
        img = _img;
        height = _height;
        width = _width;
    }

    public interface UploadImagePojoLisenter {
        void getUploadImagePojo(UploadImgPojo _uploadImgPojo);
    }
}
