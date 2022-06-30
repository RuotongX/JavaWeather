package com.example.javaweather.Model;


import android.util.Log;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.example.javaweather.R;

public class Weather implements Serializable {
    private String temp;
    private String feels;
    private String weather;
    private String description;
    private String date;
    private String time;
    private int icon;
    private String address;
    private String humidity;
    private long timeCode;


    public String getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        int t = (int) Math.round(temp - 273.15);
        String result = String.valueOf(t) + "°C";
        this.temp = result;
    }

    public String getFeels() {

        return feels;
    }

    public void setFeels(double feels) {
        int t = (int) Math.round(feels - 273.15);
        String result = String.valueOf(t) + "°C";
        this.feels = result;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(long timeCode) {
        Date date = new Date(timeCode * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getDefault());
        this.time = sdf.format(date);
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        switch (icon) {
            case "01d":
                this.icon = R.drawable.d01;
                break;
            case "01n":
                this.icon = R.drawable.n01;
                break;
            case "02d":
                this.icon = R.drawable.d02;
                break;
            case "02n":
                this.icon = R.drawable.n02;
                break;
            case "10d":
                this.icon = R.drawable.d10;
                break;
            case "10n":
                this.icon = R.drawable.n10;
                break;
            case "09d":
                this.icon = R.drawable.d09;
                break;
            case "11d":
                this.icon = R.drawable.d11;
                break;
            case "13d":
                this.icon = R.drawable.d13;
                break;
            case "50d":
                this.icon = R.drawable.d50;
                break;
            default:
                this.icon = R.drawable.d03;
                break;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(long timeCode) {
        Date d = new Date(timeCode * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        this.date = sdf.format(d);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity + "%";
    }


    public Weather(String date, int icon, String weather, String temp) {
        this.icon = icon;
        this.date = date;
        this.weather = weather;
        this.temp = temp;
    }

    public Weather() {

    }

    public Weather(String time, String date, int icon, String weather, String temp, String feels, String description, String humidity) {
        this.icon = icon;
        this.date = date;
        this.weather = weather;
        this.temp = temp;
        this.feels = feels;
        this.description = description;
        this.time = time;
        this.humidity = humidity;
    }
}
