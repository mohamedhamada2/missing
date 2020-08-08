package com.alatheer.missing.Data.Remote;


import com.alatheer.missing.Categories.Category;
import com.alatheer.missing.Countries.CityModel;
import com.alatheer.missing.Countries.CountryModel;
import com.alatheer.missing.Data.Remote.Model.Authentication.UserData;
import com.alatheer.missing.Data.Remote.Model.Comment.Comment;
import com.alatheer.missing.Data.Remote.Model.Items.Item;
import com.alatheer.missing.Data.Remote.Model.Authentication.User;
import com.alatheer.missing.Data.Remote.Model.*;
import com.alatheer.missing.Data.Remote.Model.Success.Success;


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
import retrofit2.http.Query;

public interface GetDataService {

    @FormUrlEncoded
    @POST("api/register")
    Call<User> register_user(@Field("user_name") String user_name,
                             @Field("password") String password,
                             @Field("email") String national_num,
                             @Field("phone")String phone,
                             @Field("adress")String adress);
    @FormUrlEncoded
    @POST("api/verifycode")
    Call<User>verify_code(@Field("phone")String Phone,@Field("code")String code);
    @FormUrlEncoded
    @POST("api/login")
    Call<User>login_user(@Field("phone")String phone, @Field("password")String password);
    @GET("api/categories")
    Call<List<Category>>get_categories();
    @GET("api/all_governs")
    Call<List<CountryModel>>get_governs();
    @FormUrlEncoded
    @POST("api/get_city")
    Call<List<CityModel>>get_cities(@Field("govern_id")int govern_id);
    @Multipart
    @POST("api/add_missing_found")
    Call<Success>add_item(@Part("user_id") RequestBody user_id,
                          @Part("type")RequestBody type,
                          @Part("name")RequestBody name,
                          @Part("category_id_fk")RequestBody category_id_fk,
                          @Part("govern_id_fk")RequestBody govern_id_fk,
                          @Part("city_id_fk")RequestBody city_id_fk,
                          @Part MultipartBody.Part img);

    @POST("api/search_by")
    Call<List<Item>>get_all_items(@Query("category_id_fk") String category_id_fk,
                                  @Query("name") String name,
                                  @Query("type") String type,
                                  @Query("govern_id_fk") String govern_id_fk,
                                  @Query("city_id_fk") String city_id_fk);
    @FormUrlEncoded
    @POST("api/add_comment")
    Call<Success>addcomment(@Field("item_id_fk") String item_id_fk,
                            @Field("type") String type,
                            @Field("item_person_id_fk") String item_person_id_fk,
                            @Field("comment_person_id_fk") String comment_person_id_fk,
                            @Field("comment") String comment);
    @POST("api/get_comments")
    Call<List<Comment>>get_all_comments(@Query("item_id_fk")String item_id_fk);
    @FormUrlEncoded
    @POST("api/sendcode")
    Call<User>send_code(@Field("email")String email);
    @FormUrlEncoded
    @POST("api/resetpassword")
    Call<User>reset_password(@Field("email")String email,@Field("code")String code,@Field("password")String password);

}
