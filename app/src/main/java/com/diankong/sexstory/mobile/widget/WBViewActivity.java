package com.diankong.sexstory.mobile.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseAct;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CommitPojo;
import com.diankong.sexstory.mobile.databinding.WebviewLayoutBinding;
import com.diankong.sexstory.mobile.event.CoinEvent;
import com.diankong.sexstory.mobile.event.RefreshEvent;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.GoodTypeActivity;
import com.diankong.sexstory.mobile.modle.activity.JieSuanActivity;
import com.diankong.sexstory.mobile.modle.activity.MainActivity;
import com.diankong.sexstory.mobile.modle.activity.ShopCarActivity;
import com.diankong.sexstory.mobile.modle.modelview.WBViewMoudle;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WBViewActivity extends BaseAct<WebviewLayoutBinding, WBViewMoudle> {


    public static final String TITLE = "webview_TITLE";
    public static final String URL = "webview_url";
    public static final String HTML = "webview_html";
    private String _url;
    private String _title;
    private String _html;
    private String price;
    private int id;
    private int bookIds;
    private boolean showShare;
    private CommitPojo commitPojo;
    private List<CommitPojo> _pojos;
    private Dialog _dialog;
    private Dialog _dialog1;

    public static void startActivity(Context context, String title, String _url) {
        startActivity(context, title, _url, false);
    }

    public static void startActivity(Context context, String _url, int bookId) {
        Intent starter = new Intent(context, WBViewActivity.class);
        starter.putExtra(URL, _url);
        starter.putExtra("bookId", bookId);
        context.startActivity(starter);
    }

    public static void startActivity(Context context, String title, String _url, boolean showShare) {
        Intent starter = new Intent(context, WBViewActivity.class);
        starter.putExtra(TITLE, title);
        starter.putExtra(URL, _url);
        starter.putExtra("showShare", showShare);
        context.startActivity(starter);
    }

    public static void startActivity(Context context, String title, String html, String url) {
        Intent starter = new Intent(context, WBViewActivity.class);
        starter.putExtra(TITLE, title);
        starter.putExtra(HTML, html);
        starter.putExtra(URL, url);
        context.startActivity(starter);
    }

    public static void startActivity(Context context, String title, String url, String price, int id) {
        Intent starter = new Intent(context, WBViewActivity.class);
        starter.putExtra(TITLE, title);
        starter.putExtra(URL, url);
        starter.putExtra("PRICE", price);
        starter.putExtra("ID", id);
        context.startActivity(starter);
    }

    public static void startActivity(Context context, String title, String url, int id, boolean showShare) {
        Intent starter = new Intent(context, WBViewActivity.class);
        starter.putExtra(TITLE, title);
        starter.putExtra(URL, url);
        starter.putExtra("showShare", showShare);
        starter.putExtra("ID", id);
        context.startActivity(starter);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void init() {
        //_bind = DataBindingUtil.setContentView(this, R.layout.webview_layout);
        commitPojo = new CommitPojo();
        _pojos = new ArrayList<>();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        _title = getIntent().getStringExtra(TITLE);
        _url = getIntent().getStringExtra(URL);
        _html = getIntent().getStringExtra(HTML);
        price = getIntent().getStringExtra("PRICE");
        id = getIntent().getIntExtra("ID", 0);

        bookIds = getIntent().getIntExtra("bookId", 0);

        showShare = getIntent().getBooleanExtra("showShare", false);

        if (!showShare) {
            b.llBottom.setVisibility(View.GONE);
        } else {
//            b.llBottom.setVisibility(View.VISIBLE);
            b.llBottom.setVisibility(View.GONE);
            b.toolbar.setVisibility(View.GONE);
        }


        ToolbarUtils.initToolBar(b.toolbar, this);

        boolean booleanExtra = getIntent().getBooleanExtra("showShare", false);
        if (booleanExtra) {
//            _bind.ivShare.setVisibility(View.VISIBLE);
        }

//        b.commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View _view) {
////                Bundle bundle = new Bundle();
////                bundle.putInt("GOODSID",id);
////                startActivity(JieSuanActivity.this,bundle);
//                Intent intent = new Intent(WBViewActivity.this, JieSuanActivity.class);
//                intent.putExtra("ID", id);
//                startActivity(intent);
//            }
//        });
//
//        b.join.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View _view) {
//                EasyHttp.get(Api.apiurl + "shopCar/addShopToShopCar")
//                        .params("goodId", String.valueOf(id))
//                        .params("userId", String.valueOf(SpUtlis.getId()))
//                        .execute(new CallBackProxy<BaseResult<GoodsPojo>, GoodsPojo>(new SimpleCallBack<GoodsPojo>() {
//                            @Override
//                            public void onError(ApiException e) {
//                                ToastUtils.showShort(e.getMessage());
//                            }
//
//                            @Override
//                            public void onSuccess(final GoodsPojo GoodsPojo) {
//
//                                ToastUtils.showShort("添加到购物车成功！");
//                            }
//
//                        }) {
//                        });
//            }
//        });

//        WebviewL
//        initToolBar(_bind.toolbar);
        WebSettings webSettings = b.webview.getSettings();

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
        b.webview.addJavascriptInterface(this, "java");
        if (TextUtils.isEmpty(_title)) {
            _title = b.webview.getTitle();
        }
        b.title.setText(_title);
        final int[] num = {0};
        b.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {

                if (url == null) return false;
                //解决微信支付返回ERR_UNKNOW_URL_SCHEME
                try {
                    if (url.startsWith("weixin://") //微信
                            || url.startsWith("alipays://") //支付宝
                            || url.startsWith("mailto://") //邮件
                            || url.startsWith("tel://")//电话
                            || url.startsWith("dianping://")//大众点评
                        //其他自定义的scheme
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }


                return super.shouldOverrideUrlLoading(view, url);


            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.equals(_url))
                    // startProgressDialog();
                    super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //  stopProgressDialog();
                super.onPageFinished(view, url);
            }


            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                L.d(error.toString());
            }
        });

        if (!TextUtils.isEmpty(_html)) {
            b.webview.loadDataWithBaseURL(null, _html, "text/html", "UTF-8", null);
        } else {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("Referer", "http://a123.aqtnfj.cn");
            b.webview.loadUrl(_url, extraHeaders);
        }
