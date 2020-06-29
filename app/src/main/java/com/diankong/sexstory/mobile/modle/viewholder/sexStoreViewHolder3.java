package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.GoodsPojo;
import com.diankong.sexstory.mobile.modle.activity.GoodTypeActivity;
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

public class sexStoreViewHolder3 extends BaseViewHolder<GoodsPojo> {

    public ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private ImageView iv7;
    private ImageView iv8;
    private ImageView iv9;
    private ImageView iv10;

    public sexStoreViewHolder3(ViewGroup parent) {
        super(parent, R.layout.item_sexstore3);
        iv1 = (ImageView) $(R.id.iv1);
        iv2 = (ImageView) $(R.id.iv2);
        iv3 = (ImageView) $(R.id.iv3);
        iv4 = (ImageView) $(R.id.iv4);
        iv5 = (ImageView) $(R.id.iv5);
        iv6 = (ImageView) $(R.id.iv6);
        iv7 = (ImageView) $(R.id.iv7);
        iv8 = (ImageView) $(R.id.iv8);
        iv9 = (ImageView) $(R.id.iv9);
        iv10 = (ImageView) $(R.id.iv10);
    }

    @Override
    public void setData(final GoodsPojo data) {
        super.setData(data);

        GlideImageLoader.onDisplayImage(getContext(), iv1, data.hotGoodTypeList.get(0).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv2, data.hotGoodTypeList.get(1).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv3, data.hotGoodTypeList.get(2).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv4, data.hotGoodTypeList.get(3).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv5, data.hotGoodTypeList.get(4).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv6, data.hotGoodTypeList.get(5).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv7, data.hotGoodTypeList.get(6).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv8, data.hotGoodTypeList.get(7).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv9, data.hotGoodTypeList.get(8).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv10, data.hotGoodTypeList.get(9).imgUrl);

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.hotGoodTypeList.get(0).goodTypeId);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(0).goodTypeName);
                getContext().startActivity(intent);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.hotGoodTypeList.get(1).goodTypeId);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(1).goodTypeName);
                getContext().startActivity(intent);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.hotGoodTypeList.get(2).goodTypeId);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(2).goodTypeName);
                getContext().startActivity(intent);
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.hotGoodTypeList.get(3).goodTypeId);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(3).goodTypeName);
                getContext().startActivity(intent);
            }
        });
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.hotGoodTypeList.get(4).goodTypeId);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(4).goodTypeName);
                getContext().startActivity(intent);
            }
        });
        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.hotGoodTypeList.get(5).goodTypeId);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(5).goodTypeName);
                getContext().startActivity(intent);
            }
        });
        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.hotGoodTypeList.get(6).goodTypeId);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(6).goodTypeName);
                getContext().startActivity(intent);
            }
        });
        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.hotGoodTypeList.get(7).goodTypeId);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(7).goodTypeName);
                getContext().startActivity(intent);
            }
        });
        iv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.hotGoodTypeList.get(8).goodTypeId);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(8).goodTypeName);
                getContext().startActivity(intent);
            }
        });
        iv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", 12);
                intent.putExtra("TITLE",data.hotGoodTypeList.get(9).goodTypeName);
                getContext().startActivity(intent);
            }
        });


    }


}
