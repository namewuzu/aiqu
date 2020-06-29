package com.diankong.sexstory.mobile.modle.fragment;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.BaseFrag;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.bean.lisener.MyConversationClickListener;
import com.diankong.sexstory.mobile.databinding.MsgFragmentBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.modelview.MSGViewModle;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/21.
 * 描述：
 * =============================================
 */

public class MSGFragment extends BaseFrag<MsgFragmentBinding, MSGViewModle> {
    UserInfo userInfo;

    @Override
    protected int getLayoutResource() {
        return R.layout.msg_fragment;
    }

    @Override
    public void initView() {


        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context _context, Conversation.ConversationType _conversationType, String _s) {



                if (TextUtils.isEmpty(SpUtlis.getOpenId())) {
                    final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, getActivity());
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
                            UMengTools.getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                                @Override
                                public void getUserInfo(WxPojo UserInfo) {
                                    CommonInterface.wxLogin(getActivity(), SpUtlis.getId(), UserInfo.accessToken, UserInfo.openid);
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
                    return true;

                } else {

                    if(_conversationType.getName().equals(Conversation.ConversationType.SYSTEM)){
                        return false;
                    }

                    if (SpUtlis.getIdNo() != 1) {
//                        Intent intent = new Intent(getActivity(), ShiMingActivity.class);
//                        getActivity().startActivity(intent);

                        CommonInterface.isPayAuth(getActivity());


                        return true;
                    }

                }

                return false;

            }

            @Override
            public boolean onConversationPortraitLongClick(Context _context, Conversation.ConversationType _conversationType, String _s) {
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context _context, View _view, UIConversation _uiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context _context, View _view, UIConversation _uiConversation) {


                if (TextUtils.isEmpty(SpUtlis.getOpenId())) {
                    final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, getActivity());
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
                            UMengTools.getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                                @Override
                                public void getUserInfo(WxPojo UserInfo) {
                                    CommonInterface.wxLogin(getActivity(), SpUtlis.getId(), UserInfo.accessToken, UserInfo.openid);
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
                    return true;

                } else {

                    if(_uiConversation.getConversationType().equals(Conversation.ConversationType.SYSTEM)){
                        return false;
                    }

                    if (SpUtlis.getIdNo() != 1) {
//                        Intent intent = new Intent(getActivity(), ShiMingActivity.class);
//                        getActivity().startActivity(intent);
                        CommonInterface.isPayAuth(getActivity());
                        return true;
                    }

                }

                return false;
            }
        });


        if (TextUtils.isEmpty(SpUtlis.getOpenId())) {
            final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, getActivity());
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
                    UMengTools.getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                        @Override
                        public void getUserInfo(WxPojo UserInfo) {
                            CommonInterface.wxLogin(getActivity(), SpUtlis.getId(), UserInfo.accessToken, UserInfo.openid);
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

        FragmentManager fragmentManage = getFragmentManager();
        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        if (fragement == null) {
            fragement = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + App.getInstance().getPackageName()).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")//私聊
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群聊
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                    .appendQueryParameter(Conversation.ConversationType.CUSTOMER_SERVICE.getName(), "false")//客服
                    .build();
            fragement.setUri(uri);

            FragmentTransaction transaction = fragmentManage.beginTransaction();
            transaction.replace(R.id.ll, fragement);
            transaction.commit();
        }

        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                return findUserById(userId);
            }

        }, true);

        //发送按钮监听
//        RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {
//            @Override
//            public Message onSend(final Message _message) {
//
//                if(_message.getConversationType().getName().equals(Conversation.ConversationType.SYSTEM)){
//                    ToastUtils.showShort("不可回复系统消息");
//                }
//
//                return null;
//
//            }
//
//            @Override
//            public boolean onSent(Message _message, RongIM.SentMessageErrorCode _sentMessageErrorCode) {
//                return false;
//            }
//        });

        RongIM.getInstance().setConversationClickListener(new MyConversationClickListener());


    }

    private UserInfo findUserById(String userId) {
        EasyHttp.post(Api.apiurl + "user/getUserInfor")
                .params("userId", userId)
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final UserInfoPojo UserInfo) {
                        userInfo = new UserInfo(String.valueOf(UserInfo.id), UserInfo.nickName, Uri.parse(UserInfo.avatar));
                        RongIM.getInstance().refreshUserInfoCache(userInfo);

                    }

                }) {
                });
        return userInfo;
    }

}
