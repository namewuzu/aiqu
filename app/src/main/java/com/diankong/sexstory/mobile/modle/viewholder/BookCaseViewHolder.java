package com.diankong.sexstory.mobile.modle.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.TestPojo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class BookCaseViewHolder extends BaseViewHolder<TestPojo> {

    private TextView textview;

    public BookCaseViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_book_case);
        textview = (TextView) $(R.id.tv_icon);
    }

    @Override
    public void setData(TestPojo data) {
        super.setData(data);
        textview.setText(data.test);
    }
}
