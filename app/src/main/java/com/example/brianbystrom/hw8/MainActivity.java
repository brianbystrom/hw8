package com.example.brianbystrom.hw8;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements GetCityAsync.IData, GetCurrentWeatherAsync.IData, GetCitySearchAsync.IData{

    private DatabaseReference mDatabase;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Button setCityBTN;
    Button searchCityBtn;
    TextView currentCityTV, cityCountryTV, currentWeatherTV, temperatureTV, updatedTV;
    EditText cname;
    EditText ctryname;
    ImageView currentWeatherIV;
    ProgressBar currentCityPB;
    SharedPreferences sharedpreferences;
    long numOfElementsInFireBase;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                //Movie new_movie = (Movie) data.getExtras().getParcelable(MOVIE_KEY);
                //movies.add(new_movie);
                //Toast.makeText(MainActivity.this, "Added " + new_movie.name + ".", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(MainActivity.this, "Movie was not added.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 200) {
            if(resultCode == RESULT_OK) {
                //Movie edited_movie = (Movie) data.getExtras().getParcelable(MOVIE_KEY);
                //String original_name = (String) data.getExtras().getString(ORIGINAL_NAME_KEY);
                //updateMovie(movies, edited_movie, original_name);
                //Toast.makeText(MainActivity.this, "Edited " + edited_movie.name + ".", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(MainActivity.this, "Unable to edit movie.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get shared preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        setCityBTN = (Button) findViewById(R.id.setCityBTN);
        searchCityBtn = (Button) findViewById(R.id.searchCityBTN);
         mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = mDatabase;

        String cityString;
        cityString = sharedpreferences.getString("CURRENT CITY", "");
        cname = (EditText) findViewById(R.id.cityNameET);
        ctryname = (EditText) findViewById(R.id.countryNameET);

        searchCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cityURL = "http://dataservice.accuweather.com/locations/v1/"+ctryname.getText()+"/search?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q="+cname.getText();
                new GetCitySearchAsync(MainActivity.this).execute(cityURL);


            }
        });

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numOfElementsInFireBase = dataSnapshot.child("history").getChildrenCount();
                Log.d("chill",numOfElementsInFireBase+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        myRef.addValueEventListener(eventListener);

        //If shared preferences for set city exists load up details via async task
        //If not then jsut leave set city BTN available
        if(!cityString.equals("")) {
            String[] cityData = cityString.split("\\|");
            Log.d("CITYSTRINGSP", cityData[0] + ", " + cityData[1] + ", " + cityData[2]);

            String cityKey = cityData[0];
            String cityName = cityData[1];
            String countryName = cityData[2];

            showProgressBar();

            String weatherURL = "http://dataservice.accuweather.com/currentconditions/v1/"+cityKey+"?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q";
            Log.d("URL", weatherURL);
            new GetCurrentWeatherAsync(MainActivity.this).execute(weatherURL);

        }



        setCityBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText setCityET = new EditText(MainActivity.this);
                final EditText setCountryET = new EditText(MainActivity.this);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                String cityString;
                cityString = sharedpreferences.getString("CURRENT CITY", "");
                Log.d("CITYSTRINGSP", cityString);

                //If set city already exists fill out ET fields with existing data
                //This technically shouldn't be used since once a city is set, it will always default to showing
                //the data of the set city per instructions.
                if(!cityString.equals("")) {
                    String[] cityData = cityString.split("\\|");
                    Log.d("CITYSTRINGSP", cityData[0] + ", " + cityData[1] + ", " + cityData[2]);

                    String cityName = cityData[1];
                    String countryName = cityData[2];
                    setCityET.setText(cityName);
                    setCountryET.setText(countryName);
                }


                //Creates alert dialog for set city with 2 ET and adds to LinearLayout
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Enter city details:");

                AlertDialog dialog = builder.create();

                setCityET.setHint("Enter your city");
                setCountryET.setHint("Set your country");

                LinearLayout ll = new LinearLayout(MainActivity.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.removeAllViews();
                ll.addView(setCityET);
                ll.addView(setCountryET);
                builder.setView(ll);

                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //setCity.setValue(setCityET.getText().toString());
                        //setCountry.setValue(setCountryET.getText().toString());

                        String cityURL = "http://dataservice.accuweather.com/locations/v1/"+setCountryET.getText().toString()+"/search?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q="+setCityET.getText().toString();
                        new GetCityAsync(MainActivity.this).execute(cityURL);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


                dialog = builder.create();
                dialog.show();

            }
        });

    }

    public void setupDataS (final ArrayList<City> s) {

        if(s.size() > 0) {
            Intent i = new Intent(getApplicationContext(),CityWeather.class);
            i.putExtra("CTRYNAME",s.get(0).getCountry());
            i.putExtra("CNAME",s.get(0).getName());
            i.putExtra("KEY", s.get(0).getKey());
            startActivityForResult(i, 100);
        } else {
            Toast.makeText(this, "No city found.", Toast.LENGTH_SHORT).show();
        }

    }

    //After city data comes back, sets it to Shared preferences
    public void setupData(final ArrayList<City> s) {

        if(s.size() > 0) {

            City city = s.get(0);
            Log.d("CITY", s.get(0).getKey() + " | " + s.get(0).getName() + " | " + s.get(0).getCountry());
            Toast.makeText(this, "City set.", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedpreferences.edit();

            String cityString;
            cityString = city.getKey() + "|" + city.getName() + "|" + city.getCountry();
            Log.d("CITYSTRING", cityString);
            editor.putString("CURRENT CITY", cityString);
            editor.commit();

            //Shows progress bar and attempts to pull back data
            showProgressBar();
            String weatherURL = "http://dataservice.accuweather.com/currentconditions/v1/"+s.get(0).getKey()+"?apikey=3YYKlzAABBBldQ6AGOcj9jSin5WLAycH&q";
            Log.d("URL", weatherURL);
            new GetCurrentWeatherAsync(MainActivity.this).execute(weatherURL);


        } else {
            Log.d("CITY","NO CITY FOUND");
            Toast.makeText(this, "No city found.", Toast.LENGTH_SHORT).show();
        }
    }

    //After GetCurretnWeatherAsync runs it comes back and loads up weather it retrieved
    public void currentWeather(final ArrayList<Weather> s) {

        if(s.size() > 0) {
            Log.d("WEATHER", s.get(0).getTime() + " | " + s.get(0).getIcon() + " | " + s.get(0).getTempF());
            showCurrentWeather(s.get(0));

            //Write to firebase
            mDatabase.child("history").child(0+"").setValue(s.get(0));

        } else {
            Log.d("WEATHER","NO CITY FOUND");
            Toast.makeText(this, "No city found.", Toast.LENGTH_SHORT).show();
        }
    }

    //Function to show and hide elements of UI after current weather is received
    public void showCurrentWeather(Weather weather) {

        String cityString;
        cityString = sharedpreferences.getString("CURRENT CITY", "");

        String[] cityData = cityString.split("\\|");
        Log.d("CITYSTRINGSP", cityData[0] + ", " + cityData[1] + ", " + cityData[2]);

        String cityKey = cityData[0];
        String cityName = cityData[1];
        String countryName = cityData[2];

        currentCityTV = (TextView) findViewById(R.id.currentCityTV);
        setCityBTN = (Button) findViewById(R.id.setCityBTN);

        cityCountryTV = (TextView) findViewById(R.id.cityCountryTV);
        currentWeatherTV = (TextView) findViewById(R.id.currentWeatherTV);
        temperatureTV = (TextView) findViewById(R.id.temperatureTV);
        updatedTV = (TextView) findViewById(R.id.updatedTV);

        currentWeatherIV = (ImageView) findViewById(R.id.currentWeatherIV);

        cityCountryTV.setText(cityName + ", " + countryName);
        currentWeatherTV.setText(weather.getText());
        updatedTV.setText(weather.getTime());

        String icon;

        if(Integer.parseInt(weather.getIcon()) < 10) {
            icon = "0" + weather.getIcon();
        } else {
            icon = weather.getIcon() + "";
        }

        Picasso.with(MainActivity.this).load("http://developer.accuweather.com/sites/default/files/"+icon+"-s.png").into(currentWeatherIV);


        //TODO:  needs to be dynamic based on preference of metric user chooses in settings
        temperatureTV.setText("Temperature: " + weather.getTempF() + " F");

        hideProgressBar();
        currentCityTV.setVisibility(GONE);
        setCityBTN.setVisibility(GONE);

        cityCountryTV.setVisibility(View.VISIBLE);
        currentWeatherTV.setVisibility(View.VISIBLE);
        currentWeatherIV.setVisibility(View.VISIBLE);
        temperatureTV.setVisibility(View.VISIBLE);
        updatedTV.setVisibility(View.VISIBLE);



    }

    public void showProgressBar() {

        currentCityPB = (ProgressBar) findViewById(R.id.currentCityPB);
        currentCityPB.setVisibility(View.VISIBLE);

    }

    public void hideProgressBar() {

        currentCityPB = (ProgressBar) findViewById(R.id.currentCityPB);
        currentCityPB.setVisibility(GONE);

    }
}
