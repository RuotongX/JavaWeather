package com.example.javaweather.Contoller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaweather.Model.GetApiValueToArray;

import android.os.Bundle;
import android.util.Log;

import com.example.javaweather.Model.Weather;
import com.example.javaweather.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Weather> weatherList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        GetApiValueToArray a = new GetApiValueToArray();
        recyclerView = findViewById(R.id.recyclerView);
        weatherList = new ArrayList<>();
        createWeather();
        setAdapter();

    }
    private void setAdapter(){
        recyclerAdapterDay adapter = new recyclerAdapterDay(weatherList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    public void createWeather(){
        for(int i = 0;i<5;i++){
            Weather w = new Weather();
            w.setWeather("Sunny");
            w.setDate(1655650800+i*86400);
            w.setTemp(283.56+i*2);
            w.setDescription("meo");
            w.setFeels(281.36+i*2);
            w.setIcon("d01.png");
            weatherList.add(w);
        }

    }
}