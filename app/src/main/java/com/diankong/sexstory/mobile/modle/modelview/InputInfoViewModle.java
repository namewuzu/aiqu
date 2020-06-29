package com.diankong.sexstory.mobile.modle.modelview;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.GroupTagsPojo;
import com.diankong.sexstory.mobile.bean.OSSPojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityInputInfoBinding;
import com.diankong.sexstory.mobile.event.ChatStartEvent;
import com.diankong.sexstory.mobile.event.NickNameEvent;
import com.diankong.sexstory.mobile.event.QianMingEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.NickNameActivity;
import com.diankong.sexstory.mobile.modle.activity.QianMingActivity;
import com.diankong.sexstory.mobile.modle.adapter.GridViewAdapter;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.OssUtils;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.widget.tagcloud.TagBaseAdapter;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/23.
 * 描述：
 * =============================================
 */

public class InputInfoViewModle extends BaseViewModle<ActivityInputInfoBinding> {
    public List<MediaEntity> add_lists = new ArrayList<>();
    TimePickerView pvTime;
    OptionsPickerView pvCustomOptions;
    private GridViewAdapter mGridViewAddImgAdapter;
    private OssUtils ossService;
    private List<String> imgs;
    private List<GroupTagsPojo> pojos1;
    private TagBaseAdapter tagBaseAdapter;
    private String tag;
    private String imageUrl;
    private String sign;
    private String brithDay;
    private String position;
    private String age;
    private String hobby;
    private String monthlyIncome;
    private String wxCity;
    private String nickName = "";
    private int sex;
    private String[] split;
    private List<String> stringList;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
        EventBus.getDefault().register(this);
        getUserInfos();
        GlideImageLoader.displayRound(App.getInstance(),b.ivAvatar,SpUtlis.getAvatar());
        b.tvNickName.setText(SpUtlis.getNickName());



        b.rlTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                initTodo();
            }
        });

        b.rlTodo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                initTodo3();
            }
        });

        b.rlNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(NickNameActivity.class);
            }
        });

        b.rlQianming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(QianMingActivity.class);
            }
        });

        b.rlChatstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
