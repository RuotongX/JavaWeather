package com.example.javaweather.Controller.HourPage;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaweather.Model.Weather;
import com.example.javaweather.R;

import java.util.ArrayList;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.MyViewHolder> {
    private ArrayList<Weather> hourList;

    public HourAdapter(ArrayList<Weather> hourList) {
        hourList.remove(0);
        this.hourList = hourList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forcast_hour, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String hour = hourList.get(position).getTime();
        String temp = hourList.get(position).getTemp();
        String feels = hourList.get(position).getFeels();
        String hum = hourList.get(position).getHumidity();
        int image = hourList.get(position).getIcon();

        holder.time_tv.setText(hour);
        holder.temperature_tv.setText(temp);
        holder.feels_tv.setText(feels);
        holder.humidity_tv.setText(hum);
        holder.weather_iv.setImageResource(image);

    }

    @Override
    public int getItemCount() {
        return hourList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView time_tv;
        private TextView temperature_tv;
        private TextView feels_tv;
        private TextView humidity_tv;
        private ImageView weather_iv;

        public MyViewHolder(final View view) {
            super(view);
            time_tv = view.findViewById(R.id.recycler_time_tv);
            temperature_tv = view.findViewById(R.id.recycler_temperature_tv);
            feels_tv = view.findViewById(R.id.recycler_feels_tv);
            humidity_tv = view.findViewById(R.id.recycler_humidity_tv);
            weather_iv = view.findViewById(R.id.recycler_weather_iv);

        }
    }

}
