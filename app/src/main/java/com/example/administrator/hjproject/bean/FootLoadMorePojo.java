package com.example.administrator.hjproject.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.administrator.hjproject.BR;


/**
 * Created by Administrator on 2017/9/19.
 */

public class FootLoadMorePojo extends BaseObservable{

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
        notifyPropertyChanged(BR.isShowLine);
    }
    @Bindable
    public String text;
    @Bindable
    public int status;
    @Bindable
    public boolean isShowLine;
}
