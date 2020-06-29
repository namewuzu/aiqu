package com.diankong.sexstory.mobile.utils;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.ShareContent;
import com.diankong.sexstory.mobile.bean.Vediopojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;


/**
 * 湖南高信网络有限公司源代码，版权@归而然网络科技所有。
 * 项目: 文明社区.app
 * 作者: created by 熊凯 on 2016/11/24 14:46
 * 描述: UmengShareTools有盟社会化工具类
 */

public class UMengTools {

    private UMengTools() {
        //no instance
    }

    /**
     * 授权登陆
     */
    public static void doOauthVerify(Activity _activity, SHARE_MEDIA platform) {
        UMShareAPI mShareAPI = UMShareAPI.get(App.getInstance().getApplicationContext());
        if (mShareAPI.isInstall(_activity, platform)) {
            mShareAPI.doOauthVerify(_activity, platform, umAuthListener);
        } else {
            if (platform == SHARE_MEDIA.QQ) {
                ToastUtils.showShort("抱歉您未安装QQ客户端，无法进行QQ授权！");
            } else if (platform == SHARE_MEDIA.WEIXIN) {
                ToastUtils.showShort("抱歉您未安装微信客户端，无法进行微信授权！");
            } else {
                ToastUtils.showShort("抱歉您未安装该客户端，无法进行此应用授权！");
            }
        }

    }

