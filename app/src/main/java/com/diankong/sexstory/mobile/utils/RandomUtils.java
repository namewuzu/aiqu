package com.diankong.sexstory.mobile.utils;

import java.util.Random;

/**
 * =============================================
 * 长沙点控科技有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2018/5/24.
 * 描述：
 * =============================================
 */

public class RandomUtils {
    /**
     * 随机一个英文字母
     */
    public static String OneEn(){
        String string = "";

        // 循环得到10个字母
        for (int i = 0; i < 2; i++) {

            // 得到随机字母
            char c = (char) ((Math.random() * 26) + 97);
            // 拼接成字符串
            string += (c + "");
        }
        return string;
    }

    /**
     * 随机英文单词
     */
    public static String OneEnList(){
        String random = "";
        String[] doc = {"baidu", "taobao", "qq", "163","sogou","360","jd"
                ,"weixin","sohu","sina","iqiyi","tudou","56","youku","hao123"
                ,"4399","happy","rice","bread","beef","egg","supper","potato"
                ,"watermelon","peach","strawberry"};
        int index = (int) (Math.random() * doc.length);
        random = doc[index];

        return random;
    }

    /**
     * 1-10随机数
     */
    public static int OneNum(){
        return new Random().nextInt(10) + 1;
    }
}
