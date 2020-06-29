package com.diankong.sexstory.mobile.modle.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.CoinPojo;
import com.diankong.sexstory.mobile.utils.TimeUtils;
import com.diankong.sexstory.mobile.utils.UIUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class CoinViewHolder extends BaseViewHolder<CoinPojo> {
    public TextView tv_name;
    public TextView tv_time;
    public TextView tv_coin;


    public CoinViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_coin);
        tv_name = (TextView) $(R.id.tv_name);
        tv_time = (TextView) $(R.id.tv_time);
        tv_coin = (TextView) $(R.id.tv_coin);


    }

    @Override
    public void setData(final CoinPojo data) {
        super.setData(data);

        if(data.coinType==1){
            tv_name.setText("充值金币");
        }else if(data.coinType==2){
            tv_name.setText("金币送礼");
        }else if(data.coinType==3){
            tv_name.setText("金币提现");
        }else if(data.coinType==4){
            tv_name.setText("视频消耗金币");
        }else{
            tv_name.setText("有效评论 +1");
        }

        tv_time.setText(TimeUtils.milliseconds2String(data.createTime,TimeUtils.DEFAULT_SDF2));
        tv_coin.setText(data.coinCount+"");

        if(data.coinCount>0){
            tv_coin.setTextColor(UIUtils.getColor(R.color.red23));
        }else{
            tv_coin.setTextColor(UIUtils.getColor(R.color.gray_999));
        }

    }


}
