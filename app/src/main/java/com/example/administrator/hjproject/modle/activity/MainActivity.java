package com.example.administrator.hjproject.modle.activity;

import android.content.Context;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.BaseAct;
import com.example.administrator.hjproject.databinding.ActivityMainBinding;
import com.example.administrator.hjproject.modle.modelview.MainModelView;

public class MainActivity extends BaseAct<ActivityMainBinding,MainModelView> {

    public static void startActivity(Context _context, boolean _b) {

    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
