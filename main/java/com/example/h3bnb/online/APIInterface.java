package com.example.h3bnb.online;

import com.example.h3bnb.models.BienModel;
import com.example.h3bnb.models.CityModel;
import com.example.h3bnb.models.CountryModel;
import com.example.h3bnb.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Shaon on 8/15/2016.
 */
public interface APIInterface {

    @FormUrlEncoded
    @POST("api/calls/userCalls.php")
    Call<MSG> newUser(@Field("newuser") String keyUser, @Field("fname") String fname,
                      @Field("lname") String lname, @Field("email") String email,
                      @Field("password") String password, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("api/calls/contratsCalls.php")
    Call<MSG> newContrat(@Field("newcontrats") String keyContrat, @Field("dates") String dates,
                      @Field("chat") String chat, @Field("idUser") int idUser,
                      @Field("idBien") int idBien);


    @GET("api/calls/getUsers.php")
    Call<List<UserModel>> getUsers();

    @GET("api/calls/getCountries.php")
    Call<List<CountryModel>> getCountries();

    @GET("api/calls/getBiens.php")
    Call<List<BienModel>> getBiens();

    @GET("api/calls/getBiensByIdCity.php")
    Call<List<BienModel>> getBiensByIdCity(@Query("idCity") int id);

    @GET("api/calls/getBienById.php")
    Call<List<BienModel>> getBienById(@Query("id") int id);

    @GET("api/calls/getBiensByIdUser.php")
    Call<List<BienModel>> getBiensByIdUser(@Query("idOwner") int idUser);

    @GET("api/calls/getCityById.php")
    Call<List<CityModel>> getCityById(@Query("id") int id);


}