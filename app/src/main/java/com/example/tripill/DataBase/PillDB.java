package com.example.tripill.DataBase;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmObject;

public class PillDB extends Application {
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }

    
}
