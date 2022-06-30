package com.example.javaweather.Model;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.javaweather.Model.Converter.List;
import com.example.javaweather.Model.Converter.Overall;
import com.example.javaweather.Model.Database.RealmManager;
import com.example.javaweather.Model.Database.WeatherRealm;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class GetApiValueToArray  {
    //  For java URL, the http prefixion is needed, and after android P(9.0), to ensure data secure,https is required.
    // The reason for create a new thread to get JSON is because after Android 4.0,. program are forced to remove Internet accessing in main thread, they should be in single sub-thread.
    //  Because url is Internet based , need to add Internet permission in manifests.xml.
    private String url = "";
    private String json_string;
    private ArrayList<Weather> weatherForecast = new ArrayList<>();
    public ArrayGetter wcallback;
    private Handler handler;
    private RealmManager rm;
    private String lat,lon;


    @SuppressLint("HandlerLeak")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public GetApiValueToArray(ArrayGetter c) {
        this.wcallback = c;

        //Database initialize
        rm = new RealmManager();
        weatherForecast = rm.selectFromDB();
        if(weatherForecast.size()!=0){
            wcallback.WeatherForecast(weatherForecast);
        }
    }

    public void RefreshData(){
        StringBuilder json = new StringBuilder();
        rm.DeleteWeather();
        // The handler here is to ensure the following data process step won't be in the url data getting thread but main thread.
        // So that the callback function could be clearly in main thread not in url getting thread.
        url = "https://api.openweathermap.org/data/2.5/forecast?lat=" +
                lat +
                "&" + "lon=" +
                lon +
                "&appid=" +
                "d1580a5eaffdf2ae907ca97ceaff0235";

        handler = new Handler(msg -> {
            if (msg.what == 0) {
                json_string = json.toString();
                Gson gson = new Gson();
                Overall raw = gson.fromJson(json_string, Overall.class);
                String address = raw.getCity().getName();
                for (int i = 0; i < raw.getList().size(); i++) {
                    List list = raw.getList().get(i);
                    rm.saveDatabase(list,address);
                    Weather w = new Weather();
                    w.setTemp(list.getMain().getTemp());
                    w.setFeels(list.getMain().getFeelsLike());
                    w.setWeather(list.getDetail().get(0).getMain());
                    w.setDescription(list.getDetail().get(0).getDescription());
                    w.setDate(list.getDt());
                    w.setTime(list.getDt());
                    w.setIcon(list.getDetail().get(0).getIcon());
                    w.setAddress(address);
                    w.setHumidity(list.getMain().getHumidity());
                    weatherForecast.add(w);
                }

                Log.d("success message", "  finished");
                wcallback.WeatherForecast(weatherForecast);
            }
            return false;
        });


        new Thread(() -> {
            try {
                URL urlObject = new URL(url);
                URLConnection uc = urlObject.openConnection();
                uc.setConnectTimeout(5000);
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String inputLine = "";
                while ((inputLine = in.readLine()) != null) {
                    json.append(inputLine);
                }
                in.close();
                if (json.length() != 0) {
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}

