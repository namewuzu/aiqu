package com.example.administrator.hjproject.base;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/11.
 * 描述：
 * =============================================
 */

public class AppManager {
    private static Stack<AppCompatActivity> activityStack;
    private static Stack<AppCompatActivity> activityStackOk;
    private volatile static AppManager instance;

    private AppManager() {

    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                    activityStack = new Stack();
                }
            }

        }
        return instance;
    }


    public Stack<AppCompatActivity> getActivityStack() {
        return activityStack;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(AppCompatActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<AppCompatActivity>();
        }
        activityStack.add(activity);
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivityOK(AppCompatActivity activity) {
        if (activityStackOk == null) {
            activityStackOk = new Stack<AppCompatActivity>();
        }
        if (!activityStackOk.contains(activity)) {
            activityStackOk.add(activity);
        }
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public AppCompatActivity currentActivity() {
        try {
            AppCompatActivity activity = activityStack.lastElement();
            return activity;
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public AppCompatActivity preActivity() {
        int index = activityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        AppCompatActivity activity = activityStack.get(index);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        AppCompatActivity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(AppCompatActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(AppCompatActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (AppCompatActivity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    public void returnToActivity(Class<?> cls) {
        while (activityStack.size() != 0)
            if (activityStack.peek().getClass() == cls) {
                break;
            } else {
                finishActivity(activityStack.peek());
            }
    }


    /**
     * 是否已经打开指定的activity
     *
     * @param cls
     * @return
     */
    public boolean isOpenActivity(Class<?> cls) {
        if (activityStack != null) {

            for (AppCompatActivity activity : activityStack) {
                if (cls == activity.getClass()) {
                    return true;
                }
            }

//            for (int i = 0, size = activityStack.size(); i < size; i++) {
//                Class<? extends Activity> aClass = activityStack.get(i).getClass();
//                if (cls == aClass) {
//                    return true;
//                }
//            }
        }
        return false;
    }

    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
        } catch (Exception e) {

        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }
}
