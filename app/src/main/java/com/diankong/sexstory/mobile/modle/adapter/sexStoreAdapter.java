package com.diankong.sexstory.mobile.modle.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.diankong.sexstory.mobile.bean.GoodsPojo;
import com.diankong.sexstory.mobile.modle.viewholder.sexStoreViewHolder;
import com.diankong.sexstory.mobile.modle.viewholder.sexStoreViewHolder2;
import com.diankong.sexstory.mobile.modle.viewholder.sexStoreViewHolder3;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/23.
 * 描述：
 * =============================================
 */

public class sexStoreAdapter extends RecyclerArrayAdapter<GoodsPojo> {

    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;
    public static final int TYPE_FOUR = 4;
    public static final int TYPE_FIVE = 5;
    public static final int TYPE_SIX = 6;
    public static final int TYPE_SEVEN = 7;

    public sexStoreAdapter(Context context) {

        super(context);

    }

    @Override
    public int getViewType(int position) {

        if (getAllData().get(position).type==1) {
            return TYPE_ONE;
        }else if(getAllData().get(position).type==2){
            return TYPE_TWO;
        }else if(getAllData().get(position).type==3){
            return TYPE_THREE;
        }else if(getAllData().get(position).type==4){
            return TYPE_FOUR;
        }else if(getAllData().get(position).type==5){
            return TYPE_FIVE;
        }else if(getAllData().get(position).type==6){
            return TYPE_SIX;
        }else {
            return TYPE_SEVEN;
        }




    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_ONE){
            return new sexStoreViewHolder(parent);
        }else if(viewType==TYPE_TWO){
            return new sexStoreViewHolder(parent);
        }else if(viewType==TYPE_THREE){
            return new sexStoreViewHolder(parent);
        }else if(viewType==TYPE_FOUR){
            return new sexStoreViewHolder2(parent);
        }else if(viewType==TYPE_FIVE){
            return new sexStoreViewHolder2(parent);
        }else if(viewType==TYPE_SIX){
            return new sexStoreViewHolder(parent);
        }else if(viewType==TYPE_SEVEN){
            return new sexStoreViewHolder3(parent);
        }else{
            return new sexStoreViewHolder(parent);
        }


    }
}
