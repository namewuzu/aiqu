package io.rong.callkit.util;

import com.zhouyou.http.model.ApiResult;

/**
 * @author knife
 * @title
 * @data 16/8/31
 * @email 842786338@qq.com
 */
public class BaseResult<T> extends ApiResult<T> {

    /**
     * errMsg 错误消息
     * <p>
     * status 0成功 非0错误
     * <p>
     * data数据内容
     */


    int status;
    T obj;

    @Override
    public int getCode() {
        return status;
    }

    @Override
    public void setCode(int code) {
        status = code;
    }

    @Override
    public T getData() {
        return obj;
    }

    @Override
    public void setData(T data) {
        obj = data;
    }

    @Override
    public boolean isOk() {
        return status == 200;
    }
}
