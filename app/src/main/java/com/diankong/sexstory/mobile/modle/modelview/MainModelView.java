package com.diankong.sexstory.mobile.modle.modelview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CircleResultPojo;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityMainBinding;
import com.diankong.sexstory.mobile.databinding.ItemSayhelloBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;
import com.diankong.sexstory.mobile.modle.fragment.FirendFragment;
import com.diankong.sexstory.mobile.modle.fragment.MSGFragment;
import com.diankong.sexstory.mobile.modle.fragment.MyFragment;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UIUtils;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;


/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/11/29.
 * 描述：
 * =============================================
 */

public class MainModelView extends BaseViewModle<ActivityMainBinding> {

    private int type;
    FragmentManager fm;
    FragmentTransaction transaction;
    FirendFragment mFirendFragment;
    MSGFragment mMSGFragment;
    MyFragment mMyFragment;
    List<Button> mButtons;
    private RecyclerView recyclerView;

    private SingleTypeBindingAdapter _adapter;

    public void initUI() {
        b.setViewmodle(this);
        initViews();

        if(SpUtlis.getIdNo()==1&& !TextUtils.isEmpty(SpUtlis.getOpenId())){
            //随机打招呼
            sayHello();
        }
//        else if(SpUtlis.getIdNo()!=1&& !TextUtils.isEmpty(SpUtlis.getOpenId())){
//            //随机加关注
//            sayHello2();
//        }
    }

    /**
     * 未读消息监听回调
     * @param i
     */
    private IUnReadMessageObserver observer = new IUnReadMessageObserver() {
        @Override
        public void onCountChanged(int i) {
            L.d("数量变化s：" + i);
//            //给首页发送未读消息事件，更新未读消息图标
//            LeaveMessageBean leaveMessageBean = new LeaveMessageBean(i);
//            EventBusUtils.post(leaveMessageBean);
            if (i > 0) {
                b.tvMainUnreadPoint.setVisibility(View.VISIBLE);
            }else{
                b.tvMainUnreadPoint.setVisibility(View.INVISIBLE);
            }
        }
    };


    @SuppressLint("CommitTransaction")
    private void initViews() {
        b.setViewmodle(this);
        type = act.getIntent().getIntExtra("TYPE",0);
        mButtons = new ArrayList<>();
        mButtons.add(b.mainRbWork);
        mButtons.add(b.mainRbMessage);
        mButtons.add(b.mainRbUser);
        //初始设置“工作”按钮已选状态
        setrbSelect(0);
        setDefaultFragment();
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
//        StatusBarCompat.translucentStatusBar(act);
        //设置底部（可放两张图片进行点击切换）
//        b.bottomTabBar.init(act.getSupportFragmentManager())
//                .addTabItem("商城", R.mipmap.ic_tabd,R.mipmap.ic_tabc, HomeFragment.class)
//                .addTabItem("社区", R.mipmap.ic_tabd,R.mipmap.ic_tabc, FirendFragment.class)
//                .addTabItem("我的", R.mipmap.ic_tabb,R.mipmap.ic_taba, MyFragment.class);
//
//        b.bottomTabBar.setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
//            @Override
//            public void onTabChange(int position, String name, View view) {
//
//            }
//        });

//        b.bottomBar.init(act.getSupportFragmentManager(), R.id.ll_content)//.setRippleColor(R.drawable.bottom_bar_bg)//水波纹效果
////                .addItem("商城", act.getResources().getDrawable(R.drawable.bottom_bar_selected_01), new HomeFragment(), false)
//                .addItem("社区", act.getResources().getDrawable(R.drawable.bottom_bar_selected_02), new FirendFragment(), false)
//                .addItem("消息", act.getResources().getDrawable(R.drawable.bottom_bar_selected_01), new MSGFragment(), false)
//                .addItem("我的", act.getResources().getDrawable(R.drawable.bottom_bar_selected_03), new MyFragment(), false)
//                .create(0);//默认显示第几个界面从0开始
//        b.bottomBar.setOnBottomBarOnClick(new IBottomBarOnClick() {
//            @Override
//            public void onClickBar(View view, int tag) {
//
//                if(tag==1){
//
//                }
//            }
//        });//设置点击监听

        b.mainRbWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                setrbSelect(0);
                transaction = fm.beginTransaction();
                transaction.replace(R.id.tb, mFirendFragment);
                transaction.commit();
            }
        });

        b.mainRbMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
