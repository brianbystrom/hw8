/*
Assignment #: In Class 04
File Name: GetNewsAsync.java
Group Members: Brian Bystrom, Mohamed Salad
*/

package com.example.brianbystrom.hw8;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by brianbystrom on 2/6/17.
 */

public class GetCitySearchAsync extends AsyncTask<String, Void, ArrayList<City>> {

    IData activity;

    public GetCitySearchAsync(IData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<City> doInBackground(String... params) {

        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null ) {
                sb.append(line);
            }

            return CityUtil.DataJSONParser.parseData(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<City> s) {
        super.onPostExecute(s);


        activity.setupDataS(s);


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    static public interface IData {
        public void setupDataS(ArrayList<City> s);
    }
}

