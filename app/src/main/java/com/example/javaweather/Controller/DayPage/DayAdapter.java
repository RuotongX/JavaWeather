package com.example.javaweather.Controller.DayPage;


import android.content.res.Resources;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.javaweather.Model.Weather;
import com.example.javaweather.R;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder> {
    private ArrayList<Weather> weatherList;

    private HourRequestRecallInterface wcallback;

    public DayAdapter(ArrayList<Weather> weatherList, HourRequestRecallInterface cb) {
        this.wcallback = cb;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public DayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forcast_day, parent, false);
        return new MyViewHolder(itemView, wcallback);
    }

    @Override
    public void onBindViewHolder(@NonNull DayAdapter.MyViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();
        String date = weatherList.get(position).getDate();
        String temp = weatherList.get(position).getTemp();
        int icon = weatherList.get(position).getIcon();

        holder.weather_iv.setImageResource(icon);
        holder.date_tv.setText(date);
        holder.temperature_tv.setText(temp);

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView date_tv;
        private TextView temperature_tv;
        private ImageView weather_iv;
        private HourRequestRecallInterface wcallback;


        public MyViewHolder(final View itemView, HourRequestRecallInterface cb) {
            super(itemView);
            this.wcallback = cb;
            date_tv = itemView.findViewById(R.id.recycler_date_tv);
            temperature_tv = itemView.findViewById(R.id.recycler_temperature_tv);
            weather_iv = itemView.findViewById(R.id.recycler_weather_iv);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                wcallback.callback(position);
            });
        }
    }
}

interface HourRequestRecallInterface {
    void callback(int position);
}
