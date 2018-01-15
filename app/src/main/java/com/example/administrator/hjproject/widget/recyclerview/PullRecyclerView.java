package com.example.administrator.hjproject.widget.recyclerview;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.administrator.hjproject.R;
import com.example.administrator.hjproject.base.App;
import com.example.administrator.hjproject.base.Consts;
import com.example.administrator.hjproject.bean.FootLoadMorePojo;
import com.example.administrator.hjproject.databinding.FootLoadmoreBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;




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
    private FootLoadMorePojo footLoadMorePojo;
    private OnScrollListener onScrollListener;
    private boolean isNotAddFootLoading = false;
    private boolean isNotAddHeadLoading = true;
    private OnTopLoadMoreListener onTopLoadMoreListener;
    private boolean isNoTopMoreData = true;
    public boolean isTopLoadMore = false;
    private boolean topLoadMoreEnable;
    private FootLoadMorePojo headLoadMorePojo;
    private BindingViewHolder headHolder;
    private FootLoadmoreBinding headView;
    String[] loadMoreTextArr = new String[]{"我不同意你的说法，但我誓死捍卫你说话的权利。- 伏尔泰",
            "人不能孤独地生活，他需要社会。   --歌德", " 一致是强有力的，而纷争易于被征服。-伊索",
            "不受虚言，不听浮术，不采华名，不兴伪事。  -- 荀悦 ", "心如水之源，源清则流清，心正则事正。- 薛瑄",
            "思诚为修身之本，而明善又为思诚之本。- 朱熹", "法律是显露的道德，道德是隐藏的法律。-林肯"};
    private Random random;

    public PullRecyclerView(Context context) {
        super(context);
    }

    public PullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rootView = LayoutInflater.from(context).inflate(R.layout.recycle_root, this, true);
        swipeRefreshLayout = (MySwipeToRefresh) rootView.findViewById(R.id.mySwipe);
        _SwipeTarget = (RecyclerView) rootView.findViewById(R.id.swipe_target);

        initEvent();
    }

    public void setRecyclerViewBackgroundRes(int res) {
        _SwipeTarget.setBackgroundResource(res);
        swipeRefreshLayout.setBackgroundResource(res);
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
                if (onScrollListener != null) {
                    onScrollListener.onScrolled(recyclerView, dx, dy);
                }
                if (!isNotAddHeadLoading) {
                    if (isSlideToTop(recyclerView)) {
                        if (isNoTopMoreData) {
                            setTopMoreStatus(2, "");
                            return;
                        }
                        if (isTopLoadMore) {
                            setTopMoreStatus(1, "正在加载当中...");
                        }
                        if (topLoadMoreEnable) {
                            setTopMoreStatus(2, "");
                        }

                        if (onTopLoadMoreListener != null) {
                            isTopLoadMore = true;
                            setTopMoreStatus(1, "正在加载当中...");
                            onTopLoadMoreListener.topLoadMore();
                        }
                        return;
                    }
                }
                if (isNotAddFootLoading) {
                    return;
                }
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
                    } else {
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

    private void setTopMoreStatus(int status, String text) {

        if (mAdapter == null) {
            return;
        }
        boolean in = mAdapter instanceof BaseDataBindingAdapter;
        if (in) {
            BaseDataBindingAdapter adapter = (BaseDataBindingAdapter) mAdapter;
            HashMap<Integer, BindingViewHolder> bindingViewHolderMap = adapter.getBindingViewHolderMap();
            if (!bindingViewHolderMap.containsKey(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_HEAD_KEY)) {
                return;
            }
            headHolder = bindingViewHolderMap.get(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_HEAD_KEY);
            if (headHolder == null) {
                return;
            }
            ViewDataBinding binding = headHolder.getBinding();
            boolean in2 = binding instanceof FootLoadmoreBinding;

            if (!in2) {
                return;
            }
            if (headView == null) {
                headView = (FootLoadmoreBinding) binding;
            }
            if (headView == null) {
                return;
            }
            if (status == 1) {
                if (random == null) {
                    random = new Random();
                }
                text = loadMoreTextArr[random.nextInt(7)];
                headView.llContent.setVisibility(VISIBLE);
                headView.progress.setVisibility(VISIBLE);
            } else if (status == 2) {
                headView.llContent.setVisibility(GONE);
                headView.progress.setVisibility(GONE);
            } else if (status == 3) {
                headView.llContent.setVisibility(VISIBLE);
                headView.progress.setVisibility(GONE);
            }
            if (!TextUtils.isEmpty(text)) {
                headView.tvFootStateDec.setText(text);
            }
        }

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

    public boolean isSlideToTop(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int findFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (findFirstCompletelyVisibleItemPosition == 0) {
            return true;
        }
        return false;
    }


    private void setLoadMoreView() {
        if (mAdapter == null) {
            return;
        }
        addFootPojo(1, "上拉加载更多数据", true);

        if (mAdapter instanceof SingleTypeBindingAdapter) {
            SingleTypeBindingAdapter adapter = (SingleTypeBindingAdapter) mAdapter;
            adapter.addSingleFootConfig(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY, R.layout.foot_loadmore, footLoadMorePojo);
        } else if (mAdapter instanceof MultiTypeBindingAdapter) {
            MultiTypeBindingAdapter adapter = (MultiTypeBindingAdapter) mAdapter;
            adapter.addSingleFootConfig(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_FOOT_KEY, R.layout.foot_loadmore, footLoadMorePojo);
        }
    }

    private void addFootPojo(int status, String text, boolean isShowLine) {
        if (footLoadMorePojo == null) {
            footLoadMorePojo = new FootLoadMorePojo();
        }
        footLoadMorePojo.setStatus(status);
        footLoadMorePojo.setText(text);
        footLoadMorePojo.setShowLine(isShowLine);
    }

    public void setAdapter(RecyclerView.Adapter _adapter) {
        setAdapter(_adapter, null);

    }

    public void setManagerAdapter(RecyclerView.Adapter _adapter) {
        this.mAdapter = _adapter;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.VERTICAL, false);
        setManagerConfig(linearLayoutManager, _SwipeTarget);
        setLoadMoreView();
        _SwipeTarget.setAdapter(mAdapter);

    }

    public void setAdapter(RecyclerView.Adapter _adapter, RecyclerView.LayoutManager _manager) {
        this.mAdapter = _adapter;
        if (_manager == null) {
            _SwipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            _SwipeTarget.setLayoutManager(_manager);
        }
        setLoadMoreView();
        _SwipeTarget.setAdapter(_adapter);

    }

    public void setTopLoadMoreAdapter(RecyclerView.Adapter _adapter) {
        this.mAdapter = _adapter;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        //linearLayoutManager.setReverseLayout(true);//列表翻转*/
        _SwipeTarget.setLayoutManager(linearLayoutManager);
        isNotAddFootLoading = true;
        isNotAddHeadLoading = false;
        addTopLoadMoreView();
        _SwipeTarget.setAdapter(_adapter);
    }

    private void addTopLoadMoreView() {
        addHeadPojo(2, "下拉加载更多数据", true);
        MultiTypeBindingAdapter adapter = (MultiTypeBindingAdapter) mAdapter;
        adapter.addSingleHeadConfig(Consts.ADAPTER_FOOT_KEY.LOAD_MORE_HEAD_KEY, R.layout.foot_loadmore, headLoadMorePojo);
    }

    private void addHeadPojo(int status, String text, boolean isShowLine) {
        if (headLoadMorePojo == null) {
            headLoadMorePojo = new FootLoadMorePojo();
        }
        headLoadMorePojo.setStatus(status);
        headLoadMorePojo.setText(text);
        headLoadMorePojo.setShowLine(isShowLine);
    }

    public RecyclerView getRecyclerView() {
        return _SwipeTarget;
    }

    private OnLoadMoreListener _loadMoreListener;

    public void smoothScrollToPosition(int _position) {
        _SwipeTarget.smoothScrollToPosition(_position);

    }

    public void scrollToPosition(int _position) {
        _SwipeTarget.scrollToPosition(_position);

    }

    public void setNoMoreData(boolean isMoreData) {
        isNoMoreData = isMoreData;
    }

    public void setIsNotAddFootLoading(boolean isNotAddFootLoading) {
        this.isNotAddFootLoading = isNotAddFootLoading;
    }

    public void loadTopMoreError() {
        isTopLoadMore = false;
        setTopMoreStatus(2, "加载出错...");

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

    public interface OnTopLoadMoreListener {
        void topLoadMore();
    }

    public void setOnTopLoadMoreListener(OnTopLoadMoreListener listener) {
        this.onTopLoadMoreListener = listener;
    }

    public abstract static class OnScrollListener {
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener _loadMoreListener) {
        this._loadMoreListener = _loadMoreListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
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
        isNoMoreData = b;
    }

    public void setLoadTopMoreState(boolean b) {
        isNoTopMoreData = b;
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
            } else if (status == 3) {
                footView.llContent.setVisibility(GONE);
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

    public void noTopMoreData(List data, int count) {
        if (data == null) {
            data = new ArrayList();
        }

        if (data.size() >= count) {
            isTopLoadMore = false;
            isNoTopMoreData = false;
            setLoadMoreStatus(3, "");
        } else {
            isTopLoadMore = false;
            isNoTopMoreData = true;
            setLoadMoreStatus(2, "");
        }

    }

    public void LoadMoreStart() {
        setLoadMoreStatus(1, "正在加载中...");
    }

    public void loadTopMoreStart() {
        setTopMoreStatus(1, "正在加载中...");
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
            footView.viewLine.setVisibility(GONE);
            if (status == 1) {
                footView.llContent.setVisibility(VISIBLE);
                footView.progress.setVisibility(VISIBLE);
            } else if (status == 2) {
                footView.llContent.setVisibility(GONE);
                footView.progress.setVisibility(GONE);
            } else if (status == 3) {
                footView.llContent.setVisibility(VISIBLE);
                footView.progress.setVisibility(GONE);
            }

            if (!TextUtils.isEmpty(text)) {
                observableField.set(text);
                footView.setText(observableField.get());
            }
        }
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        _SwipeTarget.setItemAnimator(animator);
    }

    public void setManagerConfig(RecyclerView.LayoutManager manager, RecyclerView recyclerView) {
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            linearLayoutManager.setSmoothScrollbarEnabled(true);
        } else if (manager instanceof GridLayoutManager) {
            GridLayoutManager linearLayoutManager = (GridLayoutManager) manager;
            linearLayoutManager.setSmoothScrollbarEnabled(true);
        }
        manager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
