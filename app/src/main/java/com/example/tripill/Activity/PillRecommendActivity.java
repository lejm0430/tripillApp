package com.example.tripill.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Adapter.PillHistoryAdapter;
import com.example.tripill.DataBase.PillList;
import com.example.tripill.Adapter.SymptomRecommendAdpater;
import com.example.tripill.DataBase.PillDB;
import com.example.tripill.Dialog.FullImagDialog;
import com.example.tripill.Dialog.BaseDialog;
import com.example.tripill.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import net.cachapa.expandablelayout.ExpandableLayout;

import androidx.annotation.NonNull;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class PillRecommendActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    ImageView pillphoto;
    RelativeLayout viewArea;
    RelativeLayout warningArea;
    RelativeLayout expectArea;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    SymptomRecommendAdpater adapter;
    ExpandableLayout expectexp;
    ExpandableLayout warningexp;
    RelativeLayout speaker;

    ImageView arrowIc;
    ImageView arrowIcWarning;
    ImageView backbtn;
    ImageView pullimg;

    TextView expect;
    TextView warning;
    TextView expecttext;
    TextView precautionstext;
    TextView sos;
    TextView goPHbtn;
    TextView sym;
    String ageS;
    int age;
    String s1;
    String s2;
    String sumS;
    int sum;
    String pillname;
    String name;
    String s1kr;
    String s2kr;

    long now;
    long nows;
    long nowaisa;
    Date date;
    Date dates;
    Date dateasia;
    SimpleDateFormat format = new SimpleDateFormat("MM. dd. yyyy");
    SimpleDateFormat formatasia = new SimpleDateFormat("yyyy. MM. dd");
    SimpleDateFormat formats = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    private Realm realm;

    private TextToSpeech tts;

