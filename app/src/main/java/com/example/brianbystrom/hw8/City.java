/*
Assignment #: In Class 04
File Name: Data.java
Group Members: Brian Bystrom, Mohamed Salad
*/

package com.example.brianbystrom.hw8;

/**
 * Created by brianbystrom on 2/6/17.
 */

public class City {

    String key, name, country, tempF, tempC;
    Long updated;



    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
