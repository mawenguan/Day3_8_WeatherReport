package com.example.mwg.day3_8_weatherreport;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mwg.day3_8_weatherreport.adapter.WeatherAdapter;
import com.example.mwg.day3_8_weatherreport.entity.MyWeather;
import com.example.mwg.day3_8_weatherreport.manager.HttpWeatherManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.drawer_Layout)
    DrawerLayout drawerLayout_Weather;
    @BindView(R.id.navigation_Weather)
    NavigationView navigationView_Weather;

    @BindView(R.id.imageView_Menu)
    ImageView imageView_Menu;
    @BindView(R.id.textView_CityName)
    TextView textView_CityName;
    @BindView(R.id.imageView_Statics)
    ImageView imageView_Static;
    @BindView(R.id.recyclerView_Weather)
    RecyclerView recyclerView_Weather;
    @BindView(R.id.refresh_Layout)
    SwipeRefreshLayout refreshLayout;

    WeatherAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialProperties();
        setListener();
    }

    private void setListener() {
        navigationView_Weather.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this,"HaHa ",
                        Toast.LENGTH_LONG).show();
                String cityName = item.getTitle().toString();
                textView_CityName.setText(cityName);
                refreshWeathers(cityName,true);
                drawerLayout_Weather.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    private void initialProperties() {
        //实例化数据适配器
        adapter = new WeatherAdapter(this);

        imageView_Menu.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        imageView_Static.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);

        //设置导航栏的图标颜色
        navigationView_Weather.setItemIconTintList(null);
        //定义一个布局管理器
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //把布局管理器设置给RecyclerView
        recyclerView_Weather.setLayoutManager(linearLayoutManager);
        //设置适配器
        recyclerView_Weather.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获得当前城市的名字
        String cityName = textView_CityName.getText().toString();
        refreshWeathers(cityName,true);
    }

    /**
     * 加载数据并实现数据的更新
     * @param cityName
     * @param isClear
     */
    public void refreshWeathers(String cityName, final boolean isClear) {
        //加载天气信息
        HttpWeatherManager.loadWeather(this,cityName,
                new HttpWeatherManager.WeatherLoadListener() {
            @Override
            public void onWeatherLoadEnd(MyWeather myWeather) {
                //最近7天的天气的数据
                List<MyWeather.Result.Data.WeatherX> weathers =
                        myWeather.getResult().getData().getWeather();
                //把数据添加到适配器集合中
                adapter.addWeathers(weathers,isClear);
            }
        });

    }

    @OnClick(R.id.imageView_Menu)
    public void clickImageMenu() {
        //如果侧滑菜单是关闭则打开
        //如果侧滑菜单是打开的则关闭
        if (!drawerLayout_Weather.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout_Weather.openDrawer(Gravity.LEFT);
        } else {
            drawerLayout_Weather.closeDrawer(Gravity.LEFT);
        }
    }
}
