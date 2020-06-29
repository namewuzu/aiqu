package com.diankong.sexstory.mobile.modle.IM;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/14.
 * 描述：
 * =============================================
 */

public class MyExtensionModule extends DefaultExtensionModule {
    private GiftPlugin _giftPlugin = new GiftPlugin();

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModuleList = new ArrayList<>();
        IPluginModule image = new ImagePlugin();
        IPluginModule location = new DefaultLocationPlugin();
//        IPluginModule audio = new AudioPlugin();
//        IPluginModule video = new VideoPlugin();
        IPluginModule file = new FilePlugin();

        if (conversationType.equals(Conversation.ConversationType.GROUP) ||
                conversationType.equals(Conversation.ConversationType.DISCUSSION) ||
                conversationType.equals(Conversation.ConversationType.PRIVATE)) {
            pluginModuleList.add(image);
//            pluginModuleList.add(location);
//            pluginModuleList.add(audio);
//            pluginModuleList.add(video);
//            pluginModuleList.add(file);
//            pluginModuleList.add(_giftPlugin);
        } else {
            pluginModuleList.add(image);
//            pluginModuleList.add(_giftPlugin);
        }

        return pluginModuleList;
    }

}
