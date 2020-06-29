package com.diankong.sexstory.mobile.modle.modelview;

import android.view.View;

import com.diankong.sexstory.mobile.base.BaseViewModle;
import com.diankong.sexstory.mobile.bean.GroupTagsPojo;
import com.diankong.sexstory.mobile.databinding.ActivityChatStartBinding;
import com.diankong.sexstory.mobile.event.ChatStartEvent;
import com.diankong.sexstory.mobile.utils.ToolbarUtils;
import com.diankong.sexstory.mobile.widget.tagcloud.TagBaseAdapter;
import com.diankong.sexstory.mobile.widget.tagcloud.TagCloudLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/23.
 * 描述：
 * =============================================
 */

public class ChatStartViewModle extends BaseViewModle<ActivityChatStartBinding> {

    private TagBaseAdapter _tagBaseAdapter;
    private List<GroupTagsPojo> _pojos;
    private List<GroupTagsPojo> _pojos1;

    @Override
    public void initUI() {
        ToolbarUtils.initToolBar(b.toolbar,act);
        _pojos = new ArrayList<>();
        _pojos1 = new ArrayList<>();

        _pojos.add(new GroupTagsPojo("运动健身", 1));
        _pojos.add(new GroupTagsPojo("美食", 1));
        _pojos.add(new GroupTagsPojo("宠物", 1));
        _pojos.add(new GroupTagsPojo("音乐", 1));
        _pojos.add(new GroupTagsPojo("民族", 1));
        _pojos.add(new GroupTagsPojo("舞蹈", 1));
        _pojos.add(new GroupTagsPojo("小说", 1));
        _pojos.add(new GroupTagsPojo("文学", 1));
        _pojos.add(new GroupTagsPojo("诗词", 1));
        _pojos.add(new GroupTagsPojo("电影", 1));
        _pojos.add(new GroupTagsPojo("二次元", 1));
        _pojos.add(new GroupTagsPojo("动漫", 1));
        _pojos.add(new GroupTagsPojo("热门综艺", 1));
        _pojos.add(new GroupTagsPojo("韩剧", 1));
        _pojos.add(new GroupTagsPojo("cosPlay", 1));
        _pojos.add(new GroupTagsPojo("美剧", 1));
        _pojos.add(new GroupTagsPojo("数码", 1));
        _pojos.add(new GroupTagsPojo("旅行", 1));
        _pojos.add(new GroupTagsPojo("交友", 1));
        _pojos.add(new GroupTagsPojo("疗伤", 1));
        _pojos.add(new GroupTagsPojo("倾听", 1));
        _pojos.add(new GroupTagsPojo("脱口秀", 1));
        _pojos.add(new GroupTagsPojo("星座", 1));

        _tagBaseAdapter = new TagBaseAdapter(act, _pojos, 2);
        b.tcl.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {


                GroupTagsPojo pojo = _pojos.get(position);
                if (pojo.getSelect() == 0) {
                    if(_pojos1.size()<5){
                        pojo.setSelect(1);
                        _pojos1.add(pojo);
                    }

                } else {
                    pojo.setSelect(0);
                    _pojos1.remove(pojo);
                }


            }
        });
        b.tcl.setAdapter(_tagBaseAdapter);


        b.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                EventBus.getDefault().post(new ChatStartEvent(_pojos1));
                act.finish();
            }
        });
    }

    @Override
    public void initNet() {

    }
}
