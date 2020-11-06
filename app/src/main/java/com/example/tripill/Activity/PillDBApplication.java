package com.example.tripill.Activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripill.Adapter.PillList;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
