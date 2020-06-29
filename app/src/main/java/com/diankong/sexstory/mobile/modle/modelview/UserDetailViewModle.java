package com.diankong.sexstory.mobile.modle.modelview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.GiftPojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.databinding.ActivityUserDetailBinding;
import com.diankong.sexstory.mobile.databinding.ItemGiftUserBinding;
import com.diankong.sexstory.mobile.databinding.ItemUserBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.BigImagePagerActivity;
import com.diankong.sexstory.mobile.modle.activity.GiftListActivity;
import com.diankong.sexstory.mobile.modle.activity.InputInfoActivity;
import com.diankong.sexstory.mobile.modle.activity.UserDetailListActivity;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/11.
 * 描述：
 * =============================================
 */

public class UserDetailViewModle extends BaseViewModle<ActivityUserDetailBinding> {

    private SingleTypeBindingAdapter _adapter;
    private SingleTypeBindingAdapter _adapter2;
    private SingleTypeBindingAdapter re1Adapter;
    private SingleTypeBindingAdapter re2Adapter;
    private SingleTypeBindingAdapter re3Adapter;
    private SingleTypeBindingAdapter photoAdapter;
    private int userId;
    private String[] split;
    private String[] split1;
    private String[] split2;
    private String[] split3;
    private List<String> stringList;
    private List<String> stringList1;
    private List<String> stringList2;
    private List<String> stringList3;
    private long initTime;
    private long initTime2;

