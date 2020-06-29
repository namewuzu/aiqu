package com.diankong.sexstory.mobile.widget.recyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.diankong.sexstory.mobile.base.Consts;
import com.diankong.sexstory.mobile.BR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class MultiTypeBindingAdapter<T> extends BaseDataBindingAdapter<T> {
    public static final int ITEM_VIEW_NORMAL_TYPE = 10000;
    public static final int ITEM_VIEW_NO_SHOW_TYPE = -1;
    protected ArrayMap<Integer, Integer> multiTypeMap;
    protected ArrayMap<Integer, BindingViewHolder> multiTypeFootHolder;
    public List<Integer> headKeyList;
    private ArrayList headDataList;
    private ArrayList<Integer> footKeyList;
    private ArrayList footDataList;
    private BaseBindingItemPresenter headPresenter;
    private BaseBindingItemPresenter footPresenter;


    private AdapterTypeConfig footAdapterTypeConfig;

    public MultiTypeBindingAdapter(Context context) {
        super(context);
    }

    public MultiTypeBindingAdapter(Context context, List data) {
        super(context);
        if (data == null) {
            data = new ArrayList();
        }
        mData = data;
        multiTypeMap = new ArrayMap<>();
        multiTypeFootHolder = new ArrayMap<>();
    }

    /**
     * @doc 单个item的viewType时使用
     */
    public MultiTypeBindingAdapter(Context context, List data, int itemDataRes) {
        super(context);
        if (data == null) {
            data = new ArrayList();
        }
        mData = data;
        multiTypeMap = new ArrayMap<>();
        multiTypeFootHolder = new ArrayMap<>();
        multiTypeMap.put(ITEM_VIEW_NORMAL_TYPE, itemDataRes);
    }


    public interface AdapterTypeConfig {
        Map<Integer, Integer> getTypeConfigKeyAndRes();

        List getTypeConfigData();
    }

    public interface HeadDecorator<T> {
        void headDecorator(BindingViewHolder holder, int position, int viewType, List<T> mData);
    }

    public interface FootDecorator<T> {
        void footDecorator(BindingViewHolder holder, int position, int viewType, List<T> mData);
    }

    private HeadDecorator headDecorator;

    public void setMultiHeadConfig(AdapterTypeConfig config) {
        if (config == null) {
            throw new NullPointerException("config is null");
        }
        if (config.getTypeConfigKeyAndRes() == null || config.getTypeConfigKeyAndRes().size() == 0) {
            throw new NullPointerException("config.getHeadKeyAndResMap is null");
        }
        if (config.getTypeConfigData() == null || config.getTypeConfigData().size() == 0) {
            throw new NullPointerException("config.getHeadData() is null");
        }
        multiTypeMap.putAll(config.getTypeConfigKeyAndRes());
        if (headKeyList == null) {
            headKeyList = new ArrayList<>();
        }
        if (headDataList == null) {
            headDataList = new ArrayList();
        }
        Set<Integer> keySet = config.getTypeConfigKeyAndRes().keySet();
        for (Integer key : keySet
                ) {
            if (!headKeyList.contains(key)) {

                this.headKeyList.add(key);
            }
        }
        headDataList.addAll(config.getTypeConfigData());
//        L.d(headDataList.toString() + "-------------------------------------");
    }

    public void addMultiTypeMap(Map<Integer, Integer> multiTypeMap) {
        this.multiTypeMap.putAll(multiTypeMap);
    }


    public void addItemViewType(int viewType, int viewRes) {
        this.multiTypeMap.put(viewType, viewRes);
    }

    public void setMultiFootConfig(AdapterTypeConfig config) {
        if (config == null) {
            throw new NullPointerException("config is null");
        }
        if (config.getTypeConfigKeyAndRes() == null || config.getTypeConfigKeyAndRes().size() == 0) {
            throw new NullPointerException("foot config keyAndRes Map is null");
        }
        if (config.getTypeConfigData() == null || config.getTypeConfigData().size() == 0) {
            throw new NullPointerException("foot config data is null");
        }
        multiTypeMap.putAll(config.getTypeConfigKeyAndRes());
        if (footKeyList == null) {
            footKeyList = new ArrayList<>();
        }
        if (footDataList == null) {
            footDataList = new ArrayList();
        }
        this.footAdapterTypeConfig = config;
        Set<Integer> keySet = config.getTypeConfigKeyAndRes().keySet();
        for (Integer key : keySet
                ) {
            this.footKeyList.add(key);
        }
        footDataList.addAll(config.getTypeConfigData());
    }

    public void addSingleHeadConfig(int headKey, int headRes, Object headData) {
        if (headDataList == null) {
            headDataList = new ArrayList();
        }
        if (headKeyList == null) {
            headKeyList = new ArrayList();
        }
        if (headData == null) {
            headData = new Object();
        }
        if (!headKeyList.contains(headKey)) {
            headDataList.add(headData);
            headKeyList.add(headKey);
            multiTypeMap.put(headKey, headRes);
        }

    }

    public void addSingleFootConfig(int footKey, int footRes, Object footData) {
        if (footDataList == null) {
            footDataList = new ArrayList();
        }
        if (footKeyList == null) {
            footKeyList = new ArrayList();
        }
        if (footData == null) {
            footData = new Object();
        }
        if (footData != null) {
            footDataList.add(footData);
        }
        if (!footKeyList.contains(footKey)) {
            footKeyList.add(footKey);
            multiTypeMap.put(footKey, footRes);
        }

    }

    public void removeFootViewForFootKey(int footKey) {
        for (int i = 0; i < footKeyList.size(); i++) {
            if (footKeyList.get(i).equals(footKey)) {
                footKeyList.remove(i);
                footDataList.remove(i);
                return;
            }
        }
        multiTypeMap.remove(footKey);
        refresh();
    }

    public void setItemViewRes(int itemRes) {
        multiTypeMap.put(ITEM_VIEW_NORMAL_TYPE, itemRes);
    }

    public boolean isHeaderView(int position) {
        if (headKeyList == null || headKeyList.size() == 0) {
            return false;
        } else if (headKeyList.size() == 1) {
            return position == 0;
        } else if (headKeyList.size() > 1) {
            return position < headKeyList.size();
        }
        return false;
    }

    public boolean isFooterView(int position) {
        if (footKeyList == null || footKeyList.size() == 0) {
            return false;
        } else if (footKeyList.size() == 0) {
            return false;
        }
        int count = getHeadAndItemCount();
        return position >= count && position <= getItemCount();
    }

    private int getHeadAndItemCount() {
        int count = getHeadCount();
        if (mData != null || mData.size() != 0) {
            count += mData.size();
        }
        return count;
    }

    public int getHeadCount() {

        return headKeyList != null && headKeyList.size() != 0 ? headKeyList.size() : 0;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mData != null && mData.size() != 0) {
            count = mData.size();
        }
        if (headKeyList != null && headKeyList.size() != 0) {
            count += headKeyList.size();
        }
        if (footKeyList != null && footKeyList.size() != 0) {
            count += footKeyList.size();
        }
        return count;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (getLayoutRes(viewType) <= 0)
            throw  new  NullPointerException("item layout is null");
        BindingViewHolder    bindingViewHolder = new BindingViewHolder(DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(viewType), parent, false));
        mBindingViewHolderMap.put(viewType, bindingViewHolder);
        return bindingViewHolder;
    }

    @Override

    public void onBindViewHolder(BindingViewHolder holder, int position) {
        if (mData == null) {
            throw new NullPointerException("BaseDataBindingAdapter  data is null");
        }
        Object data = null;
        ViewDataBinding binding = holder.getBinding();
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_VIEW_NO_SHOW_TYPE) {
            return;
        }
        if (isHeaderView(position)) {
            if (headDataList == null || headDataList.size() == 0) return;
            //  L.d(headDataList.get(position).toString());
            data = headDataList.get(position);
            if (headDecorator != null)
                headDecorator.headDecorator(holder, position, itemViewType, headDataList);
            if (headPresenter != null)
                binding.setVariable(BR.viewmodle, headPresenter);
        } else if (isFooterView(position)) {
            if (footDataList == null || footDataList.size() == 0) return;
            data = footDataList.get(position - getHeadAndItemCount());
            multiTypeFootHolder.put(itemViewType, holder);
            if (itemViewType == Consts.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY) return;
            if (footPresenter != null)
                binding.setVariable(BR.viewmodle, footPresenter);
            if (footDecorator != null) {
                footDecorator.footDecorator(holder, position - getHeadAndItemCount(), itemViewType, footDataList);
            }

        } else {
            data = mData.get(position - getHeadCount());
            if (decorator != null)
                decorator.decorator(holder, position - getHeadCount(), itemViewType, mData);
            if (mPresenter != null) {
                binding.setVariable(BR.viewmodle, mPresenter);
            }
            itemDBinds.add(binding);
        }
        if (data == null) {
            throw new NullPointerException("BaseDataBindingAdapter  itemData is null");
        }
        // 分配数据
        binding.setVariable(BR.itemData, data);
        binding.setVariable(BR.itemPosition, position);

        //分配事件
        binding.executePendingBindings();

    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return headKeyList.get(position);
        } else if (isFooterView(position)) {
            return footKeyList.get(position - getHeadAndItemCount());
        }
        return getMyItemViewType(position - getHeadCount(), multiTypeMap, mData.get(position - getHeadCount()));
    }

    // 解决gridManager 的head 问题
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeaderView(position) || isFooterView(position)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(BindingViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public int getMyItemViewType(int position, ArrayMap<Integer, Integer> multiTypeMap, T data) {
        return ITEM_VIEW_NORMAL_TYPE;
    }

    @Override
    public int getLayoutRes(int itemViewType) {
        if (!multiTypeMap.containsKey(itemViewType)) {
            return ITEM_VIEW_NO_SHOW_TYPE ;
        }
        return multiTypeMap.get(itemViewType);
    }


    @Override
    public void clear() {

        if (footKeyList != null && footKeyList.size() != 0) {
            for (Integer footKey :
                    footKeyList) {
                multiTypeMap.remove(footKey);
            }
            footKeyList.clear();
            footDataList.clear();
        }
        if (headKeyList != null && headKeyList.size() != 0) {
            for (Integer headKey :
                    headKeyList) {
                multiTypeMap.remove(headKey);
            }
            headDataList.clear();
            headKeyList.clear();
        }


        super.clear();
    }

    public void clearList() {
        super.clear();
    }

    public HeadDecorator getHeadDecorator() {
        return headDecorator;
    }

    public void setHeadDecorator(HeadDecorator headDecorator) {
        this.headDecorator = headDecorator;
    }

    public void setHeadPresenter(BaseBindingItemPresenter presenter) {
        headPresenter = presenter;
    }

    public void setFootPresenter(BaseBindingItemPresenter presenter) {
        footPresenter = presenter;
    }


    public void setFootHolder(BindingViewHolder footHolder) {
        this.footHolder = footHolder;
    }

    public AdapterTypeConfig getFootAdapterTypeConfig() {
        return footAdapterTypeConfig;
    }

    public ArrayMap<Integer, BindingViewHolder> getMultiTypeFootHolder() {
        return multiTypeFootHolder;
    }


}
