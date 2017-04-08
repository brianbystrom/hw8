package com.example.brianbystrom.hw8;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by msalad on 4/8/2017.
 */

public class CityWeather extends AppCompatActivity implements GetLocationAsync.IDataCity, FiveDayForcastAsync.IDataFiveDay{

    // http://dataservice.accuweather.com/locations/v1/
    //{COUNTRY_CODE}/search?apikey={YOUR_API_KEY}&q={CITY_NAME}
    TextView headLineTxt;
    TextView forcastTxt;
    TextView tvDayWeather;
    TextView tvNightWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        String cityKey = "349818";
        String weatherURL = "http://dataservice.accuweather.com/locations/v1/349818?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q";
        headLineTxt = (TextView) findViewById(R.id.tv_headline);
        forcastTxt = (TextView) findViewById(R.id.tv_temp);
        tvDayWeather = (TextView) findViewById(R.id.tv_day);
        tvNightWeather = (TextView) findViewById(R.id.tv_night);

        //Start Location Async
        new GetLocationAsync(CityWeather.this).execute(weatherURL);

    }

    public void setupDataB(final ArrayList<Location> s) {

        if (s.size() > 0) {

            Location l = s.get(0);
            Log.d("LALA",l.getKey()+"dddd");
            String weatherURL = " http://dataservice.accuweather.com/forecasts/v1/daily/5day/349818?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q";
            new FiveDayForcastAsync(CityWeather.this).execute(weatherURL);

        }
    }

    public void setupDataC(final ArrayList<FiveDay> s) {

        if (s.size() > 0) {

            FiveDay f = s.get(0);
            headLineTxt.setText(f.getHeadline());
            forcastTxt.setText("Temperature: "+f.getMax()+"/"+f.getMin());
            tvDayWeather.setText(f.getDayForcast());
            tvNightWeather.setText(f.getNightForcast());

            //Log.d("LALA",l.getKey()+"dddd");

        }
    }


}