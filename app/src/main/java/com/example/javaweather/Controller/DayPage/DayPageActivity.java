package com.example.javaweather.Controller.DayPage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.javaweather.Controller.HourPage.HoursPageActivity;
import com.example.javaweather.Model.*;
import com.example.javaweather.R;


import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class DayPageActivity extends AppCompatActivity implements ArrayGetter, HourRequestRecallInterface, LocationListener {
    private ArrayList<Weather> weatherForecast, daysForecast;
    private ArrayList<ArrayList<Weather>> hoursForecast;
    private LocationManager locationManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView address_tv, temperature_tv, weather_tv, date_tv;
    private ImageView weather_iv;
    private ImageButton hourDetail_ib;
    private ProgressBar loading_bar;
    private GetApiValueToArray getApi;
    private  DayAdapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("Realm.Database.Weather").build();
        Realm.setDefaultConfiguration(configuration);
        weatherForecast = new ArrayList<>();
        daysForecast = new ArrayList<>();
        hoursForecast = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        date_tv = findViewById(R.id.date_tv);
        address_tv = findViewById(R.id.address_tv);
        temperature_tv = findViewById(R.id.temperature_tv);
        weather_tv = findViewById(R.id.weather_tv);
        weather_iv = findViewById(R.id.weather_iv);
        hourDetail_ib = findViewById(R.id.detail_ib);
        loading_bar = findViewById(R.id.loading_bar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView.setOnTouchListener((v, event) -> true);
        hourDetail_ib.setClickable(false);
        setAdapter();

        getApi = new GetApiValueToArray(DayPageActivity.this);
        if(isNetworkConnected()==false){
            //If detect network connection is false, all the elements should be interacted with. (Locking them is because once data refreshing, the new data would replace old data.)
            recyclerView.setOnTouchListener((v, event) -> false);
            hourDetail_ib.setClickable(true);
        } else{
            if(ActivityCompat.checkSelfPermission(DayPageActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(DayPageActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            }

            getLocation();
        }


        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.teal_200));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.gray_background));
        swipeRefreshLayout.setProgressViewOffset(true,10,200);
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setOnRefreshListener(()-> {
                if(isNetworkConnected()==false){
                    return;
                }
                //While refreshing the recyclerView should been disable, because data in recyclerView is changing, if user interact with them, it would return non pointer exception.
                recyclerView.setOnTouchListener((v, event) -> true);

                getLocation();
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //While user swipe recyclerview, the refresh function has been disable to prevent mistake refreshing.
                //The top if function is to prevent the system already in refreshing state, the disable command would make refreshing icon disappear.
                if(!swipeRefreshLayout.isRefreshing()){
                    if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                        swipeRefreshLayout.setEnabled(false);
                    } else {
                        swipeRefreshLayout.setEnabled(true);
                    }
                }
            }
        });

        hourDetail_ib.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(DayPageActivity.this, HoursPageActivity.class);
            intent.putExtra("HourData", hoursForecast.get(0));
            startActivity(intent);
        });


    }

    private void setAdapter() {
        adapter = new DayAdapter(daysForecast, DayPageActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) DayPageActivity.this);

    }

    //Transfer the weather list into Days(Arraylist) of Weather structure.
    public void getDaysWeather() {
        daysForecast.clear();
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
        hoursForecast.clear();
        String preDate = weatherForecast.get(0).getDate();
        ArrayList<Weather> dayStorage = new ArrayList<>();
        for (int i = 0; i < weatherForecast.size(); i++) {
            if (!preDate.equals(weatherForecast.get(i).getDate()) && dayStorage.size() != 0) {
                ArrayList<Weather> Storage = new ArrayList<>(dayStorage);
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

    //Check device is online or not
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    //Override callback function from ArrayGetter interface, aim to refresh the data on the page. Since url data getting is Asynchronous, after getting the data, page need to display.
    @Override
    public void WeatherForecast(ArrayList<Weather> weatherForecast) {

        this.weatherForecast = weatherForecast;
        getHoursWeather();
        getDaysWeather();

        Weather firstDayWeather = weatherForecast.get(0);
        loading_bar.setVisibility(View.GONE);

        date_tv.setText(firstDayWeather.getDate());
        temperature_tv.setText(firstDayWeather.getTemp());
        weather_tv.setText(firstDayWeather.getWeather());
        address_tv.setText(firstDayWeather.getAddress());
        weather_iv.setImageResource(firstDayWeather.getIcon());
        //After refreshing, set the refreshing icon disappear
        swipeRefreshLayout.setRefreshing(false);
        //Once refreshing done, recyclerView should be able to interact.
        recyclerView.setOnTouchListener((v, event) -> false);
//        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(@NonNull Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);

        getApi.setLat(lat);
        getApi.setLon(lon);
        getApi.RefreshData();
//       If need to use more address information, the following code would be useful.

//        Geocoder geocoder = new Geocoder(DayPageActivity.this, Locale.getDefault());
//        try{
//            List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
//            Address address = addressList.get(0);
//        } catch(IOException e){
//            e.printStackTrace();
//        }
        locationManager.removeUpdates(this);

        }
}

