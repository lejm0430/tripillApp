package com.example.tripill.Activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PillDBApplication extends Application {
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }
}
