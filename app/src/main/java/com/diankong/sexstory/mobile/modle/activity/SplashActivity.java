package com.diankong.sexstory.mobile.modle.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.bean.VersionInfoPojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.utils.AndroidSystemUtils;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UIUtils;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import util.UpdateAppUtils;

import static com.umeng.socialize.utils.DeviceConfig.isNetworkAvailable;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/4/4.
 * 描述：
 * =============================================
 */

public class SplashActivity extends AppCompatActivity {

    private boolean isNeedSetting;
//    private boolean isNeedLogin = true;//
//    private boolean isFirstRun = true;
    private Button _button;
    private boolean isUpdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        _button = findViewById(R.id.bt_commit);
//        initData();

        initConnect();



    }


//    private void initData() {
//
//        String token = SpUtlis.getToken();
//        if (!TextUtils.isEmpty(token)) {
//            isNeedLogin = false;
//        }
//    }

    private void initConnect() {
        if (!isNetworkAvailable(this)) {
            //提示对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setMessage("网络无法访问，请检查网络").setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = null;
                            //判断手机系统的版本  即API大于10 就是3.0或以上版本
                            if (android.os.Build.VERSION.SDK_INT > 21) {
                                intent = new Intent(Settings.ACTION_HOME_SETTINGS);
                            } else {
                                intent = new Intent();
                                ComponentName component = new ComponentName("com.android.settings",
                                        "com.android.settings.WirelessSettings");
                                intent.setComponent(component);
                                intent.setAction("android.intent.action.VIEW");
                            }
                            startActivityForResult(intent, 1);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }).show();
        } else {
            checkAndUpdate();
            checkFirstRun();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isNeedSetting) {
            if (isNetworkAvailable(this)) {
                checkFirstRun();
            } else {
                initConnect();
            }
        }
    }

    private void checkFirstRun() {


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isNeedLogin) {
////                        Go2Login();
//                    Go2Main(isNeedLogin);
//                } else {
//                    Go2Main(isNeedLogin);
//                }
//            }
//        }, 500);


        if (TextUtils.isEmpty(SpUtlis.getOpenId())) {
            final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan2, SplashActivity.this);
            TextView textView = dialog.findViewById(R.id.tv_cancel);
            ImageView imageView = dialog.findViewById(R.id.iv_can);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {

                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    UMengTools.getPlatformInfo(SplashActivity.this, SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                        @Override
                        public void getUserInfo(WxPojo UserInfo) {
                            CommonInterface.wxLogin3(SplashActivity.this, SpUtlis.getId(), UserInfo.accessToken, UserInfo.openid,_button);

                            dialog.dismiss();
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            });
        }else{
            CommonInterface.firstIn(SplashActivity.this,SpUtlis.getOpenId(),_button);
        }


    }




    private void Go2Main(final boolean _isNeedLogin) {

        EasyHttp.get(Api.apiurl + "user/firstIn")
                .params("deviceId", AndroidSystemUtils.getPhoneSign(App.getInstance()))
                .params("openid", String.valueOf(SpUtlis.getOpenId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {

                        SpUtlis.setlinkString(mTestPojo.linkString);
                        SpUtlis.savaIdNo(mTestPojo.flag);
//                        SpUtlis.setVisitor(mTestPojo.visitor);

                        if(!TextUtils.isEmpty(SpUtlis.getIMToken())){
                            RongIM.connect(SpUtlis.getIMToken(), new RongIMClient.ConnectCallback() {
                                @Override
                                public void onTokenIncorrect() {

                                }

                                @Override
                                public void onSuccess(String userid) {
                                    L.d("--onSuccess" + userid);

                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    L.d("--onSuccess" + errorCode);
                                }
                            });
                        }


                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra("ISNEEDLOGIN",_isNeedLogin);
                        startActivity(intent);
                        finish();

                    }

                }) {
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
            case 2:
                isNeedSetting = true;
                break;

            default:
                break;
        }
    }


    private void checkAndUpdate() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            checkUpDateApp();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                checkUpDateApp();
            } else {//申请权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void checkUpDateApp() {

        EasyHttp.post(Api.apiurl +"/common/getVersonInfo")
                .params("userId",String.valueOf(UIUtils.getUserInfo().id))
                .execute(new CallBackProxy<BaseResult<VersionInfoPojo>, VersionInfoPojo>(new SimpleCallBack<VersionInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final VersionInfoPojo mTestPojo) {

                        int Code = Integer.parseInt(mTestPojo.vesionCode);


                        if (mTestPojo.isForce == 1) {
                            isUpdata = true;
                        }

                        UpdateAppUtils.from(SplashActivity.this)
                                .serverVersionCode(Code) //服务器versionCode
                                .serverVersionName(mTestPojo.vesionName) //服务器versionName
                                .apkPath(mTestPojo.apkPath) //最新apk下载地址
                                .isForce(isUpdata)//是否强制更新
                                .updateInfo(mTestPojo.vesionDesc)  //更新日志信息 String
                                .update();


                    }

                }) {
                });

    }
}
