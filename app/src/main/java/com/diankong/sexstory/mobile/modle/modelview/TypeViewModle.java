package com.diankong.sexstory.mobile.modle.modelview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.BookListPojo;
import com.diankong.sexstory.mobile.databinding.ActivityTypeBinding;
import com.diankong.sexstory.mobile.databinding.ItemTypeBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.BookListActivity;
import com.diankong.sexstory.mobile.modle.activity.SearchActivity;
import com.diankong.sexstory.mobile.utils.AutoGridLayoutManager;
import com.diankong.sexstory.mobile.utils.IntentUtils;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseBindingItemPresenter;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/21.
 * 描述：
 * =============================================
 */

public class TypeViewModle extends BaseViewModle<ActivityTypeBinding> implements BaseBindingItemPresenter<BookListPojo> {

    private SingleTypeBindingAdapter _adapter;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        _adapter = new SingleTypeBindingAdapter(act, null, R.layout.item_type);
        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<BookListPojo>() {
            @Override
            public void decorator(BindingViewHolder holder, int position, int viewType, List<BookListPojo> mData) {
                ItemTypeBinding binding = (ItemTypeBinding) holder.getBinding();
                if (position % 2 == 0) {
                    setMargins(binding.llContent, 16, 0, 8, 16);
                } else {
                    setMargins(binding.llContent, 8, 0, 16, 16);
                }

                binding.tvCount.setText(mData.get(position).bookCount + "册");
//                binding.tvCount.setText(mData.get(position).bookCount + "件");
            }
        });
        _adapter.setItemPresenter(this);
        b.recyclerView.setAdapter(_adapter);
        b.recyclerView.setLayoutManager(new AutoGridLayoutManager(act, 2));
        getListData();

        b.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                startActivity(SearchActivity.class);
            }
        });
    }

    private void getListData() {
        EasyHttp.post(Api.apiurl + "book/getBookType")
                .params("token", SpUtlis.getToken())
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("userType", SpUtlis.getUserType())
                .execute(new CallBackProxy<BaseResult<List<BookListPojo>>, List<BookListPojo>>(new SimpleCallBack<List<BookListPojo>>() {
                    @Override
                    public void onError(ApiException e) {
                        if(e.getCode()==800){
                            IntentUtils.toSignActivity();
                        }
                    }

                    @Override
                    public void onSuccess(final List<BookListPojo> mTestPojo) {
                        _adapter.addAll(mTestPojo);
                    }

                }) {
                });

//        List<BookListPojo> bookListPojos = new ArrayList<>();
//        bookListPojos.add(new BookListPojo(1, "女装新品", "http://img.alicdn.com/bao/uploaded/i1/2051444128/O1CN01SWCbwc1gMfU3n85vZ_!!0-item_pic.jpg",560));
//        bookListPojos.add(new BookListPojo(2, "连衣裙", "http://img.alicdn.com/bao/uploaded/i3/1907247938/O1CN01bV22Qv28VeT0jr3lC_!!0-item_pic.jpg",238));
//        bookListPojos.add(new BookListPojo(2, "T恤", "http://img.alicdn.com/bao/uploaded/i2/397341302/O1CN01CPRpit1LUMBiSg2Gv_!!397341302.jpg",789));
//        bookListPojos.add(new BookListPojo(2, "衬衫", "http://img.alicdn.com/bao/uploaded/i3/130974249/O1CN01kSqq8x1hG5OiTMNY5_!!0-item_pic.jpg",696));
//        bookListPojos.add(new BookListPojo(2, "休闲裤", "http://img.alicdn.com/bao/uploaded/i1/728443962/TB1azbKcrYI8KJjy0FaXXbAiVXa_!!0-item_pic.jpg",456));
//        bookListPojos.add(new BookListPojo(2, "牛仔裤", "http://img.alicdn.com/bao/uploaded/i3/408107205/O1CN01YatYyK235w9akwl94_!!0-item_pic.jpg",233));
//        bookListPojos.add(new BookListPojo(2, "外套", "http://img.alicdn.com/bao/uploaded/i4/4131235327/O1CN01ZLqNdK1pDoMrFP6Gf_!!0-item_pic.jpg",236));
//        bookListPojos.add(new BookListPojo(2, "卫衣", "http://img.alicdn.com/bao/uploaded/i1/188124207/O1CN01XNOlet1gwqjs6ZQIk_!!0-item_pic.jpg",153));
//
//        _adapter.addAll(bookListPojos);

    }

    @Override
    public void initNet() {

    }


    public void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    @Override
    public void onItemClick(int position, BookListPojo itemData) {
        Bundle bundle = new Bundle();
        bundle.putInt("sortId", itemData.id);
        bundle.putString("typeName",itemData.typeName);
        startActivity(BookListActivity.class, bundle);
    }
}
