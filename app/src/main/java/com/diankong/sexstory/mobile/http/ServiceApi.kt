package com.diankong.sexstory.mobile.http

//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃   神兽保佑
//    ┃　　　┃   代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 *
 * 包名:
 * 作者: created by 胡清 on 2018/4/17 11:19
 * 描述:
 *
 */

/**
 * ┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│   │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
 * └───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
 * ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
 * │~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
 * ├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
 * │ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
 * ├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
 * │ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
 * ├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
 * │ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
 * ├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
 * │ Ctrl│    │Alt │         Space         │ Alt│    │    │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
 * └─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘
 */

interface ServiceApi {


//    companion object Factory {
//        /**
//         * 工厂
//         */
//        fun create(): ServiceApi {
//
//            val logging = HttpLoggingInterceptor()
//            logging.level = HttpLoggingInterceptor.Level.BODY
//
//            val client = OkHttpClient.Builder()
//                    .addInterceptor(logging)
//                    .retryOnConnectionFailure(true)
//                    .readTimeout(15, TimeUnit.SECONDS)
//                    .cookieJar(CookieJarImpl(PersistentCookieStore()))
//                    .build()
//
//
//            val retrofit = Retrofit
//                    .Builder()
//                    .client(client)
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .addConverterFactory(FastJsonConverterFactory.create())
////                    .addConverterFactory(GsonConverterFactory.create())
//                    .baseUrl(Api.apiurl)
//                    .build()
//
//            return retrofit.create(ServiceApi::class.java)
//        }
//    }
//
//    ///////////////////////////////////////////////////////////////
//    //   之前的
//    //////////////////////////////////////////////////////////////
//    //测试获取token
//    @GET("/api/login/getToken")
//    fun getToken(): Observable<SuperBean<TestPojo>>
//
//    //委托买入
//    @FormUrlEncoded
//    @POST("/api/order/buy")
//    fun postOrderBuy(@Field("token") token: String,
//                     @Field("price") price: String,
//                     @Field("num") num: String,
//                     @Field("type") type: String,
//                     @Field("payment_type") payment_type: String)
//            : Observable<SuperBean<succeedPojo>>
//
//    //委托卖出
//    @FormUrlEncoded
//    @POST("/api/order/trade")
//    fun postOrderTrade(@Field("token") token: String,
//                       @Field("price") price: Int,
//                       @Field("num") num: Int,
//                       @Field("type") type: Int)
//            : Observable<SuperBean<succeedPojo>>
//
//
//
//    //委托列表
//    @FormUrlEncoded
//    @POST("/api/order/search")
//    fun postOrderSearch(@Field("token") token: String,
//                        @Field("page") page: Int,
//                        @Field("status") status: Int,
//                        @Field("type") type: Int)
//            : Observable<SuperBean<Results<List<TestPojo.EntrustPojo>>>>
//
//    //委托列表
//    @FormUrlEncoded
//    @POST("/api/index/newTrade")
//    fun postNewTrade(@Field("token") token: String)
//            : Observable<SuperBean<Results<List<TestPojo>>>>
//
//    //撤銷委托
//    @FormUrlEncoded
//    @POST("/api/order/revokeTrade")
//    fun postRevokeTrade(@Field("token") token: String, @Field("trade_id") trade_id: Int)
//            : Observable<SuperBean<Any>>
//
//
//
//    ///////////////////////////////////////////////////////////////
//    //   现在的
//    //////////////////////////////////////////////////////////////

}
