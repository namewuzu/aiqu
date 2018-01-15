package com.example.administrator.hjproject.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.ColorRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.modle.activity.MainActivity;
import com.example.administrator.hjproject.utils.AndroidSystemUtils;
import com.example.administrator.hjproject.utils.SwipeWindowHelper;
import com.example.administrator.hjproject.utils.TUtil;
import com.example.administrator.hjproject.utils.ToastUtils;
import com.example.administrator.hjproject.widget.LoadingDialog;
import com.example.administrator.hjproject.widget.StatusBarCompat;
import com.meizu.flyme.reflect.StatusBarProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/11.
 * 描述：
 * =============================================
 */

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseViewModleforJava<B>> extends AppCompatActivity {

    public B mBinding;
    public V _viewModle;
    public Context _instance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        if(getLayoutId()!=0)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        _instance = App.getInstance();
        _viewModle = TUtil.getT(BaseActivity.this, 1);
        _viewModle.onCreateSavedInstanceState(savedInstanceState);
        _viewModle.init(this, mBinding);
        //_viewModle.init(mBinding);
        init();
    }

    private void init() {

    }

    /**
     * 设置横竖屏
     *
     * @return
     */
    protected boolean setPortrait() {
        return true;
    }

    /**
     * 设置layout前配置
     */
    protected void doBeforeSetcontentView() {
        //设置昼夜主题
        //initTheme();
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        if (setPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
        // 默认着色状态栏
        SetStatusBarColor();
    }




    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (_viewModle != null) {
            _viewModle.onSaveInstanceState(outState);
        }
    }

    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    private SwipeWindowHelper mSwipeWindowHelper;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!supportSlideBack()) {
            return super.dispatchTouchEvent(ev);
        }

        if (mSwipeWindowHelper == null) {
            mSwipeWindowHelper = new SwipeWindowHelper(getWindow());
        }
        try {
            return mSwipeWindowHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
        } catch (Exception _e) {
            _e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean supportSlideBack() {
        return true;
    }


    protected String getTAG() {
        return this.toString();
    }


    protected boolean setStatusBarLightMode(Window window, boolean isFontColorDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isFontColorDark) {
                // 沉浸式
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                //非沉浸式
                //   window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                //非沉浸式
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            return true;
        }
        return false;
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    protected static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
//                WindowManager.LayoutParams lp = window.getAttributes();
//                Field darkFlag = WindowManager.LayoutParams.class
//                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
//                Field meizuFlags = WindowManager.LayoutParams.class
//                        .getDeclaredField("meizuFlags");
//                darkFlag.setAccessible(true);
//                meizuFlags.setAccessible(true);
//                int bit = darkFlag.getInt(null);
//                int value = meizuFlags.getInt(lp);
//                if (dark) {
//                    value |= bit;
//                } else {
//                    value &= ~bit;
//                }
//                meizuFlags.setInt(lp, value);
//                window.setAttributes(lp);


                StatusBarProxy.setImmersedWindow(window, true);
                result = true;


//                StatusBarProxy.

            } catch (Exception e) {

            }
        }
        StatusBarProxy.setStatusBarDarkIcon(window, dark);
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    protected static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                int tranceFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }

                field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
                tranceFlag = field.getInt(layoutParams);
                extraFlagField.invoke(window, tranceFlag, tranceFlag); //

                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


