package com.example.administrator.hjproject.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2017/12/12.
 * 描述：
 * =============================================
 */

interface ViewModleRecycleforJava {
    void onResume();
    Boolean onKeyDown(int keyCode,KeyEvent event);
    void onStart();
    void onPause();
    void onDestory();
    void onStop();
    void onActivityResult(int requestCode,int resultCode, Intent data);
    void onCreateSavedInstanceState(Bundle savedInstanceState);
}
