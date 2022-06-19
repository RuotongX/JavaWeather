package com.example.javaweather.Contoller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaweather.Model.GetApiValueToArray;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.javaweather.Model.Weather;
import com.example.javaweather.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Weather> weatherList;
    private RecyclerView recyclerView;
    private TextView address, temperature, weatherCondition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        GetApiValueToArray a = new GetApiValueToArray();
        recyclerView = findViewById(R.id.recyclerView);
        address = findViewById(R.id.address);
        temperature = findViewById(R.id.temp);
        weatherCondition = findViewById(R.id.weather);
        weatherList = new ArrayList<>();

        createWeather();
        setAdapter();

    }

    private void setAdapter() {
        recyclerAdapterDay adapter = new recyclerAdapterDay(weatherList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void createWeather() {
        for (int i = 0; i < 5; i++) {
            Weather w = new Weather();
            w.setWeather("Sunny");
            w.setDate(1655650800 + i * 86400);
            w.setTemp(283.56 + i * 2);
            w.setDescription("meo");
            w.setFeels(281.36 + i * 2);
            w.setIcon("d01.png");
            weatherList.add(w);
        }

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