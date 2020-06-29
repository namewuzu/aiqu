package com.diankong.sexstory.mobile.modle.modelview;

import android.text.TextUtils;
import android.view.View;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.OSSPojo;
import com.diankong.sexstory.mobile.databinding.ActivityRenzhengStatusBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.RenZhengActivity;
import com.diankong.sexstory.mobile.modle.activity.RenZhengStatus2Activity;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2020/1/14.
 * 描述：
 * =============================================
 */

public class RenzhengStatusViewModle extends BaseViewModle<ActivityRenzhengStatusBinding> {


    private int type;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
        type = act.getIntent().getIntExtra("TYPE", 0);

        if (type == 0) {
            //审核中
            b.ivStatus.setImageResource(R.mipmap.shz);
            b.tvStatus.setText("资料正在审核中");
            b.tvStatusText.setText("管理员正在审核中，请耐心等待");
            b.tvType1Text.setVisibility(View.GONE);
            b.tvType1Text2.setVisibility(View.GONE);
            b.etTitle.setVisibility(View.GONE);
        } else if (type == 1) {
            //审核成功
            b.ivStatus.setImageResource(R.mipmap.shtg);
            b.tvStatus.setText("资料审核通过");
            b.tvStatusText.setText("恭喜您，审核认证通过");
            b.btCommit.setText("完成设置");
            b.tvType1Text.setVisibility(View.VISIBLE);
            b.tvType1Text2.setVisibility(View.VISIBLE);
            b.etTitle.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            //审核失败
            b.ivStatus.setImageResource(R.mipmap.wtg);
            b.tvStatus.setText("资料审核失败");
            b.tvStatusText.setText("抱歉,您的资料信息存在不实，请重新提交");
            b.tvType1Text.setVisibility(View.GONE);
            b.etTitle.setVisibility(View.GONE);
            b.tvType1Text2.setVisibility(View.GONE);
        }

        b.btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (type == 0) {
                    //审核中
                    act.finish();
                } else if (type == 1) {
                    //审核成功
                    updataUserInfo();
                } else if (type == 2) {
                    //审核失败
                    startActivity(RenZhengActivity.class);
                }
            }
        });

    }

    private void updataUserInfo() {
        if (TextUtils.isEmpty(b.etTitle.getText().toString())) {
            ToastUtils.showShort("请输入您要设置的金币数");
            return;
        }

        EasyHttp.get(Api.apiurl + "user/updateUserInfo")
                .params("chatCoin", b.etTitle.getText().toString())
                .params("id", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<OSSPojo>, OSSPojo>(new SimpleCallBack<OSSPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final OSSPojo _ossPojo) {
                        ToastUtils.showShort("设置成功");
                        act.finish();
                        startActivity(RenZhengStatus2Activity.class);
                    }

                }) {
                });
    }

    @Override
    public void initNet() {

    }
}
