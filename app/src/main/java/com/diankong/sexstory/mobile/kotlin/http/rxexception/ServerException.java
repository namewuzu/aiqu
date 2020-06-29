package com.diankong.sexstory.mobile.kotlin.http.rxexception;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class ServerException extends Exception {

    private String message;
    private   int code;
    private   String status;

    public ServerException(int code, String message) {
        super(message);
        this.code=code;
        this.message=message;
    }
    public ServerException(String status, String message) {
        super(message);
        this.status=status;
        this.message=message;
    }


    public int getCode() {
        return code;
    }

    public String getStatus(){
        return status;
    }

}
