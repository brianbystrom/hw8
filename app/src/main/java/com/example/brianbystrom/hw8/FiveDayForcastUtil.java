package com.example.brianbystrom.hw8;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by brianbystrom on 2/6/17.
 */

//String title, author, published_date, description, urlToImage;


public class FiveDayForcastUtil {

    static public class DataJSONParser {
        static ArrayList<FiveDay> parseData(String in) {
            ArrayList<FiveDay> fiveDayForcastsList = new ArrayList();

            try {
                JSONObject root = new JSONObject(in);

                for (int i = 0; i<root.length(); i++) {

                    FiveDay fiveDay = new FiveDay();
                   JSONObject headline = root.getJSONObject("Headline");
                        fiveDay.setHeadline(headline.getString("Text"));
                        fiveDay.setDay("Forcast for "+headline.getString("EffectiveDate"));
                    Log.d("test",headline.toString());
                   JSONArray dailyforcasts = root.getJSONArray("DailyForecasts");
                    for (int x = 0; x < dailyforcasts.length(); x++){
                        JSONObject obj = dailyforcasts.getJSONObject(x);
                        fiveDay.setMin(obj.getJSONObject("Temperature").getJSONObject("Minimum").getDouble("Value")+"");
                        fiveDay.setMax(obj.getJSONObject("Temperature").getJSONObject("Maximum").getDouble("Value")+"");
                        fiveDay.setDayForcast(obj.getJSONObject("Day").getString("IconPhrase"));
                        fiveDay.setNightForcast(obj.getJSONObject("Night").getString("IconPhrase"));
                        fiveDay.setIconDay(obj.getJSONObject("Day").getInt("Icon"));
                        fiveDay.setIconNight(obj.getJSONObject("Night").getInt("Icon"));


                        Log.d("best",fiveDay.getIconDay()+"");
                    }









                    fiveDayForcastsList.add(fiveDay);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return fiveDayForcastsList;
        }
    }
}
