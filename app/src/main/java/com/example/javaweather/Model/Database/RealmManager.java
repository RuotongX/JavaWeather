package com.example.javaweather.Model.Database;

import com.example.javaweather.Model.Converter.List;
import com.example.javaweather.Model.Weather;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmManager {
    Realm realm;
    RealmResults<WeatherRealm> weatherDatabase;

    public RealmManager(){
        realm = Realm.getDefaultInstance();
    }

    public void saveDatabase(List instance, String address){
        realm.executeTransactionAsync(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                WeatherRealm wr = realm.createObject(WeatherRealm.class,instance.getDt());
                wr.setTemp(instance.getMain().getTemp());
                wr.setFeels(instance.getMain().getFeelsLike());
                wr.setWeather(instance.getDetail().get(0).getMain());
                wr.setDescription(instance.getDetail().get(0).getDescription());
                wr.setIcon(instance.getDetail().get(0).getIcon());
                wr.setAddress(address);
                wr.setHumidity(instance.getMain().getHumidity());
            }
        });
    }

    public ArrayList<Weather> selectFromDB(){
        weatherDatabase = realm.where(WeatherRealm.class).findAll();
        ArrayList<Weather> weatherForecast = new ArrayList<>();
        for(WeatherRealm wr:weatherDatabase){
            Weather w = new Weather();
            w.setTemp(wr.getTemp());
            w.setFeels(wr.getFeels());
            w.setWeather(wr.getWeather());
            w.setDescription(wr.getDescription());
            w.setDate(wr.getTimeCode());
            w.setTime(wr.getTimeCode());
            w.setIcon(wr.getIcon());
            w.setAddress(wr.getAddress());
            w.setHumidity(wr.getHumidity());
            weatherForecast.add(w);
        }
        return weatherForecast;
    }
    public void DeleteWeather(){
        realm.deleteAll();
    }
}
