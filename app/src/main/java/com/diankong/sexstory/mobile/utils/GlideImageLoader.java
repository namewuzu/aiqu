package com.diankong.sexstory.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;
import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.youth.banner.loader.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import static com.diankong.sexstory.mobile.utils.Utils.dip2px;


/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：16/9/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GlideImageLoader extends ImageLoader  {
//public class GlideImageLoader implements ImageLoader, NineGridView.ImageLoader {

    private static GlideImageLoader _singleInstance = null;

    public GlideImageLoader() {
    }


    public static GlideImageLoader getInstance() {
        synchronized (GlideImageLoader.class) {
            if (_singleInstance == null) {
                _singleInstance = new GlideImageLoader();
            }
        }
        return _singleInstance;
    }


    public static void onDisplayGif(ImageView imageView, String filePath) {
        Glide.with(imageView.getContext())
                .load(Uri.fromFile(new File(filePath)))//
                .override(240, 240)
                .placeholder(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }


    public static void onDisplayGif(ImageView imageView, String filePath, RequestListener<Uri, GlideDrawable> listener) {
        Glide.with(imageView.getContext())
                .load(Uri.fromFile(new File(filePath)))//
                .override(240, 240)
                .listener(listener)
                .placeholder(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }


    public static void onDisplayGifUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(Uri.parse(url))//
                .override(240, 240)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .placeholder(R.mipmap.ic_mrlg)//
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }

    public static void onDisplayGifUrl(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(Uri.parse(url))//
                .override(240, 240)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .placeholder(R.mipmap.ic_mrlg)//
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }


    public static void onDisplayGifUrl(ImageView imageView, String url, RequestListener<String, GlideDrawable> listener) {
        Glide.with(imageView.getContext())
                .load(url)//
                .override(240, 240)
                .placeholder(R.mipmap.ic_mrlg)//
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(imageView));
    }


    public static void onDisplayImage(@NotNull final Context context, final ImageView imageView, @NotNull String imgUrl) {


        if (imageView == null) {
            return;
        }

        Glide.with(imageView.getContext()).load(imgUrl)//
                .placeholder(R.mipmap.ic_mrlg)//
                .error(R.mipmap.ic_mrlg)//
                .thumbnail(0.55f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);


    }

    public static void onDisplayIcon(@NotNull final Context context, final ImageView imageView, @NotNull String imgUrl) {


        if (imageView == null) {
            return;
        }

        Glide.with(imageView.getContext()).load(imgUrl)//
//                .override(85,24)
                .into(imageView);


    }

/*    public static void onDisplayImageBanner(@NotNull final Context context, final ImageView imageView, @NotNull String imgUrl, int placeholderRes) {


        if (imageView == null) {
            return;
        }
        Glide.with(context).load(imgUrl)//
                .placeholder(placeholderRes)//
                .error(R.mipmap.ic_banner)//
                .thumbnail(0.55f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }*/

    public static void onDisplayImage(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull File _file) {
//
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(_file)//
                .placeholder(R.mipmap.ic_mrlg)//
                .error(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }

    public static void onDisplayImage(final Context context, @NotNull final ImageView imageView, @NotNull Bitmap _bitmap) {

        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(_bitmap)//
                .placeholder(R.mipmap.ic_mrlg)//
                .error(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void onDisplayImage(final Context context, final ImageView imageView, Integer resource) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(resource)//
                .placeholder(R.mipmap.ic_mrlg)//
                .error(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }


    public static void onDisplayImage(final Context context, final ImageView imageView, Uri resource) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(resource)//
                .placeholder(R.mipmap.ic_mrlg)//
                .error(R.mipmap.ic_mrlg)//
                .thumbnail(0.55f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
//                    }
//                });
    }


    public static void onDisplayImage(final Context context, final ImageView imageView, Uri resource, RequestListener<Uri, GlideDrawable> listener) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(resource)//
                .placeholder(R.mipmap.ic_mrlg)//
                .error(R.mipmap.ic_mrlg)//
                .thumbnail(0.55f)
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }


    /**
     * 自定义图片的大小
     *
     * @param activity
     * @param path
     * @param imageView
     * @param width
     * @param height
     */
    public void displayImage(@NotNull final Activity activity, @NotNull String path, @NotNull final ImageView imageView, @NotNull final int width, @NotNull final int height) {

        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(path)//
                .placeholder(R.mipmap.ic_mrlg)//
                .error(R.mipmap.ic_mrlg)//
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }


    public void clearMemoryCache() {

    }

    public static void displayRound(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }
        if (!Util.isOnMainThread()) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(path)
                .error(R.mipmap.ic_mrtx)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideRoundTransformUtil(context))
                .into(imageView);
    }

    public static void displayCorner(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }
        if (!Util.isOnMainThread()) {
            return;
        }

        CornerTransform transformation = new CornerTransform(context, dip2px(context, 6));
//只是绘制左上角和右上角圆角
        transformation.setExceptCorner(false, false, false, false);


        Glide
                .with(imageView.getContext())
                .load(path)
                .asBitmap()
                .skipMemoryCache(true)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .transform(transformation)
                .into(imageView);
    }

    public static void display2Corner(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }
        if (!Util.isOnMainThread()) {
            return;
        }

        CornerTransform transformation = new CornerTransform(context, dip2px(context, 12));
