package com.diankong.sexstory.mobile.modle.modelview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.GiftPojo;
import com.diankong.sexstory.mobile.databinding.ActivityGiftListBinding;
import com.diankong.sexstory.mobile.databinding.ItemGiftBinding;
import com.diankong.sexstory.mobile.event.FocusEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.FocusActivity;
import com.diankong.sexstory.mobile.modle.activity.MoneyActivity;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/16.
 * 描述：
 * =============================================
 */

public class GiftListViewModle extends BaseViewModle<ActivityGiftListBinding> {

    private SingleTypeBindingAdapter _adapter;
    private Dialog dialog;
    private int userId;
    private int type;

    @Override
    public void initUI() {
        EventBus.getDefault().register(this);
        ToolbarUtils.initToolBar(b.toolbar, act);
        userId = act.getIntent().getIntExtra("USERID", 0);
        type = act.getIntent().getIntExtra("TYPE", 0);

        if (type == 1) {
            b.rlFocus.setVisibility(View.GONE);
        } else {
            b.rlFocus.setVisibility(View.VISIBLE);

            b.rlFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE",2);
                    bundle.putInt("INDEX",1);
                    startActivity(FocusActivity.class,bundle);
                }
            });
        }

        getListData();
    }

    @Override
    public void onDestory() {
        super.onDestory();
        EventBus.getDefault().unregister(this);
    }

    private void getListData() {

        EasyHttp.get(Api.apiurl + "gift/getAllGiftInfo")
                .execute(new CallBackProxy<BaseResult<List<GiftPojo>>, List<GiftPojo>>(new SimpleCallBack<List<GiftPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<GiftPojo> giftPojos) {

                        _adapter = new SingleTypeBindingAdapter(act, giftPojos, R.layout.item_gift);
                        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<GiftPojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<GiftPojo> mData) {
                                ItemGiftBinding itemGiftUserBinding = (ItemGiftBinding) holder.getBinding();
                                itemGiftUserBinding.tvName.setText(mData.get(position).giftName);
                                itemGiftUserBinding.tvPrice.setText(mData.get(position).giftPrice + "金币");
                                GlideImageLoader.onDisplayImage(act, itemGiftUserBinding.ivIcon, mData.get(position).giftUrl);

                                itemGiftUserBinding.ll.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        dialog = DialogUtils.showAlertMid(R.layout.dialog_gift, act);

                                        TextView commit = dialog.findViewById(R.id.tv_commit);
                                        TextView tv_title = dialog.findViewById(R.id.tv_name);
                                        TextView tv_price = dialog.findViewById(R.id.tv_price);
                                        ImageView iv_icon = dialog.findViewById(R.id.iv_icon);
                                        GlideImageLoader.onDisplayImage(act, iv_icon, mData.get(position).giftUrl);
                                        tv_price.setText(mData.get(position).giftPrice + "金币");
                                        tv_title.setText(mData.get(position).giftName);
                                        commit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View _view) {
                                                dialog.dismiss();
                                                if(type==1){
                                                    sendGift(mData.get(position).giftPrice, mData.get(position).id, mData.get(position).giftName);
                                                }else{
                                                    if(!TextUtils.isEmpty(b.tvUsername.getText().toString())){
                                                        sendGift(mData.get(position).giftPrice, mData.get(position).id, mData.get(position).giftName);
                                                    }else{
                                                        ToastUtils.showShort("请选择你想赠送的人");
                                                    }
                                                }
                                            }
                                        });
                                    }
                                });

                            }
                        });

                        b.recyclerView.setAdapter(_adapter);
                        b.recyclerView.setLayoutManager(new GridLayoutManager(act, 4));
                    }

                }) {
                });


    }

    private void sendGift(int coinCount, int giftId, String giftName) {
        EasyHttp.get(Api.apiurl + "gift/toGiftUser")
                .params("coinCount", String.valueOf(coinCount))
                .params("userId", String.valueOf(userId))
                .params("fromUserId", String.valueOf(SpUtlis.getId()))
                .params("giftId", String.valueOf(giftId))
                .params("giftName", giftName)
                .execute(new CallBackProxy<BaseResult<GiftPojo>, GiftPojo>(new SimpleCallBack<GiftPojo>() {
                    @Override
                    public void onError(ApiException e) {
//                        ToastUtils.showShort(e.getMessage());
                        if(e.getCode()==300){

                            final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_chongzhi, act);
                            TextView  textView = dialog.findViewById(R.id.tv_cancel);
                            ImageView imageView = dialog.findViewById(R.id.iv_can);

                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View _view) {
                                    startActivity(MoneyActivity.class);
                                }
                            });

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View _view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }

                    @Override
                    public void onSuccess(final GiftPojo giftPojos) {
                        ToastUtils.showShort("赠送成功");
                    }

                }) {
                });
    }

    @Override
    public void initNet() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshEvent(FocusEvent event) {
         userId = event.concertUserId;
         b.tvUsername.setText(event.nickName);
    }
}
