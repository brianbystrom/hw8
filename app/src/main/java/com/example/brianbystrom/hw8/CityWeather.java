package com.example.brianbystrom.hw8;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by msalad on 4/8/2017.
 */

public class CityWeather extends AppCompatActivity implements GetLocationAsync.IDataCity, FiveDayForcastAsync.IDataFiveDay{

    // http://dataservice.accuweather.com/locations/v1/
    //{COUNTRY_CODE}/search?apikey={YOUR_API_KEY}&q={CITY_NAME}
    private DatabaseReference mDatabase;

    TextView headLineTxt;
    TextView forcastTxt;
    TextView tvDayWeather;
    TextView tvNightWeather;
    ImageView dayIV, nightIV, day1IV, day2IV, day3IV;
    TextView day1TV, day2TV, day3TV;
    TextView cityWeatherHeaderTV;
    String cityKey, cityName, countryName;

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        cityWeatherHeaderTV = (TextView) findViewById(R.id.cityWeatherHeaderTV);

        headLineTxt = (TextView) findViewById(R.id.tv_headline);
        forcastTxt = (TextView) findViewById(R.id.tv_temp);
        tvDayWeather = (TextView) findViewById(R.id.tv_day);
        tvNightWeather = (TextView) findViewById(R.id.tv_night);
        dayIV = (ImageView) findViewById(R.id.dayIV);
        nightIV = (ImageView) findViewById(R.id.nightIV);

        day1IV = (ImageView) findViewById(R.id.day1IV);
        day2IV = (ImageView) findViewById(R.id.day2IV);
        day3IV = (ImageView) findViewById(R.id.day3IV);

        day1TV = (TextView) findViewById(R.id.day1TV);
        day2TV = (TextView) findViewById(R.id.day2TV);
        day3TV = (TextView) findViewById(R.id.day3TV);

        if(getIntent().getExtras() != null) {
            cityKey = (String) getIntent().getExtras().getString("KEY");
            cityName = (String) getIntent().getExtras().getString("CNAME");
            countryName = (String) getIntent().getExtras().getString("CTRYNAME");

            cityWeatherHeaderTV.setText("Daily forecast for " + cityName + ", " + countryName);
            String weatherURL = "http://dataservice.accuweather.com/locations/v1/"+cityKey+"?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q";
//Start Location Async
            new GetLocationAsync(CityWeather.this).execute(weatherURL);
        }







    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.forecast_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.saveMI:
                Log.d("REFRESH", "CLICKED");
                mDatabase = FirebaseDatabase.getInstance().getReference();
                DatabaseReference myRef = mDatabase;
                City c = new City();
                c.setKey(cityKey);
                c.setName(cityName);
                c.setCountry(countryName);
                myRef.child("history").child(c.getKey()).setValue(c);
                return true;
            case R.id.setCurrentMI:
                sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();


                String cityString;
                cityString = cityKey + "|" + cityName + "|" + countryName;
                Log.d("CITYSTRING", cityString);
                editor.putString("CURRENT CITY", cityString);
                editor.commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setupDataB(final ArrayList<Location> s) {

        if (s.size() > 0) {

            Location l = s.get(0);
            Log.d("LALA",l.getKey()+"dddd");
            String weatherURL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/349818?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q";
            new FiveDayForcastAsync(CityWeather.this).execute(weatherURL);

        }
    }

    public void setupDataC(final ArrayList<FiveDay> s) {

        if (s.size() > 0) {

            Log.d("SIZE", s.size() + "");

            FiveDay f = s.get(0);
            FiveDay d1 = s.get(1);
            FiveDay d2 = s.get(2);
            FiveDay d3 = s.get(3);
            headLineTxt.setText(f.getHeadline());
            forcastTxt.setText("Temperature: "+f.getMax()+"/"+f.getMin());
            tvDayWeather.setText(f.getDayForcast());
            tvNightWeather.setText(f.getNightForcast());
            day1TV.setText(d1.getDate().toString());
            day2TV.setText(d2.getDate().toString());
            day3TV.setText(d3.getDate().toString());


            String dayIcon, nightIcon, day1Icon, day2Icon, day3Icon;

            if(f.getIconDay() < 10) {
                dayIcon = "0" + f.getIconDay();
            } else {
                dayIcon = f.getIconDay() + "";
            }

            if(f.getIconNight() < 10) {
                nightIcon = "0" + f.getIconNight();
            } else {
                nightIcon = f.getIconNight() + "";
            }

            if(d1.getIconDay() < 10) {
                day1Icon = "0" + d1.getIconDay();
            } else {
                day1Icon = d1.getIconDay() + "";
            }

            if(d2.getIconDay() < 10) {
                day2Icon = "0" + d2.getIconDay();
            } else {
                day2Icon = d2.getIconDay() + "";
            }

            if(d3.getIconDay() < 10) {
                day3Icon = "0" + d3.getIconDay();
            } else {
                day3Icon = d3.getIconDay() + "";
            }





            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+dayIcon+"-s.png").into(dayIV);
            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+nightIcon+"-s.png").into(nightIV);
            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+day1Icon+"-s.png").into(day1IV);
            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+day2Icon+"-s.png").into(day2IV);
            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+day3Icon+"-s.png").into(day3IV);


            Log.d("DEMO", f.getIconNight() + "");


            //Log.d("LALA",l.getKey()+"dddd");

        }
    }


}