package com.example.tripill.DataBase;

public class pharmacyList {

    String name;
    /*double lat;
    double lng;*/

    public pharmacyList(String name/*, double lat, double lng*/) {
        this.name=name;
        /*this.lat=lat;
        this.lng=lng;*/
    }


    public String getname() {
        return name;
    }

    public void setname(String name) { this.name=name; }

/*    public double getlat() {
        return lat;
    }

    public void setlat(double lat) {
        this.lat=lat;
    }

    public double getlng() {
        return lng;
    }

    public void setlng(double lng) {
        this.lng=lng;
    }*/

}

