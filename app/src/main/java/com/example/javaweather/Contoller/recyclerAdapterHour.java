package com.example.javaweather.Contoller;

import android.util.Log;
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

public class recyclerAdapterHour extends RecyclerView.Adapter<recyclerAdapterHour.MyViewHolder>{
    private ArrayList<Weather> hourList;

    public recyclerAdapterHour(ArrayList<Weather> hourList){
        hourList.remove(0);
        this.hourList = hourList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView timeTxt;
        private TextView tempTxt;
        private TextView feelsTxt;
        private TextView humTxt;
        private ImageView weatherIcon;

        public MyViewHolder(final View view){
            super(view);
            timeTxt = view.findViewById(R.id.Colhour);
            tempTxt = view.findViewById(R.id.ColHourtemp);
            feelsTxt = view.findViewById(R.id.ColHourFeels);
            humTxt = view.findViewById(R.id.ColHourHum);
            weatherIcon = view.findViewById(R.id.ColHourImg);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forcast_hour,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String hour = hourList.get(position).getTime();
        String temp = hourList.get(position).getTemp();
        String feels = hourList.get(position).getFeels();
        String hum = hourList.get(position).getHumidity();
        holder.timeTxt.setText(hour);
        holder.tempTxt.setText(temp);
        holder.feelsTxt.setText(feels);
        holder.humTxt.setText(hum);
//        Log.d("date message",hourList.get(position).getTime()+hourList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return hourList.size();
    }

}
