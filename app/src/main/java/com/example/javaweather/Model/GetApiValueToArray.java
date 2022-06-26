package com.example.javaweather.Model;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.javaweather.Model.Converter.Example;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class GetApiValueToArray {
    //  For java URL, the http prefixion is needed, and after android P(9.0), to ensure data secure,https is required.
    // The reason for create a new thread to get JSON is because after Android 4.0,. program are forced to remove Internet accessing in main thread, they should be in single sub-thread.
    //  Because url is Internet based , need to add Internet permission in manifests.xml.
    private String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" +
            "-36.8509" +
            "&" + "lon=" +
            "174.7645" +
            "&appid=" +
            "d1580a5eaffdf2ae907ca97ceaff0235";
    private String json_string;
    private ArrayList<Weather> weatherForecast = new ArrayList<>();
    public ArrayGetter wcallback;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public GetApiValueToArray(ArrayGetter c) {
        this.wcallback = c;
        StringBuilder json = new StringBuilder();

        new Thread(() -> {
            try {
                URL urlObject = new URL(url);
                URLConnection uc = urlObject.openConnection();
                uc.setConnectTimeout(5000);
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    json.append(inputLine);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            json_string = json.toString();
            Gson gson = new Gson();
            Example raw = gson.fromJson(json_string, Example.class);
            for (int i = 0; i < raw.getList().size(); i++) {
                Weather w = new Weather();
                w.setTemp(raw.getList().get(i).getMain().getTemp());
                w.setFeels(raw.getList().get(i).getMain().getFeelsLike());
                w.setWeather(raw.getList().get(i).getDetail().get(0).getMain());
                w.setDescription(raw.getList().get(i).getDetail().get(0).getDescription());
                w.setDate(raw.getList().get(i).getDt());
                w.setTime(raw.getList().get(i).getDt());
                w.setIcon(raw.getList().get(i).getDetail().get(0).getIcon());
                w.setAddress(raw.getCity().getName());
                w.setHumidity(raw.getList().get(i).getMain().getHumidity());
                weatherForecast.add(w);
            }
            Log.d("success message", "  finished");
            wcallback.WeatherForecast(weatherForecast);

        }).start();


    }


}

