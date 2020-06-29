package com.diankong.sexstory.mobile.modle.modelview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CircleResultPojo;
import com.diankong.sexstory.mobile.bean.Image;
import com.diankong.sexstory.mobile.bean.OSSPojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.databinding.CircleFragmentBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.ReleaseMessageActivity;
import com.diankong.sexstory.mobile.modle.adapter.CircleAdapter;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/5.
 * 描述：
 * =============================================
 */

public class CircleViewModle extends BaseViewModle<CircleFragmentBinding> implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout mErrorView;
    private TextView mTvError;
    private int mPage = 1;
    private CircleAdapter mMyAdapter;
    private int type;

    private String[] split;




    @Override
    public void initUI() {
        type = frag.getArguments().getInt("TYPE", 1);



        b.recyclerView.setErrorView(R.layout.view_error);
        b.recyclerView.setEmptyView(R.layout.view_empty);
        mErrorView = (LinearLayout) b.recyclerView.getErrorView();
        mTvError = mErrorView.findViewById(R.id.tv_error);

        mMyAdapter = new CircleAdapter(act,1,act);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));

        b.recyclerView.setRefreshingColorResources(R.color.red23);
        b.recyclerView.setAdapterWithProgress(mMyAdapter);
        getListData();
        b.recyclerView.setRefreshListener(this);

        mMyAdapter.setNoMore(R.layout.view_nomore);
        mMyAdapter.setMore(R.layout.view_more, this);


        b.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    b.ivFabu.setVisibility(View.VISIBLE);
                } else {
                    b.ivFabu.setVisibility(View.GONE);
                }
            }
        });


        b.ivFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                getOSSToken();

                if(TextUtils.isEmpty(SpUtlis.getOpenId())){
                    final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, act);
                    TextView  textView = dialog.findViewById(R.id.tv_cancel);
                    ImageView imageView = dialog.findViewById(R.id.iv_can);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            dialog.dismiss();
                        }
                    });

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View _view) {
                            UMengTools.getPlatformInfo(act, SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                                @Override
                                public void getUserInfo(WxPojo UserInfo) {
                                    CommonInterface.wxLogin(act,SpUtlis.getId(),UserInfo.accessToken,UserInfo.openid);
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

//                    if(SpUtlis.getIdNo()!=1){
//                        Intent intent = new Intent(act, ShiMingActivity.class);
//                        act.startActivity(intent);
//                    }else {
//
//                    }

                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE", 1);
                    startActivity(ReleaseMessageActivity.class, bundle);
                }

            }
        });


    }



    @Override
    public void initNet() {

    }

    private void getListData() {
        EasyHttp.get(Api.apiurl2 + "community/selelctHotData")
                .params("pageNo", String.valueOf(mPage))
                .params("pageSize", String.valueOf(10))
                .params("type", String.valueOf(type))
                .params("userIdSelf", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<CircleResultPojo>, CircleResultPojo>(new SimpleCallBack<CircleResultPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final CircleResultPojo mTestPojo) {
                        if (mPage == 1) {
                            mMyAdapter.removeAll();
                        }

                        for (int i = 0; i < mTestPojo.dataList.size(); i++) {
                            split = mTestPojo.dataList.get(i).imageUrl.split(",");
                            List<Image> _stringList = new ArrayList<>();
                            for (int j = 0; j < split.length; j++) {
                                _stringList.add(new Image(split[j], 320, 320));
                            }

                            mTestPojo.dataList.get(i).imageList = _stringList;
                        }

                        mMyAdapter.addAll(mTestPojo.dataList);
                        mMyAdapter.notifyDataSetChanged();
                    }

                }) {
                });
    }


    private void getOSSToken() {
        EasyHttp.get(Api.apiurl2 + "community/getOssTokenInfo")
                .execute(new CallBackProxy<BaseResult<OSSPojo>, OSSPojo>(new SimpleCallBack<OSSPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final OSSPojo _ossPojo) {
                        SpUtlis.setAccessKey(_ossPojo.accessKey);
                        SpUtlis.setSecret(_ossPojo.secret);
                        SpUtlis.setOssToken(_ossPojo.token);
                    }

                }) {
                });
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        getListData();
    }

    @Override
    public void onLoadMore() {
        mPage++;
        getListData();
    }
}
