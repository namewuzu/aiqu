package com.diankong.sexstory.mobile.widget.recyclerview;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public interface BaseBindingItemLongPresenter<T> {
    void onItemLongClick(int position, T itemData);
}