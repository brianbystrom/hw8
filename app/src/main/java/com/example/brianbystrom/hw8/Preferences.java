package com.example.brianbystrom.hw8;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by msalad on 4/8/2017.
 */

public class Preferences extends AppCompatActivity implements GetCityAsync.IData{

    final EditText setCityET = new EditText(Preferences.this);
    final EditText setCountryET = new EditText(Preferences.this);
    final RadioButton celc = new RadioButton(Preferences.this);
    final RadioButton far = new RadioButton(Preferences.this);
    Button currentCity;
    Button tempUnit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCity = (Button) findViewById(R.id.btn_current_city) ;
        tempUnit = (Button) findViewById(R.id.btn_tempunit);
        setContentView(R.layout.activity_preferences);
        tempUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Preferences.this);
                builder.setTitle("Enter city details:");
                AlertDialog dialog = builder.create();
                LinearLayout ll = new LinearLayout(Preferences.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.removeAllViews();
                ll.addView(celc);
                ll.addView(far);
                builder.setView(ll);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(celc.isChecked()){
                            Log.d("check","Celcius is checked");
                        }
                        else if(far.isChecked()){
                            Log.d("check","Far is checked");
                        }

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


        currentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Preferences.this);
        builder.setTitle("Enter city details:");
        AlertDialog dialog = builder.create();
        LinearLayout ll = new LinearLayout(Preferences.this);
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
                new GetCityAsync(Preferences.this).execute(cityURL);

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

    @Override
    public void setupData(ArrayList<City> s) {
        if(s.size() > 0){

        }else{
            Toast.makeText(getApplicationContext(),"City not found",Toast.LENGTH_LONG).show();
        }
    }

}
