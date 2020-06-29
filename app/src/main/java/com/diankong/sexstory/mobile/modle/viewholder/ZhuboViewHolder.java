package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;
import com.diankong.sexstory.mobile.widget.GlideRoundTransform;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class ZhuboViewHolder extends BaseViewHolder<UserInfoPojo> {


    private TextView tv_name;
    private TextView tv_age;
    private TextView tv_num;
    private ImageView iv_avatar;
    private RelativeLayout rl_content;


    public ZhuboViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_zhubo);

        tv_name = (TextView) $(R.id.tv_name);
        tv_age = (TextView) $(R.id.tv_age);
        tv_num = (TextView) $(R.id.tv_num);
        iv_avatar = (ImageView) $(R.id.iv_avatar);
        rl_content = (RelativeLayout) $(R.id.rl_content);

    }

    @Override
    public void setData(final UserInfoPojo data) {
        super.setData(data);
//        GlideImageLoader.onDisplayImage(App.getInstance(), iv_avatar, data.avatar);

        Glide.with(App.getInstance())
                .load(data.avatar)
                .transform(new CenterCrop(App.getInstance()),new GlideRoundTransform(App.getInstance()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_mrlg)
                .dontAnimate()
                .crossFade()
                .into(iv_avatar);

        tv_name.setText(data.nickName);
        tv_age.setText(data.age + "岁");
        tv_num.setText("100%");
        rl_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), UserDetailActivity.class);
                intent.putExtra("USERID", data.id);
                getContext().startActivity(intent);
            }
        });
    }

}
