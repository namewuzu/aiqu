package com.example.administrator.hjproject.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.administrator.hjproject.utils.ActivityLifecycleHelper;
import com.example.administrator.hjproject.utils.CrashHandler;
import com.example.administrator.hjproject.widget.pagemanage.PageManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.common.SocializeConstants;

//import com.tencent.mm.sdk.openapi.IWXAPI;


/**
 * Created by Android on 2016/9/29.
 */

public class App extends Application {
    private static App _Instance = null;
    private ActivityLifecycleHelper mActivityLifecycleHelper;
    private static Context mContext;
    private static Handler mHandler;
    public static PersistentCookieStore persistentCookieStore;

    public static Context getContext() {
        return mContext;
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
        initNet();
        String sdkVersion = SocializeConstants.SDK_VERSION;
        //  L.d(sdkVersion);
        // preinitX5WebCore();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initNet() {
        OkGo.init(this);
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo okGo = OkGo.getInstance();

            //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
//            if (L.isDebug) {
//                okGo.debug("OkHttp");
//            }
            //如果使用默认的 60秒,以下三行也不需要传
            okGo.setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
            persistentCookieStore = new PersistentCookieStore();
            okGo.setCookieStore(persistentCookieStore);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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


    public float getScreenDensity() {
        if (_Instance != null) {
            return _Instance.getResources().getDisplayMetrics().density;
        } else return 0;
    }


    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }




    public static App getInstance() {
        return _Instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
