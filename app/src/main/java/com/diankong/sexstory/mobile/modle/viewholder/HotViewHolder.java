package com.diankong.sexstory.mobile.modle.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.CirclePojo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class HotViewHolder extends BaseViewHolder<CirclePojo> {
    public TextView tv_name;


    public HotViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_city);
        tv_name = (TextView) $(R.id.tv_name);


    }

    @Override
    public void setData(final CirclePojo data) {
        super.setData(data);

        tv_name.setText(data.nickName);



    }


}
