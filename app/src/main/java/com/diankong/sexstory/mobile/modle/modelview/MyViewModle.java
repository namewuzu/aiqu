package com.diankong.sexstory.mobile.modle.modelview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.OSSPojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.FragmentMyBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.CoinActivity;
import com.diankong.sexstory.mobile.modle.activity.CommentsListActivity;
import com.diankong.sexstory.mobile.modle.activity.FocusActivity;
import com.diankong.sexstory.mobile.modle.activity.GiftListActivity;
import com.diankong.sexstory.mobile.modle.activity.GoodOrderListActivity;
import com.diankong.sexstory.mobile.modle.activity.InputInfoActivity;
import com.diankong.sexstory.mobile.modle.activity.MoneyActivity;
import com.diankong.sexstory.mobile.modle.activity.MyLikeActivity;
import com.diankong.sexstory.mobile.modle.activity.SettingActivity;
import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;
import com.diankong.sexstory.mobile.modle.activity.UserDetailListActivity;
import com.diankong.sexstory.mobile.modle.activity.withDrawalActivity;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/21.
 * 描述：
 * =============================================
 */

public class MyViewModle extends BaseViewModle<FragmentMyBinding> {

    public SingleTypeBindingAdapter _adapter;

    @Override
    public void initUI() {
        requestUserInfo();

        b.ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(SettingActivity.class);
            }
        });


        b.rlDingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(GoodOrderListActivity.class);
            }
        });

//        b.tvAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View _view) {
//                startActivity(AddressActivity.class);
//
//            }
//        });

        b.rlQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                DialogUtils.showDialogPrompt(act, SpUtlis.getlinkString());
            }
        });

//        b.rlChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View _view) {
//                CommonInterface.GoChatList(act);
//            }
//        });

        b.chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    startActivity(MoneyActivity.class);
                }
            }
        });

        b.llTiezi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", SpUtlis.getId());
                    bundle.putInt("TYPE", 3);
                    startActivity(UserDetailListActivity.class, bundle);
                }
            }
        });

        b.llFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", 1);
                    bundle.putInt("INDEX", 1);
                    startActivity(FocusActivity.class, bundle);
                }


            }
        });

        b.llFans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", 3);
                    bundle.putInt("INDEX", 2);
                    startActivity(FocusActivity.class, bundle);
                }
            }
        });

        b.rlTixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", 2);
                    startActivity(GiftListActivity.class, bundle);
                }
            }
        });

        b.inputInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    getOSSToken();
                    startActivity(InputInfoActivity.class);
                }
            }
        });

        b.llLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", 1);
                    startActivity(MyLikeActivity.class, bundle);
                }
            }
        });

        b.llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", 2);
                    startActivity(MyLikeActivity.class, bundle);
                }
            }
        });

        b.llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    startActivity(CommentsListActivity.class);
                }
            }
        });

        b.tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    Intent intent = new Intent(getContext(), UserDetailActivity.class);
                    intent.putExtra("USERID", SpUtlis.getId());
                    act.startActivity(intent);
                }
            }
        });


        b.ivMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (CommonInterface.isAuth(act)) {
                    CommonInterface.GoChatList(act);
                }
            }
        });

//        b.llShopCar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View _view) {
//                startActivity(ShopCarActivity.class);
//            }
//        });
//
//        b.llShopPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View _view) {
//                startActivity(GoodOrderListActivity.class);
//            }
//        });
//
//        b.llShopHuo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View _view) {
//                startActivity(GoodOrderListActivity.class);
//            }
//        });

    }

    private void getOSSToken() {
        EasyHttp.get(Api.apiurl2 + "community/getOssTokenInfo")
                .execute(new CallBackProxy<BaseResult<OSSPojo>, OSSPojo>(new SimpleCallBack<OSSPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final OSSPojo _ossPojo) {
                        SpUtlis.setAccessKey(_ossPojo.accessKey);
                        SpUtlis.setSecret(_ossPojo.secret);
                        SpUtlis.setOssToken(_ossPojo.token);
                    }

                }) {
                });
    }

    private void requestUserInfo() {

        EasyHttp.post(Api.apiurl + "user/getUserInfor")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo _userInfoPojo) {

                        b.tvId.setText("爱趣号：" + String.valueOf(SpUtlis.getId()));
                        if (!TextUtils.isEmpty(_userInfoPojo.nickName)) {
                            b.tvName.setText(_userInfoPojo.nickName + "   ID:" + SpUtlis.getId());
                        } else {
                            b.tvName.setText("游客");
                            b.tvId.setVisibility(View.GONE);
                            b.tvCopy.setVisibility(View.GONE);
                        }
                        GlideImageLoader.displayRound(App.getInstance(), b.ivAvatar, _userInfoPojo.avatar);

                        b.tvMoney.setText(_userInfoPojo.coinPayCount + "");

                        b.rlChongzhi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                if (CommonInterface.isAuth(act)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("MONEY", _userInfoPojo.coinPayCount);
                                    startActivity(CoinActivity.class, bundle);
                                }
                            }
                        });

                        b.tixian.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
//                                if(TextUtils.isEmpty(_userInfoPojo.wxOpenid)){
//                                    DialogUtils.showDialogPrompt(act, "提示", "您还未绑定手机号，请先绑定手机号", "去绑定", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface _dialogInterface, int _i) {
//                                            startActivity(BindingPhoneActivity.class);
//                                        }
//                                    });
//                                }else{
//
//                                }
                                if (CommonInterface.isAuth(act)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("WXOPENID", _userInfoPojo.wxOpenid);
                                    bundle.putString("PHONE", _userInfoPojo.phone);
                                    startActivity(withDrawalActivity.class, bundle);
                                }
                            }
                        });

//                        b.tvRenzheng.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View _view) {
//                                if (_userInfoPojo.isAuth == 0) {
//                                    //审核中
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("TYPE", 0);
//                                    startActivity(RenZhengStatusActivity.class, bundle);
//                                } else if (_userInfoPojo.isAuth == 1) {
//                                    //通过
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("TYPE", 1);
//                                    startActivity(RenZhengStatusActivity.class, bundle);
//                                } else if (_userInfoPojo.isAuth == 2) {
//                                    //未通过
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("TYPE", 2);
//                                    startActivity(RenZhengStatusActivity.class, bundle);
//
//                                } else if (_userInfoPojo.isAuth == 3) {
//                                    //未提交
//                                    startActivity(RenZhengActivity.class);
//                                } else if (_userInfoPojo.isAuth == 4) {
//                                    //已设置金币
//                                    startActivity(RenZhengStatus2Activity.class);
//                                }
//
//                            }
//                        });
                    }

                }) {
                });


    }


    public void onHiddenChanged(boolean hidden) {

        if (!hidden) {
            requestUserInfo();
        }

    }


    @Override
    public void initNet() {

    }

}
