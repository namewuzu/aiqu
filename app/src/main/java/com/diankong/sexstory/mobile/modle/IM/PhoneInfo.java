package com.diankong.sexstory.mobile.modle.IM;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/14.
 * 描述：
 * =============================================
 */

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 自定义消息的实体类
 *
 */
@SuppressLint("ParcelCreator")
@MessageTag(value = "ec:phoneinfo" ,flag = MessageTag.ISPERSISTED|MessageTag.ISCOUNTED)
public class PhoneInfo extends MessageContent {

    private String userName;

    private String phoneNum;

    private int img;


    public static PhoneInfo setPhoneData(String name,String num,int img){
        PhoneInfo info =new PhoneInfo();
        info.userName =name;
        info.phoneNum = num;
        info.img = img;
        return info;
    }



    @Override
    public byte[] encode() {
        JSONObject object =new JSONObject();
        object.put("userName",userName);
        object.put("phoneNum",phoneNum);
        object.put("img",img);


        try {
            return object.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;

    }

    public PhoneInfo() {
    }

    public PhoneInfo(byte[] data) {
        super(data);
        String jsonStr=null;
        try {
            jsonStr =new String(data,"UTF-8");
            JSONObject object = JSON.parseObject(jsonStr);

            setUserName(object.getString("userName"));
            setPhoneNum(object.getString("phoneNum"));
            setImg(object.getInteger("img"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int _i) {
        ParcelUtils.writeToParcel(dest, userName);
        ParcelUtils.writeToParcel(dest,phoneNum);
        ParcelUtils.writeToParcel(dest,img);
    }

    public static final Creator<PhoneInfo> CREATOR = new Creator<PhoneInfo>() {


        @Override
        public PhoneInfo createFromParcel(Parcel _parcel) {
            return new PhoneInfo(_parcel);
        }

        @Override
        public PhoneInfo[] newArray(int _i) {
            return new PhoneInfo[_i];
        }
    };

    public PhoneInfo(Parcel parcel){

        userName = ParcelUtils.readFromParcel(parcel);
        phoneNum = ParcelUtils.readFromParcel(parcel);
        img = ParcelUtils.readIntFromParcel(parcel);

    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int _img) {
        this.img = _img;
    }



}
