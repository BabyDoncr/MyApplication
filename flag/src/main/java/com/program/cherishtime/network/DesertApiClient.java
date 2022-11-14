package com.program.cherishtime.network;

import com.program.cherishtime.bean.Code;
import com.program.cherishtime.bean.Msg;
import com.program.cherishtime.bean.Prompt;
import com.program.cherishtime.bean.Rank;
import com.program.cherishtime.bean.Relation;
import com.program.cherishtime.bean.Statistic;
import com.program.cherishtime.bean.Task;
import com.program.cherishtime.bean.User;
import com.program.cherishtime.bean.UserCard;
import com.program.cherishtime.bean.UserInfo;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DesertApiClient {
    String URL = "http://www.unnic.top:8080/desert/";

    // api

    @FormUrlEncoded
    @POST("api/login")
    Observable<Msg> login(@Field("appKey") String appKey, @Field("account") String account, @Field("pwd") String pwd);

    @GET("api/repeated")
    Observable<Msg> isRepeated(@Query("account") String account);

    @FormUrlEncoded
    @POST("api/register")
    Observable<Msg> register(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("api/sendcode")
    Observable<Code> getCode(@Field("email") String email, @Field("account") String account);

    @FormUrlEncoded
    @POST("api/forgetpassword")
    Observable<Msg> changePassword(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/userInfo")
    Observable<User> queryUserInfoByAccount(@Field("appKey") String appKey, @Field("account") String account);

    @FormUrlEncoded
    @POST("api/details")
    Observable<UserInfo> queryUserInfoDetails(@Field("appKey") String appKey, @Field("account") String account);

    @FormUrlEncoded
    @POST("api/usercard")
    Observable<UserCard> queryUserCard(@Field("appKey") String appKey, @Field("id") int id);

//    @Multipart
//    @POST("api/uploadimage")
//    Call<ResponseBody> uploadImageReturnCall(@Part MultipartBody.Part file, @Part("account") RequestBody account);

    @Multipart
    @POST("api/uploadimage")
    Observable<Msg> uploadImage(@Part MultipartBody.Part file, @Part("account") RequestBody account);

    @FormUrlEncoded
    @POST("api/updateinfo")
    Observable<Msg> updateUser(@FieldMap Map<String, Object> params);

    // prompt

    @GET("prompt/query")
    Observable<Prompt> onloadPrompt();

    // detail

//    @GET("detail/query")
//    Observable<Detail> queryDetailByUserId(@Query("id") int id);

    @FormUrlEncoded
    @POST("detail/update")
    Observable<Msg> updateDetail(@FieldMap Map<String, Object> params);

    @GET("detail/rank")
    Observable<ArrayList<Rank>> queryRankList();

    @GET("detail/myrank")
    Observable<Rank> queryMyRankInfo(@Query("id") int id);

    // relation

//    @GET("relation/friends")
//    Observable<List<Relationship>> queryFriendsByUserId(@Query("id") int id);

    @GET("relation/follows")
    Observable<ArrayList<Relation>> queryFollowsInfoByUserId(@Query("id") int id);

    @GET("relation/fans")
    Observable<ArrayList<Relation>> queryFansInfoByUserId(@Query("id") int id);

    @GET("relation/type")
    Observable<Msg> queryRelationType(@Query("id1") int id1, @Query("id2") int id2);

    @GET("relation/add")
    Observable<Msg> addAttention(@Query("id1") int id1, @Query("id2") int id2);

    @GET("relation/delete")
    Observable<Msg> deleteAttention(@Query("id1") int id1, @Query("id2") int id2);

    // task

//    @GET("task/querytaskbycomplete")
//    Observable<ArrayList<Task>> queryUserTask(@Query("uid") int uid, @Query("complete") int complete);
    @GET("task/querytaskbycomplete")
    Observable<ArrayList<Task>> queryUserTask(@Query("uid") int uid, @Query("complete") int complete);

    @GET("task/queryalltask")
    Observable<ArrayList<Task>> queryAllUserTask(@Query("uid") int uid);

//    @GET("task/querytask")
//    Observable<Task> queryTaskById(@Query("tid") int tid);

    @GET("task/querytask2")
    Observable<Task> queryTaskById2(@Query("uid") int uid, @Query("tid") int tid);

    @GET("task/querynum")
    Observable<Msg> queryPickTaskNum(@Query("uid") int uid);

    @FormUrlEncoded
    @POST("task/update")
    Observable<Msg> updateUserTask(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("task/updatecomplete")
    Observable<Msg> updateUserTaskComplete(
            @Field("appKey") String appKey,
            @Field("uid") int uid,
            @Field("tid") int tid,
            @Field("complete") int complete
    );

    // statistic

    @GET("statistic/query")
    Observable<ArrayList<Statistic>> queryStatisticById(@Query("uid") int uid);

//    @GET("statistic/queryone")
//    Observable<Statistic> queryStatisticByIdAndTime(@Query("uid") int uid, @Query("time") int time);
}
