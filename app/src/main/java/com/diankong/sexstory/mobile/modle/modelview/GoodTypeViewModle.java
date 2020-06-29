package com.diankong.sexstory.mobile.modle.modelview;

import android.view.View;

import com.diankong.sexstory.mobile.R;
import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.databinding.ActivityGoodtypeBinding;
import com.diankong.sexstory.mobile.modle.fragment.GoodsTypeFragment;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/11/6.
 * 描述：
 * =============================================
 */

public class GoodTypeViewModle extends BaseViewModle<ActivityGoodtypeBinding> implements View.OnClickListener {

    private String title;
    public GoodsTypeFragment _goodsTypeFragment;
    public GoodsTypeFragment _goodsTypeFragment1;
    public GoodsTypeFragment _goodsTypeFragment2;
    public GoodsTypeFragment _goodsTypeFragment3;
    public GoodsTypeFragment _goodsTypeFragment4;

    private int goodTypeId;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar, act);
        title = act.getIntent().getStringExtra("TITLE");
        goodTypeId = act.getIntent().getIntExtra("goodTypeId", 0);
        b.tvTitle.setText(title);
        b.tvEarn1.setOnClickListener(this);
        b.tvEarn2.setOnClickListener(this);
        b.llJiage.setOnClickListener(this);
        b.tvEarn4.setOnClickListener(this);

        initViewPager();
    }

    @Override
    public void initNet() {

    }

    private void initViewPager() {
        _goodsTypeFragment = GoodsTypeFragment.newInstance(0, goodTypeId);
        act.getSupportFragmentManager().beginTransaction().replace(b.llContent.getId(), _goodsTypeFragment).commit();

    }
    boolean flagDoubleCick = false;
    @Override
    public void onClick(View _view) {

        switch (_view.getId()) {
            case R.id.tv_earn1:
                flagDoubleCick = false;
                _goodsTypeFragment = GoodsTypeFragment.newInstance(0, goodTypeId);
                b.tvEarn1.setTextColor(act.getResources().getColor(R.color.red23));
                b.tvEarn2.setTextColor(act.getResources().getColor(R.color.gray_333));
                b.tvEarn3.setTextColor(act.getResources().getColor(R.color.gray_333));
                b.tvEarn4.setTextColor(act.getResources().getColor(R.color.gray_333));
                b.ivPaixu.setImageResource(R.mipmap.up);
                act.getSupportFragmentManager().beginTransaction().replace(b.llContent.getId(), _goodsTypeFragment).commit();
                break;
            case R.id.tv_earn2:
                flagDoubleCick = false;
                _goodsTypeFragment1 = GoodsTypeFragment.newInstance(1, goodTypeId);
                b.tvEarn2.setTextColor(act.getResources().getColor(R.color.red23));
                b.tvEarn1.setTextColor(act.getResources().getColor(R.color.gray_333));
                b.tvEarn3.setTextColor(act.getResources().getColor(R.color.gray_333));
                b.tvEarn4.setTextColor(act.getResources().getColor(R.color.gray_333));
                b.ivPaixu.setImageResource(R.mipmap.up);
                act.getSupportFragmentManager().beginTransaction().replace(b.llContent.getId(), _goodsTypeFragment1).commit();
                break;

            case R.id.ll_jiage:
                if(!flagDoubleCick){
                    _goodsTypeFragment4 = GoodsTypeFragment.newInstance(4, goodTypeId);
                    b.tvEarn3.setTextColor(act.getResources().getColor(R.color.red23));
                    b.tvEarn2.setTextColor(act.getResources().getColor(R.color.gray_333));
                    b.tvEarn1.setTextColor(act.getResources().getColor(R.color.gray_333));
                    b.tvEarn4.setTextColor(act.getResources().getColor(R.color.gray_333));
                    b.ivPaixu.setImageResource(R.mipmap.up);
                    act.getSupportFragmentManager().beginTransaction().replace(b.llContent.getId(), _goodsTypeFragment4).commit();

                    flagDoubleCick = true;
                }else{
                    _goodsTypeFragment3 = GoodsTypeFragment.newInstance(3, goodTypeId);
                    b.tvEarn3.setTextColor(act.getResources().getColor(R.color.red23));
                    b.tvEarn2.setTextColor(act.getResources().getColor(R.color.gray_333));
                    b.tvEarn1.setTextColor(act.getResources().getColor(R.color.gray_333));
                    b.tvEarn4.setTextColor(act.getResources().getColor(R.color.gray_333));
                    b.ivPaixu.setImageResource(R.mipmap.down);
                    act.getSupportFragmentManager().beginTransaction().replace(b.llContent.getId(), _goodsTypeFragment3).commit();
                    flagDoubleCick = false;
                }


                break;

            case R.id.tv_earn4:
                flagDoubleCick = false;
                _goodsTypeFragment2 = GoodsTypeFragment.newInstance(2, goodTypeId);
                b.tvEarn4.setTextColor(act.getResources().getColor(R.color.red23));
                b.tvEarn2.setTextColor(act.getResources().getColor(R.color.gray_333));
                b.tvEarn3.setTextColor(act.getResources().getColor(R.color.gray_333));
                b.tvEarn1.setTextColor(act.getResources().getColor(R.color.gray_333));
                b.ivPaixu.setImageResource(R.mipmap.up);
                act.getSupportFragmentManager().beginTransaction().replace(b.llContent.getId(), _goodsTypeFragment2).commit();
                break;


        }
    }
}
