package com.example.javaweather.Contoller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.javaweather.Model.*;
import com.example.javaweather.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ArrayGetter {
    private ArrayList<Weather> weatherList =  new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView address, temperature, weatherCondition,daydate;
    private ImageView weaimg;
    private ImageButton hourPage;
    private GetApiValueToArray a;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a = new GetApiValueToArray(MainActivity.this);

        recyclerView = findViewById(R.id.recyclerView);
        daydate = findViewById(R.id.DayDate);
        address = findViewById(R.id.address);
        temperature = findViewById(R.id.temp);
        weatherCondition = findViewById(R.id.weather);
        weaimg = findViewById(R.id.weaimg);
        hourPage = (ImageButton) findViewById(R.id.hourDetail);
        hourPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,HoursPageActivity.class);
                intent.putExtra("HourData",a.gethf().get(0));
                startActivity(intent);
                }
            });
        hourPage.setClickable(false);

    }

    private void setAdapter() {
        recyclerAdapterDay adapter = new recyclerAdapterDay(a.getDaysWeather(),a.getHoursWeather());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void WeatherForcast(ArrayList weatherForcast) {

        this.weatherList = weatherForcast;

        new Handler(Looper.getMainLooper()).post(new Runnable(){
            @Override
            public void run() {
                daydate.setText(weatherList.get(0).getDate());
                temperature.setText(weatherList.get(0).getTemp());
                weatherCondition.setText(weatherList.get(0).getWeather());
                address.setText(weatherList.get(0).getAddress());
                String imageName = weatherList.get(0).getIcon();
                int resID = getResources().getIdentifier(imageName,"drawable",MainActivity.this.getPackageName());
                weaimg.setImageResource(resID);
                setAdapter();
                hourPage.setClickable(true);
            }
        });


//        Log.d("API message",weatherForcast);
    }




}

//    private FusedLocationProviderClient fusedLocationProviderClient;
//fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        // TODO: Consider calling
//        //    ActivityCompat#requestPermissions
//        // here to request the missing permissions, and then overriding
//        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//        //                                          int[] grantResults)
//        // to handle the case where the user grants the permission. See the documentation
//        // for ActivityCompat#requestPermissions for more details.
//        return;
//        }
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//@Override
//public void onComplete(@NonNull Task<Location> task) {
//        Location location = task.getResult();
//        if(location != null) {
//        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
//        try{
//        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//        address.setText(addressList.get(0).getLocality());
//        } catch(IOException e){
//
//        }
//        }
//        }
//        });