package com.diankong.sexstory.mobile.bean.lisener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.diankong.sexstory.mobile.modle.activity.UserDetailActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/12.
 * 描述：
 * =============================================
 */

public class MyConversationClickListener implements RongIM.ConversationClickListener {
    @Override
    public boolean onUserPortraitClick(Context _context, Conversation.ConversationType _conversationType, UserInfo _userInfo, String _s) {

        if(!_conversationType.equals(Conversation.ConversationType.SYSTEM)){
            Intent intent = new Intent(_context, UserDetailActivity.class);
            intent.putExtra("USERID", Integer.parseInt(_userInfo.getUserId()));
            _context.startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context _context, Conversation.ConversationType _conversationType, UserInfo _userInfo, String _s) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context _context, View _view, Message _message) {
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context _context, String _s, Message _message) {

        String str1=_s.substring(0, _s.indexOf(":"));
        String str2=_s.substring(str1.length()+1, _s.length());

        if(_message.getConversationType().equals(Conversation.ConversationType.SYSTEM)){

            Intent intent = new Intent(_context, UserDetailActivity.class);
            intent.putExtra("USERID", Integer.parseInt(str2));
            _context.startActivity(intent);

            return true;
        }

        return false;
    }

    @Override
    public boolean onMessageLongClick(Context _context, View _view, Message _message) {
        return false;
    }
}
