package com.example.javaweather.Contoller;

import androidx.appcompat.app.AppCompatActivity;
import com.example.javaweather.Model.GetApiValueToArray;

import android.os.Bundle;
import android.util.Log;

import com.example.javaweather.R;

public class MainActivity extends AppCompatActivity {
    GetApiValueToArray a = new GetApiValueToArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}