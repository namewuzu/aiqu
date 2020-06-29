package com.diankong.sexstory.mobile.modle.modelview;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.GroupTagsPojo;
import com.diankong.sexstory.mobile.bean.OSSPojo;
import com.diankong.sexstory.mobile.databinding.ActivityReleaseMessageBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.adapter.GridViewAdapter;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.OssUtils;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.utils.Utils;
import com.diankong.sexstory.mobile.widget.tagcloud.TagBaseAdapter;
import com.diankong.sexstory.mobile.widget.tagcloud.TagCloudLayout;
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
 * 作者：Created by 胡清 on 2019/12/7.
 * 描述：
 * =============================================
 */

public class ReleaseMessageViewModle extends BaseViewModle<ActivityReleaseMessageBinding> {

    public List<MediaEntity> add_lists = new ArrayList<>();
    private GridViewAdapter mGridViewAddImgAdapter;
    private OssUtils ossService;
    private List<String> imgs;
    private TagBaseAdapter tagBaseAdapter;
    private int hotId;
    private int type;
    private String title;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        type = act.getIntent().getIntExtra("TYPE", 0);
        hotId = act.getIntent().getIntExtra("HOTID", 0);
        title = act.getIntent().getStringExtra("TITLE");
        if (type == 2) {
            b.tvHuati.setVisibility(View.GONE);
            b.tcl.setVisibility(View.GONE);
            b.tvRemen.setText("#" + title + "#");
        }


        getHostList();

        b.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (TextUtils.isEmpty(b.etContent.getText().toString())) {
                    ToastUtils.showShort("请说点什么吧...");
                    return;
                }

                if (add_lists == null || add_lists.size() <= 0) {
                    ToastUtils.showShort("上传图片才能发动态哦！");
                    return;
                }

                commit();

            }
        });


        mGridViewAddImgAdapter = new GridViewAdapter(act, add_lists);
        b.gridView.setAdapter(mGridViewAddImgAdapter);

        b.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//
                Phoenix.with()
                        .theme(PhoenixOption.THEME_DEFAULT)// 主题
                        .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                        .maxPickNumber(9)// 最大选择数量
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


    }

    private void getHostList() {
        EasyHttp.get(Api.apiurl2 + "topic/getAllTopics")
                .execute(new CallBackProxy<BaseResult<List<GroupTagsPojo>>, List<GroupTagsPojo>>(new SimpleCallBack<List<GroupTagsPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<GroupTagsPojo> _groupTagsPojos) {
                        tagBaseAdapter = new TagBaseAdapter(act, _groupTagsPojos,1);

                        b.tcl.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
                            @Override
                            public void itemClick(int position) {

                                GroupTagsPojo pojo = _groupTagsPojos.get(position);
                                if (pojo.getSelect() == 0) {
                                    for (GroupTagsPojo po : _groupTagsPojos) {
                                        po.setSelect(0);
                                    }
                                    pojo.setSelect(1);
                                } else {
                                    for (GroupTagsPojo po : _groupTagsPojos) {
                                        po.setSelect(0);
                                    }
                                    pojo.setSelect(0);
                                }

                                if (pojo.getSelect() == 1) {
                                    b.tvRemen.setText("#" + pojo.topicTitle + "#");
                                    hotId = pojo.id;
                                } else {
                                    b.tvRemen.setText("");
                                }
                            }
                        });
                        b.tcl.setAdapter(tagBaseAdapter);
                    }

                }) {
                });
    }



    private void commit() {

        if (Utils.isFastClick()) {

            b.avi.show();
        //初始化OssService类，参数分别是Content，accessKeyId，accessKeySecret，endpoint，bucketName（后4个参数是您自己阿里云Oss中参数）
        ossService = new OssUtils(App.getInstance(), SpUtlis.getAccessKey(), SpUtlis.getSecret(), "http://oss-cn-shenzhen.aliyuncs.com", "lvideoapk");
//初始化OSSClient
        ossService.initOSSClient();
        imgs = new ArrayList<>();
//开始上传，参数分别为content，上传的文件名filename，上传的文件路径filePath
        for (int i = 0; i < add_lists.size(); i++) {
            ossService.beginupload("dsa" + System.currentTimeMillis(), add_lists.get(i).getLocalPath());
            imgs.add(ossService.getImg());
        }

//        for (String img: imgs) {
//            L.d("图片数组++++++++++"  + img);
//        }

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < imgs.size(); i++) {
            stringBuffer.append(imgs.get(i).toString().trim() + ",");
        }
        String imageUrl = stringBuffer.substring(0, stringBuffer.length() - 1).toString();

        L.d("图片数组++++++++++" + imageUrl);

        EasyHttp.get(Api.apiurl2 + "community/uploadCommunity")
                .params("descs", b.etContent.getText().toString())
                .params("avatar", SpUtlis.getAvatar())
                .params("userNickname", SpUtlis.getNickName())
                .params("hotId", String.valueOf(hotId))
                .params("imageUrl", imageUrl)
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<OSSPojo>, OSSPojo>(new SimpleCallBack<OSSPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                        b.avi.hide();
                    }

                    @Override
                    public void onSuccess(final OSSPojo _ossPojo) {
                        ToastUtils.showShort("上传成功，请等待审核");
                        b.avi.hide();
                        act.finish();
                    }

                }) {
                });

        }else{
            ToastUtils.showShort("请勿重复点击");
        }

    }

    @Override
    public void initNet() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {//相册
            //返回的数据
            List<MediaEntity> result = Phoenix.result(data);
            L.d("=====result==" + result.toString());
            add_lists.clear();
            add_lists.addAll(result);
//            adapter.updateItems(add_lists);
//            btnOkEnable(add_lists);

            mGridViewAddImgAdapter.notifyDataSetChanged();


        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
