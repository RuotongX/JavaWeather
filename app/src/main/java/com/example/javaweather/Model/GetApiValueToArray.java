package com.example.javaweather.Model;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class GetApiValueToArray {
    //  For java URL, the http prefixion is needed, and after android P(9.0), to ensure data secure,https is required.
    // The reason for create a new thread to get JSON is because after Android 4.0,. program are forced to remove Internet accessing in main thread, they should be in single sub-thread.
    //  Because url is Internet based , need to add Internet permission in manifests.xml.
    private String url = "https://api.openweathermap.org/data/2.5/forecast?lat="+
            "-36.8509"+
            "&"+ "lon="+
            "174.7645"+
            "&appid="+
            "d1580a5eaffdf2ae907ca97ceaff0235";
    String temp;
    ArrayList weatherForcast = new ArrayList();

    public GetApiValueToArray(){
        StringBuilder json = new StringBuilder();
        new Thread(() -> {
            try {
                URL urlObject = new URL(url);
                URLConnection uc = urlObject.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String inputLine = null;
                while ( (inputLine = in.readLine()) != null) {
                    json.append(inputLine);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            temp =  json.toString();
        }).start();
    }

    public String getTemp() {
        return temp;
    }
}
