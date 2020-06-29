package com.diankong.sexstory.mobile.modle.viewholder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.GoodsPojo;
import com.diankong.sexstory.mobile.modle.activity.GoodTypeActivity;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UIUtils;
import com.diankong.sexstory.mobile.widget.WBViewActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/19.
 * 描述：
 * =============================================
 */

public class sexStoreViewHolder2 extends BaseViewHolder<GoodsPojo> {

    private TextView tv_name;
    private TextView tv_more;
    private ImageView ic_wechat;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private ImageView iv7;
    private View view1;
    private View view2;

    public sexStoreViewHolder2(ViewGroup parent) {
        super(parent, R.layout.item_sexstore2);
        ic_wechat = (ImageView) $(R.id.iv_wechat);
        iv1 = (ImageView) $(R.id.iv1);
        iv2 = (ImageView) $(R.id.iv2);
        iv3 = (ImageView) $(R.id.iv3);
        iv4 = (ImageView) $(R.id.iv4);
        iv5 = (ImageView) $(R.id.iv5);
        iv6 = (ImageView) $(R.id.iv6);
        iv7 = (ImageView) $(R.id.iv7);
        tv_name = (TextView) $(R.id.tv_name);
        tv_more = (TextView) $(R.id.tv_more);
        view1 = (View) $(R.id.view1);
        view2 = (View) $(R.id.view2);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setData(final GoodsPojo data) {
        super.setData(data);
        tv_name.setText(data.title);
        if (data.ifWx == 1) {
            //微信
            GlideImageLoader.onDisplayImage(getContext(), ic_wechat, data.showImgUrl);
            ic_wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    //点击复制的方法
                    copy(data.wxNo);
                    ToastUtils.showShort("复制成功");
                }
            });
        } else if (data.ifWx == 2) {
            //图片
            GlideImageLoader.onDisplayImage(getContext(), ic_wechat, data.showImgUrl);
            ic_wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {

                }
            });
        } else if (data.ifWx == 3) {
            //没有
            ic_wechat.setVisibility(View.GONE);
        }


        if (data.type == 4) {
            view1.setBackgroundColor(getContext().getResources().getColor(R.color.type_4));
            view2.setBackgroundColor(getContext().getResources().getColor(R.color.type_4));
            tv_name.setTextColor(UIUtils.getColor(R.color.type_42));


            tv_name.setCompoundDrawablesWithIntrinsicBounds(
                    getContext().getResources().getDrawable(R.mipmap.ic_bt_d, null), null,null, null);
        } else if (data.type == 5) {
            view1.setBackgroundColor(getContext().getResources().getColor(R.color.type_5));
            view2.setBackgroundColor(getContext().getResources().getColor(R.color.type_5));
            tv_name.setTextColor(UIUtils.getColor(R.color.type_52));


            tv_name.setCompoundDrawablesWithIntrinsicBounds(
                    getContext().getResources().getDrawable(R.mipmap.ic_bt_e, null), null,null, null);
        }


        GlideImageLoader.onDisplayImage(getContext(), iv1, data.twoList.list1.get(0).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv2, data.twoList.list1.get(1).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv3, data.twoList.list1.get(2).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv4, data.twoList.list2.get(0).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv5, data.twoList.list2.get(1).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv6, data.twoList.list2.get(2).imgUrl);
        GlideImageLoader.onDisplayImage(getContext(), iv7, data.twoList.list2.get(3).imgUrl);


        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WBViewActivity.startActivity(getContext(),"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + data.twoList.list1.get(0).goodId + "&userId=" + SpUtlis.getId(),data.twoList.list1.get(0).goodId,true);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WBViewActivity.startActivity(getContext(),"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + data.twoList.list1.get(1).goodId + "&userId=" + SpUtlis.getId(),data.twoList.list1.get(1).goodId,true);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WBViewActivity.startActivity(getContext(),"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + data.twoList.list1.get(2).goodId + "&userId=" + SpUtlis.getId(),data.twoList.list1.get(2).goodId,true);
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WBViewActivity.startActivity(getContext(),"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + data.twoList.list2.get(0).goodId + "&userId=" + SpUtlis.getId(),data.twoList.list2.get(0).goodId,true);
            }
        });
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WBViewActivity.startActivity(getContext(),"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + data.twoList.list2.get(1).goodId + "&userId=" + SpUtlis.getId(),data.twoList.list2.get(1).goodId,true);
            }
        });
        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WBViewActivity.startActivity(getContext(),"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + data.twoList.list2.get(2).goodId + "&userId=" + SpUtlis.getId(),data.twoList.list2.get(2).goodId,true);
            }
        });
        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WBViewActivity.startActivity(getContext(),"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + data.twoList.list2.get(3).goodId + "&userId=" + SpUtlis.getId(),data.twoList.list2.get(3).goodId,true);
            }
        });

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
