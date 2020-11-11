package com.example.tripill.Activity;

import android.app.Application;

import io.realm.Realm;

public class PillDBApplication extends Application {

//    TextView nonehistory;
//
//    RecyclerView drawer_recycler;
//
//    ArrayList<PillList> gpillList;
//    public ArrayList<PillList> getPillList(){
//        return gpillList;
//    }
//    public void setPillList(ArrayList<PillList> pillList;){
//        this.gpillList = pillList;
//    }

    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }
}
