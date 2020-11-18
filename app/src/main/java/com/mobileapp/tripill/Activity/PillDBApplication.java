package com.mobileapp.tripill.Activity;

import android.app.Application;

import io.realm.Realm;

public class PillDBApplication extends Application {

    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }
}
