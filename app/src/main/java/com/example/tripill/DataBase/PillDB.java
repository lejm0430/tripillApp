package com.example.tripill.DataBase;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PillDB extends RealmObject {
        @PrimaryKey
        private String key;

        private String Pillname;
        private String Symptom1;
        private String Symptom2;
        private int age;

    public String getKey(){
        return key;
    }
    public void setKey(String key){
        this.key = key;
    }

        public String getName(){
            return Pillname;
        }
        public void setName(String Pillname){
            this.Pillname = Pillname;
        }


    public String getS1(){
        return Symptom1;
    }
    public void setS1(String Symptom1){
        this.Symptom1 = Symptom1;
    }


    public String getS2(){
        return Symptom2;
    }
    public void setS2(String Symptom2){
        this.Symptom2 = Symptom2;
    }


    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }


}
