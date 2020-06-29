package com.diankong.sexstory.mobile.modle.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.diankong.sexstory.mobile.bean.CirclePojo;
import com.diankong.sexstory.mobile.modle.viewholder.CityViewHolder;
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

public class CityAdapter extends RecyclerArrayAdapter<CirclePojo> {
    public CityAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(parent);
    }
}
