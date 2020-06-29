package com.diankong.sexstory.mobile.modle.IM;

import android.content.Context;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/14.
 * 描述：
 * =============================================
 */

@ProviderTag(messageContent = PhoneInfo.class, showPortrait = false, centerInHorizontal = true)
public class PhoneProvider extends IContainerItemProvider.MessageProvider<PhoneInfo> {

    private Context context;

    @Override
    public View newView(Context _context, ViewGroup _viewGroup) {
        this.context = _context;
        View view = LayoutInflater.from(context).inflate(R.layout.chat_gift, _viewGroup, false);
        PhoneHolder holder = new PhoneHolder();
//        holder.tv_name = view.findViewById(R.id.tv_name);
//        holder.tv_desc = view.findViewById(R.id.tv_desc);
        holder.img = view.findViewById(R.id.iv_icon);

        view.setTag(holder);
        return view;

    }
    @Override
    public void bindView(View _view, int _i, PhoneInfo _phoneInfo, UIMessage _uiMessage) {
        PhoneHolder holder = (PhoneHolder) _view.getTag();
//        holder.tv_name.setText(_phoneInfo.getUserName());
//        holder.tv_desc.setText(_phoneInfo.getPhoneNum());
        holder.img.setImageResource(_phoneInfo.getImg());
    }

    @Override
    public Spannable getContentSummary(PhoneInfo _phoneInfo) {
        return null;
    }

    @Override
    public void onItemClick(View _view, int _i, PhoneInfo _phoneInfo, UIMessage _uiMessage) {

    }




    class PhoneHolder{

        RelativeLayout phoneLayout;
        TextView tv_name;
        TextView tv_desc;
        ImageView img;

    }


}
