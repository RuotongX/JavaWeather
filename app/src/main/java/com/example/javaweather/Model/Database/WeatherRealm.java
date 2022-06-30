package com.example.javaweather.Model.Database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WeatherRealm extends RealmObject {
    @PrimaryKey
    private long timeCode;

    private double temp;
    private double feels;
    private String weather;
    private String description;
    private String icon;
    private String address;
    private int humidity;

    public long getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(long timeCode) {
        this.timeCode = timeCode;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels() {
        return feels;
    }

    public void setFeels(double feels) {
        this.feels = feels;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
