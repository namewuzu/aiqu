package com.diankong.sexstory.mobile.utils;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/12/9.
 * 描述：
 * =============================================
 */

public class STSGetter extends OSSFederationCredentialProvider {

    private OSSFederationToken ossFederationToken;
    String ak;
    String sk;
    String token;
    String expiration;
    public STSGetter() {
    }

    public OSSFederationToken getFederationToken() {
        getParameter();//拿到需要用到的参数
        return new OSSFederationToken(ak, sk, token, expiration);
    }

    private void getParameter() {


    }
}