//
//        if (!TextUtils.isEmpty(_url)) {
//
//            Map<String, String> extraHeaders = new HashMap<>();
//            extraHeaders.put("Referer", "https://newh5.yueyangdongdong.com");
//            b.webview.loadUrl(_url, extraHeaders);
////                mActivityWebBinding.nativewebview.loadUrl(mUrl);
//        } else if (!TextUtils.isEmpty(_url)) {
//            // WebView加载web资源
//            mActivityWebBinding.nativewebview.loadData(mHtmlStr, "text/html; charset=UTF-8", null);
//        }
//        b.webview.setWebChromeClient(new WebChromeClient() {
//
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                L.d("newProgress : " + newProgress);
//                if (newProgress > 75) {
//
//                }
//            }
//        });

        b.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                testJS();
            }
        });


        CommonInterface commonInterface = new CommonInterface();
        commonInterface.setShowCallBack(new CommonInterface.ShowCallBack() {
            @Override
            public void onShown() {
                ToastUtils.showShort("分享成功");
                getAllData();
                EventBus.getDefault().post(new CoinEvent(true));
            }
        });


    }

    //Android 调用js方法
    @SuppressLint("SetJavaScriptEnabled")
    private void testJS() {
        b.webview.loadUrl("javascript:backPage()");
        EventBus.getDefault().post(new RefreshEvent(true));
        finish();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void backAndroid() {
        b.webview.loadUrl("javascript:backAndroid()");
        EventBus.getDefault().post(new RefreshEvent(true));
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getAllData() {
        b.webview.loadUrl("javascript:getAllData()");
    }

    //js 调用Android
    @JavascriptInterface
    public void androidShare() {
        CommonInterface.ShareDialog(this, bookIds);
    }

    @JavascriptInterface
    public void androidContentBack() {
        finish();
    }

    @JavascriptInterface
    public void androidToast(String _s) {
        ToastUtils.showShort(_s);
    }

    @JavascriptInterface
    public void androidDialog(final String id, final String count, final String goodName, final String goodSmallIcon, final String goodPrice, final String fee,String fare) {
        showDialoges(id,count,goodName,goodSmallIcon,goodPrice,fee,fare);
    }

    private void showDialoges(final String id, final String count, final String goodName, final String goodSmallIcon, final String goodPrice, final String fee, final String fare) {

         final Dialog  _dialog1 = DialogUtils.showAlertMid(R.layout.dialog_succeed,this);

        TextView tv_join = _dialog1.findViewById(R.id.tv_join);
        TextView tv_close = _dialog1.findViewById(R.id.tv_colse);
        TextView tv_gocar = _dialog1.findViewById(R.id.tv_gocar);

        tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(WBViewActivity.this, ShopCarActivity.class);
                startActivity(intent);
            }
        });

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _dialog1.dismiss();
            }
        });

        tv_gocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _pojos.clear();
                _pojos.add(new CommitPojo(Integer.parseInt(id), Integer.parseInt(count), goodName, goodSmallIcon, Double.parseDouble(goodPrice),Integer.parseInt(fare)));

                commitPojo.fee = Double.parseDouble(fee);
                commitPojo.fare = Integer.parseInt(fare);
                commitPojo.goods = _pojos;

                Intent intent = new Intent(WBViewActivity.this, JieSuanActivity.class);
                intent.putExtra("BEAN", commitPojo);
                startActivity(intent);
            }
        });
    }

    @JavascriptInterface
    public void androidBookCase() {
//        CommonAction.getInstance().OutSign();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("TYPE", 0);
        intent.putExtra("BACK", 1);
        startActivity(intent);
    }

    @JavascriptInterface
    public void androidShopCar() {
//        CommonAction.getInstance().OutSign();
        Intent intent = new Intent(this, ShopCarActivity.class);
        startActivity(intent);
    }



    @JavascriptInterface
    public void androidGoodType(String goodTypeId, String title) {
//        CommonAction.getInstance().OutSign();
        Intent intent = new Intent(this, GoodTypeActivity.class);
        intent.putExtra("goodTypeId", Integer.parseInt(goodTypeId));
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }

    @JavascriptInterface
    public void androidBack(final int userId, final int bookId, final int isUpload, final int chapterorder, final int thisChapId) {
        EasyHttp.post(Api.apiurl + "book/setRedRecord")
                .params("userId", String.valueOf(SpUtlis.getId()))
                .params("bookId", String.valueOf(bookIds))
                .params("isUpload", String.valueOf(isUpload))
                .params("chapterorder", String.valueOf(chapterorder))
                .params("chapterId", String.valueOf(thisChapId))
                .execute(new CallBackProxy<BaseResult<String>, String>(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort("失败" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(final String mTestPojo) {
                        finish();
                    }

                }) {
                });
    }


    @JavascriptInterface
    public void androidOrder(final String id, final String count, final String goodName, final String goodSmallIcon, final String goodPrice, final String fee,String fare) {
//        commitPojo.goods
        _pojos.clear();
        _pojos.add(new CommitPojo(Integer.parseInt(id), Integer.parseInt(count), goodName, goodSmallIcon, Double.parseDouble(goodPrice),Integer.parseInt(fare)));

        commitPojo.fee = Double.parseDouble(fee);
        commitPojo.fare = Integer.parseInt(fare);
        commitPojo.goods = _pojos;

        Intent intent = new Intent(this, JieSuanActivity.class);
        intent.putExtra("BEAN", commitPojo);
        startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (b.webview.canGoBack()) {
                b.webview.goBack();
                return true;
            }
            onBackPressed();
            return true;
        }
        return false;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.webview_layout;
    }


}
