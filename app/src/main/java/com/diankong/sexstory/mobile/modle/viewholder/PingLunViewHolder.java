package com.diankong.sexstory.mobile.modle.viewholder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CirclePojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.event.RefreshEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.TimeUtils;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.umeng.socialize.bean.SHARE_MEDIA;
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

public class PingLunViewHolder extends BaseViewHolder<CirclePojo> {
    public TextView tv_name;
    public TextView tv_time;
    public TextView tv_desc;
    public TextView tv_pinglun;
    public TextView replyedUserName;
    public ImageView iv_avatar;
    public ImageView iv_pinglun;
    public LinearLayout ll_reply;
    public EditText et;
    public TextView send;
    public Activity _context;
    public int isReply2 = 1;
    public int replyedId;
    public String replyedName;
    public int communityId;


    public PingLunViewHolder(ViewGroup parent, EditText et, TextView send, int communityId, Activity _context) {
        super(parent, R.layout.item_detail_pinglun);
        this.et = et;
        this.send = send;
        this.communityId = communityId;
        this._context = _context;
        tv_name = (TextView) $(R.id.tv_name);
        tv_pinglun = (TextView) $(R.id.tv_pinglun);
        replyedUserName = (TextView) $(R.id.replyedUserName);
        tv_time = (TextView) $(R.id.tv_time);
        tv_desc = (TextView) $(R.id.tv_desc);
        iv_avatar = (ImageView) $(R.id.iv_avatar);
        iv_pinglun = (ImageView) $(R.id.iv_pinglun);
        ll_reply = (LinearLayout) $(R.id.ll_reply);
    }

    @Override
    public void setData(final CirclePojo data) {
        super.setData(data);

        tv_name.setText(data.userName);
        tv_desc.setText(data.comment);
        tv_pinglun.setText(data.comment);
        replyedUserName.setText(data.replyedUserName + "：");
        tv_time.setText(TimeUtils.getTimeStateNew(String.valueOf(data.createTime)));
        GlideImageLoader.displayRound(App.getInstance(), iv_avatar, data.avatarUrl);

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), UserDetailActivity.class);
                intent.putExtra("USERID", data.userId);
                getContext().startActivity(intent);
            }
        });

        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), UserDetailActivity.class);
                intent.putExtra("USERID", data.userId);
                getContext().startActivity(intent);
            }
        });


        if (data.isReply == 1) {
            ll_reply.setVisibility(View.GONE);
            tv_desc.setVisibility(View.VISIBLE);
        } else {
            ll_reply.setVisibility(View.VISIBLE);
            tv_desc.setVisibility(View.GONE);
        }

        iv_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                if (data.userId == SpUtlis.getId()) {
                    ToastUtils.showShort("不能回复自己！");
                    return;
                }

                isReply2 = 2;
                et.setHint("回复:" + data.userName);
                replyedId = data.userId;
                replyedName = data.userName;

                SpUtlis.setReplyedName(data.userName);

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {


                if (TextUtils.isEmpty(et.getText().toString())) {
                    ToastUtils.showShort("请输入您的评论");
                    return;
                }

                if (TextUtils.isEmpty(SpUtlis.getOpenId())) {

                    final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, _context);
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
                            UMengTools.getPlatformInfo(_context, SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                                @Override
                                public void getUserInfo(WxPojo UserInfo) {
                                    CommonInterface.wxLogin(_context, SpUtlis.getId(), UserInfo.accessToken, UserInfo.openid);
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

                } else {

//                    if (SpUtlis.getIdNo() != 1) {
//                        Intent intent = new Intent(_context, ShiMingActivity.class);
//                        _context.startActivity(intent);
//                    } else {
//
//
//                    }

                    if (et.getHint().toString().contains("回复")) {
                        comment(communityId, et.getText().toString(), 2, SpUtlis.getReplyedName(), data.userId, SpUtlis.getId(), String.valueOf("游客" + SpUtlis.getId()));
                    } else {
                        comment(communityId, et.getText().toString(), 1, SpUtlis.getReplyedName(), data.userId, SpUtlis.getId(), String.valueOf("游客" + SpUtlis.getId()));
                    }
                }

            }
        });

    }

    public void comment(final int communityId, final String comment, final int isReply, String replyedUserName, int replyedUserId, int userId, String userName) {
        EasyHttp.get(Api.apiurl2 + "community/comment")
                .params("comment", String.valueOf(comment))
                .params("communityId", String.valueOf(communityId))
                .params("isReply", String.valueOf(isReply))
                .params("replyedUserName", String.valueOf(replyedUserName))
                .params("replyedUserId", String.valueOf(replyedUserId))
                .params("userId", String.valueOf(userId))
                .params("userName", String.valueOf(userName))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {
                        et.setText("");
                        et.setHint("说点什么吧...");
                        isReply2 = 1;
                        EventBus.getDefault().post(new RefreshEvent(true));


//                        L.d("多少" + comment.length());
//
//                        if(String.valueOf(comment).length()>=10){
//
//                        }

                        community(SpUtlis.getId(), communityId);
                    }

                }) {
                });
    }


    public void community(int userId, int communityId) {
        EasyHttp.get(Api.apiurl2 + "community/userCommunityfee")
                .params("userId", String.valueOf(userId))
                .params("id", String.valueOf(communityId))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {
                        ToastUtils.showShort(mTestPojo.coinDesc);
                    }

                }) {
                });
    }


}
