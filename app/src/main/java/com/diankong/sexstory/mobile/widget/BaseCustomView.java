
package com.diankong.sexstory.mobile.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by hss01248 on 11/4/2015.
 */
public  abstract class BaseCustomView extends FrameLayout {
    protected   Context context;
    protected View rootView;

    public BaseCustomView(Context context) {
        this(context, null);
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (getLayoutId() == 0)throw new NullPointerException("baseCustomView layoutId is null");
        rootView = View.inflate(context,getLayoutId(), null);
        addView(rootView);
        initView(context,attrs,  DataBindingUtil.bind(rootView));
        initData(context );
        initEvent(context);
        this.context=context;

    }

    protected abstract int getLayoutId();
    protected abstract void initView(Context context, AttributeSet attrs, ViewDataBinding bindView);
    protected abstract void initEvent(Context context);
    protected abstract void initData(Context context);

}
