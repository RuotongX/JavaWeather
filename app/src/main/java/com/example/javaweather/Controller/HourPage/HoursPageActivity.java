package com.example.javaweather.Controller.HourPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javaweather.Model.Weather;
import com.example.javaweather.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HoursPageActivity extends AppCompatActivity {
    private ImageButton back_ib;
    private RecyclerView recyclerView;
    private TextView feels_tv, humidity_tv, description_tv, temperature_tv, date_tv, time_tv;
    private ImageView weather_iv;
    ArrayList<Weather> hourlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hour_weather);
        Intent intent = getIntent();
        hourlist = (ArrayList) intent.getSerializableExtra("HourData");
        recyclerView = findViewById(R.id.recyclerHourView);
        feels_tv = findViewById(R.id.feels_tv);
        humidity_tv = findViewById(R.id.humidity_tv);
        description_tv = findViewById(R.id.description_tv);
        temperature_tv = findViewById(R.id.temperature_tv);
        date_tv = findViewById(R.id.date_tv);
        time_tv = findViewById(R.id.time_tv);
        weather_iv = findViewById(R.id.weather_iv);

        Weather initialHourWeather = hourlist.get(0);

        feels_tv.setText(initialHourWeather.getFeels());
        humidity_tv.setText(initialHourWeather.getHumidity());
        description_tv.setText(initialHourWeather.getDescription());
        temperature_tv.setText(initialHourWeather.getTemp());
        date_tv.setText(initialHourWeather.getDate());
        time_tv.setText(initialHourWeather.getTime());
        weather_iv.setImageResource(initialHourWeather.getIcon());

        setAdapter();


        back_ib = findViewById(R.id.back_button);
        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ;
            }
        });
    }

    private void setAdapter() {
        HourAdapter adapter = new HourAdapter(hourlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