    @Override
    public void initUI() {
        userId = act.getIntent().getIntExtra("USERID", 0);
//        RongContext.getInstance().getEventBus().register(this);
//        EventBus.getDefault().register(this);
        b.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
//                ToastUtils.showShort("eee");
                act.finish();
            }
        });
        getUserInfos();
        getGiftInfo();

        if (userId != SpUtlis.getId()) {
            b.tvInfo.setVisibility(View.GONE);
            b.tvFocus.setVisibility(View.VISIBLE);
            b.send.setVisibility(View.VISIBLE);
            b.sendGift.setVisibility(View.VISIBLE);
//            b.goVodie.setVisibility(View.VISIBLE);
        } else {
            b.tvInfo.setVisibility(View.VISIBLE);
            b.tvFocus.setVisibility(View.GONE);
            b.send.setVisibility(View.GONE);
            b.sendGift.setVisibility(View.GONE);
//            b.goVodie.setVisibility(View.GONE);
        }

        b.tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(act, InputInfoActivity.class);
                act.startActivity(intent);
            }
        });



    }




    @Override
    public void onStop() {

    }

    @Override
    public void onDestory() {
        super.onDestory();
//        RongContext.getInstance().getEventBus().unregister(this);
//        EventBus.getDefault().unregister(this);
        initTime2 = System.currentTimeMillis();
    }

    @Override
    public void onResume() {
        super.onResume();
        getGiftInfo();
        getUserInfos();
    }

    private void getGiftInfo() {

        EasyHttp.get(Api.apiurl + "gift/userGifts")
                .params("userIdSelf", String.valueOf(userId))
                .execute(new CallBackProxy<BaseResult<List<GiftPojo>>, List<GiftPojo>>(new SimpleCallBack<List<GiftPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<GiftPojo> giftPojos) {


                        _adapter2 = new SingleTypeBindingAdapter(act, giftPojos, R.layout.item_gift_user);
                        _adapter2.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<GiftPojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<GiftPojo> mData) {
                                ItemGiftUserBinding itemGiftUserBinding = (ItemGiftUserBinding) holder.getBinding();
                                itemGiftUserBinding.tvName.setText(mData.get(position).giftName);
                                itemGiftUserBinding.tvNum.setText("x" + mData.get(position).giftCount);
                                GlideImageLoader.onDisplayImage(act, itemGiftUserBinding.ivIcon, mData.get(position).giftUrl);

                            }
                        });


                        b.recyclerView2.setAdapter(_adapter2);
                        b.recyclerView2.setLayoutManager(new GridLayoutManager(act, 4));
                    }

                }) {
                });


    }

    private void getUserInfos() {
        EasyHttp.get(Api.apiurl2 + "community/getUserInfoAndCommunitys")
                .params("userId", String.valueOf(userId))
                .params("userIdSelf", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {


                        if(!isDestroy((Activity)act)){
                            GlideImageLoader.displayRound(act, b.ivAvatar, mTestPojo.avatar);
                        }
                        b.tvName.setText(mTestPojo.nickName);
//                        b.tvAge.setText(mTestPojo.age + "岁");
//                        b.tvAddress.setText(mTestPojo.wxCity);
                        b.tvPrice.setText(mTestPojo.fansCount + "粉丝");
//                        b.tvFuns.setText("视频价格：" + mTestPojo.chatCoin + "金币/分钟");
//                        b.tvId.setText("普通聊天价格：" + mTestPojo.charterCoin + "金币/次");
                        if (mTestPojo.recordList != null && mTestPojo.recordList.size() > 0) {


                            _adapter = new SingleTypeBindingAdapter(act, mTestPojo.recordList, R.layout.item_user);
                            _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<UserInfoPojo>() {
                                @Override
                                public void decorator(BindingViewHolder holder, final int position, int viewType, final List<UserInfoPojo> mData) {
                                    ItemUserBinding itemUserBinding = (ItemUserBinding) holder.getBinding();

                                    if(!TextUtils.isEmpty(mData.get(position).imageUrl)){
                                        if (mData.get(position).imageUrl.indexOf(",") != -1) {
                                            String str = mData.get(position).imageUrl;
                                            String substring = str.substring(0, mData.get(position).imageUrl.indexOf(","));
                                            GlideImageLoader.onDisplayImage(act, itemUserBinding.ivIcon, substring);
                                        } else {
                                            GlideImageLoader.onDisplayImage(act, itemUserBinding.ivIcon, mData.get(position).imageUrl);
                                        }
                                    }


                                    itemUserBinding.ivIcon.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View _view) {
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("ID", mTestPojo.id);
                                            bundle.putInt("TYPE", 1);
                                            startActivity(UserDetailListActivity.class, bundle);
                                        }
                                    });
                                }
                            });

                            b.recyclerView3.setAdapter(_adapter);
                            b.recyclerView3.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.HORIZONTAL, false));
                        } else {
                            b.llHot.setVisibility(View.GONE);
                        }
                        b.send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                if (userId != 0) {
                                    if (!TextUtils.isEmpty(SpUtlis.getOpenId())) {

                                        if(SpUtlis.getIdNo()!=1){
//                                            Intent intent = new Intent(act, ShiMingActivity.class);
//                                            act.startActivity(intent);
                                            CommonInterface.isPayAuth(act);
                                        }else {
                                            CommonInterface.GoChat(act, String.valueOf(userId), mTestPojo.nickName, mTestPojo.avatar);
                                        }
//                                        if(mTestPojo.chatTime > 0){
//                                            CommonInterface.GoChat(act, String.valueOf(userId), mTestPojo.nickName, mTestPojo.avatar);
//                                        }else {
//                                            //去充值
//
//
//                                            final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_chongzhi, act);
//                                            TextView  textView = dialog.findViewById(R.id.tv_cancel);
//                                            ImageView imageView = dialog.findViewById(R.id.iv_can);
//
//                                            imageView.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View _view) {
//                                                    startActivity(MoneyActivity.class);
//                                                }
//                                            });
//
//                                            textView.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View _view) {
//                                                    dialog.dismiss();
//                                                }
//                                            });

//                                        }
                                    } else {
//                                        ToastUtils.showShort("请先授权");
                                        final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, act);
                                        TextView  textView = dialog.findViewById(R.id.tv_cancel);
                                        ImageView imageView = dialog.findViewById(R.id.iv_can);

                                        textView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View _view) {
                                                dialog.dismiss();
                                            }
                                        });

                                        imageView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View _view) {
                                                UMengTools.getPlatformInfo(act, SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                                                    @Override
                                                    public void getUserInfo(WxPojo UserInfo) {
                                                        CommonInterface.wxLogin(act,SpUtlis.getId(),UserInfo.accessToken,UserInfo.openid);
                                                        dialog.dismiss();
                                                    }

                                                    @Override
                                                    public void onCancel() {

                                                    }

                                                    @Override
                                                    public void onError() {

                                                    }
                                                });
                                            }
                                        });
                                    }
                                } else {
                                    ToastUtils.showShort("错误");
                                }
                            }
                        });

