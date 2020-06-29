package com.diankong.sexstory.mobile.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.diankong.sexstory.mobile.bean.lisener.ImagePickerSingleLisenter;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.diankong.sexstory.mobile.utils.FileUtil.getCacheFile;
import static com.diankong.sexstory.mobile.utils.FileUtil.getDiskCacheDir;


/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class PictureUtils {
    public static final int REQUEST_CAMERA = 0x02;
    public static final int CROP_PHOTO = 0x03;
    private static Uri uri;
    private static File cameraFile;
    private static String cachPath;
    private static File cacheFile;

    public static void pickFromCamera(final AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT < 23) {
            openCamera(activity);
            L.d("OK");
        } else {
            PermissionsUtil.requestPermission(activity, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            openCamera(activity);
                            L.d("OK--------------------");
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            L.d("notOK");
                        }
                    },
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    public static void handleResult(Activity a, ImagePickerSingleLisenter cropPickListeners, int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CAMERA) {
            startPhotoZoom(a, cameraFile, 500);
        } else if (requestCode == CROP_PHOTO) {
            try {
                if (resultCode == RESULT_OK) {
                    cropPickListeners.getSingleImage(cachPath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private static Uri getCameraCacheUri() {
        File picture = FileUtil.getAppPictureDir("Picture");
        String name = String.format("imagecrop%d.jpg", System.currentTimeMillis());
        Uri uri = Uri
                .fromFile(picture)
                .buildUpon()
                .appendPath(name)
                .build();

        String filePath = uri.toString();
        int first = filePath.indexOf("/");
        int second = filePath.indexOf("/", first + 1);
        second = second + first;
        String replace = second == filePath.length() - 1 ? filePath.substring(0, second) : filePath.substring(0, second) + filePath.substring(second + 1, filePath.length());
        return uri;
    }

    public static void startActionCapture(Activity activity, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity));
        activity.startActivityForResult(intent, requestCode);
    }

    public static File getCameraFile() {
        return cameraFile;
    }

    private static void openCamera(Activity activity) {
       /* if (cameraFile != null && cameraFile.exists()) {
            cameraFile.delete();
        }
        if (cacheFile != null && cacheFile.exists()) {
            cacheFile.delete();
        }*/
        cachPath = getDiskCacheDir() + "/crop_image.jpg";
        cacheFile = getCacheFile(new File(getDiskCacheDir()), "crop_image.jpg");
        cameraFile = getCacheFile(new File(getDiskCacheDir()), "avatar" + System.currentTimeMillis() + ".jpg");
       // SPController.AuthInfo.saveCameraFilePath(cameraFile.getAbsolutePath());
        try {
            cameraFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(cameraFile);
        } else {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(activity, "cn.natrip.android.civilizedcommunity.fileprovider", cameraFile);
        }
        // 启动相机程序
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * 剪裁图片
     */
    private static void startPhotoZoom(Activity activity, File file, int size) {
        Log.i("TAG", getImageContentUri(activity, file) + "裁剪照片的真实地址");
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(getImageContentUri(activity, file), "image/*");//自己使用Content Uri替换File Uri
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 600);
            intent.putExtra("outputY", 600);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cacheFile));//定义输出的File Uri
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            activity.startActivityForResult(intent, CROP_PHOTO);
        } catch (ActivityNotFoundException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Uri getImageContentUri(Context context, File imageFile) {

        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    public static Uri getUriForFile(Activity context) {
        if (context == null) {
            throw new NullPointerException();
        }

        File picture = FileUtil.getAppPictureDir("picture");
        String name = String.format("imagecrop%d.jpg", System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, "cn.natrip.android.civilizedcommunity.fileprovider", picture);
        } else {
            uri = Uri
                    .fromFile(picture)
                    .buildUpon()
                    .appendPath(name)
                    .build();
        }
        return uri;
    }

    public static void savePicRefreshGallery(Activity activity, String filePath) {
        File file = new File(filePath);
        try {
            MediaStore.Images.Media.insertImage(activity.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT > 19) {
            saveImageSendScanner(activity, new MyMediaScannerConnectionClient(filePath));
        } else {

            saveImageSendBroadcast(activity, filePath);
        }
    }

    /**
     * 保存后用广播扫描，Android4.4以下使用这个方法
     *
     * @author YOLANDA
     */
    public static void saveImageSendBroadcast(Activity activity, String filePath) {
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
        //  activity. sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory()+ filePath)));
    }

    /**
     * 保存后用MediaScanner扫描，通用的方法
     */
    public static void saveImageSendScanner(Activity activity, MyMediaScannerConnectionClient scannerClient) {

        final MediaScannerConnection scanner = new MediaScannerConnection(activity, scannerClient);
        scannerClient.setScanner(scanner);
        scanner.connect();
    }

    public static class MyMediaScannerConnectionClient implements MediaScannerConnection.MediaScannerConnectionClient {

        private MediaScannerConnection mScanner;

        private String mScanPath;

        public MyMediaScannerConnectionClient(String scanPath) {
            mScanPath = scanPath;
        }

        public void setScanner(MediaScannerConnection con) {
            mScanner = con;
        }

        @Override
        public void onMediaScannerConnected() {
            mScanner.scanFile(mScanPath, "image/*");
        }

        @Override
        public void onScanCompleted(String path, Uri uri) {
            mScanner.disconnect();
        }
    }

}
