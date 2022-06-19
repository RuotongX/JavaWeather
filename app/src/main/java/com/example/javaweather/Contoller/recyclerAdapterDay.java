package com.example.javaweather.Contoller;

import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaweather.Model.Weather;
import com.example.javaweather.R;

import java.util.ArrayList;

public class recyclerAdapterDay extends RecyclerView.Adapter<recyclerAdapterDay.MyViewHolder>{
    private ArrayList<Weather> weatherList;

    public recyclerAdapterDay(ArrayList<Weather> weatherList){
        weatherList.remove(0);
        this.weatherList = weatherList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView dateTxt;
        private TextView tempTxt;
        private ImageView weatherIcon;

        public MyViewHolder(final View view){
            super(view);
            dateTxt = view.findViewById(R.id.Colhour);
            tempTxt = view.findViewById(R.id.Coltemp);
            weatherIcon = view.findViewById(R.id.Colimg);
        }

    }

    @NonNull
    @Override
    public recyclerAdapterDay.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forcast_day,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterDay.MyViewHolder holder, int position) {
        String date = weatherList.get(position).getDate();
        String temp = weatherList.get(position).getTemp();
        holder.dateTxt.setText(date);
        holder.tempTxt.setText(temp);

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