//                        b.goVodie.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View _view) {
//                                if (userId != 0) {
//                                    if (!TextUtils.isEmpty(SpUtlis.getOpenId())) {
//                                        if (mTestPojo.mine > 0) {
//                                            if (mTestPojo.isConcert == 2) {
//                                                ToastUtils.showShort("请先关注对方");
//                                            } else {
//                                                if (mTestPojo.isAuth == 1) {
//                                                    CommonInterface.GoChatVedio(act, String.valueOf(userId), mTestPojo.nickName, mTestPojo.avatar, mTestPojo.mine);
//                                                } else {
//                                                    ToastUtils.showShort("对方还没有认证聊主身份");
//                                                }
//                                            }
//                                        } else {
//                                            //去充值
//
//                                            final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_chongzhi, act);
//                                            TextView  textView = dialog.findViewById(R.id.tv_cancel);
//                                            ImageView imageView = dialog.findViewById(R.id.iv_can);
//
//                                            imageView.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View _view) {
//                                                    startActivity(MoneyActivity.class);
//                                                }
//                                            });
//
//                                            textView.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View _view) {
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                        }
//
//                                    } else {
////                                        ToastUtils.showShort("请先授权");
//                                        final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, act);
//                                        TextView  textView = dialog.findViewById(R.id.tv_cancel);
//                                        ImageView imageView = dialog.findViewById(R.id.iv_can);
//
//                                        textView.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View _view) {
//                                                dialog.dismiss();
//                                            }
//                                        });
//
//                                        imageView.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View _view) {
//                                                UMengTools.getPlatformInfo(act, SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
//                                                    @Override
//                                                    public void getUserInfo(WxPojo UserInfo) {
//                                                        CommonInterface.wxLogin(SpUtlis.getId(),UserInfo.accessToken,UserInfo.openid);
//                                                        dialog.dismiss();
//                                                    }
//
//                                                    @Override
//                                                    public void onCancel() {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onError() {
//
//                                                    }
//                                                });
//                                            }
//                                        });
//
//                                    }
//                                } else {
//                                    ToastUtils.showShort("错误");
//                                }
//
//
//                            }
//                        });

                        b.sendGift.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {

//                                if(mTestPojo.isAuth==1){
                                if (CommonInterface.isAuth(act)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("USERID", mTestPojo.id);
                                    bundle.putInt("TYPE", 1);
                                    startActivity(GiftListActivity.class, bundle);
                                }

//                                }else{
//                                    ToastUtils.showShort("对方还没有认证聊主身份");
//                                }


                            }
                        });

                        if (mTestPojo.isConcert == 1) {
                            b.tvFocus.setText("取消关注");
                        } else {
                            b.tvFocus.setText("关注");
                        }

                        b.tvFocus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {

                                if (CommonInterface.isAuth(act)) {
                                    if (b.tvFocus.getText().toString().equals("关注")) {
                                        flo(mTestPojo.id);
                                    } else {
                                        flo2(mTestPojo.id);
                                    }
                                }
                            }
                        });

                        if (!TextUtils.isEmpty(mTestPojo.sign)) {
                            b.tvGexing1.setVisibility(View.VISIBLE);
                            b.tvGexing2.setVisibility(View.GONE);
                            b.tvGexing1.setText(mTestPojo.sign);
                        } else {
                            b.tvGexing1.setVisibility(View.GONE);
                            b.tvGexing2.setVisibility(View.VISIBLE);
                        }


                        b.tvAge.setText("年龄:" + mTestPojo.age);
                        if (!TextUtils.isEmpty(mTestPojo.monthlyIncome)) {
                            b.tvMonthlyIncome.setText("月收入:" + mTestPojo.monthlyIncome);
                        } else {
                            b.tvMonthlyIncome.setVisibility(View.GONE);
                        }

                        if (!TextUtils.isEmpty(mTestPojo.position)) {
                            b.tvJob.setText("工作:" + mTestPojo.position);
                        } else {
                            b.tvJob.setVisibility(View.GONE);
                        }
                        if (mTestPojo.sex==1) {
                            b.tvSex.setText("性别:男");
                        } else {
                            b.tvSex.setText("性别:女");
                        }


                        if(TextUtils.isEmpty(mTestPojo.wxCity)){
                            b.llWxCity.setVisibility(View.GONE);
                        }else{
                            b.llWxCity.setVisibility(View.VISIBLE);
                            b.tvWxCity.setText(mTestPojo.wxCity);
                        }

                        if(TextUtils.isEmpty(mTestPojo.hobby)){
                            b.llHobby.setVisibility(View.GONE);
                        }else{
                            b.llHobby.setVisibility(View.VISIBLE);
                            b.tvHobby.setText(mTestPojo.hobby);
                        }


