package com.example.brianbystrom.hw8;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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

public class CityWeather extends AppCompatActivity implements GetLocationAsync.IDataCity, FiveDayForcastAsync.IDataFiveDay, GetCurrentWeatherAsync.IData{

    // http://dataservice.accuweather.com/locations/v1/
    //{COUNTRY_CODE}/search?apikey={YOUR_API_KEY}&q={CITY_NAME}
    private DatabaseReference mDatabase;
    DatabaseReference myRef;
    TextView headLineTxt;
    TextView forcastTxt;
    TextView tvDayWeather;
    TextView tvNightWeather;
    ImageView dayIV, nightIV, day1IV, day2IV, day3IV;
    TextView day1TV, day2TV, day3TV;
    TextView cityWeatherHeaderTV;
    String cityKey, cityName, countryName;
    String tempF, tempC;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("city");
        myRef = mDatabase;

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


        //







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
                String weatherURL = "http://dataservice.accuweather.com/currentconditions/v1/"+cityKey+"?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q";
                Log.d("URL", weatherURL);
                new GetCurrentWeatherAsync(CityWeather.this).execute(weatherURL);
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
            case R.id.settingsMI:
                Intent i = new Intent(getApplicationContext(),Preferences.class);
                //i.putExtra("CTRYNAME",s.get(0).getCountry());
                //i.putExtra("CNAME",s.get(0).getName());
                //i.putExtra("KEY", s.get(0).getKey());
                //i.putExtra("DBSIZE",sizeOfDB);
                //myRef.child((sizeOfDB)+"").setValue(s.get(0));
                startActivityForResult(i, 200);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setupDataB(final ArrayList<Location> s) {

        if (s.size() > 0) {
           // Log.d("Location",s.get(0).ge)
            Location l = s.get(0);
            Log.d("LALA",l.getKey()+"dddd");
            String weatherURL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+l.getKey()+"?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q";
            new FiveDayForcastAsync(CityWeather.this).execute(weatherURL);

        }
    }

    public void currentWeather(final ArrayList<Weather> s) {

        if(s.size() > 0) {
            Log.d("WEATHER", s.get(0).getTime() + " | " + s.get(0).getIcon() + " | " + s.get(0).getTempF());

            Log.d("REFRESH", "CLICKED");
            mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = mDatabase;
            String key = mDatabase.child("city").push().getKey();
            City c = new City();
            c.setKey(cityKey);
            c.setName(cityName);
            c.setCountry(countryName);
            c.setTempC(s.get(0).getTempC());
            c.setTempF(s.get(0).getTempF());
            c.setUpdated(System.currentTimeMillis());
            myRef.child("city").child(cityKey).setValue(c);

            //showCurrentWeather(s.get(0));

            //Write to firebase
            //mDatabase.child("history").child(0+"").setValue(s.get(0));

        } else {
            //Log.d("WEATHER","NO CITY FOUND");
            //Toast.makeText(this, "No city found.", Toast.LENGTH_SHORT).show();
        }
    }

    public void writeToFirebase(ArrayList<FiveDay> s){
        /*long sizeOfDB = getIntent().getExtras().getLong("DBSIZE");
        Weather w = new Weather();
        w.setCity(getIntent().getExtras().getString("CNAME"));
        w.setCountry(getIntent().getExtras().getString("CTRYNAME"));
        w.setFav(false);
        w.setTime(s.get(0).getDate());
        w.setTempF(s.get(0).getMax());
        w.setPositionInFirebase(sizeOfDB);
        myRef.child(sizeOfDB+"").setValue(w);*/
    }

    public void setupCurrentDay(ArrayList<FiveDay> s){
        FiveDay f = s.get(0);
        f.setCity(getIntent().getExtras().getString("CNAME"));
        f.setCountry(getIntent().getExtras().getString("CTRYNAME"));
        headLineTxt.setText(f.getHeadline());
        forcastTxt.setText("Temperature: "+f.getMax()+"/"+f.getMin());
        tvDayWeather.setText(f.getDayForcast());
        tvNightWeather.setText(f.getNightForcast());
        String dayIcon, nightIcon;
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
        Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+dayIcon+"-s.png").into(dayIV);
        Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+nightIcon+"-s.png").into(nightIV);


        Log.d("DEMO", f.getIconNight() + "");
    }

    public void setupDataC(final ArrayList<FiveDay> s) {

        String day1Icon = "";
        String day2Icon = "";
        String day3Icon = "";

        if (s.size() > 0) {
            //writeToFirebase(s);
            setupCurrentDay(s);

            Log.d("DATESET", s.get(0).getDate() + ", " + s.get(1).getDate());

            mRecyclerView = (RecyclerView) findViewById(R.id.fiveday_list);
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            //Log.d("date of the second val",s.get(8).getDate());
            mAdapter = new MyFiveDayAdapter(s,this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

//            FiveDay d1 = s.get(1);
//            FiveDay d2 = s.get(2);
//            FiveDay d3 = s.get(3);
//
//            day1TV.setText(d1.getDate().toString());
//            day2TV.setText(d2.getDate().toString());
//            day3TV.setText(d3.getDate().toString());
//            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+day1Icon+"-s.png").into(day1IV);
//            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+day2Icon+"-s.png").into(day2IV);
//            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+day3Icon+"-s.png").into(day3IV);


        }
    }

    @Override
    public void onBackPressed() {
        Log.d("RETURNED","BACK");
        setResult(RESULT_OK);
        finish();
    }


}