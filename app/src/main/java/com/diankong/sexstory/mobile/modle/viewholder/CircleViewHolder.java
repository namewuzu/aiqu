package com.diankong.sexstory.mobile.modle.viewholder;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.App;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CirclePojo;
import com.diankong.sexstory.mobile.bean.Image;
import com.diankong.sexstory.mobile.bean.ShareContent;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.databinding.ItemPinglunBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.CircleDetailsActivity;
import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.TimeUtils;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.diankong.sexstory.mobile.widget.NineGridChildLayout;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.umeng.socialize.UMShareListener;
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
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class CircleViewHolder extends BaseViewHolder<CirclePojo> {
//    private TextView tvName;

    //    public NineGridlayout ivMore;
//    public CustomImageView ivOne;
    public NineGridChildLayout ivOne;
    public TextView tv_name;
    public TextView tv_delete;
    public TextView tv_age;
    public TextView tv_time;
    public TextView tv_desc;
    public TextView tv_address;
    public TextView tv_look;
    public TextView tv_share;
    public TextView tv_pinglun;
    public TextView tv_dianzan;
    public TextView tv_flo;
    public TextView tv_price;
    public ImageView iv_avatar;
    public ImageView iv_sex;
    public ImageView iv_zan;
    public ImageView im_pinglun;
    public RecyclerView _recyclerView;
    public LinearLayout ll_content;
    public LinearLayout ll;
    public LinearLayout llname;
    private List<String> _strings;
    private SingleTypeBindingAdapter _adapter;
    private int isLike;
    private int type;
    private AppCompatActivity _appCompatActivity;

    public CircleViewHolder(ViewGroup parent, int type, AppCompatActivity _appCompatActivity) {
        super(parent, R.layout.item_circle);
        this.type = type;
        this._appCompatActivity = _appCompatActivity;
        tv_name = (TextView) $(R.id.tv_name);
        tv_delete = (TextView) $(R.id.tv_delete);
        tv_age = (TextView) $(R.id.tv_age);
        tv_time = (TextView) $(R.id.tv_time);
        tv_desc = (TextView) $(R.id.tv_desc);
        tv_price = (TextView) $(R.id.tv_price);
        tv_address = (TextView) $(R.id.tv_address);
        tv_look = (TextView) $(R.id.tv_look);
        tv_share = (TextView) $(R.id.tv_share);
        tv_flo = (TextView) $(R.id.tv_flo);
        tv_pinglun = (TextView) $(R.id.tv_pinglun);
        tv_dianzan = (TextView) $(R.id.tv_dianzan);
        iv_avatar = (ImageView) $(R.id.iv_avatar);
        iv_sex = (ImageView) $(R.id.iv_sex);
        iv_zan = (ImageView) $(R.id.iv_zan);
        im_pinglun = (ImageView) $(R.id.im_pinglun);
        _recyclerView = (RecyclerView) $(R.id.recyclerView);
        ivOne = (NineGridChildLayout) $(R.id.iv_ngrid_layout);
        ll_content = (LinearLayout) $(R.id.ll_content);
        ll = (LinearLayout) $(R.id.ll);
        llname = (LinearLayout) $(R.id.llname);
//        ivMore = (NineGridlayout) $(R.id.iv_ngrid_layout);
    }

    @Override
    public void setData(final CirclePojo data) {
        super.setData(data);

        if(type==3){
            tv_delete.setVisibility(View.VISIBLE);

            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    deleteTie(data.id);
                }
            });
        }

        _strings = new ArrayList<>();

        if(data.states==0){
            _strings.add("http://a123.feimiao.xin/index2/image/shz.png");
        }else if(data.states==2){
            for (Image image : data.imageList) {
                _strings.add(image.getUrl());
            }
        }

        if(data.imageList!=null&&data.imageList.size()>0){
            ivOne.setUrlList(_strings);
        }else{
            ivOne.setVisibility(View.GONE);
        }



        if (data.sex == 1) {
            iv_sex.setBackgroundResource(R.mipmap.ic_bt_c);
        } else {
            iv_sex.setBackgroundResource(R.mipmap.ic_bt_d);
        }

        tv_name.setText(data.userNickname);
        tv_desc.setText(data.descs);
        GlideImageLoader.displayRound(App.getInstance(), iv_avatar, data.avatar);

        tv_look.setText(data.showCount + "");
        tv_share.setText(data.shareCount + "");
        tv_pinglun.setText(data.replyCount + "");
        tv_dianzan.setText(data.pointCount + "");
        tv_age.setText(data.age + "岁");
        tv_price.setText("评论得"+data.coin + "金币(可提现)");

        tv_address.setText(data.address);
        tv_time.setText(TimeUtils.getTimeStateNew(String.valueOf(data.createTime)));

        _adapter = new SingleTypeBindingAdapter(getContext(), data.replies, R.layout.item_pinglun);
        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<CirclePojo>() {
            @Override
            public void decorator(BindingViewHolder holder, int position, int viewType, List<CirclePojo> mData) {
                ItemPinglunBinding itemPinglunBinding = (ItemPinglunBinding) holder.getBinding();
                if (mData.get(position).isReply == 1) {
                    itemPinglunBinding.tvUser.setText(mData.get(position).userName + "：");
                    itemPinglunBinding.tvHuifu.setVisibility(View.GONE);
                    itemPinglunBinding.replyedUserName.setVisibility(View.GONE);
                } else {
                    itemPinglunBinding.tvUser.setText(mData.get(position).userName);
                    itemPinglunBinding.replyedUserName.setText(mData.get(position).replyedUserName);
                    itemPinglunBinding.tvHuifu.setVisibility(View.VISIBLE);
                    itemPinglunBinding.replyedUserName.setVisibility(View.VISIBLE);
                }
                itemPinglunBinding.tvPinglun.setText(mData.get(position).comment);

            }
        });
        _recyclerView.setAdapter(_adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iv_zan.setImageResource(R.mipmap.zana);
        iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {


                if(TextUtils.isEmpty(SpUtlis.getOpenId())){
                    final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, _appCompatActivity);
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
                            UMengTools.getPlatformInfo(_appCompatActivity, SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                                @Override
                                public void getUserInfo(WxPojo UserInfo) {
                                    CommonInterface.wxLogin(_appCompatActivity,SpUtlis.getId(),UserInfo.accessToken,UserInfo.openid);
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
//                        Intent intent = new Intent(_appCompatActivity, ShiMingActivity.class);
//                        _appCompatActivity.startActivity(intent);
//                    }else {
//
//
//                    }

                    if (isLike == 0) {
                        iv_zan.setImageResource(R.mipmap.zanb);
                        tv_dianzan.setText(data.pointCount + 1 + "");
                        isLike = 1;

                        updatePoint(data.id, 1);
                    } else {
                        iv_zan.setImageResource(R.mipmap.zana);
                        tv_dianzan.setText(data.pointCount + "");
                        isLike = 0;
                        updatePoint(data.id, 2);
                    }
                }


            }
        });


