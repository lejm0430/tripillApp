package com.example.tripill.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Adapter.PillHistoryAdapter;
import com.example.tripill.DataBase.PillList;
import com.example.tripill.Adapter.SymptomRecommendAdpater;
import com.example.tripill.DataBase.PillDB;
import com.example.tripill.Dialog.FullImagDialog;
import com.example.tripill.R;
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
    Date date;
    Date dates;
    SimpleDateFormat format = new SimpleDateFormat("MM. dd. yyyy");
    SimpleDateFormat formats = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");



    private Realm realm;

    public static Context prcontext; //--
    private TextToSpeech tts;

    public MainActivity mainActivity = new MainActivity();

//    private static PillRecommendActivity instance = new PillRecommendActivity();
//    public static PillRecommendActivity getInstance(){return instance;}

    private GpsTracker gpsTracker;



    private static final int GPS_ENABLE_REQUEST_CODE = 300;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소


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
        }else if(sum ==7 || name.equals(getString(R.string.strepsil))){
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
        }else if(sum ==8 || sum == 15 || name.equals(getString(R.string.mucopect_Tab))) {
            pillphoto.setImageResource(R.drawable.mucoj);
            pillname = "Mucopect Tab";
            expecttext.setText(R.string.mucopect_Tab_expect);
            precautionstext.setText(R.string.mucopect_Tab_precautions);
        }else if(sum ==8 || sum == 15 && age < 15 || name.equals(getString(R.string.mucopect_Syrup))){
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

        if(s2 == null || s2.isEmpty()){
            sym.setText(s1);
        }else {
            sym.setText(s1 + "/" + s2);
        }

        pullimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullImagDialog dialog = new FullImagDialog(PillRecommendActivity.this);
                dialog.callFunction(pillname);
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SosDialog dialog = new SosDialog(PillRecommendActivity.this);
//                dialog.callFunction();


                check();
                callFunction();
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
                    }
                }else if(state == 1){
                    if (expect.getCurrentTextColor() != getColor(R.color.black)) {
                        expect.setTextColor(getColor(R.color.black));
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

                    }
                }else if(state == 1){
                    if (warning.getCurrentTextColor() != getColor(R.color.black)) {
                        warning.setTextColor(getColor(R.color.black));
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

        ((MainActivity)MainActivity.mcontext).pillList = new ArrayList<PillList>();

        realm = Realm.getDefaultInstance();
        basicCRUD(realm);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        ((MainActivity)MainActivity.mcontext).drawer_recycler.setLayoutManager(linearLayoutManager);

        ((MainActivity)MainActivity.mcontext).historyadapter = new PillHistoryAdapter(((MainActivity)MainActivity.mcontext).pillList,this);
        ((MainActivity)MainActivity.mcontext).drawer_recycler.setAdapter(((MainActivity)MainActivity.mcontext).historyadapter);

    }





    public void callFunction() {

        final Dialog dlg=new Dialog(this);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dlg.setContentView(R.layout.dialog_alluse);

        WindowManager.LayoutParams params=dlg.getWindow().getAttributes();
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;

        dlg.show();
        dlg.setCancelable(true);

        final Button cancle=(Button) dlg.findViewById(R.id.canclebtn);
        final Button ok=(Button) dlg.findViewById(R.id.okbtn);
        final RelativeLayout layout=(RelativeLayout) dlg.findViewById(R.id.layout);
        final TextView text=(TextView) dlg.findViewById(R.id.text);

        text.setText("당신의 증상과 위치가\n응급메시지로 전송됩니다.\n정말 메시지를 전송할까요?");
        ok.setText("전송");

        layout.setClipToOutline(true);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                int hasFineLocationPermission = ContextCompat.checkSelfPermission(PillRecommendActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(PillRecommendActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

                if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                        hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){
                    messege();
                }

                dlg.dismiss();

            }
        });

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

                        checkRunTimePermission();
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


    void checkRunTimePermission(){

        // 위치 퍼미션을 가지고 있는지 체크
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(PillRecommendActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(PillRecommendActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                Toast.makeText(this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(PillRecommendActivity.this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);


            } else {
                ActivityCompat.requestPermissions(PillRecommendActivity.this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);
            }

        }

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
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "'주소 미발견'";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    public void messege() {


        gpsTracker = new GpsTracker(PillRecommendActivity.this);
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude,longitude);

        Log.d("TAG","latitude : "+latitude);
        Log.d("TAG","longitude : "+longitude);


//        String address ="서울 강남구 신사동 123-123";
        ageS = getIntent().getStringExtra("age");
        s1kr = getIntent().getStringExtra("s1kr");
        s2kr= getIntent().getStringExtra("s2kr");

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);

        if(s2kr != null){
            String smsBody = "저는 외국인입니다." + "저의 위치는 " + address + "이고, 저의 나이는 " + ageS +"세 입니다. 저의 증상은 "+ s1kr+ "," + s2kr +"입니다. 살려줘";

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

    public void basicCRUD(Realm realm){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                PillDB pd = realm.createObject(PillDB.class,getTimes());
                pd.setName(pillname);
                pd.setS1(s1);
                pd.setS2(s2);
                pd.setAge(ageS);
                pd.setDate(getTime());
                pd.setS1kr(s1kr);
                pd.setS2kr(s2kr);
                if(pd.getName() == null){
                    ((MainActivity)MainActivity.mcontext).drawer_recycler.setVisibility(View.GONE);
                }else{
                    RealmResults<PillDB> result = realm.where(PillDB.class).findAll();

                    for (PillDB pill : result) {
                        ((MainActivity)MainActivity.mcontext).pillList.add(new PillList(pill.getS1(),pill.getS2(),pill.getAge(),pill.getDate(),pill.getName(), pill.getS1kr(), pill.getS2kr()));
                    }
                    ((MainActivity)MainActivity.mcontext).nonehistory.setVisibility(View.GONE);
                }
            }
        });
    }
}