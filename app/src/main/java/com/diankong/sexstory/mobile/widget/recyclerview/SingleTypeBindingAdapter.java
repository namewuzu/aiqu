package com.diankong.sexstory.mobile.widget.recyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class SingleTypeBindingAdapter  extends BaseDataBindingAdapter  {
    public static final int ITEM_VIEW_NORMAL_TYPE = 10001;
    private int mLayoutRes;

    public SingleTypeBindingAdapter(Context context, List data, int layoutRes) {
        super(context);
        if (data == null) {
            data = new ArrayList();
        }
        mData = data;
        mLayoutRes = layoutRes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BindingViewHolder bindingViewHolder = new BindingViewHolder(DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(viewType), parent, false));
        mBindingViewHolderMap.put(viewType, bindingViewHolder);
        return bindingViewHolder;
       // View view = View.inflate(mContext, getLayoutRes(viewType), parent);
       // View view = LayoutInflater.from(mContext).inflate(getLayoutRes(viewType),parent);
      //  return new BindingViewHolder(DataBindingUtil.bind(view));
    }

    @Override
    @LayoutRes
    public int getLayoutRes(int viewType) {
        if (viewType == footSingleKey) {
            return footSinglefootRes;
        }
        return mLayoutRes;
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterView(position)) {
            return footSingleKey;
        }
        return ITEM_VIEW_NORMAL_TYPE;
    }


}
