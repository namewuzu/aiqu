package com.diankong.sexstory.mobile.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.lisener.ImgLisenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/7.
 * 描述：
 * =============================================
 */

public class UpLoadImage {
    // 运行sample前需要配置以下字段为有效的值

    private static  String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    private static  String accessKeyId = "LTAI8ReqhvZ6nORT";
    private static  String accessKeySecret = "wLit5avBJyayPYN4lD6l1cOC7wBfzy";
    private static  String uploadFilePath = "<upload_file_path>";

    private static String testBucket = "lvideoapk";

    private static OSSCredentialProvider credentialProvider;

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final UpLoadImage INSTANCE = new UpLoadImage();
    }

    //获取单例
    public static UpLoadImage getInstance() {
        credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        return UpLoadImage.SingletonHolder.INSTANCE;
    }


    public void upLoadImage(final Context context, String path, OSSProgressCallback ossProgressCallback, final ImgLisenter<String> response){
        final ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        if (TextUtils.isEmpty(path)) {
            response.onFail("err");
            return;
        }

        File file = new File(path);
        if (null == file || !file.exists()) {
            response.onFail("err");
            return;
        }



        final String uploadObject = SpUtlis.getDeviceId()+".jpg";
        OSS oss = new OSSClient(App.getInstance(), endpoint, credentialProvider, conf);
        PutObjectRequest put = new PutObjectRequest(testBucket, uploadObject, ImageUtils.compressImageByPixel(path));
        // 异步上传时可以设置进度回调
        if(ossProgressCallback!=null){
            put.setProgressCallback(ossProgressCallback);
        }
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        response.onSucceed(uploadObject);
                    }
                });

            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (clientExcepion != null) {
                    clientExcepion.printStackTrace();
                    response.onFail("err");
                }
                if (serviceException != null) {
                    response.onFail("err");
                }
            }
        });



    }


    private void upLoadImage(final Context context, final List<String> urls, final  List<String> newPaths, final ImgLisenter<List<String>> response) {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        if (urls.size() <= 0) {
            // 文件全部上传完毕，这里编写上传结束的逻辑，如果要在主线程操作，最好用Handler或runOnUiThead做对应逻辑
            response.onSucceed(newPaths);
            return;// 这个return必须有，否则下面报越界异常，原因自己思考下哈
        }
        final String url = urls.get(0);
        if (TextUtils.isEmpty(url)) {
            urls.remove(0);
            // url为空就没必要上传了，这里做的是跳过它继续上传的逻辑。
            upLoadImage(context,urls,newPaths,response);
            return;
        }

        File file = new File(url);
        if (null == file || !file.exists()) {
            urls.remove(0);
            // 文件为空或不存在就没必要上传了，这里做的是跳过它继续上传的逻辑。
            upLoadImage(context,urls,newPaths,response);
            return;
        }

        final String uploadObject = SpUtlis.getDeviceId()+".jpg";
        OSS oss = new OSSClient(App.getInstance(), endpoint, credentialProvider, conf);
        PutObjectRequest put = new PutObjectRequest(testBucket, uploadObject,  ImageUtils.compressImageByPixel(url));

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                newPaths.add(uploadObject);
                urls.remove(0);
                // 文件为空或不存在就没必要上传了，这里做的是跳过它继续上传的逻辑。
                upLoadImage(context,urls,newPaths,response);


                //response.onSucceed(uploadObject);
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (clientExcepion != null) {
                    clientExcepion.printStackTrace();
                    response.onFail("err");
                }
                if (serviceException != null) {
                    response.onFail("err");
                }



            }
        });

    }


    /**
     * 批量上传图片
     * @param context
     * @param paths
     * @param response
     */
    public void upLoadImages(final Context context, List<String> paths, final ImgLisenter<List<String>> response){
        upLoadImage(context,paths,new ArrayList<String>(),response);
    }



}
