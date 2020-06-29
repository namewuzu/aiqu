package com.diankong.sexstory.mobile.modle.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.GroupTagsPojo;

import java.util.List;


/**
 * @author fyales
 * @since 8/26/15.
 */
public class MoneyAdapter extends BaseAdapter {

    private Context mContext;
    private List<GroupTagsPojo> mList;

    public MoneyAdapter(Context context, List<GroupTagsPojo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public GroupTagsPojo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.money_tagview,parent, false);
            holder = new ViewHolder(convertView);
            //   holder.tagBtn = (Button) convertView.findViewById(R.id.tag_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GroupTagsPojo pojo = getItem(position);

//        holder.tagBtn.setText(pojo.getTagname());
        if (pojo.getSelect()==0) {
            holder.tagBtn.setBackgroundResource(R.drawable.frame_white_withdra);
            holder.tagBtn.setTextColor(mContext.getResources().getColor(R.color.gray_333));
        } else {
            holder.tagBtn.setBackgroundResource(R.drawable.shape_text_red_shadow_normal);
            holder.tagBtn.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
        holder.tagBtn.setSelected(pojo.getSelect() != 0);

        holder.tagBtn.setText(pojo.getTagname());
        return convertView;
    }

    static class ViewHolder {
         TextView tagBtn;
//        private final TagviewBinding _bind;

        ViewHolder(View _view) {
//            _bind = DataBindingUtil.bind(_view);
            tagBtn = _view.findViewById(R.id.tag_btn);
        }
    }
}
