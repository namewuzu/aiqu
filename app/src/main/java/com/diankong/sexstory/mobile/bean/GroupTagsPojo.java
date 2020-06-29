package com.diankong.sexstory.mobile.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.diankong.sexstory.mobile.BR;

import java.io.Serializable;


/**
 * Created by apanda on 2016/12/19.
 */

public class GroupTagsPojo extends BaseObservable implements Serializable {
    private String tagid;//F
    private int tagintid;//F
    private String tagname;//
    private int select;//
    public int normalcount;
    public int opencount;
    public String code;
    public int join;
    public int coin;

    public int id;
    public int zoneCount;
    public int peopleCount;
    public int showCount;
    public String topicTitle;
    public String topicDesc;
    public String createTime;
    public String img;

    public int getJoin() {
        return join;
    }

    public void setJoin(int _join) {
        join = _join;
    }

    public GroupTagsPojo(String tagname, int coin, int select){
        this.tagname = tagname;
        this.coin = coin;
        this.select = select;
    }

    public GroupTagsPojo(String _tagname,int tagintid){
        this.tagname = _tagname;
        this.tagintid = tagintid;
    }

    @Bindable
    public int authmaodule;

    public double lat;
    public double lon;

    @Bindable
    public String getTagid() {
        return tagid;
    }

    public void setTagid(String _tagid) {
        tagid = _tagid;
        notifyPropertyChanged(BR.tagid);
    }

    @Bindable
    public int getTagIntid() {
        return tagintid;
    }

    public void setTagIntid(int _tagintid) {
        tagintid = _tagintid;
        notifyPropertyChanged(BR.tagIntid);
    }

    @Bindable
    public String getTagname() {
        return tagname;
    }

    public void setTagname(String _tagname) {
        tagname = _tagname;
        notifyPropertyChanged(BR.tagname);
    }

    @Bindable
    public int getSelect() {
        return select;
    }

    public void setSelect(int _select) {
        select = _select;
        notifyPropertyChanged(BR.select);
    }
}
