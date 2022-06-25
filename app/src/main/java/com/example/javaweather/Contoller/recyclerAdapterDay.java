package com.example.javaweather.Contoller;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaweather.Model.Weather;
import com.example.javaweather.R;

import java.util.ArrayList;

import javax.annotation.Resource;

public class recyclerAdapterDay extends RecyclerView.Adapter<recyclerAdapterDay.MyViewHolder>{
    private ArrayList<Weather> weatherList;
    private ArrayList<ArrayList<Weather>> hoursForcast = new ArrayList<>();

    public recyclerAdapterDay(ArrayList<Weather> weatherList, ArrayList<ArrayList<Weather>> hoursForcast){
        this.hoursForcast = hoursForcast;
        this.weatherList = weatherList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView dateTxt;
        private TextView tempTxt;
        private ImageView weatherIcon;


        public MyViewHolder(final View itemView){
            super(itemView);
            dateTxt = itemView.findViewById(R.id.Coldate);
            tempTxt = itemView.findViewById(R.id.ColDaytemp);
            weatherIcon = itemView.findViewById(R.id.ColDayimg);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent();
                    intent.setClass(itemView.getContext(),HoursPageActivity.class);
                    intent.putExtra("HourData",hoursForcast.get(position+1));
                    itemView.getContext().startActivity(intent);
                }
            });
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
        Resources res = holder.itemView.getContext().getResources();
        String date = weatherList.get(position).getDate();
        String temp = weatherList.get(position).getTemp();
        String imageName = weatherList.get(position).getIcon();
        int resID = res.getIdentifier(imageName,"drawable","com.example.javaweather");
        holder.weatherIcon.setImageResource(resID);
        holder.dateTxt.setText(date);
        holder.tempTxt.setText(temp);

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
