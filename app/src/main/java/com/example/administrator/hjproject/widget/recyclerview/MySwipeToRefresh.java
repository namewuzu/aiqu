package com.example.administrator.hjproject.widget.recyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MySwipeToRefresh extends SwipeRefreshLayout {


    public MySwipeToRefresh(Context context) {
        super(context);
    }

    public MySwipeToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int moveLength = 10;
    private float downX;
    private float downY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                try {

                    float move_x = event.getX();
                    float move_y = event.getY();
                    float moveX = move_x - downX;
                    float moveY = move_y - downY;
                    if (Math.abs(moveX) > Math.abs(moveY)) {//水平方向上拉动,则不拦截

                        return false;

                    }
                } catch (IllegalArgumentException e) {

                    e.printStackTrace();
                }
                break;
            default:
                break;

        }

        return super.onInterceptTouchEvent(event);
    }
}
