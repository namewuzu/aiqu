package com.diankong.sexstory.mobile.modle.modelview;

import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.OSSPojo;
import com.diankong.sexstory.mobile.databinding.ActivityRenzhengBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.OssUtils;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/30.
 * 描述：
 * =============================================
 */

public class RenzhengViewModle extends BaseViewModle<ActivityRenzhengBinding> {

    public List<MediaEntity> add_lists = new ArrayList<>();
    private OssUtils ossService;
    private List<String> imgs;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
        b.zipai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Phoenix.with()
                        .theme(PhoenixOption.THEME_DEFAULT)// 主题
                        .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                        .maxPickNumber(1)// 最大选择数量
                        .minPickNumber(0)// 最小选择数量
                        .spanCount(4)// 每行显示个数
                        .enablePreview(true)// 是否开启预览
                        .enableCamera(false)// 是否开启拍照
                        .enableAnimation(false)// 选择界面图片点击效果
                        .enableCompress(true)// 是否开启压缩
                        .compressPictureFilterSize(300)//多少kb以下的图片不压缩
                        .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
                        .thumbnailHeight(160)// 选择界面图片高度
                        .thumbnailWidth(160)// 选择界面图片宽度
                        .enableClickSound(false)// 是否开启点击声音
                        .pickedMediaList(add_lists)// 已选图片数据
                        .videoFilterTime(0)//显示多少秒以内的视频
                        .mediaFilterSize(0)//显示多少kb以下的图片/视频，默认为0，表示不限制
                        .start(act, 1, 1);
            }
        });


        b.btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(add_lists==null||add_lists.size()<=0){
                    ToastUtils.showShort("请上传您的认证照片");
                    return;
                }

                commit();
            }
        });
    }

    private void commit() {




        ossService = new OssUtils(App.getInstance(), SpUtlis.getAccessKey(), SpUtlis.getSecret(), "http://oss-cn-shenzhen.aliyuncs.com", "lvideoapk");
//初始化OSSClient
        ossService.initOSSClient();
        imgs = new ArrayList<>();
//开始上传，参数分别为content，上传的文件名filename，上传的文件路径filePath
        for (int i = 0; i < add_lists.size(); i++) {
            ossService.beginupload("dsa" + System.currentTimeMillis(), add_lists.get(i).getLocalPath());
            imgs.add(ossService.getImg());
        }



        EasyHttp.get(Api.apiurl + "user/uploadChatInfo")
                .params("userImage", imgs.get(0))
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<OSSPojo>, OSSPojo>(new SimpleCallBack<OSSPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final OSSPojo _ossPojo) {
                        ToastUtils.showShort("上传成功，请等待审核");
                        act.finish();
                    }

                }) {
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {//相册
            //返回的数据
            List<MediaEntity> result = Phoenix.result(data);
            L.d("=====result==" + result.toString());
            add_lists.clear();
            add_lists.addAll(result);

            Glide.with(act).load(result.get(0).getLocalPath()).into(b.ivPhoto);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initNet() {

    }
}
