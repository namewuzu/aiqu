package com.diankong.sexstory.mobile.modle.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.diankong.sexstory.mobile.bean.CirclePojo;
import com.diankong.sexstory.mobile.modle.viewholder.CircleViewHolder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class CircleAdapter extends RecyclerArrayAdapter<CirclePojo> {
    private int type;
    private AppCompatActivity _appCompatActivity;
    public CircleAdapter(Context context,int type,AppCompatActivity _appCompatActivity) {
        super(context);
        this.type=type;
        this._appCompatActivity = _appCompatActivity;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CircleViewHolder(parent,type,_appCompatActivity);
    }
}
