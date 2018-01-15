package com.example.administrator.hjproject.base

import android.app.Activity
import android.content.Intent
import android.databinding.ViewDataBinding
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Window
import com.example.administrator.hjproject.R
import com.example.administrator.hjproject.utils.AndroidSystemUtils
import com.example.administrator.hjproject.utils.TUtil
import com.example.administrator.hjproject.utils.ToastUtils
import com.meizu.flyme.reflect.StatusBarProxy


/**
 *
 * 类名: com.example.administrator.hjproject.base$
 * 标记:
 * 描述: $Description$
 *
 */
abstract class BaseFrag<B : ViewDataBinding, V : BaseViewModle<B>> : Fragment(), java.io.Serializable {
    protected abstract fun getLayoutResource(): Int
    protected lateinit var viewModle: V
    protected lateinit var b: B


    override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        //   doBeforeSetcontentView()
        val view = inflater!!.inflate(getLayoutResource(), container, false)
        b = android.databinding.DataBindingUtil.bind<B>(view)
        viewModle = TUtil.getT(this, 1)!!
        viewModle.initFrag(this)
        viewModle.init(activity as AppCompatActivity, b, context)
        viewModle.init(activity as AppCompatActivity, b)
        viewModle.onCreateSavedInstanceState(savedInstanceState)
        initView()
        return view

    }


    override fun onStart() {
        super.onStart()
        viewModle.onStart()

    }

    abstract fun initView()

    fun initToolBar(toolbar: Toolbar, title: String) {
        toolbar.setTitle(title)
        toolbar.setTitleTextColor(context.resources.getColor(R.color.titlecolor))
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }


    inline fun <reified T : Activity> Activity.gotoActivity() {
        val intent = Intent(this, T::class.java)
        activity.startActivity(intent)
    }


    private fun tag(): String? {
        return this.javaClass.simpleName
    }


    protected fun toast(text: String) {
        ToastUtils.showShort(context, text)
    }


    override fun onResume() {
        super.onResume()
        viewModle.onResume()

    }


    override fun onDestroy() {
        super.onDestroy()
        viewModle.onDestory()
    }


    override fun onPause() {
        super.onPause()
        viewModle.onPause()
    }

    /**
     * 设置layout前配置
     */
    protected fun doBeforeSetcontentView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            initStatusBarTextColor(setDarkStatusTextColor())
    }


    open fun setDarkStatusTextColor(): Boolean {
        return true
    }

    /**
     * 默认着色状态栏
     */
    protected fun initStatusBarTextColor(isdark: Boolean) {
        if (AndroidSystemUtils.isMIUI()) MIUISetBarLightMode(activity.window, isdark)
        else if (AndroidSystemUtils.isMeizuFlymeOS()) FlymeSetBarLightMode(activity.window, isdark)
        else setStatusBarLightMode(activity.window, isdark)
    }

    //
    fun setStatusBarLightMode(window: Window, isFontColorDark: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isFontColorDark) window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            return true
        }
        return false
    }

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    StatusBarProxy.setImmersedWindow(window, true)
                }
                result = true
            } catch (e: Exception) {
            }
        }
        StatusBarProxy.setStatusBarDarkIcon(window, dark)
        return result
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
}

