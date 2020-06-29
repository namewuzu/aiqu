package com.diankong.sexstory.mobile.modle.modelview;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.GroupTagsPojo;
import com.diankong.sexstory.mobile.bean.MoneyPojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityWithDrawalBinding;
import com.diankong.sexstory.mobile.event.PhoneEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.BindingPhoneActivity;
import com.diankong.sexstory.mobile.modle.adapter.MoneyAdapter;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.PictureUtils;
import com.diankong.sexstory.mobile.utils.Qrcode;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.utils.Utils;
import com.diankong.sexstory.mobile.utils.ViewUtils;
import com.diankong.sexstory.mobile.widget.tagcloud.TagCloudLayout;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/25.
 * 描述：
 * =============================================
 */

public class WithDrawalViewModle extends BaseViewModle<ActivityWithDrawalBinding> {

    private Dialog _dialog;
    private Bitmap _bitmap;
    private String wxopenid;
    private String phone;
    private List<GroupTagsPojo> _tagsPojos;
    private MoneyAdapter tagBaseAdapter;
    private boolean flag;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
        EventBus.getDefault().register(this);
        getData();
        phone = act.getIntent().getStringExtra("PHONE");
        wxopenid = act.getIntent().getStringExtra("WXOPENID");


        _tagsPojos = new ArrayList<>();
        GroupTagsPojo groupTagsPojo = new GroupTagsPojo("2元", 2, 1);
        GroupTagsPojo groupTagsPojo2 = new GroupTagsPojo("10元", 10, 0);
        GroupTagsPojo groupTagsPojo3 = new GroupTagsPojo("20元", 20, 0);
        GroupTagsPojo groupTagsPojo4 = new GroupTagsPojo("30元", 30, 0);
        GroupTagsPojo groupTagsPojo5 = new GroupTagsPojo("50元", 50, 0);
        GroupTagsPojo groupTagsPojo6 = new GroupTagsPojo("100元", 100, 0);
        GroupTagsPojo groupTagsPojo7 = new GroupTagsPojo("200元", 200, 0);
        GroupTagsPojo groupTagsPojo8 = new GroupTagsPojo("500元", 500, 0);

        _tagsPojos.add(groupTagsPojo);
        _tagsPojos.add(groupTagsPojo2);
        _tagsPojos.add(groupTagsPojo3);
        _tagsPojos.add(groupTagsPojo4);
        _tagsPojos.add(groupTagsPojo5);
        _tagsPojos.add(groupTagsPojo6);
        _tagsPojos.add(groupTagsPojo7);
        _tagsPojos.add(groupTagsPojo8);

        tagBaseAdapter = new MoneyAdapter(act, _tagsPojos);
        b.tcl.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                GroupTagsPojo groupTagsPojo1 = _tagsPojos.get(position);
                b.tvMoney.setText(groupTagsPojo1.coin+"元");

                for (GroupTagsPojo pojo : _tagsPojos) {
                    pojo.setSelect(0);
                }
                groupTagsPojo1.setSelect(1);
                tagBaseAdapter.notifyDataSetChanged();

            }
        });
        b.tcl.setAdapter(tagBaseAdapter);

        b.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(TextUtils.isEmpty(phone)){
                    startActivity(BindingPhoneActivity.class);
                }else{
                    if(TextUtils.isEmpty(wxopenid)){
                        dialog();
                    }else{
                        commit();
                    }
                }
            }
        });


    }
    private void commit() {
        String str = b.tvMoney.getText().toString();
        String money = str.substring(0, str.indexOf("元"));

        if(flag) {
            if (Utils.isFastClick()) {
                b.avi.show();
                EasyHttp.post(Api.apiurl + "enterOrder/enterPay")
                        .params("id", String.valueOf(SpUtlis.getId()))
                        .params("balance", money)
                        .execute(new CallBackProxy<BaseResult<MoneyPojo>, MoneyPojo>(new SimpleCallBack<MoneyPojo>() {
                            @Override
                            public void onError(ApiException e) {
                                ToastUtils.showShort(e.getMessage());
                                b.avi.hide();
                            }

                            @Override
                            public void onSuccess(MoneyPojo _moneyPojo) {
                                ToastUtils.showShort("提现成功");
                                b.avi.hide();
                                getData();
                            }


                        }) {
                        });
            }else{
                ToastUtils.showShort("请勿重复点击");
            }

        }else{
            ToastUtils.showShort("余额不足");
        }

    }

    @Override
    public void onDestory() {
        super.onDestory();
        EventBus.getDefault().unregister(this);
    }


    private void getData(){
        EasyHttp.post(Api.apiurl + "user/getUserCoinInfo")
                .params("userId",String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<MoneyPojo>, MoneyPojo>(new SimpleCallBack<MoneyPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(MoneyPojo _moneyPojo) {
                        b.coin.setText(_moneyPojo.coin+"");
                        b.money.setText("￥"+_moneyPojo.canCansh);

                        if (_moneyPojo.canCansh < 2) {
                            flag = false;
                        }else{
                            flag = true;
                        }

                    }


                }) {
                });

    }

    private void dialog() {

        EasyHttp.post(Api.apiurl + "common/getWxLinkH5")
                .params("userId",String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(UserInfoPojo _userInfoPojo) {

                                if(_dialog==null){
                                    _dialog = DialogUtils.showAlert2Mid(R.layout.dialog_wx, act);
                                }
                                _dialog.show();
                                final ImageView iv_code = (ImageView) _dialog.findViewById(R.id.iv_code);
                                TextView tv_commit = (TextView) _dialog.findViewById(R.id.tv_commit);
                                if(!TextUtils.isEmpty(_userInfoPojo.link)){
                                    _bitmap = Qrcode.getcode(_userInfoPojo.link, null);
                                    iv_code.setImageBitmap(_bitmap);
                                }


                                tv_commit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        savePic(iv_code);
                                    }
                                });
                            }


                }) {
    });


    }

    private String imagePath = Api.COO_PATH_IMG + "wxsq.png";

    private void savePic(final ImageView _iv_code) {
        Observable.just(new Object())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object _integer) {
                        PermissionsUtil.requestPermission(act, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permission) {
                                if (ViewUtils.saveBitmap(_iv_code, "userqr")) {
                                    ToastUtils.showShort("保存成功!\n保存路径为：手机相册目录下,请耐心等待图片存储,或截图保存到自己相册");
                                    //ImagePicker.galleryAddPic(_instance, new File(imagePath));
                                    PictureUtils.savePicRefreshGallery(act, imagePath);
                                } else {
                                    ToastUtils.showShort("保存失败，请稍后再试。或截图保存到自己相册");
                                }
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permission) {

                            }
                        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    }
                });
    }

    @Override
    public void initNet() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshEvent(PhoneEvent event) {
         phone = event.phone;
    }
}
