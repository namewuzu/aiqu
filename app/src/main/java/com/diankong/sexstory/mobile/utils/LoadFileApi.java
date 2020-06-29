package com.diankong.sexstory.mobile.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * =============================================
 * 芒果瑞集团有限公司，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/1/3.
 * 描述：
 * =============================================
 */

public interface LoadFileApi {
    @GET
    Call<ResponseBody> loadPdfFile(@Url String fileUrl);
}
