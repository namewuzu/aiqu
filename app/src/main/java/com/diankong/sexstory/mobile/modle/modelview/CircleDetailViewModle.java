package com.diankong.sexstory.mobile.modle.modelview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CirclePojo;
import com.diankong.sexstory.mobile.bean.CircleResultPojo;
import com.diankong.sexstory.mobile.bean.Image;
import com.diankong.sexstory.mobile.bean.ShareContent;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.databinding.ActivityCircleDetailBinding;
import com.diankong.sexstory.mobile.event.RefreshEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;
import com.diankong.sexstory.mobile.modle.adapter.PingLunAdapter;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.TimeUtils;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.diankong.sexstory.mobile.widget.NineGridChildLayout;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/6.
 * 描述：
 * =============================================
 */

public class CircleDetailViewModle extends BaseViewModle<ActivityCircleDetailBinding> implements RecyclerArrayAdapter.OnLoadMoreListener {

    private LinearLayout mErrorView;
    private TextView mTvError;
    private int mPage = 1;
    private PingLunAdapter mMyAdapter;
    private int id;
    private List<String> _strings;
    private int isLike;
    private List<Image> stringList;
    private String[] split;
    private int zan;

    @Override
    public void initUI() {
        id = act.getIntent().getIntExtra("ID", 481);
        ToolbarUtils.initToolBar(b.toolbar,act);

        EventBus.getDefault().register(this);
        getShowCount();
        b.recyclerView.setErrorView(R.layout.view_error);
        b.recyclerView.setEmptyView(R.layout.view_empty);
        mErrorView = (LinearLayout) b.recyclerView.getErrorView();
        mTvError = mErrorView.findViewById(R.id.tv_error);

        mMyAdapter = new PingLunAdapter(act,b.et,b.send,id);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));

        b.recyclerView.setRefreshingColorResources(R.color.red23);
        b.recyclerView.setAdapterWithProgress(mMyAdapter);
        getListData();
