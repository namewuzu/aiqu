package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.BookInfoPojo;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.BookDetailsActivity;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class BookHisListViewHolder extends BaseViewHolder<BookInfoPojo> {

    private ImageView iv_icon;
    private TextView tv_title;
    private TextView tv_introduc;
    private TextView tv_author;
    private TextView tv_num;
    private TextView tv_type;
    private LinearLayout ll_content;

    public BookHisListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_home_vertical);
        tv_title = (TextView) $(R.id.tv_title);
        tv_introduc = (TextView) $(R.id.tv_introduc);
        tv_author = (TextView) $(R.id.tv_author);
        tv_num = (TextView) $(R.id.tv_num);
        tv_type = (TextView) $(R.id.tv_type);
        iv_icon = (ImageView) $(R.id.iv_icon);
        ll_content = (LinearLayout) $(R.id.ll_content);
    }

    @Override
    public void setData(final BookInfoPojo data) {
        super.setData(data);
        tv_title.setText(data.bookName);
        tv_introduc.setText(data.intro);
        tv_author.setText(data.author);
        tv_num.setVisibility(View.GONE);
        tv_type.setVisibility(View.GONE);
        GlideImageLoader.onDisplayImage(getContext(), iv_icon, Api.IMGURL + data.bookId + "/" + data.bookId + Api.IMGURL_FOOT1);

        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), BookDetailsActivity.class);
                intent.putExtra("articleid",data.bookId);
                intent.putExtra("articlename",data.articlename);
                intent.putExtra("intro",data.intro);
                intent.putExtra("author",data.author);
                getContext().startActivity(intent);
            }
        });
    }
}
