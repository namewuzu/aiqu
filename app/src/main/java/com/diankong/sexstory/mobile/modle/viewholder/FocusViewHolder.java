package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.event.FocusEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.TimeUtils;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class FocusViewHolder extends BaseViewHolder<UserInfoPojo> {

    private ImageView iv_icon;
    private ImageView iv_sex;
    private TextView tv_name;
    private TextView tv_age;
    private TextView tv_time;
    private TextView tv_flo;
    private RelativeLayout rl;
    private int index;
    private int type;
    private AppCompatActivity act;


    public FocusViewHolder(ViewGroup parent, int index, int type,AppCompatActivity act) {
        super(parent, R.layout.item_focus);
        this.index = index;
        this.type = type;
        this.act = act;
        tv_name = (TextView) $(R.id.tv_name);
        tv_age = (TextView) $(R.id.tv_age);
        tv_time = (TextView) $(R.id.tv_time);
        tv_flo = (TextView) $(R.id.tv_flo);
        iv_icon = (ImageView) $(R.id.iv_icon);
        iv_sex = (ImageView) $(R.id.iv_sex);
        rl = (RelativeLayout) $(R.id.rl);

    }

    @Override
    public void setData(final UserInfoPojo data) {
        super.setData(data);

        GlideImageLoader.displayRound(App.getInstance(),iv_icon,data.avatar);
        tv_name.setText(data.nickName);
        tv_age.setText(data.age+"");
        tv_time.setText(TimeUtils.milliseconds2String(data.createTime,TimeUtils.DEFAULT_SDF2));
//        tv_flo.setText(data.nickName);

//        if (data.isConcert == 1) {
//            tv_flo.setText("取消关注");
//        } else {
//            tv_flo.setText("关注");
//        }

        if(data.sex==1){
            iv_sex.setImageResource(R.mipmap.ic_bt_c);
        }else{
            iv_sex.setImageResource(R.mipmap.ic_bt_d);
        }

        if(type==1){
            tv_flo.setVisibility(View.VISIBLE);
        }else{
            tv_flo.setVisibility(View.GONE);
        }

        tv_flo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (tv_flo.getText().toString().equals("关注")) {
                    flo(data.concertUserId);
                } else {
                    flo2(data.concertUserId);
                }
            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(type==2){
                    EventBus.getDefault().post(new FocusEvent(data.concertUserId,data.nickName));
                    act.finish();
                }else{
                    Intent intent = new Intent(getContext(), UserDetailActivity.class);
                    if(index==1){
                        intent.putExtra("USERID",data.concertUserId);
                    }else{
                        intent.putExtra("USERID",data.fansId);
                    }
                    getContext().startActivity(intent);
                }

            }
        });
    }


    //关注
    public void flo(int id) {
        EasyHttp.get(Api.apiurl2 + "user/concertUser")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("concertUserId", String.valueOf(id))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {
                        tv_flo.setText("取消关注");
                    }

                }) {
                });


    }

    //取消关注
    public void flo2(int id) {
        EasyHttp.get(Api.apiurl2 + "user/unconcertUser")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("concertUserId", String.valueOf(id))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {
                        tv_flo.setText("关注");
                    }

                }) {
                });


    }
}
