package com.example.administrator.hjproject.base

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import com.example.administrator.hjproject.R
import com.example.administrator.hjproject.utils.AndroidSystemUtils
import com.example.administrator.hjproject.utils.SwipeWindowHelper
import com.example.administrator.hjproject.utils.TUtil
import com.meizu.flyme.reflect.StatusBarProxy


//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃   神兽保佑
//    ┃　　　┃   代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 *
 * 包名:
 * 作者: created by 胡清 on 2017/6/27 13:18
 * 描述:
 *
 */

/**
 * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 * ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
 * │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
 * ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
 * │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
 * ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
 * │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
 * ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
 * │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
 * ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
 * │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
 * └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘
 */


abstract class BaseAct<B : android.databinding.ViewDataBinding, V : BaseViewModle<B>> : AppCompatActivity() {

    protected lateinit var b: B
    protected lateinit var viewModle: V


    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        if(initStatusBar())
        doBeforeSetcontentView()
        b = android.databinding.DataBindingUtil.setContentView<B>(this, getLayoutResource())
        //initStatusBar(false)
        viewModle = TUtil.getT(this, 1)!!

        viewModle.init(this, b)
        viewModle.onCreateSavedInstanceState(savedInstanceState)
        init()
    }


    open fun initStatusBar(fitSystemWindows: Boolean) {
        val mContentView = window.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        val mChildView = mContentView.getChildAt(0)
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            mChildView.setFitsSystemWindows(fitSystemWindows);
        }
    }

    open fun initStatusBar(): Boolean {
        return true
    }


    open fun init() {


    }

    override fun onStart() {
        super.onStart()
        viewModle.onStart()
    }

    open fun setDarkStatusTextColor(): Boolean {
        return true
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
        viewModle.onKeyDown(keyCode, event)
    }


    /**
     * 设置layout前配置
     */
    protected fun doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this)
        // 设置竖屏
        if (setPortrait()) requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // 默认着色状态栏

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initStatusBarTextColor(setDarkStatusTextColor())
    }


    /**
     * 默认着色状态栏
     */
    protected fun initStatusBarTextColor(isdark: Boolean) {
        if (AndroidSystemUtils.isMIUI()) MIUISetBarLightMode(window, isdark)
        else if (AndroidSystemUtils.isMeizuFlymeOS()) FlymeSetBarLightMode(window, isdark)
        else setStatusBarLightMode(window, isdark)
    }

    //
//
    fun setStatusBarLightMode(window: Window, isFontColorDark: Boolean): Boolean {
        if (isFontColorDark) window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        return true
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
            //    /**
//     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
//     * 可以用来判断是否为Flyme用户
//
//     * @param window 需要设置的窗口
//     * *
//     * @param dark   是否把状态栏字体及图标颜色设置为深色
//     * *
//     * @return boolean 成功执行返回true
//     */
    fun FlymeSetBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            try {
                StatusBarProxy.setImmersedWindow(window, true)
                result = true
            } catch (e: Exception) {
            }
        }
        StatusBarProxy.setStatusBarDarkIcon(window, dark)
        return result
    }

    open fun finishActivity() {
        AppManager.getAppManager().finishActivity()
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上

     * @param window 需要设置的窗口
     * *
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * *
     * @return boolean 成功执行返回true
     */
    protected fun MIUISetBarLightMode(window: Window?, dark: Boolean): Boolean {

        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                var field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                val darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (dark) extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                else extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT")
                val tranceFlag = field.getInt(layoutParams)
                extraFlagField.invoke(window, tranceFlag, tranceFlag) //
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }




    /**
     * 设置横竖屏

     * @return
     */
    open fun setPortrait(): Boolean {
        return true
    }


    /**
     * 是否支持滑动返回

     * @return
     */
    open fun supportSlideBack(): Boolean {
        return true
    }

    fun initToolBar(toolbar: Toolbar) {
//        toolbar.setTitle(title)
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_xqsh_back_black);
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false);
    }


    private var mSwipeWindowHelper: SwipeWindowHelper? = null

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (!supportSlideBack()) {
            return super.dispatchTouchEvent(ev)
        }

        if (mSwipeWindowHelper == null) {
            mSwipeWindowHelper = SwipeWindowHelper(window)
        }
        try {
            return mSwipeWindowHelper!!.processTouchEvent(ev) || super.dispatchTouchEvent(ev)
        } catch (_e: Exception) {
            _e.printStackTrace()
        }

        return false
    }


    /**
     * menu 点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home// 点击返回图标事件
            -> onBackPressed()
            else -> menuItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }


    fun menuItemSelected(item: MenuItem) {
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }


    protected abstract fun getLayoutResource(): Int

    override fun onResume() {
        super<AppCompatActivity>.onResume()
        android.util.Log.v(tag(), tag() + "::onResume()")
        viewModle.onResume()
    }

    private fun tag(): String? {
        return this.localClassName
    }

    override fun onPause() {
        super<AppCompatActivity>.onPause()
        viewModle.onPause()
        android.util.Log.v(tag(), tag() + "::onPause()")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModle.onActivityResult(requestCode, resultCode, data)
    }


    inline fun <reified T : Activity> Activity.gotoActivity() {
        val intent = Intent(this, T::class.java)
        this.startActivity(intent)
    }

    override fun onDestroy() {
        android.util.Log.v(tag(), tag() + "::onDestory()")
        super<AppCompatActivity>.onDestroy()
        AppManager.getAppManager().finishActivity(this)
        viewModle.onDestory()
    }

    override fun onStop() {
        super.onStop()
        viewModle.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        viewModle!!.onSaveInstanceState(outState)

    }

}

