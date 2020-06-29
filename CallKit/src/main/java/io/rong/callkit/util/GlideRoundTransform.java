package io.rong.callkit.util;

/**
 * Created by Ethan on 2018/6/1.
 */

//public class GlideRoundTransform extends BitmapTransformation {
//
//    private static float radius = 8f;
//
//    public GlideRoundTransform() {
//        this(8);
//    }
//
//    public GlideRoundTransform(int dp) {
//        super();
//        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
//    }
//
//    @Override
//    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//        return roundCrop(pool, toTransform);
//    }
//
//    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
//        if (source == null) return null;
//
//        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        if (result == null) {
//            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        }
//
//        Canvas canvas = new Canvas(result);
//        Paint paint = new Paint();
//        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//        paint.setAntiAlias(true);
//        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
//        canvas.drawRoundRect(rectF, radius, radius, paint);
//        return result;
//    }
//
//    @Override
//    public void updateDiskCacheKey(MessageDigest messageDigest) {
//    }
//}
