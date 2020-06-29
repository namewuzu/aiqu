package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.OrderPojo;
import com.diankong.sexstory.mobile.modle.activity.PinglunActivity;
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

public class GoodOrderListViewHolder extends BaseViewHolder<OrderPojo> {
    private ImageView ivPic;
    private TextView tvName;
    private TextView tvType;
    private TextView tvMoney;
    private TextView tvStatus;
    private TextView tv_orderid;
    private TextView tv_pinglun;

    public GoodOrderListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_goods_type_list);
        tvStatus = (TextView) $(R.id.tv_status);
        tvMoney = (TextView) $(R.id.tv_money);
        tvType = (TextView) $(R.id.tv_type);
        tvName = (TextView) $(R.id.tv_name);
        tv_orderid = (TextView) $(R.id.tv_orderid);
        tv_pinglun = (TextView) $(R.id.tv_pinglun);
        ivPic = (ImageView) $(R.id.iv_pic);
    }

    @Override
    public void setData(final OrderPojo data) {
        super.setData(data);
        tvMoney.setText(data.goodPrice);
        tvName.setText(data.goodName);
        tv_orderid.setText("订单号：" + data.orderId);
        GlideImageLoader.onDisplayImage(getContext(),ivPic,data.goodSmallIcon);

        if(data.orderStatus==1){
            tvStatus.setText("未支付");
        }else if(data.orderStatus==2){
            tvStatus.setText("已支付");
        }else {
            tvStatus.setText("支付失败");
        }

        if(data.orderType==1){
            tvType.setText("微信支付");
        }else if(data.orderStatus==2){
            tvType.setText("支付宝支付");
        }

        tv_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), PinglunActivity.class);
                intent.putExtra("GOODID",data.goodId);
                getContext().startActivity(intent);
            }
        });

    }
}
