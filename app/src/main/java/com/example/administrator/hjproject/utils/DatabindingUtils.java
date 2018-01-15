package com.example.administrator.hjproject.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.App;

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
                .load(R.mipmap.ic_loading)
                .into(tv);
    }
}
