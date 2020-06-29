package com.diankong.sexstory.mobile.modle.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.bean.BaseResult;
import com.diankong.sexstory.mobile.bean.CommitPojo;
import com.diankong.sexstory.mobile.bean.GoodsPojo;
import com.diankong.sexstory.mobile.bean.ShopPojo;
import com.diankong.sexstory.mobile.http.Api;
import com.diankong.sexstory.mobile.modle.activity.JieSuanActivity;
import com.diankong.sexstory.mobile.utils.GlideImageLoader;
import com.diankong.sexstory.mobile.utils.L;
import com.diankong.sexstory.mobile.utils.SpUtlis;
import com.diankong.sexstory.mobile.utils.ToastUtils;
import com.diankong.sexstory.mobile.widget.AmountView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/27.
 * 描述：
 * =============================================
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private Context context;
    private List<ShopPojo> mAllData;
    private TextView mTvShopcartTotal;
    private CheckBox mCheckboxAll;
    private RecyclerView _recyclerView;
    private TextView tv_commit;
    private CommitPojo _pojos;
    private List<CommitPojo> list;

    public ShoppingCartAdapter(Context mContext, List<ShopPojo> allData, TextView tvShopcartTotal, CheckBox checkboxAll, RecyclerView _recyclerView,TextView tv_commit) {

        this.context = mContext;
        this.mAllData = allData;
        this.mTvShopcartTotal = tvShopcartTotal;
        this.mCheckboxAll = checkboxAll;
        this._recyclerView = _recyclerView;
        this.tv_commit = tv_commit;
        /**
         * 总金额
         * */
        ShowTotalPrice();
        /**
         * 设置点击事件
         * */
        setListener();
        /**
         * 校验是否全选
         * */
        checkAll();

        commit();

    }

    private void commit() {
        _pojos = new CommitPojo();
        list = new ArrayList<>();
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

                if(getTotalPrices()==0){
                    ToastUtils.showShort("请选择您想购买的商品!");
                    return;
                }

                if (mAllData != null && mAllData.size() >= 0) {
                    for (int i = 0; i < mAllData.size(); i++) {
                        ShopPojo goodsBean = mAllData.get(i);
                        if (goodsBean.isChoosed()) {
                            L.d(goodsBean.toString()+"--------------------------");
                            list.add(new CommitPojo(goodsBean.id,goodsBean.count,goodsBean.goodName,goodsBean.goodSmallIcon,goodsBean.goodPrice));
                        }
                    }
                }

                _pojos.goods = list;
                _pojos.fee = getTotalPrices();
                Intent intent = new Intent(context, JieSuanActivity.class);
                intent.putExtra("BEAN",_pojos);
                context.startActivity(intent);
            }
        });
    }

    /**
     * 设置点击事件
     */
    private void setListener() {

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                //根据位置得到对应的bean
                ShopPojo goodsBean = mAllData.get(positon);
                //设置取反装填
                goodsBean.setChoosed(!goodsBean.isChoosed());
                //刷新数据
                notifyItemChanged(positon);
                //校验是否是全选
                checkAll();
                //重新计算
                ShowTotalPrice();
            }
        });
        //设置全选和非全选按钮的点击事件
        mCheckboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到状态
                boolean isChecked = mCheckboxAll.isChecked();
                //根据状态设置全选和非全选
                checkAll_none(isChecked);
                //计算总价格
                ShowTotalPrice();
            }
        });


    }

    /**
     * 设置全选和非全选
     */
    public void checkAll_none(boolean isChecked) {

        if (mAllData != null && mAllData.size() >= 0) {
            for (int i = 0; i < mAllData.size(); i++) {
                ShopPojo goodsBean = mAllData.get(i);
                goodsBean.setChoosed(isChecked);
                notifyItemChanged(i);
            }
        }

    }

    /**
     * 校验是否全选
     */
    public void checkAll() {
        if (mAllData != null && mAllData.size() >= 0) {
            int number = 0;
            for (int i = 0; i < mAllData.size(); i++) {
                ShopPojo goodsBean = mAllData.get(i);
                if (!goodsBean.isChoosed()) {
                    //非全选
                    mCheckboxAll.setChecked(false);

                } else {
                    number++;
                }
            }
            if (number == mAllData.size()) {
                //全选
                mCheckboxAll.setChecked(true);

            }

        } else {
            mCheckboxAll.setChecked(false);
        }
    }

    /**
     * 合计金额
     */
    public void ShowTotalPrice() {
        mTvShopcartTotal.setText("￥" + getTotalPrices());
    }

    private double getTotalPrices() {

        double totalPrice = 0.0;
        if (mAllData != null && mAllData.size() >= 0) {
            for (int i = 0; i < mAllData.size(); i++) {
                ShopPojo goodsBean = mAllData.get(i);
                if (goodsBean.isChoosed()) {
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getCount()) * Double.valueOf(goodsBean.goodPrice);
                }
            }
        }
        return totalPrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_shopcar, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //根据位置得到对应的bean
        final ShopPojo goodsBean = mAllData.get(position);
        //设置数据
        holder.ck.setChecked(goodsBean.isChoosed());
        GlideImageLoader.onDisplayImage(context, holder.iv_icon, goodsBean.goodSmallIcon);
        holder.tv_name.setText(goodsBean.goodName);
        holder.tv_price.setText("￥"+String.valueOf(goodsBean.goodPrice));
        holder.amountView.setGoods_storage(10000);
        holder.amountView.setEtAmountText(String.valueOf(goodsBean.getCount()));
        ((SimpleItemAnimator)_recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        holder.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, final int amount) {
//
                goodsBean.setCount(amount);
                editShopCar(goodsBean.id, amount);
                //刷新适配器

//                if (_recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE&&_recyclerView.isComputingLayout()) {
//                    _recyclerView.post(new Runnable() {
//                        @Override
//                        public void run() {
////                            notifyItemChanged(position);
//                        }
//                    });
//
//                }else{
////                    notifyItemChanged(position);
//                }
                ShowTotalPrice();

//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });


                //再次计算总价格


            }
        });

    }

    @Override
    public int getItemCount() {
        return mAllData == null ? 0 : mAllData.size();
    }

    /**
     * 监听者
     */
    public interface OnItemClickListener {
        /**
         * 点击某一条目的时候暴露的接口方法
         */
        void onItemClick(int positon);

    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox ck;
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_price;
        private AmountView amountView;
        private LinearLayout _layout;


        public ViewHolder(View itemView) {
            super(itemView);
            ck = itemView.findViewById(R.id.ck);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            amountView = (AmountView) itemView.findViewById(R.id.amount_view);
            _layout = itemView.findViewById(R.id.ll_content);

            _layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }

                }
            });
        }
    }

    private void editShopCar(int id, int count) {
        EasyHttp.get(Api.apiurl + "shopCar/editShopToShopCar")
                .params("goodId", String.valueOf(id))
                .params("count", String.valueOf(count))
                .params("userId", String.valueOf(SpUtlis.getId()))
                .execute(new CallBackProxy<BaseResult<GoodsPojo>, GoodsPojo>(new SimpleCallBack<GoodsPojo>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(final GoodsPojo GoodsPojo) {
                    }

                }) {
                });
    }



}
