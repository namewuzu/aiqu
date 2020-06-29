package com.diankong.sexstory.mobile.base;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.UserInfoPojo;
import com.diankong.sexstory.mobile.bean.WxPojo;
import com.diankong.sexstory.mobile.modle.IM.MyExtensionModule;
import com.diankong.sexstory.mobile.modle.IM.PhoneInfo;
import com.diankong.sexstory.mobile.modle.IM.PhoneProvider;
import com.diankong.sexstory.mobile.modle.push.RongCloudEvent;
import com.diankong.sexstory.mobile.utils.ACache;
import com.diankong.sexstory.mobile.utils.ActivityLifecycleHelper;
import com.diankong.sexstory.mobile.utils.CommonInterface;
import com.diankong.sexstory.mobile.utils.CrashHandler;
import com.diankong.sexstory.mobile.utils.DialogUtils;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.UMengTools;
import com.diankong.sexstory.mobile.widget.pagemanage.PageManager;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.model.HttpParams;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

//import com.tencent.mm.sdk.openapi.IWXAPI;


/**
 * Created by Android on 2016/9/29.
 */

public class App extends Application {
    private static App _Instance = null;
    private ActivityLifecycleHelper mActivityLifecycleHelper;
    private static Context mContext;
    private static Handler mHandler;
    public static UserInfoPojo userInfo;
    public static IWXAPI mWxApi;
    //    public static PersistentCookieStore persistentCookieStore;


    public static Context getContext() {
        return mContext;
    }

    public static IWXAPI getmWxApi (){
        return mWxApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _Instance = this;
        mHandler = new Handler();
        mContext = getApplicationContext();
        // CleanManager.cleanAllData(this);
        //  2. 启动计时器
//        handler.postDelayed(runnable, 10000);//每两秒执行一次runnable.
        init();

        Phoenix.config()
                .imageLoader(new ImageLoader() {
                    @Override
                    public void loadImage(Context mContext, ImageView imageView
                            , String imagePath, int type) {
                        Glide.with(mContext)
                                .load(imagePath)
                                .into(imageView);
                    }
                });

//        initNet();
//        String sdkVersion = SocializeConstants.SDK_VERSION;
//        //  L.d(sdkVersion);
//        // preinitX5WebCore();
        Logger.addLogAdapter(new AndroidLogAdapter());
//
        UMShareAPI.get(this);//初始化友盟sdk
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,
                ZhanghaoConfig.UMENG_KEY);
        UMConfigure.setLogEnabled(true);
        PlatformConfig.setWeixin(ZhanghaoConfig.APP_ID, ZhanghaoConfig.WX_SECRET_KEY);
//        //PushSDK初始化(如使用推送SDK，必须调用此方法)
//        UmengPushManage.initUpush(this);
//
//        MobclickAgent.setDebugMode(true);
//        MobclickAgent.setCatchUncaughtExceptions(true); //是否需要错误统计功能
//        MobclickAgent.openActivityDurationTrack(false); //来禁止默认的Activity页面统计方式。 http://www.tuicool.com/articles/7nyuUj
//        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        initEasyHttp();
        X5Init();
        RongIM.init(this);
        RongCloudEvent.init(this);

        RongIM.registerMessageType(PhoneInfo.class);
        RongIM.registerMessageTemplate(new PhoneProvider());

