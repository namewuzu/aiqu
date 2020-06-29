package com.diankong.sexstory.mobile.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.diankong.sexstory.mobile.utils.DimensionConvert;

/**
 * =============================================
 * 芒果瑞集团有限公司，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/1/3.
 * 描述：
 * =============================================
 */

public class PeopleViewPager extends ViewPager {
    private float xPosition;// 手指触摸点的x轴坐标
    private final float MINIMUM_DISTANCE = 3.0f;// 手指移动的最小距离
    private int distance;// 根据屏幕密度计算出来的，手指移动的最小距离

    public PeopleViewPager (Context context)
    {
        super (context);
        distance = DimensionConvert.dip2px (context,MINIMUM_DISTANCE);
    }

    public PeopleViewPager (Context context, AttributeSet attrs)
    {
        super (context, attrs);
        distance = DimensionConvert.dip2px (context,MINIMUM_DISTANCE);
    }

    @Override
    public boolean onInterceptTouchEvent (MotionEvent ev)
    {
        // 先保存手指按下的x轴的坐标
        if (ev.getAction () == MotionEvent.ACTION_DOWN)
            xPosition = ev.getX ();
        if (ev.getAction () == MotionEvent.ACTION_MOVE)
        {
            /*
             计算手指移动时的坐标跟按下的坐标之间的绝对值，如果超过给定的值，
             就认为viewpager需要滚动。通过调节distance的大小，可以改变滑动
             灵敏度
              */
            if (Math.abs (ev.getX () - xPosition) < distance)
                return false;
            else// 意思就是：touch事件已经被PeopleViewPager自己消费了，不会传递到子控件
                return true;
        }
        // 其他情况，依旧保持默认的处理方法
        return super.onInterceptTouchEvent (ev);
    }

}
