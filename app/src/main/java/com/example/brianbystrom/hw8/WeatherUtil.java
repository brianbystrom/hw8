package com.example.brianbystrom.hw8;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by brianbystrom on 2/6/17.
 */

//String title, author, published_date, description, urlToImage;


public class WeatherUtil {

    static public class DataJSONParser {

        //SharedPreferences prefs = this.getSharedPreferences(
               // "com.example.brianbystrom.hw8", Context.MODE_PRIVATE);
        static ArrayList<Weather> parseData(String in) {
            ArrayList<Weather> weatherList = new ArrayList();

            try {
                JSONArray root = new JSONArray(in);

                for (int i = 0; i<root.length(); i++) {

                    Weather weather = new Weather();


                    JSONObject dataJSONObject = root.getJSONObject(i);
                    weather.setTime(dataJSONObject.getString("EpochTime"));
                    weather.setText(dataJSONObject.getString("WeatherText"));
                    weather.setIcon(dataJSONObject.getString("WeatherIcon"));

                    JSONObject tempJSONObject = dataJSONObject.getJSONObject("Temperature");
                    JSONObject cJSONObject = tempJSONObject.getJSONObject("Metric");
                    JSONObject fJSONObject = tempJSONObject.getJSONObject("Imperial");

                    weather.setTempC(cJSONObject.getString("Value"));
                    weather.setTempF(fJSONObject.getString("Value"));

                    weather.setCity("Charlotte");
                    weather.setCountry("US");
                    weatherList.add(weather);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return weatherList;
        }
    }
}
