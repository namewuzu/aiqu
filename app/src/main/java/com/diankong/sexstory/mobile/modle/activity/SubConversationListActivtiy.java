package com.diankong.sexstory.mobile.modle.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.diankong.sexstory.mobile.R;

import io.rong.imkit.fragment.SubConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/14.
 * 描述：
 * =============================================
 */

public class SubConversationListActivtiy extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subconversationlist);
        FragmentManager fragmentManage = getSupportFragmentManager();
        SubConversationListFragment fragement = (SubConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("subconversationlist")
                .appendQueryParameter("type", Conversation.ConversationType.PRIVATE.getName())
                .build();
        fragement.setUri(uri);
    }
}
