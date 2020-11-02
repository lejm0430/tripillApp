package com.example.tripill.DataBase;

import io.realm.Realm;
import io.realm.RealmObject;

public class PillDB extends RealmObject {
    public String sym1;
    public String sym2;
    public int age;
    public String date;
    public String pillname;

    public String getPillname(){
        return pillname;
    }
    public void setPillname(String sym1){
        this.pillname = pillname;
    }
}
