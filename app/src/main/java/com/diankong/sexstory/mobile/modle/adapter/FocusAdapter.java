package com.diankong.sexstory.mobile.modle.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.modle.viewholder.FocusViewHolder;
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

public class FocusAdapter extends RecyclerArrayAdapter<UserInfoPojo> {

    private int index;
    private int type;
    private AppCompatActivity act;

    public FocusAdapter(Context context,int index,int type,AppCompatActivity act) {
        super(context);
        this.index = index;
        this.type = type;
        this.act =act;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FocusViewHolder(parent,index,type,act);
    }
}
