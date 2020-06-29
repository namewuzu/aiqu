package com.diankong.sexstory.mobile.widget.loopviewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.diankong.sexstory.mobile.bean.GoodsPojo;


/**
 *
 * @since 1.0  2016/07/17
 */
public class AutoSwitchView extends AutoLoopSwitchBaseView {

    public AutoSwitchView(Context context) {
        super(context);
    }

    public AutoSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AutoSwitchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onSwitch(int index, Object o) {
        GoodsPojo model = (GoodsPojo) o;
        if (model != null) {
        }
    }

    @Override
    protected View getFailtView() {
        return null;
    }

    @Override
    protected long getDurtion() {
        return 3000;
    }

    @Override
    public void setAdapter(AutoLoopSwitchBaseAdapter adapter) {
        super.setAdapter(adapter);
        mHandler.sendEmptyMessage(LoopHandler.MSG_REGAIN);
    }
    public void setFocusable(boolean f){
        mViewPager.setFocusable(f);
    }
}