    private static UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data != null) {
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    L.d("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }
                WxPojo wxpojo = Convert.fromJson(JsonUtils.map2json(data), WxPojo.class);
                /*if (data.containsKey("gender")) {
                    String gender = data.get("gender");
                    if (gender.equals("女")) {
                        userInfoPojo.sex = "女";
                    } else {
                        userInfoPojo.sex = "男";
                    }
                }*/
                userInfoListener.getUserInfo(wxpojo);
            }
            ToastUtils.showShort("授权成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            ToastUtils.showShort("授权失败");
            userInfoListener.onError();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.showShort("授权取消了");
            userInfoListener.onCancel();
        }
    };


    private static OnUserInfoListener userInfoListener;

    public static void deleteOauth(AppCompatActivity userSettingActivity) {

        UMShareAPI.get(userSettingActivity).deleteOauth(userSettingActivity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    public interface OnUserInfoListener {
        void getUserInfo(WxPojo UserInfo);

        void onCancel();

        void onError();


    }


    /**
     * 获取用户信息
     *
     * @param _activity         当前页面
     * @param platform          授权方式
     * @param _userInfoListener 获取到用户信息回调
     */
    public static void getPlatformInfo(Activity _activity, SHARE_MEDIA platform, OnUserInfoListener _userInfoListener) {
        userInfoListener = _userInfoListener;
        UMShareAPI mShareAPI = UMShareAPI.get(App.getInstance().getApplicationContext());
        //  mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
//        isInstall
        if (mShareAPI.isInstall(_activity, platform)) {
            mShareAPI.getPlatformInfo(_activity, platform, umAuthListener);

        } else {
            if (platform == SHARE_MEDIA.QQ) {
                ToastUtils.showShort("抱歉您未安装QQ客户端，无法进行QQ登录！");
            } else if (platform == SHARE_MEDIA.WEIXIN) {

                ToastUtils.showShort("抱歉您未安装微信客户端，无法进行微信登录！");
            } else {
                ToastUtils.showShort("抱歉您未安装该客户端，无法进行此应用登录！");
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    //
    //  测试 代码
    //  UMengTools.getPlatformInfo(this, SHARE_MEDIA.QQ, new UMengTools.OnUserInfoListener() {
    //  @Override
    //  public void getUserInfo(QQUserInfo _qqUserInfo) {
    //    L.d("获取到的用户信息----->" + _qqUserInfo.toString());
    // }
    //});
    ///////////////////////////////////////////////////////////////////////////


    /**
     * 设置分享
     *
     * @param _activity     上下文
     * @param platform      分享平台
     * @param _shareContent 分享内容
     */
    public static void setShareAction(final Activity _activity, SHARE_MEDIA platform, ShareContent _shareContent, final Vediopojo _vediopojo) {

        UMShareAPI umShareAPI = UMShareAPI.get(_activity);
        if (platform == SHARE_MEDIA.SMS || umShareAPI.isInstall(_activity, platform)) {

            UMWeb web = new UMWeb(_shareContent.shareUrl);
            web.setDescription(_shareContent.desc);
            web.setThumb(new UMImage(_activity, _shareContent.thumbRes));
            web.setTitle(_shareContent.title);
            new ShareAction(_activity).setPlatform(platform)
                    .withMedia(web)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onResult(SHARE_MEDIA platform) {
                            L.d("platform" + platform);
                            ToastUtils.showShort("分享成功啦");
                            _vediopojo.share=1;
                        }

                        @Override
                        public void onError(SHARE_MEDIA platform, Throwable t) {
                            ToastUtils.showShort("分享失败");
                            if (t != null) {
                                L.d("throw:" + t.getMessage());
                            }
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA platform) {
                            ToastUtils.showShort("分享取消了");
                        }
                    })
                    .share();
        } else {
            if (platform == SHARE_MEDIA.QQ) {
                ToastUtils.showShort("抱歉您为安装QQ客户端，无法进行QQ分享！");
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                ToastUtils.showShort("抱歉您为安装微信客户端，无法进行微信分享！");
            } else {
                ToastUtils.showShort("抱歉您为安装该客户端，无法进行此应用分享！");
            }

        }
    }

    /**
     * 设置分享
     *
     * @param _activity     上下文
     * @param platform      分享平台
     * @param _shareContent 分享内容
     */
    public static void setShareAction(final Activity _activity, SHARE_MEDIA platform, ShareContent _shareContent) {

        UMShareAPI umShareAPI = UMShareAPI.get(_activity);
        if (platform == SHARE_MEDIA.SMS || umShareAPI.isInstall(_activity, platform)) {

            UMWeb web = new UMWeb(_shareContent.shareUrl);
            web.setDescription(_shareContent.desc);
            web.setThumb(new UMImage(_activity, _shareContent.thumbRes));
            web.setTitle(_shareContent.title);
            new ShareAction(_activity).setPlatform(platform)
                    .withMedia(web)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onResult(SHARE_MEDIA platform) {
                            L.d("platform" + platform);
                            ToastUtils.showShort("分享成功啦");
                        }

                        @Override
                        public void onError(SHARE_MEDIA platform, Throwable t) {
                            ToastUtils.showShort("分享失败");
                            if (t != null) {
                                L.d("throw:" + t.getMessage());
                            }
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA platform) {
                            ToastUtils.showShort("分享取消了");
                        }
                    })
                    .share();
        } else {
            if (platform == SHARE_MEDIA.QQ) {
                ToastUtils.showShort("抱歉您为安装QQ客户端，无法进行QQ分享！");
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                ToastUtils.showShort("抱歉您为安装微信客户端，无法进行微信分享！");
            } else {
                ToastUtils.showShort("抱歉您为安装该客户端，无法进行此应用分享！");
            }

        }
    }

    /**
     * 设置分享
     *
     * @param _activity     上下文
     * @param platform      分享平台
     * @param _shareContent 分享内容
     */
    public static void setShareAction(final Activity _activity, SHARE_MEDIA platform, ShareContent _shareContent,UMShareListener _umShareListener) {

        UMShareAPI umShareAPI = UMShareAPI.get(_activity);
        if (platform == SHARE_MEDIA.SMS || umShareAPI.isInstall(_activity, platform)) {

            UMWeb web = new UMWeb(_shareContent.shareUrl);
            web.setDescription(_shareContent.desc);
            web.setThumb(new UMImage(_activity, _shareContent.thumbUrl));
            web.setTitle(_shareContent.title);
            new ShareAction(_activity).setPlatform(platform)
                    .withMedia(web)
                    .setCallback(_umShareListener)
                    .share();
        } else {
            if (platform == SHARE_MEDIA.QQ) {
                ToastUtils.showShort("抱歉您为安装QQ客户端，无法进行QQ分享！");
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                ToastUtils.showShort("抱歉您为安装微信客户端，无法进行微信分享！");
            } else {
                ToastUtils.showShort("抱歉您为安装该客户端，无法进行此应用分享！");
            }

        }
    }

    /**
     * 设置分享
     *
     * @param _activity     上下文
     * @param platform      分享平台
     * @param _shareContent 分享内容
     */
    public static void setShareAction(final Activity _activity, SHARE_MEDIA platform, ShareContent _shareContent,UMShareListener _umShareListener,int img) {

        UMShareAPI umShareAPI = UMShareAPI.get(_activity);
        if (platform == SHARE_MEDIA.SMS || umShareAPI.isInstall(_activity, platform)) {

            UMWeb web = new UMWeb(_shareContent.shareUrl);
            web.setDescription(_shareContent.desc);
            web.setThumb(new UMImage(_activity, _shareContent.thumbUrl));
            web.setTitle(_shareContent.title);
            new ShareAction(_activity).setPlatform(platform)
                    .withMedia(web)
                    .setCallback(_umShareListener)
                    .share();
        } else {
            if (platform == SHARE_MEDIA.QQ) {
                ToastUtils.showShort("抱歉您为安装QQ客户端，无法进行QQ分享！");
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                ToastUtils.showShort("抱歉您为安装微信客户端，无法进行微信分享！");
            } else {
                ToastUtils.showShort("抱歉您为安装该客户端，无法进行此应用分享！");
            }

        }
    }

    /**
     * 分享图片
     */

    public static void setShareAction(final Activity _activity,SHARE_MEDIA platform,int img,UMShareListener _umShareListener){
        UMShareAPI umShareAPI = UMShareAPI.get(_activity);
        if (platform == SHARE_MEDIA.SMS || umShareAPI.isInstall(_activity, platform)) {
            UMImage umImage = new UMImage(_activity,img);
            new ShareAction(_activity).setPlatform(platform)
                    .withMedia(umImage)
                    .setCallback(_umShareListener).share();
        }else {
            if (platform == SHARE_MEDIA.QQ) {
                ToastUtils.showShort("抱歉您为安装QQ客户端，无法进行QQ分享！");
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                ToastUtils.showShort("抱歉您为安装微信客户端，无法进行微信分享！");
            } else {
                ToastUtils.showShort("抱歉您为安装该客户端，无法进行此应用分享！");
            }
        }
    }

    /**
     * 分享图片
     */

    public static void setShareAction(final Activity _activity,SHARE_MEDIA platform,String img,UMShareListener _umShareListener){
        UMShareAPI umShareAPI = UMShareAPI.get(_activity);
        if (platform == SHARE_MEDIA.SMS || umShareAPI.isInstall(_activity, platform)) {
            UMImage umImage = new UMImage(_activity,img);
            new ShareAction(_activity).setPlatform(platform)
                    .withMedia(umImage)
                    .setCallback(_umShareListener).share();
        }else {
            if (platform == SHARE_MEDIA.QQ) {
                ToastUtils.showShort("抱歉您为安装QQ客户端，无法进行QQ分享！");
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                ToastUtils.showShort("抱歉您为安装微信客户端，无法进行微信分享！");
            } else {
                ToastUtils.showShort("抱歉您为安装该客户端，无法进行此应用分享！");
            }
        }
    }

    /**
     * 分享视频
     * @param act
     * @param platform
     * @param _umShareListener
     */
    public static void Share(final Activity act, final SHARE_MEDIA platform, final int vedioClassId, final String vedioTitle, final UMShareListener _umShareListener){

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final String netIp = IpUtils.getNetIp();
//
//
//        ServiceApi.Factory.create().getRandowTitle(SpUtlis.getToken(), UIUtils.getUserInfo().id, "loveapppool",UIUtils.getUserInfo().id).observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new RxSubscriber<SuperBean<SharePojo>>(act, true) {
//                    @Override
//                    protected void _onError(String message) {
//                        LoadingDialog.cancelDialogForLoading();
//                        ToastUtils.showShort(message);
//                    }
//
//                    @Override
//                    protected void _onNext(SuperBean<SharePojo> pojo) {
//                        LoadingDialog.cancelDialogForLoading();
//                        if (pojo.status.equals("001")) {
//                            ShareContent shareContent = new ShareContent();
////                            shareContent.title = pojo.obj.title;
//                            shareContent.title = vedioTitle;
//                            shareContent.desc = pojo.obj.title;
////                            shareContent.shareUrl = pojo.obj.url + "?ip=" + netIp + "&contentId=" + vedioClassId + "&userId=" + UIUtils.getUserInfo().id;
//                            shareContent.shareUrl = "http://" + RandomUtils.OneEn() + "." + pojo.obj.url +"/" + RandomUtils.OneEnList()+"/" + RandomUtils.OneNum() +"/" +"jumpPage.html"+ "?ip=" + netIp + "&contentId=" + vedioClassId + "&userId=" + UIUtils.getUserInfo().id+"&subtractPercentage=" + pojo.obj.subtractPercentage;
//                            shareContent.thumbUrl = pojo.obj.imgUrl;
//                            setShareAction(act,platform,shareContent,_umShareListener,1);
//                        } else {
//                            ToastUtils.showShort(pojo.msg);
//                        }
//
//
//                    }
//
//                });
//            }
//        }).start();

    }


}
