package com.diankong.sexstory.mobile.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.diankong.sexstory.mobile.bean.UploadImgPojo;
import com.diankong.sexstory.mobile.http.Api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Created by apanda on 2017/1/19.
 */

public class ViewUtils {

    /**
     * 保存View为图片的方法
     */
    public static boolean saveBitmap(View v, String name) {


        String fileName = name + ".png";

        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);
        String TAG = "TIKTOK";
        L.e(TAG + "保存图片");
        File f = new File(Environment.getExternalStorageDirectory() + "/ivedio/qr_img", fileName);
        if (f.exists()) {
            f.delete();
        } else {
            FileUtils.createFileByDeleteOldFile(f);
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            L.i(TAG + "已经保存");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static File saveBitmapV2(View v, String name) {


        String fileName = name + ".png";
        File f = new File(Environment.getExternalStorageDirectory() + "/coo/qr_img", fileName);
        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);
        String TAG = "TIKTOK";
        L.e(TAG + "保存图片");
        if (f.exists()) {
            f.delete();
        } else {
            FileUtils.createFileByDeleteOldFile(f);
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            L.i(TAG + "已经保存");
            return f;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static Bitmap getBitmapFromView(View v) {
        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);
        return bm;
    }


    /**
     * 保存文件
     *
     * @param bm
     * @throws IOException
     */
    public static String saveBitmap2File(Bitmap bm) {
        String path = Api.COO_IMG_PATH;
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }

        File myCaptureFile = new File(path + System.currentTimeMillis());
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        } catch (FileNotFoundException _e) {
            _e.printStackTrace();
        } finally {
            try {
                bos.flush();
            } catch (IOException _e) {
                _e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException _e) {
                _e.printStackTrace();
            }
            return myCaptureFile.getAbsolutePath();
        }

    }


    /*
   * 从网络上获取图片，如果图片在本地存在的话就直接拿，如果不存在再去服务器上下载图片
   * 这里的path是图片的地址
   */
    public static Uri getImageURI(String localPath, String path) throws Exception {
        File file = new File(localPath);
        // 如果图片存在本地缓存目录，则不去服务器下载
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
        } else {
            // 从网络上获取图片
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
                // 返回一个URI对象
                return Uri.fromFile(file);
            }
        }
        return null;
    }


    public static String getVideoThumbnail(ContentResolver cr, String path) {
//        Bitmap bitmap = null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inDither = false;
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Cursor cursor = cr.query(uri, new String[]{MediaStore.Video.Media._ID}, null, null, null);
//
//        if (cursor == null || cursor.getCount() == 0) {
//            return null;
//        }
//        cursor.moveToFirst();
//        String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));  //image id in image table.s
//
//        if (videoId == null) {
//            return null;
//        }
//        cursor.close();
//        long videoIdLong = Long.parseLong(videoId);
//        bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoIdLong, MediaStore.Images.Thumbnails.MICRO_KIND, options);

        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path,
                MediaStore.Images.Thumbnails.MINI_KIND);


        return saveBitmap2File(bitmap);
    }


    //判断视频时长是否符合
    public static int judgeVideoTime(Context cxt, String vediopath) {

        int seconds = 0;
//        String[] projection = new String[]{MediaStore.Video.Media.DURATION};
//        Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//                , projection, MediaStore.Video.Media.DATA + "='" + vediopath + "'", null, null);
//        if (cursor != null) {
//            if (cursor.getCount() == 1) {
//                cursor.moveToFirst();
//                long duration = 0;
//                if (cursor.moveToFirst()) {
//                    do {
//                        duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
//                    } while (cursor.moveToNext());
//                }
//                seconds = (int) duration / 1000;
//            }
//
//        }
//        cursor.close();


        MediaPlayer mp = MediaPlayer.create(cxt, Uri.parse(vediopath));
        seconds = mp.getDuration();
        mp.release();

        return seconds;

    }

    /**
     * 获取图片宽度
     *
     * @param file 图片文件
     * @return 宽度
     */
    public static UploadImgPojo getImgWidthHeight(String file) {
        UploadImgPojo ret = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(file);
            ret = new UploadImgPojo(file, bmp.getHeight(), bmp.getWidth());
        } catch (Exception _e) {
            _e.printStackTrace();
        }
        return ret;
    }


    /**
     * 获取图片宽度
     *
     * @param file 图片文件
     * @return 宽度
     */
    public static void compressAndUpload(List<String> file, UploadImgPojo.UploadImagePojoLisenter listener) {
        for (String s : file) {
           // compressAndUpload(s, listener);
        }
    }

/*
    public static void compressAndUpload(final String filePath, final UploadImgPojo.UploadImagePojoLisenter listener) {

        File file = new File(filePath);
        final String fileName = UploadImage.getKey(filePath, "Bussiness");

        //     C.cp(file)
        Luban.get(App.getContext())
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                    @Override
                    public Observable<? extends File> call(Throwable throwable) {
                        return Observable.empty();
                    }
                })
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(File file) {
                        UploadImage.uploadImageToQiniu(file, fileName, new QNImageUpLoadListener() {
                            @Override
                            public void onSuccess(String fileName) {
                                super.onSuccess(fileName);
                                if (listener != null) {
                                    listener.getUploadImagePojo(getImgWidthHeight(filePath));
                                }
                            }

                            @Override
                            public void onError() {
                                super.onError();
                            }
                        });
                    }
                });
    }*/

    public static int[] getLoaction(View _originView) {
        int[] location = new int[2];
        location[0] = (int) _originView.getPivotX();
        location[1] = (int) _originView.getPivotY();


        return location;
    }
}
