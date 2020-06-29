package com.diankong.sexstory.mobile.modle.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.ImageView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseFrag;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.ShareContent;
import com.diankong.sexstory.mobile.databinding.FragmentFreeBinding;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.modelview.FreeViewModle;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.diankong.sexstory.mobile.widget.WBViewActivity;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/18.
 * 描述：
 * =============================================
 */

public class FreeFragment extends BaseFrag<FragmentFreeBinding,FreeViewModle>{

    private Dialog _dialog;
    private Intent intent;
    private int type;
    private WebSettings webSettings;

    public static FreeFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("TYPE", type);
        FreeFragment fragment = new FreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_free;
    }


    @Override
    public void initView() {


    }

    @Override
    public void onResume() {
        super.onResume();
        type = getArguments().getInt("TYPE");
        initWB();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @SuppressLint("JavascriptInterface")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

//        viewModle.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            initWB();

        }
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        initWB();
//    }

    private void initWB() {

        webSettings = b.webview.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.setSupportZoom(true);//是否可以缩放，默认true
        webSettings.setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webSettings.setAppCacheEnabled(true);//是否使用缓存
        webSettings.setDomStorageEnabled(true);//DOM Storage

        // settings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 设置可以使用
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ;
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //   int screenWidth = ScreenUtils.getScreenWidth(_instance);
        b.webview.getSettings().setDefaultTextEncodingName("utf-8");
        b.webview.addJavascriptInterface(this, "android");

        //打开的网址
        b.webview.loadUrl("http://a123.lctfrx.cn/index2/scCountDown.html?userId=" + SpUtlis.getId());
//        if (type==1) {
//
//        }else{
//            b.webview.loadUrl("http://47.244.153.104/index2/scdial.html?userId=" + SpUtlis.getId());
//        }

    }

    @JavascriptInterface
    public void androidShareShow() {




    }

    //Android 调用js方法
    @SuppressLint("SetJavaScriptEnabled")
    private void testJS() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                b.webview.loadUrl("javascript:shuaxin()");
            }
        });


    }

    @JavascriptInterface
    public void androidShareShowDialog(final String goodId) {

//        ToastUtils.showShort("sss");

        if(_dialog==null){
            _dialog = DialogUtils.showAlertMid(R.layout.dialog_show, getActivity());
        }

        ImageView commit = _dialog.findViewById(R.id.commit);
        ImageView qx = _dialog.findViewById(R.id.qx);

        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _dialog.dismiss();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                testJS();
                tongJiWechat(Integer.parseInt(goodId));
                _dialog.dismiss();
            }

        });

        _dialog.show();
    }

    @JavascriptInterface
    public void androidShowDetail(String goodId,String userId) {

//        WBViewActivity.startActivity(getContext(),"商品详情","http://47.244.153.104/index2/sc.html?id=" + goodId + "&userId=" + userId,true);
        if(intent==null){
            intent = new Intent(getContext(),WBViewActivity.class);
        }
        intent.putExtra("webview_url","http://a123.lctfrx.cn/index2/sc.html?id=" + goodId + "&userId=" + userId);
        intent.putExtra("showShare",true);
        getContext().startActivity(intent);

        L.d("0000");
    }

    private void tongJiWechat(int goodId) {
        EasyHttp.get(Api.apiurl + "good/shareClick")
                .params("userId",String.valueOf(SpUtlis.getId()))
                .params("goodId",String.valueOf(goodId))
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

        UMengTools.setShareAction(getActivity(), SHARE_MEDIA.WEIXIN, shareContent, new UMShareListener() {
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
