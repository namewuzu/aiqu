package com.example.administrator.hjproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by apanda on 2017/6/26.
 */

public class MultyLineRadioGroup extends RadioGroup {
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public MultyLineRadioGroup(Context context) {
        super(context);
    }

    public MultyLineRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    public void addView(final View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof LinearLayout) {
            int childCount = ((LinearLayout) child).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = ((LinearLayout) child).getChildAt(i);
                if (view instanceof RadioButton) {
                    final RadioButton button = (RadioButton) view;
                    ((RadioButton) button).setOnTouchListener(new OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            ((RadioButton) button).setChecked(true);
                            checkRadioButton((RadioButton) button);
                            if (mOnCheckedChangeListener != null) {
                                mOnCheckedChangeListener.onCheckedChanged(MultyLineRadioGroup.this, button.getId());
                            }
                            return true;
                        }
                    });
                }
            }
        }

        super.addView(child, index, params);
    }

    private void checkRadioButton(RadioButton radioButton) {
        View child;
        int radioCount = getChildCount();
        for (int i = 0; i < radioCount; i++) {
            child = getChildAt(i);
            if (child instanceof RadioButton) {
                if (child == radioButton) {
                    // do nothing
                } else {
                    ((RadioButton) child).setChecked(false);
                }
            } else if (child instanceof LinearLayout) {
                int childCount = ((LinearLayout) child).getChildCount();
                for (int j = 0; j < childCount; j++) {
                    View view = ((LinearLayout) child).getChildAt(j);
                    if (view instanceof RadioButton) {
                        final RadioButton button = (RadioButton) view;
                        if (button == radioButton) {
                            // do nothing
                        } else {
                            ((RadioButton) button).setChecked(false);
                        }
                    }
                }
            }
        }
    }
}