package com.diankong.sexstory.mobile.modle.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/12.
 * 描述：
 * =============================================
 */

public class ConversationActivity extends FragmentActivity {

    private String title;
    private TextView titles;
    private Toolbar _toolbar;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        titles = findViewById(R.id.title);
        _toolbar = findViewById(R.id.toolbar);
        iv_back = findViewById(R.id.iv_back);
//        ToolbarUtils.initToolBar(_toolbar,ConversationActivity.this);
        Uri data = getIntent().getData();
        title = data.getQueryParameter("title").toString();

        titles.setText(title);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });
    }
}
