package com.example.missing.Data.Remote;


import com.example.missing.Data.Remote.Model.Authentication.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetDataService {

    @FormUrlEncoded
    @POST("api/register")
    Call<User> register_user(@Field("user_name") String user_name,
                             @Field("password") String password,
                             @Field("national_num") String national_num,
                             @Field("phone")String phone,
                             @Field("adress")String adress);
    @FormUrlEncoded
    @POST("api/login")
    Call<User>login_user(@Field("phone")String phone,@Field("password")String password);
}