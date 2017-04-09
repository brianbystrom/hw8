package com.example.brianbystrom.hw8;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by msalad on 4/8/2017.
 */

public class FiveDay {
    String headline;
    String min;
    String max;
    String dayForcast;
    String nightForcast;
    String day;
    int iconDay;
    int iconNight;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIconDay() {
        return iconDay;
    }

    public void setIconDay(int iconDay) {
        this.iconDay = iconDay;
    }

    public int getIconNight() {
        return iconNight;
    }

    public void setIconNight(int iconNight) {
        this.iconNight = iconNight;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public FiveDay() {
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getDayForcast() {
        return dayForcast;
    }

    public void setDayForcast(String dayForcast) {
        this.dayForcast = dayForcast;
    }

    public String getNightForcast() {
        return nightForcast;
    }

    public void setNightForcast(String nightForcast) {
        this.nightForcast = nightForcast;
    }
}
