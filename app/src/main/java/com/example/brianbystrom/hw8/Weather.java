/*
Assignment #: In Class 04
File Name: Data.java
Group Members: Brian Bystrom, Mohamed Salad
*/

package com.example.brianbystrom.hw8;

/**
 * Created by brianbystrom on 2/6/17.
 */

public class Weather {

    String time, text, icon, tempC, tempF, citycountry;

    public String getCitycountry() {
        return citycountry;
    }

    public void setCitycountry(String citycountry) {
        this.citycountry = citycountry;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public Weather() {

    }
}
