package com.diankong.sexstory.mobile.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.diankong.sexstory.mobile.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class ShareActivity extends Activity {

    private String sharePlatform;
    private String title;
    private String text;
    private String url;
    private String imageUrl;
    private SHARE_MEDIA media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent.hasExtra("platform")) {
            this.sharePlatform = intent.getStringExtra("platform");
        }
        if (intent.hasExtra("title")) {
            this.title = intent.getStringExtra("title");
        }
        if (intent.hasExtra("describe")) {
            this.text = intent.getStringExtra("describe");
        }
        if (intent.hasExtra("link")) {
            this.url = intent.getStringExtra("link");
        }
        if (intent.hasExtra("image")) {
            this.imageUrl = intent.getStringExtra("image");
        }
        if (TextUtils.isEmpty(this.url) || TextUtils.isEmpty(this.sharePlatform)) {
            finish();
        } else if (this.sharePlatform.equals("wechat_moments")) {
            this.media = SHARE_MEDIA.WEIXIN_CIRCLE;
        } else if (this.sharePlatform.equals("wechat_friend")) {
            this.media = SHARE_MEDIA.WEIXIN;
        } else if (this.sharePlatform.equals("qq_mobile")) {
            this.media = SHARE_MEDIA.QQ;
        } else if (this.sharePlatform.equals("qq_zone")) {
            this.media = SHARE_MEDIA.QZONE;
        } else if (this.sharePlatform.equals("sina_weibo")) {
            this.media = SHARE_MEDIA.SINA;
        } else if (this.sharePlatform.equals("alipay_friend")) {
            this.media = SHARE_MEDIA.ALIPAY;
        } else {
            finish();
        }

        Config.checkIfNoneShowIntall(this);

        if (!TextUtils.isEmpty(Config.sharePkg) && !TextUtils.isEmpty(Config.shareAppId)) {
            UMWeb web = new UMWeb(this.url);
            web.setTitle(this.title);
            if(!TextUtils.isEmpty(imageUrl)){
                web.setThumb(new UMImage(this, this.imageUrl));
            }else {
                web.setThumb(new UMImage(this, R.mipmap.ic_launcher));
            }
            web.setDescription(this.text);
            new ShareAction(this)
                    .withText(text)
                    .withMedia(web)
                    .setPlatform(this.media).setCallback(this.umShareListener).share();
        }
    }

    UMShareListener umShareListener = new UMShareListener() {
        public void onResult(SHARE_MEDIA platform) {
            Log.e("share","onResult");
        }

        public void onError(SHARE_MEDIA platform, Throwable throwable) {

            Log.e("share","onerror",throwable);

        }
        public void onCancel(SHARE_MEDIA platform) {
            if (!ShareActivity.this.isFinishing()) {
                ShareActivity.this.finish();
            }
        }
        public void onStart(SHARE_MEDIA arg0) {
            Log.e("share","onStart");
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
