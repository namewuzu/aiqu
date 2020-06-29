package com.diankong.sexstory.mobile.modle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.diankong.sexstory.mobile.bean.GoodsPojo;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.widget.WBViewActivity;
import com.diankong.sexstory.mobile.widget.loopviewpage.AutoLoopSwitchBaseAdapter;

import java.util.List;


/**
 * @author ryze
 * @since 1.0  2016/07/17
 */
public class AutoSwitchAdapter extends AutoLoopSwitchBaseAdapter {

    private Context mContext;

    private List<GoodsPojo> mDatas;

    public AutoSwitchAdapter() {
        super();
    }

    public AutoSwitchAdapter(Context mContext, List<GoodsPojo> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getDataCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public View getView(final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        final GoodsPojo pojo = (GoodsPojo) getItem(position);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        GlideImageLoader.onDisplayImage(mContext,imageView,mDatas.get(position).url);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                WBViewActivity.startActivity(mContext,"商品详情","http://a123.lctfrx.cn/index2/sc.html?id=" + mDatas.get(position).id + "&userId=" + SpUtlis.getId(),mDatas.get(position).id,true);
            }
        });

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(mContext, BookDetailsActivity.class);
////                intent.putExtra("articleid",mDatas.get(position).bookId);
////                intent.putExtra("articlename",mDatas.get(position).bookName);
////                mContext.startActivity(intent);
//
//                IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext, ZhanghaoConfig.APP_ID);
//                // 检查手机或者模拟器是否安装了微信
//                if (!wxapi.isWXAppInstalled()) {
////                    ToastUtil.makeText("您还没有安装微信");
//                    return;
//                }
//
//                // 初始化一个WXWebpageObject对象
//                WXWebpageObject webpageObject = new WXWebpageObject();
//                // 填写网页的url
//                webpageObject.webpageUrl = "https://www.baidu.com";
//
//                // 用WXWebpageObject对象初始化一个WXMediaMessage对象
//                WXMediaMessage msg = new WXMediaMessage(webpageObject);
//                // 填写网页标题、描述、位图
//                msg.title = "试试分享";
//                msg.description = "随便试试";
//                // 如果没有位图，可以传null，会显示默认的图片
//                msg.setThumbImage(null);
//
//
//
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = "webpage";
//                req.message = msg;
//                req.scene = SendMessageToWX.Req.WXSceneSession;
//
//
//                ShareUtil.shareWxOrCircle(mContext,req);
////                wxapi.sendReq(req);
//             }
//        });
        return imageView;
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < getDataCount()) {

            return mDatas.get(position);
        }
        return null;
    }


    @Override
    public View getEmptyView() {
        return null;
    }

    @Override
    public void updateView(View view, int position) {

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
