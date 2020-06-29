package com.diankong.sexstory.mobile.modle.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.diankong.sexstory.mobile.bean.CirclePojo;
import com.diankong.sexstory.mobile.modle.viewholder.PingLunViewHolder;
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

public class PingLunAdapter extends RecyclerArrayAdapter<CirclePojo> {

    public EditText et;
    public TextView send;
    public int communityId;
    public Activity _context;

    public PingLunAdapter(Activity context, EditText _et, TextView _send , int communityId) {
        super(context);
        this.et = _et;
        this.send = _send;
        this.communityId = communityId;
        this._context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PingLunViewHolder(parent,et,send,communityId,_context);
    }
}
