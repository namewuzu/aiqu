package com.diankong.sexstory.mobile.widget.tagcloud;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.GroupTagsPojo;
import com.diankong.sexstory.mobile.databinding.TagviewBinding;

import java.util.List;


/**
 * @author fyales
 * @since 8/26/15.
 */
public class TagBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<GroupTagsPojo> mList;
    private int type;

    public TagBaseAdapter(Context context, List<GroupTagsPojo> list,int type) {
        mContext = context;
        mList = list;
        this.type = type;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tagview, parent, false);
            holder = new ViewHolder(convertView);
//               holder.tagBtn = (Button) convertView.findViewById(R.id.tag_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GroupTagsPojo pojo = getItem(position);
        if(type==1){
            holder._bind.tagBtn.setText("#" + pojo.topicTitle + "#");
        }else{
            holder._bind.tagBtn.setText(pojo.getTagname());
        }


//        if (position == 0 || position == 3 || position == 6) {
//            holder._bind.tagBtn.setBackgroundResource(R.drawable.radius_8_green);
//        } else if (position == 1 || position == 4 || position == 7) {
//            holder._bind.tagBtn.setBackgroundResource(R.drawable.radius_8_pink);
//        } else if (position == 2 || position == 5 || position == 8) {
//            holder._bind.tagBtn.setBackgroundResource(R.drawable.radius_8_org);
//        }


        holder._bind.setPojo(pojo);


        return convertView;
    }

    static class ViewHolder {
        // Button tagBtn;
        private final TagviewBinding _bind;

        ViewHolder(View _view) {
            _bind = DataBindingUtil.bind(_view);
        }
    }
}
