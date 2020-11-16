package com.example.tripill.Activity;

import android.app.Application;
import android.content.Context;

import com.example.tripill.DataBase.PillDB;

import io.realm.Realm;

public class PillDBApplication extends Application {

    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }
}