//                transaction = fm.beginTransaction();
//                transaction.replace(R.id.tb, mFirendFragment);
//                CommonInterface.GoChatList(act);
                b.tvMainUnreadPoint.setVisibility(View.INVISIBLE);
                setrbSelect(1);
                transaction = fm.beginTransaction();
                transaction.replace(R.id.tb, mMSGFragment);
                transaction.commit();
            }
        });

        b.mainRbUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                setrbSelect(2);
                transaction = fm.beginTransaction();
                transaction.replace(R.id.tb, mMyFragment);
                transaction.commit();
            }
        });

        firstRun();

        getIMToken(SpUtlis.getId(), SpUtlis.getNickName(), SpUtlis.getAvatar());
        RongIM.getInstance().addUnReadMessageCountChangedObserver(observer, Conversation.ConversationType.PRIVATE);

    }
    private void setrbSelect(int index) {
        for (int i = 0; i < mButtons.size(); i++) {
            mButtons.get(i).setSelected(i == index);

        }
    }

    public static void getIMToken(int id, String name, String portrait) {
        EasyHttp.post(Api.apiurl2 + "tcp/getToken")
                .params("id", String.valueOf(id))
                .params("name", String.valueOf(name))
                .params("portrait", String.valueOf(portrait))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final UserInfoPojo UserInfo) {
                        SpUtlis.saveIMToken(UserInfo.token);

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

                }) {
                });

    }




    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        mFirendFragment = new FirendFragment();
        mMSGFragment = new MSGFragment();
        mMyFragment = new MyFragment();
        fm = act.getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.tb, mFirendFragment);
        transaction.commit();
    }


    private void firstRun() {
        SharedPreferences sharedPreferences = act.getSharedPreferences("FirstRun", 0);
        Boolean first_run = sharedPreferences.getBoolean("First", true);
        if (first_run) {
            sharedPreferences.edit().putBoolean("First", false).commit();
//            requestFist();
        }


    }

//    private void getOSSToken() {
//        EasyHttp.get(Api.apiurl2 + "community/getOssTokenInfo")
//                .execute(new CallBackProxy<BaseResult<OSSPojo>, OSSPojo>(new SimpleCallBack<OSSPojo>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        ToastUtils.showShort(e.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccess(final OSSPojo _ossPojo) {
//                        SpUtlis.setAccessKey(_ossPojo.accessKey);
//                        SpUtlis.setSecret(_ossPojo.secret);
//                        SpUtlis.setOssToken(_ossPojo.token);
//                    }
//
//                }) {
//                });
//    }


    @Override
    public void initNet() {

    }

    private void sayHello() {
        EasyHttp.post(Api.apiurl +"/user/getNewRandomUsers")
                .params("userId",String.valueOf(UIUtils.getUserInfo().id))
                .params("sex",String.valueOf(UIUtils.getUserInfo().sex))
                .execute(new CallBackProxy<BaseResult<List<UserInfoPojo>>, List<UserInfoPojo>>(new SimpleCallBack<List<UserInfoPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<UserInfoPojo> mTestPojo) {

                        final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_say_hello, act);
                        TextView tuichu = dialog.findViewById(R.id.tv_tuichu);
                        TextView zhaohu = dialog.findViewById(R.id.tv_zhaohu);
                        recyclerView = dialog.findViewById(R.id.recyclerView);
                        final StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < mTestPojo.size(); i++) {
                            stringBuffer.append(mTestPojo.get(i).id + ",");
                        }
                        final String ids = stringBuffer.substring(0, stringBuffer.length() - 1).toString();



                        tuichu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                dialog.dismiss();
                            }
                        });

                        zhaohu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                sayToUser(ids);
                                dialog.dismiss();
                            }
                        });

                        _adapter = new SingleTypeBindingAdapter(act,mTestPojo,R.layout.item_sayhello);
                        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<UserInfoPojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<UserInfoPojo> mData) {
                                ItemSayhelloBinding itemTuichuBinding = (ItemSayhelloBinding) holder.getBinding();

                                GlideImageLoader.displayRound(act,itemTuichuBinding.ivAvatar,mData.get(position).avatar);
                                itemTuichuBinding.tvName.setText(mData.get(position).nickName);
                                itemTuichuBinding.llContent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        Intent intent = new Intent(act,UserDetailActivity.class);
                                        intent.putExtra("USERID",mData.get(position).id);
                                        act.startActivity(intent);
                                    }
                                });
                            }
                        });
                        recyclerView.setAdapter(_adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(act,3));

                    }

                }) {
                });

    }





    private void sayToUser(String ids) {
        EasyHttp.get(Api.apiurl + "/user/sayToUser")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("ids", String.valueOf(ids))
                .execute(new CallBackProxy<BaseResult<CircleResultPojo>, CircleResultPojo>(new SimpleCallBack<CircleResultPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final CircleResultPojo mTestPojo) {
                        ToastUtils.showShort("打招呼成功！");
                    }

                }) {
                });
    }


    @Override
    public void onDestory() {
        super.onDestory();
        RongIM.getInstance().addUnReadMessageCountChangedObserver(observer, Conversation.ConversationType.PRIVATE);

//移除监听，防止内存泄漏
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
    }
}
