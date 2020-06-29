package com.diankong.sexstory.mobile.modle.modelview;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.databinding.MsgFragmentBinding;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/21.
 * 描述：
 * =============================================
 */

public class MSGViewModle extends BaseViewModle<MsgFragmentBinding> implements RongIM.UserInfoProvider{


    @Override
    public void initUI() {
        RongIM.setUserInfoProvider(this, false);
    }





    @Override
    public void initNet() {

    }

    @Override
    public UserInfo getUserInfo(String _s) {
        return null;
    }
}
