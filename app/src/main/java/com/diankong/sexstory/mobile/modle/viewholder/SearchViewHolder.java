package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;
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

public class SearchViewHolder extends BaseViewHolder<UserInfoPojo> {

    private TextView tv_name;
    private TextView tv_id;
    private TextView tv_address;
    private TextView tv_age;
    private TextView tv_time;
    private ImageView iv_avatar;
    private ImageView iv_sex;
    private LinearLayout ll_content;

    public SearchViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_search);
        tv_name = (TextView) $(R.id.tv_name);
        tv_id = (TextView) $(R.id.tv_id);
        tv_address = (TextView) $(R.id.tv_address);
        tv_age = (TextView) $(R.id.tv_age);
        tv_time = (TextView) $(R.id.tv_time);
        iv_avatar = (ImageView) $(R.id.iv_avatar);
        iv_sex = (ImageView) $(R.id.iv_sex);
        ll_content = (LinearLayout) $(R.id.ll_content);

    }

    @Override
    public void setData(final UserInfoPojo data) {
        super.setData(data);

        tv_name.setText(data.nickName);
        tv_id.setText("爱趣号：" + data.id);
        tv_address.setText(data.address);
        tv_age.setText(data.age+"岁");
        GlideImageLoader.displayRound(App.getInstance(),iv_avatar,data.avatar);

        if(data.sex==1){
            iv_sex.setImageResource(R.mipmap.ic_bt_c);
        }else{
            iv_sex.setImageResource(R.mipmap.ic_bt_d);
        }

        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),UserDetailActivity.class);
                intent.putExtra("USERID",data.id);
                getContext().startActivity(intent);
            }
        });

    }
}
