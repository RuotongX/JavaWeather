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


    public String getTemp() {
        int t = (int)Math.round(temp);
        String result = String.valueOf(t);
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
        if(icon.equals("01d")||
                icon.equals("01n")||
                icon.equals("02d")||
                icon.equals("02n")||
                icon.equals("03d")||
                icon.equals("03n")||
                icon.equals("09d")||
                icon.equals("09n")||
                icon.equals("10d")||
                icon.equals("10n")||
                icon.equals("11d")||
                icon.equals("11n")||
                icon.equals("12d")||
                icon.equals("12n")||
                icon.equals("50d")||
                icon.equals("50n")) {
            this.icon = icon;
        } else {
            this.icon = "03d";
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
}
