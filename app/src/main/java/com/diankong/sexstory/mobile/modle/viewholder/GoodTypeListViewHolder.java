package com.diankong.sexstory.mobile.modle.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.GoodsPojo;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.widget.WBViewActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class GoodTypeListViewHolder extends BaseViewHolder<GoodsPojo> {
    private ImageView ivPic;
    private TextView tvName;
    private TextView tvNum;
    private TextView tvMoney;
    private LinearLayout _layout;

    public GoodTypeListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_goods_order_list);
        tvNum = (TextView) $(R.id.tv_num);
        tvName = (TextView) $(R.id.tv_name);
        tvMoney = (TextView) $(R.id.tv_money);
        ivPic = (ImageView) $(R.id.iv_pic);
        _layout = (LinearLayout) $(R.id.ll_content);
    }

    @Override
    public void setData(final GoodsPojo data) {
        super.setData(data);
        tvNum.setText(String.valueOf(data.sales));
        tvMoney.setText(String.valueOf(data.goodPrice));
        tvName.setText(String.valueOf(data.goodName));
        GlideImageLoader.onDisplayImage(getContext(),ivPic,data.goodSmallIcon);

        _layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WBViewActivity.startActivity(getContext(),"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + data.id + "&userId=" + SpUtlis.getId(),data.id,true);
            }
        });
    }
}