//        tv_look.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View _view) {
//                Intent intent = new Intent(getContext(), CircleDetailsActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
        im_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), CircleDetailsActivity.class);
                intent.putExtra("ID", data.id);
                getContext().startActivity(intent);
            }
        });

        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), CircleDetailsActivity.class);
                intent.putExtra("ID", data.id);
                getContext().startActivity(intent);
            }
        });

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), UserDetailActivity.class);
                intent.putExtra("USERID", data.userId);
                getContext().startActivity(intent);
            }
        });


        llname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(), UserDetailActivity.class);
                intent.putExtra("USERID", data.userId);
                getContext().startActivity(intent);
            }
        });



        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                sharecounts(data.id);
                tongJiWechat(data.id);
            }
        });

        if (data.isConcert == 1) {
            tv_flo.setText("取消关注");
        } else {
            tv_flo.setText("关注");
        }

        tv_flo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                if(TextUtils.isEmpty(SpUtlis.getOpenId())){
                    final Dialog dialog = DialogUtils.showAlertMid(R.layout.dialog_shouquan, _appCompatActivity);
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
                            UMengTools.getPlatformInfo(_appCompatActivity, SHARE_MEDIA.WEIXIN, new UMengTools.OnUserInfoListener() {
                                @Override
                                public void getUserInfo(WxPojo UserInfo) {
                                    CommonInterface.wxLogin(_appCompatActivity,SpUtlis.getId(),UserInfo.accessToken,UserInfo.openid);
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
                    if (tv_flo.getText().toString().equals("关注")) {
                        flo(data.userId);
                    } else {
                        flo2(data.userId);
                    }
                }


            }
        });

    }

    public void deleteTie(int id) {
        EasyHttp.get(Api.apiurl2 + "community/deleteById")
                .params("id", String.valueOf(id))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {
                        ToastUtils.showShort("删除成功");
                    }

                }) {
                });
    }

    public void updatePoint(int id, int flag) {
        EasyHttp.get(Api.apiurl2 + "community/updatePoint")
                .params("id", String.valueOf(id))
                .params("flag", String.valueOf(flag))
                .params("userId",String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {

                    }

                }) {
                });
    }

    public void sharecounts(int id) {
        EasyHttp.get(Api.apiurl2 + "community/updateShareCount")
                .params("id", String.valueOf(id))
                .params("userId",String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {

                    }

                }) {
                });


    }

    //关注
    public void flo(int id) {
        EasyHttp.get(Api.apiurl2 + "user/concertUser")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("concertUserId", String.valueOf(id))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {
                        tv_flo.setText("取消关注");
                    }

                }) {
                });


    }

    //取消关注
    public void flo2(int id) {
        EasyHttp.get(Api.apiurl2 + "user/unconcertUser")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("concertUserId", String.valueOf(id))
                .execute(new CallBackProxy<BaseResult<UserInfoPojo>, UserInfoPojo>(new SimpleCallBack<UserInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final UserInfoPojo mTestPojo) {
                        tv_flo.setText("关注");
                    }

                }) {
                });


    }

    private void tongJiWechat(int goodId) {
        EasyHttp.get(Api.apiurl + "good/shareClick")
                .params("userId",String.valueOf(SpUtlis.getId()))
                .params("communityId",String.valueOf(goodId))
                .execute(new CallBackProxy<BaseResult<ShareContent>, ShareContent>(new SimpleCallBack<ShareContent>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort("失败" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(final ShareContent mTestPojo) {
//                        share(mTestPojo.title, mTestPojo.desc, mTestPojo.shareUrl, mTestPojo.thumbUrl);
                        EasyHttp.post(Api.apiurl + "good/getFreeGoodShareInfo")
                                .execute(new CallBackProxy<BaseResult<ShareContent>, ShareContent>(new SimpleCallBack<ShareContent>() {
                                    @Override
                                    public void onError(ApiException e) {
                                        ToastUtils.showShort("失败" + e.getMessage());
                                    }

                                    @Override
                                    public void onSuccess(final ShareContent mTestPojo) {
                                        share(mTestPojo.title, mTestPojo.desc, mTestPojo.shareUrl, mTestPojo.thumbUrl);
                                    }

                                }) {
                                });
                    }

                }) {
                });

    }


    private void share(String title, String desc, String shareUrl, String thumbUrl) {
        ShareContent shareContent = new ShareContent();
        shareContent.title = title;
        shareContent.desc = desc;
        shareContent.shareUrl = shareUrl;
        if (TextUtils.isEmpty(thumbUrl)) {
            shareContent.thumbRes = R.mipmap.ic_launcher;
        } else {
            shareContent.thumbUrl = thumbUrl;
        }

        UMengTools.setShareAction(_appCompatActivity, SHARE_MEDIA.WEIXIN, shareContent, new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA _share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA _share_media) {
                L.d("platform" + _share_media);
            }

            @Override
            public void onError(SHARE_MEDIA _share_media, Throwable _throwable) {
                ToastUtils.showShort("分享失败");
                if (_throwable != null) {
                    L.d("throw:" + _throwable.getMessage());
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA _share_media) {
                ToastUtils.showShort("分享取消了");
            }
        });


    }

}
