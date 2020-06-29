package com.diankong.sexstory.mobile.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/12.
 * 描述：
 * =============================================
 */

public class DatabindingUtils {

    @BindingAdapter({"showLoadingGif"})
    public static void showLoadingGif(ImageView tv, int status) {
        Glide.with(App.getContext())
                .load(R.mipmap.loading_roll)
                .into(tv);
    }

    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(ImageView tv, String url) {
        GlideImageLoader.onDisplayImage(App.getContext(), tv, url);
    }
    @BindingAdapter({"avatarUrl"})
    public static void setAvatarUrl(ImageView tv, String url) {
        GlideImageLoader.displayRound(App.getContext(),tv,url);
    }

}
