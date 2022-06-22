package com.example.javaweather.Contoller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.javaweather.Model.Weather;
import com.example.javaweather.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Weather> weatherList;
    private ArrayList<Weather> daysForcast =  new ArrayList<>();;
    private RecyclerView recyclerView;
    private TextView address, temperature, weatherCondition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        GetApiValueToArray a = new GetApiValueToArray();
        recyclerView = findViewById(R.id.recyclerView);
        address = findViewById(R.id.date);
        temperature = findViewById(R.id.feelstemp);
        weatherCondition = findViewById(R.id.weather);


        weatherList = new ArrayList<>();
        createWeather();
        getDaysWeather();

        temperature.setText(daysForcast.get(0).getTemp());
        weatherCondition.setText(daysForcast.get(0).getWeather());
        setAdapter();

    }

    private void setAdapter() {
        recyclerAdapterDay adapter = new recyclerAdapterDay(daysForcast);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void createWeather() {
        for (int i = 0; i < 40; i++) {
            Weather w = new Weather();
            w.setWeather("Sunny");
            w.setDate(1655650800 + i * 10800);
            w.setTemp(283.56 + i * 2);
            w.setDescription("meo");
            w.setFeels(281.36 + i * 2);
            w.setIcon("d01.png");
            w.setDescription("Mainly sunny");
            w.setHumidity(58);
            weatherList.add(w);
        }

    }
    public void getDaysWeather(){
        String preDate = weatherList.get(0).getDate();
        Weather firstday = new Weather(weatherList.get(0).getDate(),weatherList.get(0).getIcon(),weatherList.get(0).getWeather(),weatherList.get(0).getTemp());
        daysForcast.add(firstday);
        for (int i = 1; i<weatherList.size();i++){
            if(!preDate.equals(weatherList.get(i).getDate())){
                Weather day = new Weather(weatherList.get(i).getDate(),weatherList.get(i).getIcon(),weatherList.get(i).getWeather(),weatherList.get(i).getTemp());
                daysForcast.add(day);
                Log.d("Program message",preDate);
            }
            preDate = weatherList.get(i).getDate();
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