package com.example.h3bnb.online;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shaon on 11/7/2016.
 */

public class APIClient {
    //put the ip address  and the name of your php project here
    public static final String BASE_URL_USER = "http://192.168.40.1:8000/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient2( ) {
        if (retrofit==null) {
            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL_USER).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }
}