package com.example.mwg.day3_8_weatherreport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mwg.day3_8_weatherreport.R;
import com.example.mwg.day3_8_weatherreport.entity.MyWeather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwg on 2018/1/3.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    public Context context;
    public WeatherAdapter(Context context) {
        this.context=context;
    }

    private List<MyWeather.Result.Data.WeatherX> weathers=
            new ArrayList<MyWeather.Result.Data.WeatherX>();

    //实现对天气数据集合的访问
    public void addWeathers(List<MyWeather.Result.Data.WeatherX> weatherXList,
            boolean isClear){
        if(isClear){
            //先清空，再添加数据
            this.weathers.clear();
            this.weathers.addAll(weatherXList);
        }else{
            this.weathers.addAll(weatherXList);
        }
        notifyDataSetChanged();
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        ViewHolder holder=null;
        View itemView= LayoutInflater.from(context).inflate(
                R.layout.weather_item_layout,parent,false);

        holder=new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        //获得要适配的数据对象
        MyWeather.Result.Data.WeatherX weatherX = weathers.get(position);
        //天气的状态的编号
        /**
         * 0是晴天
         * 1是多云
         * 2是阴
         * 3是雨
         */
        String contionNo=weatherX.getInfo().getDay().get(0);
        if("0".equals(contionNo)){
            holder.imageView_Condition.setImageResource(R.drawable.sunny);
        }else if("1".equals(contionNo)){
            holder.imageView_Condition.setImageResource(R.drawable.cloudy);
        }else if("2".equals(contionNo)){
            holder.imageView_Condition.setImageResource(R.drawable.lotcloudy);
        }else{
            holder.imageView_Condition.setImageResource(R.drawable.rain);
        }
        //设置天气
        String condition=weatherX.getInfo().getDay().get(1);
        holder.textView_Condition.setText(condition);
        //设置温度
        String dayTemp=weatherX.getInfo().getDay().get(2);
        String nightTemp=weatherX.getInfo().getNight().get(2);
        String temp=dayTemp+"℃/"+nightTemp+"℃";
        holder.textView_Temp.setText(temp);
        //设置日期
        String weekDay=weatherX.getWeek();
        holder.textView_WeekDay.setText(weekDay);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    //自定一个ViewHolder类实现控件的封装
    public  static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView_Condition;
        TextView textView_Condition;
        TextView textView_Temp;
        TextView textView_WeekDay;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_Condition=  itemView.findViewById(R.id.imageView_Item_Weather);
            textView_Condition=  itemView.findViewById(R.id.textView_Item_Condition);
            textView_Temp= itemView.findViewById(R.id.textView_Item_Temp);
            textView_WeekDay=  itemView.findViewById(R.id.textView_Item_WeekDay);
        }
    }
}
