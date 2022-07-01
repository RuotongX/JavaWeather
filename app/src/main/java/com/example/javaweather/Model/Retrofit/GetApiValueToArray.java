package com.example.javaweather.Model.Retrofit;


import android.annotation.SuppressLint;

import android.os.Build;

import android.os.Handler;

import android.util.Log;


import androidx.annotation.RequiresApi;


import com.example.javaweather.Model.ArrayGetter;
import com.example.javaweather.Model.Converter.WeatherListApi;
import com.example.javaweather.Model.Converter.Overall;
import com.example.javaweather.Model.Database.RealmManager;

import com.example.javaweather.Model.Weather;

import java.util.ArrayList;

import java.util.Collections;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GetApiValueToArray {
    //  For java URL, the http prefixion is needed, and after android P(9.0), to ensure data secure,https is required.
    // The reason for create a new thread to get JSON is because after Android 4.0,. program are forced to remove Internet accessing in main thread, they should be in single sub-thread.
    //  Because url is Internet based , need to add Internet permission in manifests.xml.
    private String url;
    //    private String json_string;
    private ArrayList<Weather> weatherForecast = new ArrayList<>();
    public ArrayGetter wcallback;
    private Handler handler;
    private RealmManager rm;
    private String lat, lon;
    private String key = "d1580a5eaffdf2ae907ca97ceaff0235";
    private Overall raw;

    @SuppressLint("HandlerLeak")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public GetApiValueToArray(ArrayGetter c) {
        this.wcallback = c;

        //Database initialize
        rm = new RealmManager();
        weatherForecast = rm.selectFromDB();
        Log.d("amount message", String.valueOf(weatherForecast.size()));
        if (weatherForecast.size() != 0) {
            //when local database has data, UI thread get recall method and display the data. The other thread are getting new data at backend if network available.
            Collections.sort(weatherForecast);
            wcallback.WeatherForecast(weatherForecast);
        }
    }

    public void RefreshData() {
        rm.DeleteWeather();
        // The handler here is to ensure the following data process step won't be in the url data getting thread but main thread.
        // So that the callback function could be clearly in main thread not in url getting thread.
        url = "https://api.openweathermap.org/data/2.5/";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Overall> call = jsonPlaceHolderApi.getPosts(lat, lon, key);

        call.enqueue(new Callback<Overall>() {
            @Override
            public void onResponse(Call<Overall> call, Response<Overall> response) {
                if (!response.isSuccessful()) {
                    Log.d("URL message", "Code" + response.code());
                }

                raw = response.body();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Call<Overall> call, Throwable t) {
                Log.d("URL message", "Getting information failed");
                handler.sendEmptyMessage(1);
            }
        });

        handler = new Handler(msg -> {
            if (msg.what == 0) {
                weatherForecast.clear();
                for (int i = 0; i < raw.getList().size(); i++) {
                    WeatherListApi weatherListApi = raw.getList().get(i);
                    Weather w = new Weather();
                    w.setTemp(weatherListApi.getMain().getTemp());
                    w.setFeels(weatherListApi.getMain().getFeelsLike());
                    w.setWeather(weatherListApi.getDetail().get(0).getMain());
                    w.setDescription(weatherListApi.getDetail().get(0).getDescription());
                    w.setDate(weatherListApi.getDt());
                    w.setTime(weatherListApi.getDt());
                    w.setIcon(weatherListApi.getDetail().get(0).getIcon());
                    w.setAddress(raw.getCity().getName());
                    w.setHumidity(weatherListApi.getMain().getHumidity());
                    weatherForecast.add(w);
                }
                rm.saveDatabase(raw);
                Log.d("success message", "  finished");
                wcallback.WeatherForecast(weatherForecast);
            }
            return false;
        });

    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}

