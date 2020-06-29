/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diankong.sexstory.mobile.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class ImageUtils {


    /**
     * 压缩图片
     */

    private static BitmapFactory.Options options = new BitmapFactory.Options();

    public static File zoomToFix(String filePath, int h) {
        File file = new File(filePath);
        try {

            options.inJustDecodeBounds = true; // 先设置预读图片，既只读取大小信息 
            // =
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
            options.inJustDecodeBounds = false; // 读出大小信息后改为非预读
            int be = options.outWidth / h;
            // 计算缩放倍数
            if (be <= 0)
                be = 1;
            options.inSampleSize = be; // 设置缩放，可惜只能是整数

            bitmap = BitmapFactory.decodeFile(filePath, options); // 根据缩放比实际读取图片

            FileOutputStream fos = new FileOutputStream(file); // 保存图片文件
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
        } catch (FileNotFoundException e) {
            Log.e("ImageUtils", e.getMessage(), e);

        }
        return file;
    }

    /**
     * 多线程压缩图片的质量
     *
     * @author JPH
     * @param bitmap
     *            内存中的图片
     * @param imgPath
     *            图片的保存路径
     * @date 2014-12-5下午11:30:43
     */
    public static String compressImageByQuality(final Bitmap bitmap,
                                                final String imgPath) {

        // TODO Auto-generated method stub
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 质量压缩方法，把压缩后的数据存放到baos中
        // (100表示不压缩，0表示压缩到最小)
        while (baos.toByteArray().length / 1024 > 100) {// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即让下一次的写入覆盖之前的内容
            options -= 10;// 图片质量每次减少10
            if (options < 0)
                options = 0;// 如果图片质量小于10，则将图片的质量压缩到最小值
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 将压缩后的图片保存到baos中
            if (options == 0)
                break;// 如果图片的质量已降到最低则，不再进行压缩
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File(imgPath));// 将压缩后的图片保存的本地上指定路径中
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgPath;

    }

    /**
     * 按比例缩小图片的像素以达到压缩的目的
     *
     * @author JPH
     * @param imgPath
     * @date 2014-12-5下午11:30:59
     */
    public static String compressImageByPixel(String imgPath) {
        long timeStamp = System.currentTimeMillis();
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int width = newOpts.outWidth;
        int height = newOpts.outHeight;
        float maxSize = 1000f;// 默认1000px
        int be = 1;
        if (width > height && width > maxSize) {// 缩放比,用高或者宽其中较大的一个数据进行计算
            be = (int) (newOpts.outWidth / maxSize);
        } else if (width < height && height > maxSize) {
            be = (int) (newOpts.outHeight / maxSize);
        }
        be++;
        newOpts.inSampleSize = be;// 设置采样率
        newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        String path = JFileHelper.getSDCardPath() + "/" + String.valueOf(timeStamp) + "temp.jpg";
        return compressImageByQuality(bitmap, path);// 压缩好比例大小后再进行质量压缩
    }


    /**
     * 根据当前的状态来旋转箭头。
     */
    @SuppressWarnings("all")
    public static void rotateArrow(ImageView arrow, boolean flag) {
        float pivotX = arrow.getWidth() / 2f;
        float pivotY = arrow.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        // flag为true则向上
        if (flag) {
            fromDegrees = 180f;
            toDegrees = 360f;
        } else {
            fromDegrees = 0f;
            toDegrees = 180f;
        }
        //旋转动画效果   参数值 旋转的开始角度  旋转的结束角度  pivotX x轴伸缩值
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees,
                pivotX, pivotY);
        //该方法用于设置动画的持续时间，以毫秒为单位
        animation.setDuration(50);
        //设置重复次数
        //animation.setRepeatCount(int repeatCount);
        //动画终止时停留在最后一帧
        animation.setFillAfter(true);
        //启动动画
        arrow.startAnimation(animation);
    }


}