/*    */

    /**
     * 设置主题
     *//*
    private void initTheme() {
        ChangeModeController.setTheme(this, R.style.DayTheme, R.style.NightTheme);
    }*/
    protected void finishActivity() {
        AppManager.getAppManager().finishActivity();
    }


    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {

        //if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)

//        setStatusBarLightMode(getWindow(), true);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
////            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
////            local LayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//            //    SetTranslanteBar(true);
//        } else {
//            //   SetTranslanteBar();
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initStatusBarTextColor(true);
    }

    protected void initStatusBarTextColor(boolean isdark) {

        if (AndroidSystemUtils.isMIUI()) {
            MIUISetStatusBarLightMode(getWindow(), isdark);
        } else if (AndroidSystemUtils.isMeizuFlymeOS()) {
            FlymeSetStatusBarLightMode(getWindow(), isdark);
        } else {
            setStatusBarLightMode(getWindow(), isdark);
        }
    }


    /**
     * 着色状态栏（4.4以上系统有效）
     */

    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar(boolean hideStatusBarBackground) {
        StatusBarCompat.translucentStatusBar(this, hideStatusBarBackground);
    }


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityAnim(Class<?> cls) {
        startActivityAnim(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityAnim(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.activity_open, 0);
    }


    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        startProgressDialog(msg, true);
    }


    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg, boolean cancleAble) {
        LoadingDialog.showDialogForLoading(this);
    }


    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    public void stopProgressDialogImidiatily() {
        LoadingDialog.cancelDialogNow();
    }


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUtils.showShort(text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUtils.showShort(resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUtils.showLong(resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUtils.showLong(text);
    }

/*    */

    /**
     * 带图片的toast
     *
     * @param
     * @param
     *//*
    public void showToastWithImg(String text, int res) {
        ToastUtils.showToastWithImg(text, res);
    }

    */
    @Override
    protected void onStart() {
        super.onStart();
        _viewModle.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _viewModle.onResume();
    }

    /**
     * 带图片的toast
     *
     * @param
     *//*
    public void showToastWithImg(String text) {
        showToastWithImg(text, R.mipmap.ic_yq_logo);
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        if (mPresenter != null)
            mPresenter.onActivityOnResume();
        super.onResume();
    }*/
    @Override
    protected void onPause() {
        super.onPause();
        _viewModle.onPause();
    }

    /**
     * 停止动画
     */
    @Override
    protected void onStop() {
        stopProgressDialogImidiatily();
        super.onStop();
        _viewModle.onStop();
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
        _viewModle.onDestory();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _viewModle.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        _viewModle.onKeyDown(keyCode,event);
        return super.onKeyDown(keyCode, event);
    }

    @MenuRes
    public int menuLayout = 0;

    /**
     * 显示右侧菜单， 不现实导航按钮
     *
     * @param toolbar
     * @param showNavi
     * @param menu_layout
     */
    public void initToolBar(Toolbar toolbar, boolean showNavi, @MenuRes int menu_layout) {
        initToolBar(toolbar, "", -1, showNavi);
        menuLayout = menu_layout;
    }

    /**
     * 显示右侧菜单， 显示导航按钮
     *
     * @param toolbar
     * @param menu_layout
     */
    public void initToolBar(Toolbar toolbar, @MenuRes int menu_layout) {
        initToolBar(toolbar, "", -1, true);
        menuLayout = menu_layout;
    }

    /**
     * 显示右侧菜单， 显示导航按钮
     *
     * @param toolbar
     * @param menu_layout
     */
    public void initToolBar(Toolbar toolbar, @MenuRes int menu_layout, @ColorRes int color) {
        initToolBar(toolbar, "", color, true);
        menuLayout = menu_layout;
    }

    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, String title, @ColorRes int title_color, boolean showNavi) {
        toolbar.setTitle(title);
        if (title_color != -1) {
            toolbar.setTitleTextColor(getResources().getColor(title_color));
        }

        setSupportActionBar(toolbar);
        if (showNavi) {
            toolbar.setNavigationIcon(R.mipmap.ic_xqsh_back_black);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    /**
     * 初始化 Toolbar
     */
    public void initToolBarWhiteNav(Toolbar toolbar) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_ggqjz_back_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, String title, @ColorRes int title_color) {
        initToolBar(toolbar, title, title_color, true);

    }


    public void initToolBar(Toolbar toolbar, String title) {
        initToolBar(toolbar, title, -1, true);
    }

    public void initToolBar(Toolbar toolbar) {
        initToolBar(toolbar, "", -1, true);
    }

    public void initToolBar(Toolbar toolbar, Bundle _bundle) {
        initToolBar(toolbar, "", -1, true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                onBackPressed();
                break;
            default:
                menuItemSelected(item);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void menuItemSelected(MenuItem item) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        if (menuLayout != 0) {
            getMenuInflater().inflate(menuLayout, menu);
        }
        return true;
    }


    /**
     * 取得UI树的根布局
     *
     * @return
     */
    public FrameLayout getRootView() {
        return (FrameLayout) getWindow().getDecorView().getRootView();
//        return (FrameLayout) findViewById(android.R.id.content);
    }


    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra("LAUNCHED_NOTICE", false)) {
            fromNoticeBackPressed();
        } else {
//            super.onBackPressed();
            AppManager.getAppManager().finishActivity(this);
            System.gc();
        }
    }

    /**
     * 从通知跳转回来来的,返回时候的操作,默认回到,消息列表
     */
    protected void fromNoticeBackPressed() {
        if (!AppManager.getAppManager().isOpenActivity(MainActivity.class)) {
            MainActivity.startActivity(_instance, true);
        } else {
//            super.onBackPressed();
            finish();
        }
    }

}
