package com.example.tripill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SosMessage {
    Activity activity;
    String address, age, s1kr, s2kr;
    String smsBody;

    public SosMessage(@NonNull Activity context){
        activity = context;
    }
    public void init(String address, String age, String s1kr, String s2kr) {
        this.address = address;
        this.age = age;
        this.s1kr = s1kr;
        this.s2kr = s2kr;
    }

    public void intent(){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);

        if(s2kr != null){
            smsBody = "저는 외국인입니다." + "저의 위치는 " + address + "이고, 저의 나이는 " + age +"세 입니다. 저의 증상은 "+ s1kr+ "," + s2kr +"입니다. 살려줘";
        }else{
            smsBody = "저는 외국인입니다." + "저의 위치는 " + address + "이고, 저의 나이는 " + age +"세 입니다. 저의 증상은 "+ s1kr+"입니다. 살려줘";

        }
        sendIntent.putExtra("sms_body", smsBody); // 보낼 문자

        sendIntent.putExtra("address", "01011112222"); // 받는사람 번호

        sendIntent.setType("vnd.android-dir/mms-sms");

        sendIntent.setType("vnd.android-dir/mms-sms");

        activity.startActivity(sendIntent);
    }
}
