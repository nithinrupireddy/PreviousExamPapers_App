package com.nithin.finalproject.Model;

import java.io.Serializable;

/**
 * Created by nithin on 3/25/2018.
 */

public class Image implements Serializable {

    public  String name;
    public String large,medium;

    public Image()
    {

    }

    public String getMedium() {
        return medium;
    }



    public Image(String name, String large) {
        this.name = name;
        this.large = large;
    }

    public String getName() {
        return name;
    }



    public String getLarge() {
        return large;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
