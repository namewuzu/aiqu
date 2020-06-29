package com.diankong.sexstory.mobile.modle.modelview;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.BookInfoPojo;
import com.diankong.sexstory.mobile.databinding.ActivityBookDetailsBinding;
import com.diankong.sexstory.mobile.databinding.ItemLikeBinding;
import com.diankong.sexstory.mobile.event.BookInfoEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.BookDetailsActivity;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.IntentUtils;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/23.
 * 描述：
 * =============================================
 */

public class BookDetailsViewModle extends BaseViewModle<ActivityBookDetailsBinding> {
    private int articleid;
    private String articlename;
    private String intro;
    private String author;
    private SingleTypeBindingAdapter _adapter;
    private int redCharter;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);

        articleid = act.getIntent().getIntExtra("articleid", 0);
        articlename = act.getIntent().getStringExtra("articlename");
        intro = act.getIntent().getStringExtra("intro");
        author = act.getIntent().getStringExtra("author");

        requestBookInfo();


        _adapter = new SingleTypeBindingAdapter(act, null, R.layout.item_like);
        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<BookInfoPojo>() {
            @Override
            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<BookInfoPojo> mData) {
                ItemLikeBinding binding = (ItemLikeBinding) holder.getBinding();
                GlideImageLoader.onDisplayImage(act, binding.ivIcon, Api.IMGURL + mData.get(position).articleid + "/" + mData.get(position).articleid + Api.IMGURL_FOOT1);

                binding.llContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View _view) {
                        Intent intent = new Intent(act, BookDetailsActivity.class);
                        intent.putExtra("articleid",mData.get(position).articleid);
                        intent.putExtra("articlename",mData.get(position).articlename);
                        act.startActivity(intent);
                    }
                });

            }
        });
        b.recyclerView.setAdapter(_adapter);
        b.recyclerView.setLayoutManager(new GridLayoutManager(act, 4));

        getLikeDataList();

        b.tvHuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                getLikeDataList();
            }
        });
        b.tvHuans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                getLikeDataList();
            }
        });




        b.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

            }
        });
    }

    private void requestBookInfo() {
        EasyHttp.post(Api.apiurl + "book/getBookInforById")
                .params("id", String.valueOf(articleid))
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("token", SpUtlis.getToken())
                .params("userType", SpUtlis.getUserType())
                .execute(new CallBackProxy<BaseResult<BookInfoPojo>, BookInfoPojo>(new SimpleCallBack<BookInfoPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        if (e.getCode() == 800) {
                            IntentUtils.toSignActivity();
                        }
                        if (e.getCode() == 500) {
                            ToastUtils.showShort(e.getMessage());
                        }
                    }

                    @Override
                    public void onSuccess(final BookInfoPojo mTestPojo) {
                        b.tvTitle.setText(mTestPojo.articlename);
                        b.tvBookName.setText(mTestPojo.articlename);
                        GlideImageLoader.onDisplayImage(act, b.ivIcon, Api.IMGURL + mTestPojo.articleid + "/" + mTestPojo.articleid + Api.IMGURL_FOOT1);
                        b.tvAuthor.setText(mTestPojo.author);
                        b.tvAuthor.setVisibility(View.GONE);
                        b.tvDuration.setText("共" + mTestPojo.chapters + "章");
                        b.tvIntroduc.setText(mTestPojo.intro);
                        redCharter = mTestPojo.redCharter;
                        if (mTestPojo.upload) {
                            b.tvBookcase.setText("移除书架");
                            b.tvBookcase.setTextColor(act.getResources().getColor(R.color.gray_99));
                        } else {
                            b.tvBookcase.setText("加入书架");
                            b.tvBookcase.setTextColor(act.getResources().getColor(R.color.colorAccent));
                        }


                        b.tvRead.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                requestCount(articleid);

//                WBViewActivity.startActivity(act, "书本信息", "http://47.244.48.177/webPage/h5/reading-h5.html");
                                CommonInterface.getHtml(act, 1, SpUtlis.getId(), articleid, mTestPojo.redCharter, mTestPojo.chapterId, mTestPojo.upload ? 1 : 0);

                            }
                        });

                        b.tvMulv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                CommonInterface.getHtml(act, 2, SpUtlis.getId(), articleid, mTestPojo.redCharter, mTestPojo.chapterId, mTestPojo.upload ? 1 : 0);
                            }
                        });

                        b.tvBookcase.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View _view) {
                                if ("加入书架".equals(b.tvBookcase.getText().toString())) {
                                    requestBookCase(mTestPojo);
                                } else {
                                    request2BookCase(mTestPojo);
                                }

                            }
                        });

                    }

                }) {
                });

    }

    private void request2BookCase(final BookInfoPojo _mTestPojo) {
        EasyHttp.post(Api.apiurl + "book/removeUserBook")
                .params("token", SpUtlis.getToken())
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("bookId", String.valueOf(articleid))
                .params("userType", SpUtlis.getUserType())
                .execute(new CallBackProxy<BaseResult<String>, String>(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        if (e.getCode() == 800) {
                            IntentUtils.toSignActivity();
                        }
                    }

                    @Override
                    public void onSuccess(final String mTestPojo) {
                        b.tvBookcase.setText("加入书架");
                        b.tvBookcase.setTextColor(act.getResources().getColor(R.color.colorAccent));
                        BookInfoPojo bookInfoPojo = new BookInfoPojo(articleid, articlename, author, intro,1,_mTestPojo.redCharter);
                        EventBus.getDefault().post(new BookInfoEvent(bookInfoPojo,2));
                    }

                }) {
                });

    }

    private void requestBookCase(final BookInfoPojo _mTestPojo) {
        EasyHttp.post(Api.apiurl + "book/addUserBooks")
                .params("token", SpUtlis.getToken())
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("bookId", String.valueOf(articleid))
                .params("userType", SpUtlis.getUserType())
                .execute(new CallBackProxy<BaseResult<String>, String>(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        if (e.getCode() == 800) {
                            IntentUtils.toSignActivity();
                        }
                    }

                    @Override
                    public void onSuccess(final String mTestPojo) {
                        b.tvBookcase.setText("移除书架");
                        b.tvBookcase.setTextColor(act.getResources().getColor(R.color.gray_99));
                        BookInfoPojo bookInfoPojo = new BookInfoPojo(articleid, articlename, author, intro,1,_mTestPojo.redCharter);
                        EventBus.getDefault().post(new BookInfoEvent(bookInfoPojo,1));
                    }

                }) {
                });

    }

    private void requestCount(int articleid) {
        EasyHttp.post(Api.apiurl + "book/setBookRedRecord")
                .params("bookId", String.valueOf(articleid))
                .params("token", SpUtlis.getToken())
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("userType", SpUtlis.getUserType())
                .execute(new CallBackProxy<BaseResult<String>, String>(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        if (e.getCode() == 800) {
                            IntentUtils.toSignActivity();
                        }
                    }

                    @Override
                    public void onSuccess(final String mTestPojo) {

                    }

                }) {
                });

    }

    private void getLikeDataList() {
        _adapter.clear();
        EasyHttp.post(Api.apiurl + "book/getLikeBooks")
                .params("token", SpUtlis.getToken())
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("userType", SpUtlis.getUserType())
                .execute(new CallBackProxy<BaseResult<List<BookInfoPojo>>, List<BookInfoPojo>>(new SimpleCallBack<List<BookInfoPojo>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final List<BookInfoPojo> mTestPojo) {
                        _adapter.addAll(mTestPojo);
                    }

                }) {
                });

    }

    @Override
    public void initNet() {

    }

    @Override
    public void onResume() {
        super.onResume();
        requestBookInfo();
    }
}
