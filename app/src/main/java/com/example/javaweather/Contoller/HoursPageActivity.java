package com.example.javaweather.Contoller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private View prebutton;
    private RecyclerView recyclerView;
    private TextView feelstemp,humidity,weather,temp,date,hour;
    private ImageView weaHourimg;
    ArrayList<Weather> hourlist = new ArrayList<>();

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hour_weather);
        Intent intent = getIntent();
        hourlist = (ArrayList) intent.getSerializableExtra("HourData");
        recyclerView = findViewById(R.id.recyclerHourView);
        feelstemp = findViewById(R.id.feelstemp);
        humidity = findViewById(R.id.humidityValue);
        weather = findViewById(R.id.Hourweather);
        temp = findViewById(R.id.Hourtemp);
        date = findViewById(R.id.Hourdate);
        hour = findViewById(R.id.hour);
        weaHourimg = findViewById(R.id.weaHourimg);

        feelstemp.setText(hourlist.get(0).getFeels());
        humidity.setText(hourlist.get(0).getHumidity());
        weather.setText(hourlist.get(0).getDescription());
        temp.setText(hourlist.get(0).getTemp());
        date.setText(hourlist.get(0).getDate());
        hour.setText(hourlist.get(0).getTime());

        String imageName = hourlist.get(0).getIcon();
        int resID = getResources().getIdentifier(imageName,"drawable",HoursPageActivity.this.getPackageName());
        weaHourimg.setImageResource(resID);

        setAdapter();


        prebutton = findViewById(R.id.back);
        prebutton.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                finish();;
            }
        });
    }
    private void setAdapter() {
        recyclerAdapterHour adapter = new recyclerAdapterHour(hourlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
