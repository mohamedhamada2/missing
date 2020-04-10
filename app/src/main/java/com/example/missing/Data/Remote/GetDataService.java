package com.example.missing.Data.Remote;


import com.example.missing.Categories.Category;
import com.example.missing.Countries.CityModel;
import com.example.missing.Countries.CountryModel;
import com.example.missing.Data.Remote.Items.Item;
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
    @GET("api/categories")
    Call<List<Category>>get_categories();
    @GET("api/all_governs")
    Call<List<CountryModel>>get_governs();
    @FormUrlEncoded
    @POST("api/get_city")
    Call<List<CityModel>>get_cities(@Field("govern_id")int govern_id);
    @Multipart
    @POST("api/add_missing_found")
    Call<Item>add_item(@Part("user_id") RequestBody user_id,
                       @Part("type")RequestBody type,
                       @Part("name")RequestBody name,
                       @Part("category_id_fk")RequestBody category_id_fk,
                       @Part("govern_id_fk")RequestBody govern_id_fk,
                       @Part("city_id_fk")RequestBody city_id_fk,
                       @Part MultipartBody.Part img);
}
