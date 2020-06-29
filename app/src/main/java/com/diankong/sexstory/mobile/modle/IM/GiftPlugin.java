package com.diankong.sexstory.mobile.modle.IM;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.utils.DialogUtils;

import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/14.
 * 描述：
 * =============================================
 */

public class GiftPlugin implements IPluginModule {


    @Override
    public Drawable obtainDrawable(Context _context) {
        return _context.getResources().getDrawable(R.mipmap.gouwuche);//功能图标
    }

    @Override
    public String obtainTitle(Context _context) {
        return "礼物";
    }

    @Override
    public void onClick(Fragment _fragment, final RongExtension _rongExtension) {
//        StartDajianActivity.start(fragment.getActivity(),rongExtension.getTargetId());//点击事件，rongExtension.getTargetId()获取到聊天对象的id
//        Intent intent = new Intent(_fragment.getActivity(), AddressActivity.class);
//        _fragment.getActivity().startActivity(intent);

//        DialogUtils.showDialogPrompt(_fragment.getActivity(), "提示", "正在研发中..." + _rongExtension.getTargetId(), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface _dialogInterface, int _i) {
//                sendPhone("sss","ddd",_rongExtension.getTargetId());
//            }
//        });

        final Dialog dialog = DialogUtils.showAlert(R.layout.chat_gift, _fragment.getActivity());

        ImageView imageView = dialog.findViewById(R.id.iv_icon);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                sendPhone("sss","ddd",_rongExtension.getTargetId(),R.mipmap.free);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void onActivityResult(int _i, int _i1, Intent _intent) {

    }

    public void sendPhone(String name,String phone,String chatUserid,int img){

        final PhoneInfo info =new PhoneInfo();
        info.setUserName(name);
        info.setPhoneNum(phone);
        info.setImg(img);
        //chatUserid 是接收消息方的id   Conversation.ConversationType 是消息会话的类型在这里表示的是私聊
        Message message = Message.obtain(chatUserid, Conversation.ConversationType.PRIVATE,info);


        RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback() {
            @Override //表示消息添加到本地数据库
            public void onAttached(Message message) {

            }
            @Override//消息发送成功
            public void onSuccess(Message message) {

            }
            @Override //消息发送失败
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {

            }
        });

    }

}