        setMyExtensionModule();
        // 三个参数分别是上下文、应用的appId、是否检查签名（默认为false）
        mWxApi = WXAPIFactory.createWXAPI(this, ZhanghaoConfig.APP_ID, true);
// 注册
//        mWxApi.handleIntent(new Intent(),this );
        mWxApi.registerApp(ZhanghaoConfig.APP_ID);




    }

    public void X5Init() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                L.e("X5", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                L.e("X5", " onCoreInitFinished   @@@@@@@@@@" );
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }


    private void initEasyHttp() {
        EasyHttp.init(this);//默认初始化

        //全局设置请求头
        HttpHeaders headers = new HttpHeaders();
//        headers.put("User-Agent", SystemInfoUtils.getUserAgent(this, AppConstant.APPID));
        //全局设置请求参数
        HttpParams params = new HttpParams();
//        params.put("token", SpUtlis.getToken());
//        params.put("userId", String.valueOf(SpUtlis.getId()));
        if(SpUtlis.getUserMsgPojo()!=null){
            params.put("userType", SpUtlis.getUserMsgPojo().userType);
        }

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        EasyHttp.getInstance()

                //可以全局统一设置全局URL
                .setBaseUrl("https://www.apiopen.top")//设置全局URL

                // 打开该调试开关并设置TAG,不需要就不要加入该行
                // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                .debug("EasyHttp", true)

                //如果使用默认的60秒,以下三行也不需要设置
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 100)
                .setConnectTimeout(60 * 100)

                //可以全局统一设置超时重连次数,默认为3次,那么最差的情况会请求4次(一次原始请求,三次重连请求),
                //不需要可以设置为0
                .setRetryCount(1)//网络不好自动重试3次
                //可以全局统一设置超时重试间隔时间,默认为500ms,不需要可以设置为0
                .setRetryDelay(500)//每次延时500ms重试
                //可以全局统一设置超时重试间隔叠加时间,默认为0ms不叠加
                .setRetryIncreaseDelay(500)//每次延时叠加500ms

                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体请看CacheMode
                .setCacheMode(com.zhouyou.http.cache.model.CacheMode.NO_CACHE)
                //可以全局统一设置缓存时间,默认永不过期
                .setCacheTime(-1)//-1表示永久缓存,单位:秒 ，Okhttp和自定义RxCache缓存都起作用
                //全局设置自定义缓存保存转换器，主要针对自定义RxCache缓存
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                //全局设置自定义缓存大小，默认50M
                .setCacheMaxSize(100 * 1024 * 1024)//设置缓存大小为100M
                //设置缓存版本，如果缓存有变化，修改版本后，缓存就不会被加载。特别是用于版本重大升级时缓存不能使用的情况
                .setCacheVersion(1)//缓存版本为1
                //.setHttpCache(new Cache())//设置Okhttp缓存，在缓存模式为DEFAULT才起作用

                //可以设置https的证书,以下几种方案根据需要自己设置
                .setCertificates()                                  //方法一：信任所有证书,不安全有风险
                //.setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
                //配置https的域名匹配规则，不需要就不要加入，使用不当会导致https握手失败
                //.setHostnameVerifier(new SafeHostnameVerifier())
                //.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCommonHeaders(headers)//设置全局公共头
                .addCommonParams(params);//设置全局公共参数
        //.addNetworkInterceptor(new NoCacheInterceptor())//设置网络拦截器
        //.setCallFactory()//局设置Retrofit对象Factory
        //.setCookieStore()//设置cookie
        //.setOkproxy()//设置全局代理
        //.setOkconnectionPool()//设置请求连接池
        //.setCallbackExecutor()//全局设置Retrofit callbackExecutor
        //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
        //.addInterceptor(new GzipRequestInterceptor())//开启post数据进行gzip后发送给服务器
//                .addInterceptor(new CustomSignInterceptor());//添加参数签名拦截器
    }

//    private void initNet() {
//        OkGo.init(this);
//        try {
//            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
//            OkGo okGo = OkGo.getInstance();
//
//            //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
////            if (L.isDebug) {
////                okGo.debug("OkHttp");
////            }
//            //如果使用默认的 60秒,以下三行也不需要传
//            okGo.setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
//                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
//                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
//                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
//                    .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
//                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
//                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
//            persistentCookieStore = new PersistentCookieStore();
//            okGo.setCookieStore(persistentCookieStore);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private void init() {
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());
        initLoadingPageManager();
        unCatchException();
    }


    public static Handler getMainHandler() {
        return mHandler;
    }


    private void initLoadingPageManager() {
        /*LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.pager_error;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.pager_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.pager_empty;*/
        PageManager.initInApp(this);
    }

    private void unCatchException() {

        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));

//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread _thread, Throwable _throwable) {
//                //处理全局异常
//                //获取错误的信息
//                String errorInfo = StringUtils.getErrorInfo(_throwable);
////                String errorInfo = _throwable.getMessage();
//  HttpLogManager.getInstance().recordErrorLog(errorInfo);
//                L.d(errorInfo);
//                final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        });
    }

    // 模拟器的启动报错
/*    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    public float getScreenDensity() {
        if (_Instance != null) {
            return _Instance.getResources().getDisplayMetrics().density;
        } else return 0;
    }


    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }

    public static void setUserInfo(UserInfoPojo userInfo) {
        if (userInfo != null) {
            App.userInfo = null;
            App.userInfo = userInfo;
        }
    }

    public static UserInfoPojo getUserInfo() {
        if (userInfo == null) {
            String userInfo = SpUtlis.UserInfo.getUserInfo();
            if (TextUtils.isEmpty(userInfo)) {
                App.userInfo = new UserInfoPojo();
            } else {
                App.userInfo = com.alibaba.fastjson.JSONObject.parseObject(userInfo, UserInfoPojo.class);
            }

        }
        return userInfo;
    }

    public static void clearUserInfo() {
        SpUtlis.UserInfo.saveUserInfo("");
        ACache.SharedPrefsUtils.setStringPreference(Consts.USER_GUID, "");
        App.userInfo = null;
    }


    public static App getInstance() {
        return _Instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


//    //各个平台的配置
//    {
//        //微信
//        if(BuildConfig.DEBUG){
//            PlatformConfig.setWeixin(ZhanghaoConfig.APP_ID_DEBUG, ZhanghaoConfig.WX_SECRET_KEY_DEBUG);
//        }else{
//            PlatformConfig.setWeixin(ZhanghaoConfig.APP_ID, ZhanghaoConfig.WX_SECRET_KEY);
//        }
//    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
