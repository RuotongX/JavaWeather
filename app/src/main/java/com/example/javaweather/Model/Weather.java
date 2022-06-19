package com.example.javaweather.Model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Weather {
    private double temp;
    private double feels;
    private String weather;
    private String description;
    private String date;
    private String time;
    private String icon;
    private String address;
    private int humidity;


    public String getTemp() {
        int t = (int)Math.round(temp);
        String result = String.valueOf(t)+"°C";
        return result;
    }

    public void setTemp(double temp) {
        this.temp = temp - 273.15;
    }

    public double getFeels() {
        return feels;
    }

    public void setFeels(double feels) {
        this.feels = feels - 273.15;
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
        Date date = new Date(timeCode*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getDefault());
        this.time = sdf.format(date);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        switch(icon) {
            case "01d":
                this.icon = "d01.png";
            case "01n":
                this.icon = "n01.png";
            case "02d":
                this.icon = "d02.png";
            case "02n":
                this.icon = "n02.png";
            case "10d":
                this.icon = "d10.png";
            case "10n":
                this.icon = "n10.png";
            case "03d":
                this.icon = "d03.png";
            case "09d":
                this.icon = "d09.png";
            case "11d":
                this.icon = "d11.png";
            case "13d":
                this.icon = "d13.png";
            case "50d":
                this.icon = "d50.png";
            default:
                this.icon = "d03.png";
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(long timeCode) {
        Date d = new Date(timeCode*1000L);
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

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
