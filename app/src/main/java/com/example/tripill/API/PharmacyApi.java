/*
package com.example.tripill.API;

import android.content.Context;

import com.example.tripill.Activity.PharmacyMap;
import com.example.tripill.DataBase.pharmacyList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class PharmacyApi {

    Context context;
    StringBuilder responseBuilder = new StringBuilder();

    ArrayList<pharmacyList> list = new ArrayList<>();

    PharmacyApi(Context context, double latitude, double longtitude, String name){
        this.context = context;

        try{
            String StrUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyDKS1jSRPdpY014AguQ5ORacQQLc4e3nyo&location=" + latitude +","+ longtitude +"&radius=1000&type=pharmacy";
            URL url = new URL(StrUrl);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                responseBuilder.append(line);
            }

            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void parsing(){
        try{

            JSONArray jsonArray;
            JSONObject jsonObject;

            jsonObject =new JSONObject(responseBuilder.toString());
            jsonArray = jsonObject.getJSONArray("results");


            for(int i =0; i<jsonArray.length();i++) {
                JSONObject result=jsonArray.getJSONObject(i);


                //위도 경도
                JSONObject geometry = result.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                String lat = location.getString("lat");
                String lng = location.getString("lng");

                // 이름
                String name=result.getString("name");

                list.add(new pharmacyList(name,Double.valueOf(lat),Double.valueOf(lng))); //list에 파싱한 값들을 넣음
            } //for
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<pharmacyList> getList(){
        return list;
    }

}

*/
