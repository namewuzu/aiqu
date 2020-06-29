package com.diankong.sexstory.mobile.widget.recyclerview;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.Consts;
import com.diankong.sexstory.mobile.databinding.FootLoadmoreBinding;
import com.diankong.sexstory.mobile.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




/**
 * Created by apanda on 2016/11/11.
 */

public class PullRecyclerView extends FrameLayout {
    public RecyclerView _SwipeTarget;
    //   SwipeToLoadLayout _SwipeToLoadLayout;
    private View rootView;
    private MySwipeToRefresh swipeRefreshLayout;
    private RecyclerView.Adapter mAdapter;
    private boolean isNoMoreData;
    private boolean isLoadMore;
    private boolean loadMoreEnable;
    private FootLoadmoreBinding footView;
    private BindingViewHolder footHolder;

    public RelativeLayout rlContent;
    public FrameLayout flContent;

    public PullRecyclerView(Context context) {
        super(context);
    }

    public PullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rootView = LayoutInflater.from(context).inflate(R.layout.recycle_root, this, true);
        swipeRefreshLayout = (MySwipeToRefresh) rootView.findViewById(R.id.mySwipe);
        _SwipeTarget = (RecyclerView) rootView.findViewById(R.id.swipe_target);

        flContent = (FrameLayout) rootView.findViewById(R.id.fl_content);
        rlContent = (RelativeLayout) rootView.findViewById(R.id.rl_content);
        initEvent();
    }

    private void initEvent() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryRed, R.color.e5red, R.color.fered);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (_loadMoreListener != null) {
                    _loadMoreListener.onRefresh();
                }

            }
        });
        _SwipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    if (isNoMoreData) {
                        setLoadMoreStatus(3, "没有更多数据了");
                        return;
                    }
                    if (isLoadMore) {
                        setLoadMoreStatus(1, "正在加载当中...");
                        return;
                    }
                    if (!loadMoreEnable) {
                        setLoadMoreStatus(2, "");
                        return;
                    }else {
                        setLoadMoreStatus(3, "上拉加载更多数据");
                    }
                    refreshEnd();
                    if (_loadMoreListener != null) {
                        isLoadMore = true;
                        setLoadMoreStatus(1, "正在加载当中...");
                        _loadMoreListener.onLoadMore();
                    }

                }
            }
        });
    }


    public RecyclerView getSwipeTarget() {
        return _SwipeTarget;
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


    private void setLoadMoreView() {
        if (mAdapter == null) {
            return;
        }
        if (mAdapter instanceof SingleTypeBindingAdapter) {
            SingleTypeBindingAdapter adapter = (SingleTypeBindingAdapter) mAdapter;
            adapter.addSingleFootConfig(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY, R.layout.foot_loadmore, new Object());
        } else if (mAdapter instanceof MultiTypeBindingAdapter) {
            MultiTypeBindingAdapter adapter = (MultiTypeBindingAdapter) mAdapter;
            adapter.addSingleFootConfig(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY, R.layout.foot_loadmore, new Object());
        }
    }

    public void setAdapter(RecyclerView.Adapter _adapter) {
        setAdapter(_adapter, null);

    }
    public void setManagerAdapter(RecyclerView.Adapter _adapter) {
        this.mAdapter = _adapter;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UIUtils.getContext(), LinearLayoutManager.VERTICAL, false);
       // CommonUtils.setManagerConfig(linearLayoutManager,_SwipeTarget);
        _SwipeTarget.setAdapter(mAdapter);
        setLoadMoreView();
    }

    public void setAdapter(RecyclerView.Adapter _adapter, RecyclerView.LayoutManager _manager) {
        this.mAdapter = _adapter;
        if (_manager == null) {
            _SwipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            _SwipeTarget.setLayoutManager(_manager);
        }
        _SwipeTarget.setAdapter(_adapter);
        setLoadMoreView();
    }

    public RecyclerView getRecyclerView() {
        return _SwipeTarget;
    }

    private OnLoadMoreListener _loadMoreListener;

    public void smoothScrollToPosition(int _position) {
        _SwipeTarget.smoothScrollToPosition(_position);
    }

    public void setNoMoreData(boolean isMoreData) {
        isNoMoreData = isMoreData;
    }

    public interface OnLoadMoreListener {
        /**
         * 下拉
         */
        void onRefresh();

        /**
         * 上拉
         */
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener _loadMoreListener) {
        this._loadMoreListener = _loadMoreListener;
    }


    /**
     * 设置是否可以下拉和上拉
     *
     * @param refreshEnable
     * @param loadMoreEnable
     */
    public void setRefreshLoaderMore(boolean refreshEnable, boolean loadMoreEnable) {
        swipeRefreshLayout.setEnabled(refreshEnable);

        this.loadMoreEnable = loadMoreEnable;
    }

    boolean refreshState = false;
    boolean loadMoreState = false;

    /**
     * 是否能够上啦加载
     */
    public void setLoadMoreState(boolean b) {
        loadMoreState = b;
    }

    /**
     * @param b true  显示  false 隐藏
     * @doc 设置refresh的下拉刷新头的状态
     */
    public void setRefreshState(boolean b) {
        refreshState = b;
        swipeRefreshLayout.setRefreshing(b);
    }

    public void loadMoreEnd() {
        isLoadMore = false;
        setLoadMoreStatus(2, "");
    }

    private void setLoadMoreStatus(int status, String text) {
        if (mAdapter == null) {
            return;
        }
        boolean in = mAdapter instanceof BaseDataBindingAdapter;
        if (in) {
            BaseDataBindingAdapter adapter = (BaseDataBindingAdapter) mAdapter;
            HashMap<Integer, BindingViewHolder> bindingViewHolderMap = adapter.getBindingViewHolderMap();
            if (!bindingViewHolderMap.containsKey(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY)) {
                return;
            }
            footHolder = bindingViewHolderMap.get(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY);
            if (footHolder == null) {
                return;
            }
            ViewDataBinding binding = footHolder.getBinding();
            boolean in2 = binding instanceof FootLoadmoreBinding;

            if (!in2) {
                return;
            }
            if (footView == null) {
                footView = (FootLoadmoreBinding) binding;
            }
            if (footView == null) {
                return;
            }
            if (status == 1) {
                footView.llContent.setVisibility(VISIBLE);
                footView.progress.setVisibility(VISIBLE);
            } else if (status == 2) {
                footView.llContent.setVisibility(GONE);
                footView.progress.setVisibility(GONE);
            }else if(status ==3){
                footView.llContent.setVisibility(VISIBLE);
                footView.progress.setVisibility(GONE);
            }
            if (!TextUtils.isEmpty(text)) {
                footView.tvFootStateDec.setText(text);
            }
        }
    }


    public void loadMoreError() {
        isLoadMore = false;
        setLoadMoreStatus(1, "加载出错...");
    }

    public void noMoreData(List data, int count) {

        if (data == null) {
            data = new ArrayList();
        }

        if (data.size() >= count) {
            isLoadMore = false;
            isNoMoreData = false;
            setLoadMoreStatus(3, "上拉加载更多数据");
        } else {
            isLoadMore = false;
            isNoMoreData = true;
            setLoadMoreStatus(3, "没有更多数据了");
        }

    }

    public void LoadMoreStart() {
        setLoadMoreStatus(1, "正在加载中...");
    }

    public void refreshEnd() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    public void setRecyclerViewConfig() {
        _SwipeTarget.setNestedScrollingEnabled(false);
        _SwipeTarget.setHasFixedSize(true);

    }


    public static void setLoadMoreStatus(BaseDataBindingAdapter mAdapter, int status, String text) {
        if (mAdapter == null) {
            return;
        }
        ObservableField<String> observableField = new ObservableField<>();
        BindingViewHolder footHolder = null;
        FootLoadmoreBinding footView = null;
        boolean in = mAdapter instanceof BaseDataBindingAdapter;
        if (in) {
            BaseDataBindingAdapter adapter = (BaseDataBindingAdapter) mAdapter;
            HashMap<Integer, BindingViewHolder> bindingViewHolderMap = adapter.getBindingViewHolderMap();
            if (!bindingViewHolderMap.containsKey(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY)) {
                return;
            }
            footHolder=bindingViewHolderMap.get(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY);
            if (footHolder == null) {
                return;
            }
            ViewDataBinding binding = footHolder.getBinding();
            boolean in2 = binding instanceof FootLoadmoreBinding;

            if (!in2) {
                return;
            }
            if (footView == null) {
                footView = (FootLoadmoreBinding) binding;
            }
            if (footView == null) {
                return;
            }
            footView.viewLine.setVisibility(GONE);
            if (status == 1) {
                footView.llContent.setVisibility(VISIBLE);
                footView.progress.setVisibility(VISIBLE);
            } else if (status == 2) {
                footView.llContent.setVisibility(GONE);
                footView.progress.setVisibility(GONE);
            }else if(status ==3){
                footView.llContent.setVisibility(VISIBLE);
                footView.progress.setVisibility(GONE);
            }

            if (!TextUtils.isEmpty(text)) {
                observableField.set(text);
                footView.setText(observableField.get());
            }
        }
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator){
        _SwipeTarget.setItemAnimator(animator);
    }
}