//只是绘制左上角和右上角圆角
        transformation.setExceptCorner(false, false, true, true);


        Glide
                .with(imageView.getContext())
                .load(path)
                .asBitmap()
                .skipMemoryCache(true)
                .error(R.mipmap.ic_mrlg)
                .placeholder(R.mipmap.ic_mrlg)
                .transform(transformation)
                .into(imageView);
    }



    public static void displayRound2(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }
        if (!Util.isOnMainThread()) {
            return;
        }
        Glide
                .with(App.getContext())
                .load(path)
                .error(R.mipmap.ic_mrtx)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideRoundTransformUtil(context))
                .into(imageView);
    }

/*    public static void displayBorderRound2(@NotNull final Context context, float borderWidth, int color, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }

        displayBorderImage(App.getContext(), borderWidth, color, imageView, path);
    }

    public static void displayBorderRound2ForPath(@NotNull final Context context, float borderWidth, int color, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        displayBorderImage(App.getContext(), borderWidth, color, imageView, file);
    }

    public static void displayBorderRound(@NotNull final Context context, float borderWidth, int color, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }

        displayBorderImage(imageView.getContext(), borderWidth, color, imageView, path);
    }*/

/*    private static void displayBorderImage(@NotNull Context context, float borderWidth, int color, @NotNull ImageView imageView, @NotNull String path) {
        Glide.with(context)
                .load(path)
                .error(R.mipmap.ic_mrtx)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideBorderTransform(context, DisplayUtil.dip2px(borderWidth), UIUtils.getColor(color)))
                .into(imageView);
    }

    private static void displayBorderImage(@NotNull Context context, float borderWidth, int color, @NotNull ImageView imageView, @NotNull File path) {
        Glide.with(context)
                .load(path)
                .error(R.mipmap.ic_mrtx)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideBorderTransform(context, DisplayUtil.dip2px(borderWidth), UIUtils.getColor(color)))
                .into(imageView);
    }*/

/*    public static void displayGroupRound(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String path) {

        if (imageView == null) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.ic_mrqz)
                .centerCrop()
                .transform(new GlideRoundTransformUtil(context))
                .into(imageView);
    }*/


    public static void displayRound(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull @IntegerRes Integer resouce) {

        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(resouce)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.ic_mrtx)
                .centerCrop()
                .transform(new GlideRoundTransformUtil(context))
                .into(imageView);
    }

    public static void displayRadius(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl) {

        if (imageView == null) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CornersTransform(context))
                .into(imageView);


    }

    public static void displayRadiusForPath(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl) {
        File file = new File(imgUrl);
        if (!file.exists()) {
            return;
        }
        Glide.with(imageView.getContext()).load(file)//
                .placeholder(R.mipmap.ic_mrlg)//
                .error(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//
                .into(imageView);
    }

    public static void displayRadius(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull Uri imgUrl) {

        if (imageView == null) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CornersTransform(context))
                .into(imageView);
    }


    public static void displayRadiusAndRes(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl, int res) {
        if (imageView == null) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CornersTransform(context))
                .error(res)
                .into(imageView);
    }

    public static void displayRadius(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl, final int radius) {
        if (imageView == null) {
            return;
        }
        Glide
                .with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CornersTransform(context, radius))
                .error(R.mipmap.ic_mrlg)
                .into(imageView);


    }


/*    public static void displayBlur(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(imgUrl).centerCrop()
                .placeholder(R.mipmap.ic_mrlg)//
                .error(R.mipmap.ic_mrlg)//
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new BlurTransformation(context))
                .error(R.color.colorPrimaryRed)
                .into(imageView);
    }*/

    public static void displaywhi(@NotNull final Context context, @NotNull final ImageView imageView, @NotNull String imgUrl) {
        if (imageView == null) {
            return;
        }
        //Glide.with(context).load(imgUrl).placeholder(Consts.USER_AVATAR_PLACEHOLDER).transform(new GlideCircleTransform(context,2,context.getResources().getColor(R.color.white))).into(imageView);

        Glide.with(context).load(imgUrl).centerCrop().placeholder(R.mipmap.ic_mrtx)
                .transform(new GlideCircleTransform(context, 2, context.getResources().getColor(R.color.white)))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */


        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);

        //Picasso 加载图片简单用法
//        Picasso.with(context).load(path).into(imageView);

        //用fresco加载图片简单用法，记得要写下面的createImageView方法
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }

    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        //float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = (int) ((float)vw/(float) 1.78);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);
    }


}
