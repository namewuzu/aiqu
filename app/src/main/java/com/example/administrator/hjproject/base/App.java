package com.example.administrator.hjproject.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.administrator.hjproject.utils.ActivityLifecycleHelper;
import com.example.administrator.hjproject.utils.CrashHandler;
import com.example.administrator.hjproject.widget.pagemanage.PageManager;
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
        String sdkVersion = SocializeConstants.SDK_VERSION;
        //  L.d(sdkVersion);
        // preinitX5WebCore();
        Logger.addLogAdapter(new AndroidLogAdapter());
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
