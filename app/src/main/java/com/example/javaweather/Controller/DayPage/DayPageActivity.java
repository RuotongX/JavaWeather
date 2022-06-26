package com.example.javaweather.Controller.DayPage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javaweather.Controller.HourPage.HoursPageActivity;
import com.example.javaweather.Model.*;
import com.example.javaweather.R;

import java.util.ArrayList;

public class DayPageActivity extends AppCompatActivity implements ArrayGetter, HourRequestRecallInterface {
    private ArrayList<Weather> weatherForecast, daysForecast = new ArrayList<>();
    private ArrayList<ArrayList<Weather>> hoursForecast = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView address_tv, temperature_tv, weather_tv, date_tv;
    private ImageView weather_iv;
    private ImageButton hourDetail_ib;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetApiValueToArray(DayPageActivity.this);

        recyclerView = findViewById(R.id.recyclerView);
        date_tv = findViewById(R.id.date_tv);
        address_tv = findViewById(R.id.address_tv);
        temperature_tv = findViewById(R.id.temperature_tv);
        weather_tv = findViewById(R.id.weather_tv);
        weather_iv = findViewById(R.id.weather_iv);
        hourDetail_ib = findViewById(R.id.detail_ib);
        hourDetail_ib.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(DayPageActivity.this, HoursPageActivity.class);
            intent.putExtra("HourData", hoursForecast.get(0));
            startActivity(intent);
        });
        hourDetail_ib.setClickable(false);

    }

    private void setAdapter() {
        DayAdapter adapter = new DayAdapter(daysForecast, DayPageActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    //Transfer the weather list into Days(Arraylist) of Weather structure.
    public void getDaysWeather() {
        String preDate = weatherForecast.get(0).getDate();
        for (int i = 0; i < weatherForecast.size(); i++) {
            if (!preDate.equals(weatherForecast.get(i).getDate())) {
                Weather day = new Weather(weatherForecast.get(i).getDate(), weatherForecast.get(i).getIcon(), weatherForecast.get(i).getWeather(), weatherForecast.get(i).getTemp());
                daysForecast.add(day);
            }
            preDate = weatherForecast.get(i).getDate();
        }

    }

    // Transfer the weather list into Days(ArrayList) Hour(Arraylist) of Weather structure.
    // For example, there are 7 days in a week(Arraylist), each days have 24 hours(Arraylist), each hour has a Weather data.
    public void getHoursWeather() {
        String preDate = weatherForecast.get(0).getDate();
        ArrayList<Weather> dayStorage = new ArrayList<Weather>();
        for (int i = 0; i < weatherForecast.size(); i++) {
            if (!preDate.equals(weatherForecast.get(i).getDate()) && dayStorage.size() != 0) {
                ArrayList<Weather> Storage = new ArrayList<>();
                Storage.addAll(dayStorage);
                hoursForecast.add(Storage);
                dayStorage.clear();
            }
            Weather day = new Weather(weatherForecast.get(i).getTime(),
                    weatherForecast.get(i).getDate(),
                    weatherForecast.get(i).getIcon(),
                    weatherForecast.get(i).getWeather(),
                    weatherForecast.get(i).getTemp(),
                    weatherForecast.get(i).getFeels(),
                    weatherForecast.get(i).getDescription(),
                    weatherForecast.get(i).getHumidity());
            dayStorage.add(day);
            preDate = weatherForecast.get(i).getDate();
        }
        hoursForecast.add(dayStorage);
    }

    //Override callback function from ArrayGetter interface, aim to refresh the data on the page. Since url data getting is Asynchronous, after getting the data, page need to display.
    @Override
    public void WeatherForecast(ArrayList<Weather> weatherForecast) {
        this.weatherForecast = weatherForecast;
        getHoursWeather();
        getDaysWeather();

        Weather firstDayWeather = weatherForecast.get(0);
        date_tv.setText(firstDayWeather.getDate());
        temperature_tv.setText(firstDayWeather.getTemp());
        weather_tv.setText(firstDayWeather.getWeather());
        address_tv.setText(firstDayWeather.getAddress());
        weather_iv.setImageResource(firstDayWeather.getIcon());
        setAdapter();
        hourDetail_ib.setClickable(true);


    }

    // Override callback function from HourRequestRecall interface, aim to jump to HourPage by passing the selected day's weather data.
    @Override
    public void callback(int position) {
        Intent intent = new Intent();
        intent.setClass(DayPageActivity.this, HoursPageActivity.class);
        intent.putExtra("HourData", hoursForecast.get(position + 1));
        DayPageActivity.this.startActivity(intent);
    }
}