//    private GpsTracker gpsTracker;

    LatLng currentPosition;

    Location location;

    private static final int GPS_ENABLE_REQUEST_CODE = 300;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소


    public static Context prcontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_recommend);

        pillphoto = findViewById(R.id.pillphoto);
        viewArea = findViewById(R.id.viewArea);
        warningArea = findViewById(R.id.warningArea);
        expectArea = findViewById(R.id.expectArea);
        expectexp = findViewById(R.id.expectexp);
        warningexp = findViewById(R.id.warningexp);
        arrowIc = findViewById(R.id.arrowIc);
        arrowIcWarning = findViewById(R.id.arrowIcWarning);
        expect = findViewById(R.id.expect);
        warning = findViewById(R.id.warning);
        expecttext = findViewById(R.id.expecttext);
        precautionstext = findViewById(R.id.precautionstext);
        backbtn = findViewById(R.id.backbtn);
        sos = findViewById(R.id.sos);
        pullimg = findViewById(R.id.pullimg);
        goPHbtn = findViewById(R.id.goPHbtn);
        sym = findViewById(R.id.symptom);
        speaker = findViewById(R.id.speaker);

        prcontext = this;

        pillphoto.setClipToOutline(true);

        tts = new TextToSpeech(this, this);

        ageS = getIntent().getStringExtra("age");
        s1 = getIntent().getStringExtra("s1");
        s2 = getIntent().getStringExtra("s2");
        sumS = getIntent().getStringExtra("sum");
        name  = getIntent().getStringExtra("name");
        s1kr = getIntent().getStringExtra("s1kr");
        s2kr = getIntent().getStringExtra("s2kr");

        age = Integer.parseInt(ageS);

        if(sumS != null){
            sum = Integer.parseInt(sumS);
        }

        if(sum>=1 && sum <=3 || name.equals(getString(R.string.penzal))) {
            pillphoto.setImageResource(R.drawable.penzal);
            pillname = getString(R.string.penzal);

            expecttext.setText(getString(R.string.penzal_expect));
            precautionstext.setText(R.string.penzal_precautions);
        }else if(sum>=4 && sum<=6 || name.equals(getString(R.string.tylenol))){
            pillphoto.setImageResource(R.drawable.tylenol);
            pillname = "Tylenol";
            expecttext.setText(R.string.tylenol_expect);
            precautionstext.setText(R.string.tylenol_precautions);
        }else if(sum ==7 && age>=12 || name.equals(getString(R.string.strepsil))){
            pillphoto.setImageResource(R.drawable.strepsil);
            pillname = "Strepsil";
            expecttext.setText(R.string.strepsil_expect);
            precautionstext.setText(R.string.strepsil_precautions);
        }
        else if(sum == 7 && age < 12 || name.equals(getString(R.string.minol))){
            pillphoto.setImageResource(R.drawable.minol);
            pillname = "Minol-F Troche";
            expecttext.setText(R.string.minol_expect);
            precautionstext.setText(R.string.minol_precautions);
        }else if(sum ==8 && age >= 15 || sum == 15 && age >= 15 || name.equals(getString(R.string.mucopect_Tab))) {
            pillphoto.setImageResource(R.drawable.mucoj);
            pillname = "Mucopect Tab";
            expecttext.setText(R.string.mucopect_Tab_expect);
            precautionstext.setText(R.string.mucopect_Tab_precautions);
        }else if(sum ==8 && age < 15 || sum == 15 && age < 15 || name.equals(getString(R.string.mucopect_Syrup))){
            pillphoto.setImageResource(R.drawable.mucos);
            pillname = "Mucopect Syrup";
            expecttext.setText(R.string.mucopect_Syrup_expect);
            precautionstext.setText(R.string.mucopect_Syrup_precautions);
        }else if(sum == 20 || name.equals(getString(R.string.lirexpen))){
            pillphoto.setImageResource(R.drawable.lirexpen);
            pillname = "Lirexpen Tab";
            expecttext.setText(R.string.lirexpen_expect);
            precautionstext.setText(R.string.lirexpen_precautions);
        }else if(sum == 25 || name.equals(getString(R.string.fucidin))){
            pillphoto.setImageResource(R.drawable.whosidin);
            pillname = "Fucidin Ointment";
            expecttext.setText(R.string.fucidin_expect);
            precautionstext.setText(R.string.fucidin_precautions);
        }else if(sum == 35 ||  name.equals(getString(R.string.ru))){
            pillphoto.setImageResource(R.drawable.ru);
            pillname = "RU-21";
            expecttext.setText(R.string.ru_expect);
            precautionstext.setText(R.string.ru_precautions);
        }else if(sum == 40 || sum == 90 || name.equals(getString(R.string.gas))){
            pillphoto.setImageResource(R.drawable.sohwa);
            pillname = "Gas Whal Myung Su";
            expecttext.setText(R.string.gas_expect);
            precautionstext.setText(R.string.gas_precautions);
        }else if(sum == 50 || name.equals(getString(R.string.buscopan))){
            pillphoto.setImageResource(R.drawable.buscopan);
            pillname = "Buscopan Plus Tab";
            expecttext.setText(R.string.buscopan_expect);
            precautionstext.setText(R.string.buscopan_precautions);
        }else if(sum == 60 || name.equals(getString(R.string.mebo))){
            pillphoto.setImageResource(R.drawable.mibo);
            pillname = "Mebo Ointment";
            expecttext.setText(R.string.mebo_expect);
            precautionstext.setText(R.string.mebo_precautions);
        }else if(sum >= 100 || name.equals(getString(R.string.ezn))){
            pillphoto.setImageResource(R.drawable.easyn);
            pillname = "EZN 6 Eve";
            expecttext.setText(R.string.ezn_expect);
            precautionstext.setText(R.string.ezn_precautions);
        }

        name = null;

        if(s2kr == null || s2kr.isEmpty()){
            sym.setText(s1kr);
        }else {
            sym.setText(s1kr + "/" + s2kr);
        }

        pullimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullImagDialog dialog = new FullImagDialog(PillRecommendActivity.this);
                dialog.init(pillname);
                dialog.show();
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
                BaseDialog dialog = new BaseDialog(PillRecommendActivity.this);
                String canclecontents,confirm;
                canclecontents = getString(R.string.sos);
                confirm = getString(R.string.send);
                dialog.init(null,canclecontents,confirm);
                dialog.show();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        expectexp.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                if(state == 2){
                    if (expect.getCurrentTextColor() != getColor(R.color.cobalt)) {
                        expect.setTextColor(getColor(R.color.cobalt));
                        Typeface face = ResourcesCompat.getFont(prcontext, R.font.bold);
                        expect.setTypeface(face);
                    }
                }else if(state == 1){
                    if (expect.getCurrentTextColor() != getColor(R.color.black)) {
                        expect.setTextColor(getColor(R.color.black));
                        Typeface face = ResourcesCompat.getFont(prcontext, R.font.medium);
                        expect.setTypeface(face);
                    }
                }
            }
        });

        warningexp.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                if(state == 2){
                    if (warning.getCurrentTextColor() != getColor(R.color.cobalt)) {
                        warning.setTextColor(getColor(R.color.cobalt));
                        Typeface face = ResourcesCompat.getFont(prcontext, R.font.bold);
                        warning.setTypeface(face);
                    }
                }else if(state == 1){
                    if (warning.getCurrentTextColor() != getColor(R.color.black)) {
                        warning.setTextColor(getColor(R.color.black));
                        Typeface face = ResourcesCompat.getFont(prcontext, R.font.medium);
                        warning.setTypeface(face);
                    }
                }
            }
        });

        viewArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expectexp.toggle();
                arrowIc.setImageResource(expectexp.isExpanded() ? R.drawable.ic_close_arrow_light_gray : R.drawable.ic_open_arrow_light_gray);
            }
        });

        warningArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warningexp.toggle();
                arrowIcWarning.setImageResource(warningexp.isExpanded() ? R.drawable.ic_close_arrow_light_gray : R.drawable.ic_open_arrow_light_gray);
            }
        });

        goPHbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PillRecommendActivity.this,PharmacyMap.class);
                startActivity(intent);
            }
        });

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speech();
            }
        });



        recyclerView = findViewById(R.id.recycle);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        if(s2 == null || s2.isEmpty()){
            String[] main_text =  {s1};


            adapter = new SymptomRecommendAdpater(main_text);

            recyclerView.setAdapter(adapter);
        }else{
            String[] main_text =  {s1,s2};


            adapter = new SymptomRecommendAdpater(main_text);

            recyclerView.setAdapter(adapter);
        }
        ///////list

        ((MainActivity)MainActivity.context).pillList = new ArrayList<PillList>();

        realm = Realm.getDefaultInstance();
        basicCRUD(realm);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        ((MainActivity)MainActivity.context).drawer_recycler.setLayoutManager(linearLayoutManager);

        ((MainActivity)MainActivity.context).historyadapter = new PillHistoryAdapter(((MainActivity)MainActivity.context).pillList,this);
        ((MainActivity)MainActivity.context).drawer_recycler.setAdapter(((MainActivity)MainActivity.context).historyadapter);

    }

    public void check(){
        int hasFineLocationPermission=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) { //퍼미션 허용 O

        } else {  //퍼미션 허용 X

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                ActivityCompat.requestPermissions(PillRecommendActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);

            } else {
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }


        }
    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }

            } //for
        }

    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (!checkLocationServicesStatus()) {
                    Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
                    if (checkLocationServicesStatus()) {

                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {   //gps 네트워크 여부
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }





    public String getCurrentAddress( double latitude, double longitude) {

        //GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, R.string.no_gocode, Toast.LENGTH_LONG).show();
            return String.valueOf(R.string.no_gocode);
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, R.string.Invalid_GPS, Toast.LENGTH_LONG).show();
            return String.valueOf(R.string.Invalid_GPS);

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, R.string.cant_find_address, Toast.LENGTH_LONG).show();
            return String.valueOf(R.string.cant_find_address);

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

                location = location;




            }


        }

    };

    public void messege() {

        String address = getCurrentAddress(location.getLatitude(), location.getLongitude());

        ageS = getIntent().getStringExtra("age");
        s1kr = getIntent().getStringExtra("s1kr");
        s2kr= getIntent().getStringExtra("s2kr");

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);

        if(s2kr != null){
            String smsBody = "저는 외국인입니다." + "저의 위치는 '" + address + "'이고, 저의 나이는 " + ageS +"세 입니다. 저의 증상은 "+ s1kr+ "," + s2kr +"입니다. 살려줘";

            sendIntent.putExtra("sms_body", smsBody); // 보낼 문자

            sendIntent.putExtra("address", "01011112222"); // 받는사람 번호

            sendIntent.setType("vnd.android-dir/mms-sms");

            startActivity(sendIntent);
        }else{
            String smsBody = "저는 외국인입니다." + "저의 위치는 " + address + "이고, 저의 나이는 " + ageS +"세 입니다. 저의 증상은 "+ s1kr+"입니다. 살려줘";

            sendIntent.putExtra("sms_body", smsBody); // 보낼 문자

            sendIntent.putExtra("address", "01011112222"); // 받는사람 번호

            sendIntent.setType("vnd.android-dir/mms-sms");

            startActivity(sendIntent);
        }

    }




    private void Speech(){
        String text = sym.getText().toString().trim();
        tts.setPitch((float) 0.8);
        tts.setSpeechRate((float) 1.0);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int language = tts.setLanguage(Locale.KOREAN);
        }
    }

    protected void onDestroy(){
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();

    }

    public String getTime(){
        now = System.currentTimeMillis();
        date = new Date(now);
        return format.format(date);
    }

    public String getTimes(){
        nows = System.currentTimeMillis();
        dates = new Date(nows);
        return formats.format(dates);
    }

    public String getTimeAsia(){
        nowaisa = System.currentTimeMillis();
        dateasia = new Date(nowaisa);
        return formatasia.format(dateasia);
    }


    public void basicCRUD(Realm realm){
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                PillDB pd = realm.createObject(PillDB.class,getTimes());
                pd.setName(pillname);
                pd.setS1(s1);
                pd.setS2(s2);
                pd.setAge(ageS);
                if(language == "ko" || language == "ja" || language == "zh"){
                    pd.setDate(getTimeAsia());
                }else{
                    pd.setDate(getTime());
                }
                pd.setS1kr(s1kr);
                pd.setS2kr(s2kr);
                if(pd.getName() == null){
                    ((MainActivity)MainActivity.context).drawer_recycler.setVisibility(View.GONE);
                }else{
                    RealmResults<PillDB> result = realm.where(PillDB.class).findAll();

                    for (PillDB pill : result) {
                        ((MainActivity)MainActivity.context).pillList.add(new PillList(pill.getS1(),pill.getS2(),pill.getAge(),pill.getDate(),pill.getName(), pill.getS1kr(), pill.getS2kr()));
                    }
                    ((MainActivity)MainActivity.context).nonehistory.setVisibility(View.GONE);
                }
            }
        });
    }
}