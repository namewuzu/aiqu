package com.diankong.sexstory.mobile.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.Image;
import com.diankong.sexstory.mobile.modle.activity.BigImagePagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2016/12/16 0016.
 * 描述：
 * =============================================
 */

public class NineGridChildLayout extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;
    private boolean glideFlag;

    public NineGridChildLayout(Context context) {
        super(context);
    }

    public NineGridChildLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {
        if (mContext == null) {
            return false;
        }
        try {
            Glide
                    .with(mContext)
                    .load(url)
                    .priority(Priority.LOW)
                    .centerCrop()
                    .override(508, 508)
                    .thumbnail(0.5f)
                    .into(new GlideDrawableImageViewTarget(imageView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);

                            //    Drawable bitmap = resource.getCurrent();

                            int w = resource.getIntrinsicWidth();
                            int h = resource.getIntrinsicHeight();

                            int newW;
                            int newH;
                            if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                                newW = parentWidth / 2;
                                newH = newW * 5 / 3;
                            } else if (h < w) {//h:w = 2:3
                                newW = parentWidth * 2 / 3;
                                newH = newW * 2 / 3;
                            } else {//newH:h = newW :w
                                newW = parentWidth / 2;
                                newH = h * newW / w;
                            }
                            setOneImageLayoutParams(imageView, newW, newH);
                        }
                    });
        } catch (Exception _e) {
            _e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void displayImage(final RatioImageView imageView, final String url) {
//        GlideImageLoader.onDisplayImage(mContext, imageView, url);

        Glide.with(mContext)
                .load(url)
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_mrlg)
                .dontAnimate()
                .crossFade()
                .into(imageView);


//        Observable.just(url) // 输入类型 String
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String imgUrl) {
//                        if (I.isGif(url)) {
//                            if (glideFlag) {
//                                GlideImageLoader.onDisplayGifUrl(UIUtils.getContext(), imageView, url);
//                            } else {
//                                GlideImageLoader.onDisplayGifUrl(imageView, url);
//                            }
//                        } else {
//                            Context context;
//                            if (glideFlag) {
//                                context = UIUtils.getContext();
//                            } else {
//                                context = imageView.getContext();
//                            }
//                            Glide.with(context).load(imgUrl)//
//                                    .placeholder(R.mipmap.ic_mrlg)//
//                                    .error(R.mipmap.ic_mrlg)//
//                                    .thumbnail(0.5f)
//                                    .override(508, 508)
//                                    .priority(Priority.LOW)
//                                    .centerCrop()
//                                    .diskCacheStrategy(DiskCacheStrategy.NONE)//
//                                    .into(imageView);
//                        }
//                    }
//                });


//        android:src="@mipmap/ic_image_loading"

    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList) {
        /// startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, firstSharedView, "sharedView").toBundle());
        //  BigImagePagerActivity.startImagePagerActivity(mContext, urlList, position);
        BigImagePagerActivity.startImagePagerActivity((Activity) mContext, urlList, position, new BigImagePagerActivity.ImageSize(640,640));

//        config.setNowThumbnailIndex(position);
//        config.setOriginImageList(itemImageViews);
//        transferee.apply(config).show(new Transferee.OnTransfereeStateChangeListener() {
//            @Override
//            public void onShow() {
//                Glide.with(getContext()).pauseRequests();
//            }
//
//            @Override
//            public void onDismiss() {
//                Glide.with(getContext()).resumeRequests();
//            }
//        });
    }



    private static void setImageList(NineGridChildLayout _layout, List<Image> imgs) {
        List<String> strings = new ArrayList<>();
        if (imgs != null) {
            for (int i = 0; i < imgs.size(); i++) {
                String img = imgs.get(i).getUrl();
                strings.add(img);
            }
        }
        _layout.setUrlList(strings);
    }

    public void setGlideFlag(boolean glideFlag) {
        this.glideFlag = glideFlag;
    }
}
