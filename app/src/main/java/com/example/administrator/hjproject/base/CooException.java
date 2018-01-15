package com.example.administrator.hjproject.base;

/**
 * Created by apanda on 2017/3/24.
 */

public class CooException extends Exception {


    private int errorCode;
    private String retCd;  //异常对应的返回码
    private String msgDes;  //异常对应的描述信息

    public CooException() {
        super();
    }

    public CooException(String message) {
        super(message);
        msgDes = message;
    }

    public CooException(String retCd, String msgDes) {
        super(msgDes);
        this.retCd = retCd;
        this.msgDes = msgDes;
    }

    public CooException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.msgDes = message;
    }

    public String getRetCd() {
        return retCd;
    }

    public String getMsgDes() {
        return msgDes;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