//                startActivity(ChatStartActivity.class);
                initTodo2();
            }
        });

        b.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
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
                        .maxPickNumber(8)// 最大选择数量
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

    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onDestory() {
        super.onDestory();
        EventBus.getDefault().unregister(this);
    }

    private void commit() {
//初始化OssService类，参数分别是Content，accessKeyId，accessKeySecret，endpoint，bucketName（后4个参数是您自己阿里云Oss中参数）

        if(add_lists!=null&&add_lists.size()>0){
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
            imageUrl = stringBuffer.substring(0, stringBuffer.length() - 1).toString();

            L.d("图片" + imageUrl);



        }else{
            imageUrl = "";
        }

//        if(pojos1!=null&&pojos1.size()>0){
//            StringBuffer stringBuffer2 = new StringBuffer();
//            for (int i = 0; i < pojos1.size(); i++) {
//                stringBuffer2.append(pojos1.get(i).getTagname() + ",");
//            }
//            tag = stringBuffer2.substring(0, stringBuffer2.length() - 1).toString();
//
//            L.d("标签" + tag);
//        }else{
//            tag  ="";
//        }


        if(b.tvQianming.getText().toString().equals("未填写")){
            sign = "";
        }else{
            sign = b.tvQianming.getText().toString();
        }


        if(b.tvTodo2.getText().toString().equals("请选择")){
            position = "";
        }else{
            position = b.tvTodo2.getText().toString();
        }

        if(b.tvTodo4.getText().toString().equals("请选择")){
            monthlyIncome = "";
        }else{
            monthlyIncome = b.tvTodo4.getText().toString();
        }

        if(TextUtils.isEmpty(b.etAge.getText().toString())){
            age = "";
        }else{
            age = b.etAge.getText().toString();
        }

        if(TextUtils.isEmpty(b.etHobby.getText().toString())){
            hobby = "";
        }else{
            hobby = b.etHobby.getText().toString();
        }

        if(b.tvTodo6.getText().toString().equals("男")){
            sex = 1;
        }else{
            sex = 2;
        }


        if(TextUtils.isEmpty(b.etCity.getText().toString())){
            wxCity = "";
        }else{
            wxCity = b.etCity.getText().toString();
        }



        EasyHttp.get(Api.apiurl + "user/updateUserInfo")
                .params("sign", sign)
//                .params("charterCoin", b.etCoin.getText().toString())
                .params("age",String.valueOf(age))
                .params("nickName",String.valueOf(nickName))
                .params("hobby",String.valueOf(hobby))
                .params("sex",String.valueOf(sex))
                .params("position", position)
                .params("monthlyIncome", monthlyIncome)
                .params("wxCity", wxCity)
                .params("photos", imageUrl)
                .params("id", String.valueOf(SpUtlis.getId()))
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

    private void initTime() {

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2029, 11, 28);

        //时间选择器
        pvTime = new TimePickerBuilder(act, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                b.tvAge.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
        pvTime.show();
    }

    private void initTodo2() {

        final List<String> cardItem = new ArrayList<>();
        cardItem.add("0-3000");
        cardItem.add("3000-5000");
        cardItem.add("5000-8000");
        cardItem.add("8000-10000");
        cardItem.add("10000以上");

        pvCustomOptions = new OptionsPickerBuilder(act, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                ToastUtils.showShort(cardItem.get(options1));
                b.tvTodo3.setVisibility(View.GONE);
                b.tvTodo4.setVisibility(View.VISIBLE);
                b.tvTodo4.setText(cardItem.get(options1));
            }
        }).build();
        pvCustomOptions.setPicker(cardItem);
        pvCustomOptions.show();
    }

    private void initTodo3() {

        final List<String> cardItem = new ArrayList<>();
        cardItem.add("男");
        cardItem.add("女");
        cardItem.add("未知");

        pvCustomOptions = new OptionsPickerBuilder(act, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                ToastUtils.showShort(cardItem.get(options1));
                b.tvTodo5.setVisibility(View.GONE);
                b.tvTodo6.setVisibility(View.VISIBLE);
                b.tvTodo6.setText(cardItem.get(options1));
            }
        }).build();
        pvCustomOptions.setPicker(cardItem);
        pvCustomOptions.show();
    }

    private void initTodo() {

        final List<String> cardItem = new ArrayList<>();
        cardItem.add("白领");
        cardItem.add("飞行员");
        cardItem.add("工程师");
        cardItem.add("公务员");
        cardItem.add("护士");
        cardItem.add("教师");
        cardItem.add("警察");
        cardItem.add("军官");
        cardItem.add("会计");
        cardItem.add("律师");
        cardItem.add("模特");
        cardItem.add("企业高管");
        cardItem.add("私人老板");
        cardItem.add("设计师");
        cardItem.add("消防人员");
        cardItem.add("行政");
        cardItem.add("学生");
        cardItem.add("演员");
        cardItem.add("医生");
        cardItem.add("自由职业");

        pvCustomOptions = new OptionsPickerBuilder(act, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                ToastUtils.showShort(cardItem.get(options1));
                b.tvTodo.setVisibility(View.GONE);
                b.tvTodo2.setVisibility(View.VISIBLE);
                b.tvTodo2.setText(cardItem.get(options1));
            }
        }).build();
        pvCustomOptions.setPicker(cardItem);
        pvCustomOptions.show();
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
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
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void NickNameEvent(NickNameEvent event) {
        nickName = event.nickname;
        b.tvNickName.setText(event.nickname);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void QianMingEvent(QianMingEvent event) {
        b.tvQianming.setText(event.content);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ChatStartEvent(ChatStartEvent event) {
//        b.tvQianming.setText(event.);
//        b.tvLiaotian.setText("");

        pojos1 = event._pojos1;
        if (pojos1 != null && event._pojos1.size() > 0) {
            tagBaseAdapter = new TagBaseAdapter(act, event._pojos1, 2);
            tagBaseAdapter.notifyDataSetChanged();
//            b.tcl.setAdapter(tagBaseAdapter);
        }


    }


    private void getUserInfos() {
        EasyHttp.get(Api.apiurl2 + "community/getUserInfoAndCommunitys")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("userIdSelf", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo)  {

                        if(!TextUtils.isEmpty(mTestPojo.nickName)){
                            b.tvNickName.setText(mTestPojo.nickName);
                        }

                        if(!TextUtils.isEmpty(mTestPojo.sign)){
                            b.tvQianming.setText(mTestPojo.sign);
                        }
                        if(mTestPojo.age>0){
                            b.etAge.setText(String.valueOf(mTestPojo.age));
                        }
                        if(!TextUtils.isEmpty(mTestPojo.hobby)){
                            b.etHobby.setText(mTestPojo.hobby);
                        }

                        if(mTestPojo.sex==1){
                            b.tvTodo6.setText("男");
                            b.tvTodo6.setVisibility(View.VISIBLE);
                            b.tvTodo5.setVisibility(View.GONE);
                        }else{
                            b.tvTodo6.setText("女");
                            b.tvTodo6.setVisibility(View.VISIBLE);
                            b.tvTodo5.setVisibility(View.GONE);
                        }

                        if(!TextUtils.isEmpty(mTestPojo.position)){
                            b.tvTodo2.setText(mTestPojo.position);
                            b.tvTodo2.setVisibility(View.VISIBLE);
                            b.tvTodo.setVisibility(View.GONE);
                        }


                        if(!TextUtils.isEmpty(mTestPojo.monthlyIncome)){
                            b.tvTodo4.setText(mTestPojo.monthlyIncome);
                            b.tvTodo4.setVisibility(View.VISIBLE);
                            b.tvTodo3.setVisibility(View.GONE);
                        }

                        if(!TextUtils.isEmpty(mTestPojo.wxCity)){
                            b.etCity.setText(mTestPojo.wxCity);
                        }

//                        if(!TextUtils.isEmpty(mTestPojo.phone)){
//                            split = mTestPojo.photo.split(",");
//
//                            stringList = new ArrayList<>();
//                            for (int j = 0; j < split.length; j++) {
//                                stringList.add(split[j]);
//                                add_lists.add(new MediaEntity(split[j],1,1,""));
//                            }
//                        }
//
//                        mGridViewAddImgAdapter = new GridViewAdapter(act, add_lists);
//                        b.gridView.setAdapter(mGridViewAddImgAdapter);

                    }

                }) {
                });

    }


}
