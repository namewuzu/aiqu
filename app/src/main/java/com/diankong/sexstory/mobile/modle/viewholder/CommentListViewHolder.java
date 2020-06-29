package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.BookInfoPojo;
import com.diankong.sexstory.mobile.modle.activity.CircleDetailsActivity;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.TimeUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class CommentListViewHolder extends BaseViewHolder<BookInfoPojo> {

    private ImageView iv_icon;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_username;
    private TextView tv_comment;
    private LinearLayout ll_content;


    public CommentListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_comment_list);
        tv_name = (TextView) $(R.id.tv_name);
        tv_time = (TextView) $(R.id.tv_time);
        tv_username = (TextView) $(R.id.tv_username);
        tv_comment = (TextView) $(R.id.tv_comment);
        iv_icon = (ImageView) $(R.id.iv_icon);
        ll_content = (LinearLayout) $(R.id.ll_content);

    }

    @Override
    public void setData(final BookInfoPojo data) {
        super.setData(data);

        tv_name.setText(data.userNickname);
        tv_time.setText(TimeUtils.milliseconds2String(data.createTime));
        tv_username.setText(data.userName);
        tv_comment.setText(data.comment);
        GlideImageLoader.displayRound(App.getInstance(),iv_icon,data.avatar);

        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), CircleDetailsActivity.class);
                intent.putExtra("ID", data.id);
                getContext().startActivity(intent);
            }
        });
    }
}
