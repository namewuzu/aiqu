package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.CirclePojo;
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

public class CityViewHolder extends BaseViewHolder<CirclePojo> {
    public TextView tv_name;
    public TextView tv_age;
    public TextView tv_city;
    public TextView tv_desc;
    public ImageView iv_avatar;
    public ImageView iv_sex;
    public RelativeLayout rl_content;

    public CityViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_city);
        tv_name = (TextView) $(R.id.tv_name);
        tv_age = (TextView) $(R.id.tv_age);
        tv_city = (TextView) $(R.id.tv_city);
        tv_desc = (TextView) $(R.id.tv_desc);
        iv_avatar = (ImageView) $(R.id.iv_avatar);
        iv_sex = (ImageView) $(R.id.iv_sex);
        rl_content = (RelativeLayout) $(R.id.rl_content);

    }

    @Override
    public void setData(final CirclePojo data) {
        super.setData(data);

        tv_name.setText(data.nickName);
        tv_age.setText(data.age+"岁");
        tv_city.setText(data.wxCity);
        tv_desc.setText(data.remark);

        GlideImageLoader.displayRound(App.getInstance(),iv_avatar,data.avatar);

        if (data.sex == 1) {
            iv_sex.setBackgroundResource(R.mipmap.ic_bt_c);
        } else {
            iv_sex.setBackgroundResource(R.mipmap.ic_bt_d);
        }

        rl_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), UserDetailActivity.class);
                intent.putExtra("USERID",data.id);
                getContext().startActivity(intent);
            }
        });

    }


}
