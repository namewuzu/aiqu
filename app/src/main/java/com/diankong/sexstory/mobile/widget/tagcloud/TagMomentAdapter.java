package com.diankong.sexstory.mobile.widget.tagcloud;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.databinding.TagviewBinding;

import java.util.Map;
import java.util.Set;



/**
 * @author fyales
 * @since 8/26/15.
 */
public class TagMomentAdapter extends BaseAdapter {

    private Context mContext;
    private Map<Boolean,String > mList;

    public TagMomentAdapter(Context context, Map<Boolean,String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tagview, null);
            holder = new ViewHolder(convertView);
            //   holder.tagBtn = (Button) convertView.findViewById(R.id.tag_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Set<Boolean> booleen = mList.keySet();
        for (int i = 0; i < booleen.size(); i++) {

        }
//        String  = mList.get(booleen);
//
//        holder._bind.setPojo(pojo);
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
