package com.diankong.sexstory.mobile.modle.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseAct;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityMainBinding;
import com.diankong.sexstory.mobile.databinding.ItemTuichuBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.modelview.MainModelView;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UIUtils;
import com.diankong.sexstory.mobile.widget.StatusBarCompat;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.umeng.socialize.UMShareAPI;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MainActivity extends BaseAct<ActivityMainBinding, MainModelView> {

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    private int back;



    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    private RecyclerView recyclerView;

    private SingleTypeBindingAdapter _adapter;

    public static void startActivity(Context _context, boolean _b) {

    }

    // 在onNewIntent（）传入一标识符
    // 作用：标识是否要退出App
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            // 是否退出App的标识
            boolean isExitApp = intent.getBooleanExtra("exit", false);
            if (isExitApp) {
                // 关闭自身
                this.finish();
            }
        }
        // 结束进程
        // System.exit(0);
    }


    @SuppressLint("ResourceType")
    @Override
    public void init() {
        super.init();
        StatusBarCompat.translucentStatusBar(this);
        back = getIntent().getIntExtra("BACK", 0);

//        requestToken();

    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (back == 0) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

//                exit();
                exit2();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
//            moveTaskToBack(false);
        }
    }

    private void exit2(){

        EasyHttp.post(Api.apiurl +"/user/loginOutAndGetUsers")
                .params("id",String.valueOf(UIUtils.getUserInfo().id))
                .execute(new CallBackProxy<BaseResult<List<UserInfoPojo>>, List<UserInfoPojo>>(new SimpleCallBack<List<UserInfoPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<UserInfoPojo> mTestPojo) {

                        final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_exit, MainActivity.this);
                        ImageView tuichu = dialog.findViewById(R.id.iv_tuichu);
                        ImageView check = dialog.findViewById(R.id.iv_check);
                        recyclerView = dialog.findViewById(R.id.recyclerView);

                        tuichu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                dialog.dismiss();
                                finish();
                                System.exit(0);
                            }
                        });

                        check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                dialog.dismiss();
                            }
                        });

                        _adapter = new SingleTypeBindingAdapter(MainActivity.this,mTestPojo,R.layout.item_tuichu);
                        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<UserInfoPojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<UserInfoPojo> mData) {
                                ItemTuichuBinding itemTuichuBinding = (ItemTuichuBinding) holder.getBinding();

                                GlideImageLoader.displayRound(MainActivity.this,itemTuichuBinding.ivAvatar,mData.get(position).avatar);
                                itemTuichuBinding.tvName.setText(mData.get(position).nickName);
                                itemTuichuBinding.llContent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        Intent intent = new Intent(MainActivity.this,UserDetailActivity.class);
                                        intent.putExtra("USERID",mData.get(position).id);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                        recyclerView.setAdapter(_adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));

                    }

                }) {
                });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }



//
//    //权限请求结果
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case 1:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                 //   checkUpDateApp();
//                } else {
//                    new ConfirmDialog(this, new Callback() {
//                        @Override
//                        public void callback(int position) {
//                            if (position==1){
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
//                                startActivity(intent);
//                            }
//                        }
//                    }).setContent("暂无读写SD卡权限\n是否前往设置？").show();
//                }
//                break;
//        }
//
//    }





}
