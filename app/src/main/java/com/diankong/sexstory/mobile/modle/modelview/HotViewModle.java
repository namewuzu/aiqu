package com.diankong.sexstory.mobile.modle.modelview;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CirclePojo;
import com.diankong.sexstory.mobile.bean.CircleResultPojo;
import com.diankong.sexstory.mobile.bean.GroupTagsPojo;
import com.diankong.sexstory.mobile.bean.Image;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.databinding.ActivityHotBinding;
import com.diankong.sexstory.mobile.databinding.HeaderItemHotBinding;
import com.diankong.sexstory.mobile.databinding.ItemHotListBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.CircleDetailsActivity;
import com.diankong.sexstory.mobile.modle.activity.ReleaseMessageActivity;
import com.diankong.sexstory.mobile.modle.adapter.CircleAdapter;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
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
 * 作者：Created by 胡清 on 2019/12/11.
 * 描述：
 * =============================================
 */

public class HotViewModle extends BaseViewModle<ActivityHotBinding> implements RecyclerArrayAdapter.OnLoadMoreListener {

    private LinearLayout mErrorView;
    private TextView mTvError;
    private int mPage = 1;
    private CircleAdapter mMyAdapter;
    private GroupTagsPojo _pojo;
    private SingleTypeBindingAdapter _adapter;
    private String[] split;

    @Override
    public void initUI() {
        _pojo = (GroupTagsPojo) act.getIntent().getSerializableExtra("POJO");
        ToolbarUtils.initToolBar(b.toolbar, act);

        b.recyclerView.setErrorView(R.layout.view_error);
        b.recyclerView.setEmptyView(R.layout.view_empty);
        mErrorView = (LinearLayout) b.recyclerView.getErrorView();
        mTvError = mErrorView.findViewById(R.id.tv_error);

        mMyAdapter = new CircleAdapter(act,2,act);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(act));

        b.recyclerView.setRefreshingColorResources(R.color.red23);
        b.recyclerView.setAdapterWithProgress(mMyAdapter);
        getListData();
//        b.recyclerView.setRefreshListener(this);

        mMyAdapter.setNoMore(R.layout.view_nomore);
        mMyAdapter.setMore(R.layout.view_more, this);

        mMyAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(act).inflate(R.layout.header_item_hot, parent, false);
            }

            @Override
            public void onBindView(View headerView) {
                HeaderItemHotBinding bind = DataBindingUtil.bind(headerView);

                String replace = _pojo.img.replace("\\", "");
//                GlideImageLoader.onDisplayImage(act, itemHuatiBinding.ivIcon, replace);
                GlideImageLoader.onDisplayImage(act, bind.ivIcon, replace);
                bind.count1.setText(_pojo.zoneCount + "");
                bind.count2.setText(_pojo.peopleCount + "");
                bind.count3.setText(_pojo.showCount + "");

                requestHotPeople(bind.recyclerView);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(act);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                bind.recyclerView.setLayoutManager(linearLayoutManager);
            }

        });
    }

    private void requestHotPeople(final RecyclerView _recyclerView) {
        EasyHttp.get(Api.apiurl2 + "topic/getHotCommunityTopic")
                .params("userIdSelf", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<List<CirclePojo>>, List<CirclePojo>>(new SimpleCallBack<List<CirclePojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final List<CirclePojo> _circlePojos) {
                        _adapter = new SingleTypeBindingAdapter(act, _circlePojos, R.layout.item_hot_list);
                        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<CirclePojo>() {
                            @Override
                            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<CirclePojo> mData) {
                                ItemHotListBinding hotListBinding = (ItemHotListBinding) holder.getBinding();
                                GlideImageLoader.displayRound(App.getInstance(), hotListBinding.ivIcon, mData.get(position).avatar);

                                hotListBinding.ivIcon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View _view) {
                                        Intent intent = new Intent(act, CircleDetailsActivity.class);
                                        intent.putExtra("ID",mData.get(position).id);
                                        act.startActivity(intent);
                                    }
                                });
                            }
                        });
                        _recyclerView.setAdapter(_adapter);
                    }

                }) {
                });

        b.ivFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                if(TextUtils.isEmpty(SpUtlis.getOpenId())){

                    DialogUtils.showDialogPrompt(act, "提示", "您还没有授权用户信息，请先授权信息，再发布您的动态吧。", "去授权", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface _dialogInterface, int _i) {
                            UMengTools.getPlatformInfo(act, SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                                @Override
                                public void getUserInfo(WxPojo UserInfo) {
                                    CommonInterface.wxLogin(act,SpUtlis.getId(),UserInfo.accessToken,UserInfo.openid);
                                    _dialogInterface.dismiss();
                                }

                                @Override
                                public void onCancel() {

                                }

                                @Override
                                public void onError() {

                                }
                            });
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface _dialogInterface, int _i) {
                            _dialogInterface.dismiss();
                        }
                    });

                }else{

//                    if(SpUtlis.getIdNo()!=1){
//                        Intent intent = new Intent(act, ShiMingActivity.class);
//                        act.startActivity(intent);
//                    }else{
//
//                    }

                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE",2);
                    bundle.putInt("HOTID",_pojo.id);
                    bundle.putString("TITLE",_pojo.topicTitle);
                    startActivity(ReleaseMessageActivity.class,bundle);

                }

            }
        });
    }

    @Override
    public void initNet() {

    }

    private void getListData() {
        EasyHttp.get(Api.apiurl2 + "topic/selectCommunityByTopic")
                .params("pageNo", String.valueOf(mPage))
                .params("topicId", String.valueOf(_pojo.id))
                .params("pageSize", String.valueOf(10))
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


    @Override
    public void onLoadMore() {
        mPage++;
        getListData();
    }
}