//        b.recyclerView.setRefreshListener(this);

        mMyAdapter.setNoMore(R.layout.view_nomore);
        mMyAdapter.setMore(R.layout.view_more, this);

        mMyAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(act).inflate(R.layout.header_item, parent, false);
            }

            @Override
            public void onBindView(View headerView) {

                NineGridChildLayout ivOne = headerView.findViewById(R.id.iv_ngrid_layout);
                TextView tv_name = headerView.findViewById(R.id.tv_name);
                TextView tv_flo = headerView.findViewById(R.id.tv_flo);
                TextView tv_age = headerView.findViewById(R.id.tv_age);
                TextView tv_time = headerView.findViewById(R.id.tv_time);
                TextView tv_desc = headerView.findViewById(R.id.tv_desc);
                TextView tv_address = headerView.findViewById(R.id.tv_address);
                TextView tv_look = headerView.findViewById(R.id.tv_look);
                TextView tv_share = headerView.findViewById(R.id.tv_share);
                TextView tv_pinglun = headerView.findViewById(R.id.tv_pinglun);
                TextView tv_dianzan = headerView.findViewById(R.id.tv_dianzan);
                ImageView iv_avatar = headerView.findViewById(R.id.iv_avatar);
                ImageView iv_sex = headerView.findViewById(R.id.iv_sex);
                ImageView iv_zan = headerView.findViewById(R.id.iv_zan);
                ImageView iv_share = headerView.findViewById(R.id.iv_share);
                LinearLayout llname = headerView.findViewById(R.id.llname);



                setHeadData(ivOne, tv_name, tv_age, tv_time, tv_desc, tv_address, tv_look, tv_share, tv_pinglun, tv_dianzan, iv_avatar, iv_sex, iv_zan,iv_share,llname);


            }

        });


        b.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(TextUtils.isEmpty(b.et.getText().toString())){
                    ToastUtils.showShort("请输入您的评论");
                    return;
                }

                if(TextUtils.isEmpty(SpUtlis.getOpenId())){

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

                }else{
//                    if(SpUtlis.getIdNo()!=1){
//                        Intent intent = new Intent(act, ShiMingActivity.class);
//                        act.startActivity(intent);
//                    }else{
//
//                    }

                    comment(id,b.et.getText().toString(),1,"",0, SpUtlis.getId(),String.valueOf("游客"+SpUtlis.getId()));

                }



            }
        });


    }


    public void community(int userId,int communityId){
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


    public void comment(final int communityId, String comment, final int isReply, String replyedUserName, int replyedUserId, int userId, String userName){
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
                        b.et.setText("");
                        b.et.setHint("说点什么吧...");

                       getListData();
                        community(SpUtlis.getId(),communityId);
                    }

                }) {
                });
    }



    private void getShowCount() {

        EasyHttp.get(Api.apiurl2 + "community/updateShowCount")
                .params("id", String.valueOf(id))
                .params("userId",String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<CirclePojo>, CirclePojo>(new SimpleCallBack<CirclePojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final CirclePojo data) {

                    }

                }) {
                });

    }

    @Override
    public void onDestory() {
        super.onDestory();
        EventBus.getDefault().unregister(this);
    }


    private void setHeadData(final NineGridChildLayout ivOne, final TextView tv_name, final TextView tv_age, final TextView tv_time, final TextView tv_desc, final TextView tv_address, final TextView tv_look, final TextView tv_share, final TextView tv_pinglun, final TextView tv_dianzan, final ImageView iv_avatar, final ImageView iv_sex, final ImageView iv_zan,final ImageView iv_share,final LinearLayout llname) {


        EasyHttp.get(Api.apiurl2 + "community/selectById")
                .params("id", String.valueOf(id))
                .execute(new CallBackProxy<BaseResult<CirclePojo>, CirclePojo>(new SimpleCallBack<CirclePojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final CirclePojo data) {
                        _strings = new ArrayList<>();
                        split = data.imageUrl.split(",");
                        stringList = new ArrayList<>();
                        for (int j = 0; j < split.length; j++) {
                            stringList.add(new Image(split[j], 320, 320));
                        }

                        data.imageList = stringList;

                        for (Image image : data.imageList) {
                            _strings.add(image.getUrl());
                        }

                        ivOne.setUrlList(_strings);


                        if (data.sex == 1) {
                            iv_sex.setBackgroundResource(R.mipmap.ic_bt_c);
                        } else {
                            iv_sex.setBackgroundResource(R.mipmap.ic_bt_d);
                        }

                        tv_name.setText(data.userNickname);
                        tv_desc.setText(data.descs);

                        if(!isDestroy((Activity)act)){
                            GlideImageLoader.displayRound(act, iv_avatar, data.avatar);
                        }

                        iv_avatar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                Intent intent = new Intent(act, UserDetailActivity.class);
                                intent.putExtra("USERID", data.userId);
                                act.startActivity(intent);
                            }
                        });

                        llname.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                Intent intent = new Intent(act, UserDetailActivity.class);
                                intent.putExtra("USERID", data.userId);
                                act.startActivity(intent);
                            }
                        });

                        tv_look.setText(data.showCount + "");
                        tv_share.setText(data.shareCount + "");
                        tv_pinglun.setText(data.replyCount + "");
                        tv_dianzan.setText(data.pointCount + "");
                        tv_age.setText(data.age + "岁");

                        tv_address.setText(data.address);
                        tv_time.setText(TimeUtils.getTimeStateNew(String.valueOf(data.createTime)));

                        iv_share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                sharecount(data.id);
                                tongJiWechat(data.id);
                            }
                        });

                        iv_zan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {

                                if(TextUtils.isEmpty(SpUtlis.getOpenId())){
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
                                }else{

                                    if (isLike == 0) {
                                        zan = data.pointCount + 1;
                                        iv_zan.setImageResource(R.mipmap.zanb);
                                        tv_dianzan.setText(zan + "");
                                        isLike = 1;

                                        updatePoint(data.id, 1);
                                    } else {
                                        iv_zan.setImageResource(R.mipmap.zana);
                                        tv_dianzan.setText(data.pointCount + "");
                                        isLike = 0;
                                        updatePoint(data.id, 2);
                                    }
//                                    if(SpUtlis.getIdNo()!=1){
//                                        Intent intent = new Intent(act, ShiMingActivity.class);
//                                        act.startActivity(intent);
//                                    }else{
//
//                                    }


                                }


                            }
                        });


                    }

                }) {
                });

    }


    public void updatePoint(int id, int flag) {
        EasyHttp.get(Api.apiurl2 + "community/updatePoint")
                .params("id", String.valueOf(id))
                .params("flag", String.valueOf(flag))
                .params("userId",String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {

                    }

                }) {
                });
    }

    @Override
    public void initNet() {
    }

    private void getListData() {
        EasyHttp.get(Api.apiurl2 + "community/selectReplyPage")
                .params("pageNo", String.valueOf(mPage))
                .params("communityId", String.valueOf(id))
                .params("pageSize", String.valueOf(10))
                .execute(new CallBackProxy<BaseResult<CircleResultPojo>, CircleResultPojo>(new SimpleCallBack<CircleResultPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final CircleResultPojo mTestPojo) {
                        if (mPage == 1) {
                            mMyAdapter.removeAll();
                        }

                        mMyAdapter.addAll(mTestPojo.dataList);
                        mMyAdapter.notifyDataSetChanged();
                    }

                }) {
                });
    }


    @Override
    public void onLoadMore() {
        mPage++;
        getListData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshEvent(RefreshEvent event) {

        if(event.refresh){
            mPage = 1;
            getListData();
        }

    }

    public void sharecount(int id) {
        EasyHttp.get(Api.apiurl2 + "community/updateShareCount")
                .params("id", String.valueOf(id))
                .params("userId",String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {

                    }

                }) {
                });


    }


    private void tongJiWechat(int goodId) {
        EasyHttp.get(Api.apiurl + "good/shareClick")
                .params("userId",String.valueOf(SpUtlis.getId()))
                .params("communityId",String.valueOf(goodId))
                .execute(new CallBackProxy<BaseResult<ShareContent>, ShareContent>(new SimpleCallBack<ShareContent>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort("失败" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(final ShareContent mTestPojo) {
//                        share(mTestPojo.title, mTestPojo.desc, mTestPojo.shareUrl, mTestPojo.thumbUrl);
                        EasyHttp.post(Api.apiurl + "good/getFreeGoodShareInfo")
                                .execute(new CallBackProxy<BaseResult<ShareContent>, ShareContent>(new SimpleCallBack<ShareContent>() {
                                    @Override
                                    public void onError(ApiException e) {
                                        ToastUtils.showShort("失败" + e.getMessage());
                                    }

                                    @Override
                                    public void onSuccess(final ShareContent mTestPojo) {
                                        share(mTestPojo.title, mTestPojo.desc, mTestPojo.shareUrl, mTestPojo.thumbUrl);
                                    }

                                }) {
                                });
                    }

                }) {
                });

    }


    private void share(String title, String desc, String shareUrl, String thumbUrl) {
        ShareContent shareContent = new ShareContent();
        shareContent.title = title;
        shareContent.desc = desc;
        shareContent.shareUrl = shareUrl;
        if (TextUtils.isEmpty(thumbUrl)) {
            shareContent.thumbRes = R.mipmap.ic_launcher;
        } else {
            shareContent.thumbUrl = thumbUrl;
        }

        UMengTools.setShareAction(act, SHARE_MEDIA.WEIXIN, shareContent, new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA _share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA _share_media) {
                L.d("platform" + _share_media);
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


    }

    public boolean isDestroy(Activity mActivity) {
        if (mActivity== null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

}
