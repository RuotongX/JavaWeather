package com.example.javaweather.Model;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;



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
        CountDownLatch cdl = new CountDownLatch(1);
        new Thread(() -> {
            try {
                URL urlObject = new URL(url);
                URLConnection uc = urlObject.openConnection();
                uc.setConnectTimeout(5000);
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
            cdl.countDown();
        }).start();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d(temp,"important         message");
    }

    public String getTemp() {
        return temp;
    }
}
