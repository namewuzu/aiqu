package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.GoodsPojo;
import com.diankong.sexstory.mobile.databinding.ItemSexstoreBinding;
import com.diankong.sexstory.mobile.modle.activity.GoodTypeActivity;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UIUtils;
import com.diankong.sexstory.mobile.widget.WBViewActivity;
import com.diankong.sexstory.mobile.widget.recyclerview.BaseDataBindingAdapter;
import com.diankong.sexstory.mobile.widget.recyclerview.BindingViewHolder;
import com.diankong.sexstory.mobile.widget.recyclerview.SingleTypeBindingAdapter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class sexStoreViewHolder extends BaseViewHolder<GoodsPojo> {

    private TextView tv_name;
    private TextView tv_more;
    private ImageView ic_wechat;
    private ImageView iv_pic;
    private View view1;
    private View view2;
    private RecyclerView recyclerView;
    private SingleTypeBindingAdapter _adapter;

    public sexStoreViewHolder(ViewGroup parent) {
//        super(parent, R.layout.item_sexstore);
        super(parent, R.layout.item_sc);

        ic_wechat = (ImageView) $(R.id.iv_wechat);
        iv_pic = (ImageView) $(R.id.iv_pic);
        tv_name = (TextView) $(R.id.tv_name);
        tv_more = (TextView) $(R.id.tv_more);
        view1 = (View) $(R.id.view1);
        view2 = (View) $(R.id.view2);
        recyclerView = (RecyclerView) $(R.id.recyclerView);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setData(final GoodsPojo data) {
        super.setData(data);
        tv_name.setText(data.title);

        GlideImageLoader.onDisplayImage(getContext(),iv_pic,data.imageUrl);

        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.goodTypeId);
                intent.putExtra("TITLE",data.title);
                getContext().startActivity(intent);
            }
        });

        if(data.ifWx==1){
            //微信
            GlideImageLoader.onDisplayImage(getContext(),ic_wechat,data.showImgUrl);
            ic_wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    //点击复制的方法
                    copy(data.wxNo);
                    ToastUtils.showShort("复制成功");
                }
            });
        }else if(data.ifWx==2){
            //图片
            GlideImageLoader.onDisplayImage(getContext(),ic_wechat,data.showImgUrl);
            ic_wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    if(getDataPosition()==1){
                        Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                        intent.putExtra("goodTypeId",  2);
                        intent.putExtra("TITLE","女性");
                        getContext().startActivity(intent);
                    }
                }
            });
        }else if(data.ifWx==3){
            //没有
            ic_wechat.setVisibility(View.GONE);
        }

        switch (data.type){
            case 1:
                view1.setBackgroundColor(getContext().getResources().getColor(R.color.type_1));
                view2.setBackgroundColor(getContext().getResources().getColor(R.color.type_1));
                tv_name.setTextColor(UIUtils.getColor(R.color.type_12));


                tv_name.setCompoundDrawablesWithIntrinsicBounds(
                        getContext().getResources().getDrawable(R.mipmap.ic_bt_a, null), null,null, null);

                break;
            case 2:
                view1.setBackgroundColor(getContext().getResources().getColor(R.color.type_2));
                view2.setBackgroundColor(getContext().getResources().getColor(R.color.type_2));
                tv_name.setTextColor(UIUtils.getColor(R.color.type_22));


                tv_name.setCompoundDrawablesWithIntrinsicBounds(
                        getContext().getResources().getDrawable(R.mipmap.ic_bt_b, null), null,null, null);

                break;
            case 3:
                view1.setBackgroundColor(getContext().getResources().getColor(R.color.type_3));
                view2.setBackgroundColor(getContext().getResources().getColor(R.color.type_3));
                tv_name.setTextColor(UIUtils.getColor(R.color.type_32));


                tv_name.setCompoundDrawablesWithIntrinsicBounds(
                        getContext().getResources().getDrawable(R.mipmap.ic_bt_c, null), null,null, null);

                break;
            case 6:
                view1.setBackgroundColor(getContext().getResources().getColor(R.color.type_6));
                view2.setBackgroundColor(getContext().getResources().getColor(R.color.type_6));
                tv_name.setTextColor(UIUtils.getColor(R.color.type_62));


                tv_name.setCompoundDrawablesWithIntrinsicBounds(
                        getContext().getResources().getDrawable(R.mipmap.ic_bt_f, null), null,null, null);

                break;
        }

        _adapter = new SingleTypeBindingAdapter(getContext(),data.goodList,R.layout.item_sexstore);
        _adapter.setItemDecorator(new BaseDataBindingAdapter.ItemDecorator<GoodsPojo>() {
            @Override
            public void decorator(BindingViewHolder holder, final int position, int viewType, final List<GoodsPojo> mData) {
                ItemSexstoreBinding binding = (ItemSexstoreBinding) holder.getBinding();
                binding.tvTitle.setText(mData.get(position).goodName);
                binding.tvDesc.setText(mData.get(position).goodDes);
                binding.tvPrice.setText("￥"+String.valueOf(mData.get(position).goodPrice));

                GlideImageLoader.onDisplayImage(getContext(),binding.ivImg,mData.get(position).goodSmallIcon);

//                binding.tvMore.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View _view) {
//
//                    }
//                });

                binding.llContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View _view) {
                        WBViewActivity.startActivity(getContext(),"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + mData.get(position).id + "&userId=" + SpUtlis.getId(),mData.get(position).id,true);
                    }
                });
            }
        });

        recyclerView.setAdapter(_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(getContext(),GoodTypeActivity.class);
                intent.putExtra("goodTypeId", data.goodTypeId);
                intent.putExtra("TITLE",data.title);
                getContext().startActivity(intent);
            }
        });
    }

    //复制
    private void copy(String data) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）,其他的还有
        // newHtmlText、
        // newIntent、
        // newUri、
        // newRawUri
        ClipData clipData = ClipData.newPlainText(null, data);

        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
    }

}
