package com.example.tripill.DataBase;

import com.example.tripill.DataBase.PillDB;

import java.util.ArrayList;

public class PillList {

    String symptom1;
    String symptom2;
    String age;
    String date;
    String pillname;
    String symptom1kr;
    String symptom2kr;

    public PillList(String symptom1, String symptom2, String age, String date, String pillname, String symptom1kr, String symptom2kr) {
        this.symptom1=symptom1;
        this.symptom2=symptom2;
        this.age=age;
        this.date=date;
        this.pillname=pillname;
        this.symptom1kr = symptom1kr;
        this.symptom2kr = symptom2kr;
    }

    public PillList(PillDB pillDB) {
        this.symptom1=pillDB.getS1();
        this.symptom2=pillDB.getS2();
        this.age=pillDB.getAge();
        this.date=pillDB.getDate();
        this.pillname=pillDB.getName();
        this.symptom1kr=pillDB.getS1kr();
        this.symptom2kr=pillDB.getS2kr();
    }

    public String getSymptom1() {
        return symptom1;
    }

    public void setSymptom1(String symptom1) {
        this.symptom1=symptom1;
    }

    public String getSymptom2() {
        return symptom2;
    }

    public void setSymptom2(String symptom2) {
        this.symptom2=symptom2;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age=age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public String getPillname() {
        return pillname;
    }

    public void setPillname(String pillname) {
        this.pillname=pillname;
    }


    public String getSymptom1kr() {
        return symptom1kr;
    }

    public void setSymptom1kr(String symptom1kr) {
        this.symptom1kr=symptom1kr;
    }

    public String getSymptom2kr() {
        return symptom2kr;
    }

    public void setSymptom2kr(String symptom2kr) {
        this.symptom2kr=symptom2kr;
    }


}
