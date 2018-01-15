package com.example.administrator.hjproject.widget.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.hjproject.BR;
import com.example.administrator.hjproject.base.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Created by Administrator on 2017/2/10 0010.
 */

public abstract class BaseDataBindingAdapter<T extends Object>
        extends RecyclerView.Adapter<BindingViewHolder> implements RefreshableAdapter<T> {

    protected Context mContext;
    protected BindingViewHolder footHolder;
    protected List<T> mData;
    protected BaseBindingItemPresenter mPresenter;
    protected LayoutInflater mLayoutInflater;
    protected Object footSinglefootData;
    protected int footSinglefootRes;
    protected int footSingleKey;
    protected MultiTypeBindingAdapter.FootDecorator footDecorator;

    public HashMap<Integer, BindingViewHolder> getBindingViewHolderMap() {
        return mBindingViewHolderMap;
    }

    public void setFootDecorator(MultiTypeBindingAdapter.FootDecorator footDecorator) {
        this.footDecorator = footDecorator;
    }

    protected HashMap<Integer, BindingViewHolder> mBindingViewHolderMap;

    public void setItemDecorator(ItemDecorator decorator) {
        this.decorator = decorator;
    }

    protected ItemDecorator decorator;

    public boolean isFooterView(int position) {
        if (footSingleKey == 0) {
            return false;
        } else if (footSinglefootRes <= 0) {
            return false;
        }
        int count = mData.size();
        return position >= count && position <= getItemCount();
    }

    public BaseDataBindingAdapter(Context context) {
        if (context == null) App.getContext();
        if (context == null) {
            return;
        }
        this.mContext = context;
        // mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater = LayoutInflater.from(mContext);
        mData = new ArrayList<>();
        mBindingViewHolderMap = new HashMap<Integer, BindingViewHolder>();
    }

    protected List<ViewDataBinding> itemDBinds = new ArrayList<>();

    @Override
    public void onBindViewHolder(BindingViewHolder holder, final int position) {
        if (mData == null) {
            throw new NullPointerException("BaseDataBindingAdapter  data is null");
        }

        ViewDataBinding binding = holder.getBinding();

        if (isFooterView(position)) {
            if (footSinglefootData == null) return;
            footHolder = holder;

            if (footDecorator != null) {
                footDecorator.footDecorator(holder, mData.size() + 1, getItemViewType(position), null);
            }
            // 分配数据
            binding.setVariable(BR.itemData, footSinglefootData);
            binding.executePendingBindings();
        } else {
            T data = mData.get(position);
            //分配事件
            if (mPresenter != null)
                binding.setVariable(BR.viewmodle, mPresenter);
            binding.setVariable(BR.itemPosition, position);
            if (decorator != null)
                decorator.decorator(holder, position, getItemViewType(position), mData);
            // 分配数据
            binding.setVariable(BR.itemData, data);
            binding.executePendingBindings();
            itemDBinds.add(binding);
        }
        if (canLongClick) {
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongPresenter.onItemLongClick(position, mData.get(position));
                    return canLongClick;
                }
            });
        }


    }

    public void addSingleFootConfig(int footKey, int footRes, Object footData) {
        footSingleKey = footKey;
        footSinglefootRes = footRes;
        footSinglefootData = footData;
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        if (footSingleKey > 0) {
            size = size + 1;
        }
        return size;
    }

    public void setItemPresenter(BaseBindingItemPresenter presenter) {
        mPresenter = presenter;
    }

    private BaseBindingItemLongPresenter itemLongPresenter;

    private boolean canLongClick = false;

    public void setItemLongPresenter(BaseBindingItemLongPresenter presenter) {
        itemLongPresenter = presenter;
        canLongClick = true;
    }

    public BaseBindingItemPresenter getBaseBindingItemPresenter() {
        return mPresenter;
    }

    public void refresh(int position) {
        notifyItemChanged(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void refresh(int position, Object o) {
        notifyItemChanged(position, o);
    }

    public interface ItemDecorator<T> {
        void decorator(BindingViewHolder holder, int position, int viewType, List<T> mData);
    }

    @Override
    public void refresh(List newData) {
        if (newData == null) {
            mData.clear();
            notifyDataSetChanged();
            return;
        }
        if (mData == null) {
            mData = newData;
            notifyDataSetChanged();
        } else {
            mData.clear();
            mData.addAll(newData);
            notifyDataSetChanged();
        }
    }

    public void refresh() {
        if (mData != null) {
            notifyDataSetChanged();
        }
    }


    @Override
    public void addAll(List newData) {
        if (newData == null) {
            return;
        }
        if (mData == null) {
            mData = newData;
            notifyDataSetChanged();
        } else {
            mData.addAll(newData);
            notifyDataSetChanged();
        }
    }

    public void addAllTop(List newData) {
        if (newData == null) {
            return;
        }
        if (mData == null) {
            mData = newData;
            notifyDataSetChanged();
        } else {
            mData.addAll(0, newData);
            notifyItemRangeInserted(0, newData.size());
        }
    }

    public void setmDataNull() {
        mData = null;
        // notifyDataSetChanged();
    }


    @Override
    public void clear() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void delete(int position) {
        if (mData != null && position < mData.size()) {
            mData.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public void delete(int position, int offs) {
        if (mData != null && position < mData.size()) {
            mData.remove(position);
            notifyItemRemoved(position + offs);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    @Override
    public void add(T data) {
        mData.add(data);
        notifyDataSetChanged();
        notifyItemRangeChanged(0, getItemCount());
    }


    public void add(int position, T data, int offs) {
        mData.add(position, data);
        notifyItemInserted(position + offs);
        //notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void add(int position, T data) {
        mData.add(position, data);
        // notifyDataSetChanged();
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void addLast(T data) {
        mData.add(mData.size(), data);
        // notifyDataSetChanged();
        notifyItemInserted(mData.size());
        notifyItemRangeChanged(mData.size(), getItemCount());
    }


    public void set(int position, T data) {
        mData.set(position, data);
        notifyDataSetChanged();
        //notifyItemInserted(position);
    }


    public int getLayoutRes() {
        return -1;
    }

    public int getLayoutRes(int itemViewType) {
        return -1;
    }

    public List<T> getListData() {
        return mData;
    }

    public BindingViewHolder getFootHolder() {
        return footHolder;
    }

    public ViewDataBinding getItemBind(int postion) {
        return itemDBinds.get(postion);
    }
    public boolean isDataEmpty(){
        return mData==null || mData.size()==0;
    }
}

