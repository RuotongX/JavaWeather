package com.example.javaweather.Contoller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.javaweather.Model.Weather;
import com.example.javaweather.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Weather> weatherList;
    private ArrayList<Weather> daysForcast =  new ArrayList<>();;
    private ArrayList<ArrayList<Weather>> hoursForcast = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView address, temperature, weatherCondition,daydate;
    private ImageButton hourPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        GetApiValueToArray a = new GetApiValueToArray();
        recyclerView = findViewById(R.id.recyclerView);
        daydate = findViewById(R.id.DayDate);
        address = findViewById(R.id.address);
        temperature = findViewById(R.id.temp);
        weatherCondition = findViewById(R.id.weather);
        hourPage = (ImageButton) findViewById(R.id.hourDetail);
        hourPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,HoursPageActivity.class);
                intent.putExtra("HourData",hoursForcast.get(0));
                startActivity(intent);
                }
            });



        weatherList = new ArrayList<>();
        createWeather();

        getDaysWeather();
        getHoursWeather();

        daydate.setText(weatherList.get(0).getDate());
        temperature.setText(weatherList.get(0).getTemp());
        weatherCondition.setText(weatherList.get(0).getWeather());
        setAdapter();

    }

    private void setAdapter() {

        recyclerAdapterDay adapter = new recyclerAdapterDay(daysForcast,hoursForcast);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }



    public void createWeather() {
        for (int i = 0; i < 40; i++) {
            long timecode = 1655650800 + i * 10800;
            Weather w = new Weather();
            w.setWeather("Sunny");
            w.setDate(timecode);
            w.setTime(timecode);
            w.setTemp(283.56 + i * 2);
            w.setFeels(281.36 + i * 2);
            w.setIcon("d01.png");
            w.setDescription("Mainly Sunny");
            w.setHumidity(58);
            weatherList.add(w);

        }

    }
    public void getDaysWeather(){
//        Log.d("important message",String.valueOf(weatherList.size()));
        String preDate = weatherList.get(0).getDate();
        for (int i = 0; i<weatherList.size();i++){
            if(!preDate.equals(weatherList.get(i).getDate())){
                Weather day = new Weather(weatherList.get(i).getDate(),weatherList.get(i).getIcon(),weatherList.get(i).getWeather(),weatherList.get(i).getTemp());
                daysForcast.add(day);
            }
            preDate = weatherList.get(i).getDate();

        }

    }
    public void getHoursWeather(){
        String preDate = weatherList.get(0).getDate();
        ArrayList<Weather> dayStorge = new ArrayList<Weather>();
        for (int i = 0; i<weatherList.size();i++){
            if(!preDate.equals(weatherList.get(i).getDate())&&dayStorge.size()!=0){
                ArrayList<Weather> Storge = new ArrayList<>();
                Storge.addAll(dayStorge);
                hoursForcast.add(Storge);
                dayStorge.clear();
            }
            Weather day = new Weather(weatherList.get(i).getTime(),
                    weatherList.get(i).getDate(),
                    weatherList.get(i).getIcon(),
                    weatherList.get(i).getWeather(),
                    weatherList.get(i).getTemp(),
                    weatherList.get(i).getFeels(),
                    weatherList.get(i).getDescription(),
                    weatherList.get(i).getHumidity());
            dayStorge.add(day);
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