//                        b.tvCity.setText(mTestPojo.city);

                        if (!TextUtils.isEmpty(mTestPojo.talk)) {
                            //聊天能力
//                            b.re1.setVisibility(View.VISIBLE);
//                            b.tvNoTalk.setVisibility(View.GONE);


                            split1 = mTestPojo.talk.split(",");

                            stringList1 = new ArrayList<>();
                            for (int j = 0; j < split1.length; j++) {
                                stringList1.add(split1[j]);
                            }

//                            re1Adapter = new SingleTypeBindingAdapter(act, stringList1, R.layout.item_user_usually);
//                            re1Adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<String>() {
//                                @Override
//                                public void decorator(BindingViewHolder holder, final int position, int viewType, final List<String> mData) {
//                                    ItemUserUsuallyBinding itemUserBinding = (ItemUserUsuallyBinding) holder.getBinding();
//                                    itemUserBinding.tvJob.setText(mData.get(position));
//
//                                }
//                            });
//
//                            b.re1.setAdapter(re1Adapter);
//                            b.re1.setLayoutManager(new GridLayoutManager(act, stringList1.size()));
                        } else {
//                            b.re1.setVisibility(View.GONE);
//                            b.tvNoTalk.setVisibility(View.VISIBLE);
                        }

                        if (!TextUtils.isEmpty(mTestPojo.characters)) {
                            //性格特点
//                            b.re2.setVisibility(View.VISIBLE);
//                            b.tvNoCharacter.setVisibility(View.GONE);


                            split2 = mTestPojo.characters.split(",");

                            stringList2 = new ArrayList<>();
                            for (int j = 0; j < split2.length; j++) {
                                stringList2.add(split2[j]);
                            }

//                            re2Adapter = new SingleTypeBindingAdapter(act, stringList2, R.layout.item_user_usually);
//                            re2Adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<String>() {
//                                @Override
//                                public void decorator(BindingViewHolder holder, final int position, int viewType, final List<String> mData) {
//                                    ItemUserUsuallyBinding itemUserBinding = (ItemUserUsuallyBinding) holder.getBinding();
//
//                                    itemUserBinding.tvJob.setText(mData.get(position));
//                                }
//                            });

//                            b.re2.setAdapter(re2Adapter);
//                            b.re2.setLayoutManager(new GridLayoutManager(act, stringList2.size()));
                        } else {
//                            b.re2.setVisibility(View.GONE);
//                            b.tvNoCharacter.setVisibility(View.VISIBLE);
                        }

                        if (!TextUtils.isEmpty(mTestPojo.usualPosition)) {
                            //常去场所
//                            b.re3.setVisibility(View.VISIBLE);
//                            b.tvNoPlace.setVisibility(View.GONE);


                            split3 = mTestPojo.usualPosition.split(",");

                            stringList3 = new ArrayList<>();
                            for (int j = 0; j < split3.length; j++) {
                                stringList3.add(split3[j]);
                            }

//                            re3Adapter = new SingleTypeBindingAdapter(act, stringList3, R.layout.item_user_usually);
//                            re3Adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<String>() {
//                                @Override
//                                public void decorator(BindingViewHolder holder, final int position, int viewType, final List<String> mData) {
//                                    ItemUserUsuallyBinding itemUserBinding = (ItemUserUsuallyBinding) holder.getBinding();
//
//                                    itemUserBinding.tvJob.setText(mData.get(position));
//                                }
//                            });

//                            b.re3.setAdapter(re3Adapter);
//                            b.re3.setLayoutManager(new GridLayoutManager(act, stringList3.size()));


                        } else {
//                            b.re3.setVisibility(View.GONE);
//                            b.tvNoPlace.setVisibility(View.VISIBLE);

                        }


                        if (!TextUtils.isEmpty(mTestPojo.photo)) {
                            //相册
                            split = mTestPojo.photo.split(",");

                            stringList = new ArrayList<>();
                            for (int j = 0; j < split.length; j++) {
                                stringList.add(split[j]);
                            }


                            photoAdapter = new SingleTypeBindingAdapter(act, stringList, R.layout.item_user);
                            photoAdapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<String>() {
                                @Override
                                public void decorator(BindingViewHolder holder, final int position, int viewType, final List<String> mData) {
                                    ItemUserBinding itemUserBinding = (ItemUserBinding) holder.getBinding();

                                    GlideImageLoader.onDisplayImage(act, itemUserBinding.ivIcon, mData.get(position));

                                    itemUserBinding.ivIcon.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View _view) {
                                            BigImagePagerActivity.startImagePagerActivity(act, stringList, position, new BigImagePagerActivity.ImageSize(640, 640));
                                        }
                                    });
                                }
                            });

                            b.recyclerView.setAdapter(photoAdapter);
                            b.recyclerView.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.HORIZONTAL, false));

                        } else {
                            b.llPhoto.setVisibility(View.GONE);
                        }

                    }

                }) {
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
                        b.tvFocus.setText("取消关注");
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
                        b.tvFocus.setText("关注");
                    }

                }) {
                });


    }

    @Override
    public void initNet() {

    }

    public boolean isDestroy(Activity mActivity) {
        if (mActivity== null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void PayCoinEvent(PayCoinEvent event) {
//
//
//
//    }
}
