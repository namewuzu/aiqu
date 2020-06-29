package com.diankong.sexstory.mobile.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CircleResultPojo;
import com.diankong.sexstory.mobile.bean.PayPojo;
import com.diankong.sexstory.mobile.bean.ShareContent;
import com.diankong.sexstory.mobile.bean.SharePojo;
import com.diankong.sexstory.mobile.bean.TestPojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.bean.lisener.MyConversationClickListener;
import com.diankong.sexstory.mobile.databinding.ItemSayhello2Binding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.MainActivity;
import com.diankong.sexstory.mobile.modle.activity.ShiMingActivity;
import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;
import com.diankong.sexstory.mobile.widget.WBViewActivity;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/28.
 * 描述：
 * =============================================
 */

public class CommonInterface {

    private static ShowCallBack mShowCallBack;
    public static boolean isGoChat = true;
    public static RecyclerView recyclerView;

    public static SingleTypeBindingAdapter _adapter;

    public static void ShareDialog(final AppCompatActivity act, final int bookId) {
        EasyHttp.post(Api.apiurl + "user/getShareInfo")
                .execute(new CallBackProxy<BaseResult<SharePojo>, SharePojo>(new SimpleCallBack<SharePojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final SharePojo mTestPojo) {
//                        if (_dialog == null) {
                        final Dialog _dialog = DialogUtils.showAlert2Mid(R.layout.dialog_share, act);
//                        }
                        ImageView wxhy = (ImageView) _dialog.findViewById(R.id.wx_hy);
                        ImageView wx_pyq = (ImageView) _dialog.findViewById(R.id.wx_pyq);
                        TextView share_title = (TextView) _dialog.findViewById(R.id.share_title);
                        share_title.setText("解锁阅读vip章节需要更多书币,您可以根据以下方式获取书币!\n" +
                                "\n" +
                                "分享到朋友圈或者微信好友,去分享>>");
                        wxhy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                ShareContent shareContent = new ShareContent();
                                shareContent.title = mTestPojo.linkName;
                                shareContent.desc = mTestPojo.intro;
                                shareContent.shareUrl = mTestPojo.linkUrl;
                                shareContent.thumbUrl = mTestPojo.imgUrl;
//                                        shareContent.thumbRes = R.mipmap.app_logo;
                                UMengTools.setShareAction(act, SHARE_MEDIA.WEIXIN, shareContent, new UMShareListener() {
                                    @Override
                                    public void onStart(SHARE_MEDIA _share_media) {

                                    }

                                    @Override
                                    public void onResult(SHARE_MEDIA _share_media) {
                                        L.d("platform" + _share_media);
                                        shareShow(bookId, 1, mTestPojo.id, mTestPojo.linkUrl);
                                    }

                                    @Override
                                    public void onError(SHARE_MEDIA _share_media, Throwable _throwable) {
                                        ToastUtils.showShort("分享失败");
                                        if (_throwable != null) {
                                            L.d("throw:" + _throwable.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onCancel(SHARE_MEDIA _share_media) {
                                        ToastUtils.showShort("分享取消了");
                                    }
                                });
                                _dialog.dismiss();
                            }
                        });
                        wx_pyq.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                ShareContent shareContent = new ShareContent();
                                shareContent.title = mTestPojo.linkName;
                                shareContent.desc = mTestPojo.remark;
                                shareContent.shareUrl = mTestPojo.linkUrl;
                                shareContent.thumbUrl = mTestPojo.imgUrl;
//                                        shareContent.thumbRes = R.mipmap.app_logo;
                                UMengTools.setShareAction(act, SHARE_MEDIA.WEIXIN_CIRCLE, shareContent, new UMShareListener() {
                                    @Override
                                    public void onStart(SHARE_MEDIA _share_media) {

                                    }

                                    @Override
                                    public void onResult(SHARE_MEDIA _share_media) {
                                        L.d("platform" + _share_media);
                                        shareShow(bookId, 2, mTestPojo.id, mTestPojo.linkUrl);
                                    }

                                    @Override
                                    public void onError(SHARE_MEDIA _share_media, Throwable _throwable) {
                                        ToastUtils.showShort("分享失败");
                                        if (_throwable != null) {
                                            L.d("throw:" + _throwable.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onCancel(SHARE_MEDIA _share_media) {
                                        ToastUtils.showShort("分享取消了");
                                    }
                                });
                                _dialog.dismiss();
                            }
                        });

                        _dialog.show();

                    }

                }) {
                });
    }

    // type = 1 进入读书界面，type = 2 进入目录界面
    //"http://47.244.48.177/webPage/h5/reading-h5.html?userId=1&bookId=1&chapterorder=2&thisChapId=13391"
    public static void getHtml(final AppCompatActivity act, final int type, final int userId, final int bookId, final int chapterorder, final int thisChapId, final int isUpload) {
        EasyHttp.post(Api.apiurl + "common/getH5PageUrl")
                .execute(new CallBackProxy<BaseResult<TestPojo>, TestPojo>(new SimpleCallBack<TestPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        if (e.getCode() == 800) {
                            IntentUtils.toSignActivity();
                        }
                    }

                    @Override
                    public void onSuccess(final TestPojo mTestPojo) {
                        if (type == 1) {
                            WBViewActivity.startActivity(act, mTestPojo.pageUrl + "?userId=" + userId + "&" + "bookId=" + bookId + "&" + "chapterorder=" + chapterorder + "&" + "thisChapId=" + 1 + "&" + "isUpload=" + isUpload, bookId);

                            L.d("书内容url + " + mTestPojo.pageUrl + "?userId=" + userId + "&" + "bookId=" + bookId + "&" + "chapterorder=" + chapterorder + "&" + "thisChapId=" + 1 + "&" + "isUpload=" + isUpload);
                        } else if (type == 2) {
                            WBViewActivity.startActivity(act, mTestPojo.contentsUrl + "?userId=" + userId + "&" + "bookId=" + bookId + "&" + "isUpload=" + isUpload, bookId);
                            L.d("目录的url + " + mTestPojo.contentsUrl + "?userId=" + userId + "&" + "bookId=" + bookId + "&" + "isUpload=" + isUpload);
                        }
                    }

                }) {
                });

    }

    public static void shareShow(int bookId, int shareType, int linkId, String linkUrl) {
        EasyHttp.post(Api.apiurl + "coin/share")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("fictionId", String.valueOf(bookId))
                .params("shareType", String.valueOf(shareType))
                .params("linkId", String.valueOf(linkId))
                .params("linkUrl", linkUrl)
                .execute(new CallBackProxy<BaseResult<String>, String>(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final String mTestPojo) {

                        if (mShowCallBack != null) {
                            mShowCallBack.onShown();
                        }
                    }

                }) {
                });

    }

    public void setShowCallBack(ShowCallBack c) {
        mShowCallBack = c;
    }

    public interface ShowCallBack {
        void onShown();
    }


    public static void wxLogin(final Activity _activity, int userId, String accessToken, String openid) {
        EasyHttp.post(Api.apiurl + "user/wxLogin")
//                .params("userId", String.valueOf(userId))
                .params("accessToken", String.valueOf(accessToken))
                .params("openid", String.valueOf(openid))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo UserInfo) {
                        SpUtlis.setId(UserInfo.id);
                        SpUtlis.savaOpenId(UserInfo.openid);
                        SpUtlis.savaIdNo(UserInfo.flag);
                        SpUtlis.saveNickName(UserInfo.nickName);
                        SpUtlis.saveAvatar(UserInfo.avatar);
                        SpUtlis.saveAddress(UserInfo.wxCity);
                        CommonInterface.sayHello2(_activity,UserInfo.id,UserInfo.sex);
                        getIMToken(SpUtlis.getId(), UserInfo.nickName, UserInfo.avatar);

//                        Intent intent = new Intent(_activity, ShiMingActivity.class);
//                        _activity.startActivity(intent);
                    }

                }) {
                });

    }

    public static void wxLogin3(final Activity _activity, int userId, String accessToken, String openid, final Button _button) {
        EasyHttp.post(Api.apiurl + "user/wxLogin")
//                .params("userId", String.valueOf(userId))
                .params("accessToken", String.valueOf(accessToken))
                .params("openid", String.valueOf(openid))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo UserInfo) {
                        SpUtlis.setId(UserInfo.id);
                        SpUtlis.savaOpenId(UserInfo.openid);
                        SpUtlis.savaIdNo(UserInfo.flag);
                        SpUtlis.saveNickName(UserInfo.nickName);
                        SpUtlis.saveAvatar(UserInfo.avatar);
                        SpUtlis.saveAddress(UserInfo.wxCity);
                        getIMToken(SpUtlis.getId(), UserInfo.nickName, UserInfo.avatar);
                        CommonInterface.firstIn(_activity,UserInfo.openid,_button);
//                        Intent intent = new Intent(_activity, ShiMingActivity.class);
//                        _activity.startActivity(intent);
                    }

                }) {
                });

    }

    public static boolean isAuth(final Activity _appCompatActivity) {
        if (TextUtils.isEmpty(SpUtlis.getOpenId())) {
            final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, _appCompatActivity);
            TextView textView = dialog.findViewById(R.id.tv_cancel);
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
                    UMengTools.getPlatformInfo(_appCompatActivity, SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                        @Override
                        public void getUserInfo(WxPojo UserInfo) {
                            CommonInterface.wxLogin(_appCompatActivity, SpUtlis.getId(), UserInfo.accessToken, UserInfo.openid);
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

            return false;
        }
        return true;
    }

    public static void wxLogin(int userId, String accessToken, String openid) {
        EasyHttp.post(Api.apiurl + "user/wxLogin")
                .params("userId", String.valueOf(userId))
                .params("accessToken", String.valueOf(accessToken))
                .params("openid", String.valueOf(openid))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo UserInfo) {

                        SpUtlis.savaOpenId(UserInfo.openid);

                        SpUtlis.saveNickName(UserInfo.name);
                        SpUtlis.saveAvatar(UserInfo.avatar);
                        SpUtlis.saveAddress(UserInfo.wxCity);

                        getIMToken(SpUtlis.getId(), UserInfo.name, UserInfo.avatar);

//                        Intent intent = new Intent(_activity, ShiMingActivity.class);
//                        _activity.startActivity(intent);
                    }

                }) {
                });

    }

    public static void getIMToken(int id, String name, String portrait) {
        EasyHttp.post(Api.apiurl2 + "tcp/getToken")
                .params("id", String.valueOf(id))
                .params("name", String.valueOf(name))
                .params("portrait", String.valueOf(portrait))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final UserInfoPojo UserInfo) {
                        SpUtlis.saveIMToken(UserInfo.token);

                        RongIM.connect(SpUtlis.getIMToken(), new RongIMClient.ConnectCallback() {
                            @Override
                            public void onTokenIncorrect() {

                            }

                            @Override
                            public void onSuccess(String userid) {
                                L.d("--onSuccess" + userid);

                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                L.d("--onSuccess" + errorCode);
                            }
                        });
                    }

                }) {
                });

    }

    public static void GoChat(AppCompatActivity act, final String id, String title, String avatar) {

        RongIM.getInstance().setCurrentUserInfo(new UserInfo(id, title, Uri.parse(avatar)));
        RongIM.getInstance().setMessageAttachedUserInfo(true);

        RongIM.connect(SpUtlis.getIMToken(), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String userid) {
                L.d("--onSuccess" + userid);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                L.d("--onSuccess" + errorCode);
            }
        });

        //跳转聊天页面
        RongIM.getInstance().startPrivateChat(act, id, title);
        //添加头像监听
        RongIM.getInstance().setConversationClickListener(new MyConversationClickListener());





    }


    public static void GoChatVedio(AppCompatActivity act, String id, String title, String avatar, int min) {

        RongIM.getInstance().setCurrentUserInfo(new UserInfo(id, title, Uri.parse(avatar)));
        RongIM.getInstance().setMessageAttachedUserInfo(true);

        RongIM.connect(SpUtlis.getIMToken(), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String userid) {
                L.d("--onSuccess" + userid);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                L.d("--onSuccess" + errorCode);
            }
        });
        RongCallKit.startSingleCall(act, id, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO, min);

    }


    public static void GoChatList(AppCompatActivity act) {

        RongIM.connect(SpUtlis.getIMToken(), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String userid) {
                L.d("--onSuccess" + userid);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                L.d("--onSuccess" + errorCode);
            }
        });

        Map<String, Boolean> supportedConversation = new HashMap<>();
        supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false); // 会话列表需要显示私聊会话, 第二个参数 true 代表私聊会话需要聚合显示
        supportedConversation.put(Conversation.ConversationType.GROUP.getName(), false);  // 会话列表需要显示群组会话, 第二个参数 false 代表群组会话不需要聚合显示
        RongIM.getInstance().startConversationList(act, supportedConversation);
    }


    public static void wexin() {
        EasyHttp.post(Api.apiurl + "identifyOrder/payforIdentify")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<PayPojo>, PayPojo>(new SimpleCallBack<PayPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final PayPojo _payPojo) {

                        weChatPay(new PayPojo(_payPojo.partnerId, _payPojo.prepayId, _payPojo.sign, _payPojo.nonceStr, _payPojo.pack_age, _payPojo.appid, _payPojo.timestamp, _payPojo.extData));


                    }

                }) {
                });
    }

    public static void weChatPay(PayPojo _payPojo) {

        PayReq req = new PayReq();
        req.appId = _payPojo.appid;
        req.prepayId = _payPojo.prepayId;
        req.partnerId = _payPojo.partnerId;
        req.packageValue = _payPojo.pack_age;
        req.nonceStr = _payPojo.nonceStr;
        req.timeStamp = _payPojo.timestamp;
        req.sign = _payPojo.sign;
        req.extData = "2";

        App.getmWxApi().sendReq(req);

    }

    public static void isPayAuth(final Activity _activity){
        EasyHttp.post(Api.apiurl + "identifyOrder/getIdentifyStatus")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo _userInfoPojo) {

                        if (_userInfoPojo.isOrder == 2) {
                            DialogUtils.showDialogPrompt(_activity, "提示", "为提高交友会员质量，防止违规发布言论，减少被恶意骚扰，需要进行实名认证，认证后可交友聊天(认证服务由第三方平台提供，10元/次)", "去实名", "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialogInterface, int _i) {
                                    wexin();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialogInterface, int _i) {
                                    _dialogInterface.dismiss();
                                }
                            });
                        } else {
                            Intent intent = new Intent(_activity, ShiMingActivity.class);
                            _activity.startActivity(intent);
                        }


                    }

                }) {
                });
    }

    public static void isPayAuth1(final Activity _activity){
        EasyHttp.post(Api.apiurl + "identifyOrder/getIdentifyStatus")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo _userInfoPojo) {

                        if (_userInfoPojo.isOrder == 2) {
                            DialogUtils.showDialogPrompt(_activity, "提示", "为提高圈子质量,减少会员被恶意骚扰,同时响应监管要求,社区需要实名,认证后进入(认证服务由第三方平台提供,10元/次,无其他费用)", "去实名", "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialogInterface, int _i) {
                                    wexin();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialogInterface, int _i) {
                                    _dialogInterface.dismiss();
                                }
                            });
                        } else {
                            Intent intent = new Intent(_activity, ShiMingActivity.class);
                            _activity.startActivity(intent);
                        }


                    }

                }) {
                });
    }


    public static void sayHello2(final Activity act ,int userId,int sex) {
        EasyHttp.post(Api.apiurl +"/user/getRandomRealUsers")
                .params("userId",String.valueOf(userId))
                .params("sex",String.valueOf(sex))
                .execute(new CallBackProxy<BaseResult<List<UserInfoPojo>>, List<UserInfoPojo>>(new SimpleCallBack<List<UserInfoPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<UserInfoPojo> mTestPojo) {

                        final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_say_hello2, act);
                        TextView tuichu = dialog.findViewById(R.id.tv_tuichu);
                        TextView zhaohu = dialog.findViewById(R.id.tv_zhaohu);
                        recyclerView = dialog.findViewById(R.id.recyclerView);
                        final StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < mTestPojo.size(); i++) {
                            stringBuffer.append(mTestPojo.get(i).id + ",");
                        }
                        final String ids = stringBuffer.substring(0, stringBuffer.length() - 1).toString();



                        tuichu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                dialog.dismiss();
                            }
                        });

                        zhaohu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                sayToUser(ids);
                                dialog.dismiss();

                            }
                        });

                        _adapter = new SingleTypeBindingAdapter(act,mTestPojo,R.layout.item_sayhello2);
                        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<UserInfoPojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<UserInfoPojo> mData) {
                                ItemSayhello2Binding itemTuichuBinding = (ItemSayhello2Binding) holder.getBinding();

                                GlideImageLoader.displayRound(act,itemTuichuBinding.ivAvatar,mData.get(position).avatar);
                                itemTuichuBinding.tvName.setText(mData.get(position).nickName);
                                itemTuichuBinding.llContent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        Intent intent = new Intent(act,UserDetailActivity.class);
                                        intent.putExtra("USERID",mData.get(position).id);
                                        act.startActivity(intent);
                                    }
                                });
                            }
                        });
                        recyclerView.setAdapter(_adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(act,3));

                    }

                }) {
                });

    }

    private static void sayToUser(String ids) {
        EasyHttp.get(Api.apiurl + "/user/concertToUser")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("ids", String.valueOf(ids))
                .execute(new CallBackProxy<BaseResult<CircleResultPojo>, CircleResultPojo>(new SimpleCallBack<CircleResultPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final CircleResultPojo mTestPojo) {
                        ToastUtils.showShort("关注成功！");
                    }

                }) {
                });
    }


    public static void firstIn(final Activity _activity , String _s, final Button _button) {

        EasyHttp.get(Api.apiurl + "user/firstIn")
                .params("deviceId", AndroidSystemUtils.getPhoneSign(App.getInstance()))
                .params("openid", String.valueOf(_s))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {

                        SpUtlis.setlinkString(mTestPojo.linkString);
//                        SpUtlis.savaIdNo(mTestPojo.flag);
//                        SpUtlis.setVisitor(mTestPojo.visitor);

                        if(SpUtlis.getIdNo()==1){
                            Intent intent = new Intent(_activity, MainActivity.class);
                            intent.putExtra("ISNEEDLOGIN",true);
                            _activity.startActivity(intent);
                            _activity.finish();
                        }else{

                            _button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View _view) {

                                    CommonInterface.isPayAuth1(_activity);

                                }
                            });

                        }



                        if(!TextUtils.isEmpty(SpUtlis.getIMToken())){
                            RongIM.connect(SpUtlis.getIMToken(), new RongIMClient.ConnectCallback() {
                                @Override
                                public void onTokenIncorrect() {

                                }

                                @Override
                                public void onSuccess(String userid) {
                                    L.d("--onSuccess" + userid);

                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    L.d("--onSuccess" + errorCode);
                                }
                            });
                        }





                    }

                }) {
                });


    }



}
