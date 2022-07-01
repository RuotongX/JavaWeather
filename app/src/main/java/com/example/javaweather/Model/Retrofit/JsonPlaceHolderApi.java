package com.example.javaweather.Model.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.example.javaweather.Model.Converter.Overall;


public interface JsonPlaceHolderApi {
    @GET("forecast?")
    Call<Overall> getPosts(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String key);
}
