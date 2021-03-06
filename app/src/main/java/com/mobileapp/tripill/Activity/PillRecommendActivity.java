package com.mobileapp.tripill.Activity;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mobileapp.tripill.Adapter.SymptomRecommendAdpater;
import com.mobileapp.tripill.DataBase.PillDB;
import com.mobileapp.tripill.Dialog.FullImagDialog;
import com.mobileapp.tripill.Dialog.BaseDialog;
import com.mobileapp.tripill.R;
import com.mobileapp.tripill.SosMessage;
import net.cachapa.expandablelayout.ExpandableLayout;

import androidx.annotation.NonNull;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;

import static com.mobileapp.tripill.Props.INTE_INPUT_AGE;
import static com.mobileapp.tripill.Props.INTE_SELECT_PILLNAME;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM1;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM1_KR;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM2;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM2_KR;
import static com.mobileapp.tripill.Props.INTE_SYMPTOM_SUM;
import static com.mobileapp.tripill.Props.MIN_DISTANCE_CHANGE_FOR_UPDATES;
import static com.mobileapp.tripill.Props.MIN_TIME_BW_UPDATES;
import static com.mobileapp.tripill.Props.PERMISSIONS_REQUEST_CODE;
import static com.mobileapp.tripill.Props.STRING_FAIL_ADDRESS;

public class PillRecommendActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, LocationListener {


    RelativeLayout viewArea;
    RelativeLayout warningArea;
    RelativeLayout expectArea;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SymptomRecommendAdpater adapter;

    ExpandableLayout expectexp;
    ExpandableLayout warningexp;

    ImageView speaker;
    ImageView pillphoto;
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
    String s1;
    String s2;
    String sumS;
    String pillname;
    String name;
    String s1kr;
    String s2kr;

    int age;
    int sum;

    long now;
    long nows;
    long nowaisa;
    Date date;
    Date dates;
    Date dateasia;
    SimpleDateFormat format=new SimpleDateFormat("MM. dd. yyyy");
    SimpleDateFormat formatasia=new SimpleDateFormat("yyyy. MM. dd");
    SimpleDateFormat formats=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    private Realm realm;

    public Context prcontext;


    private TextToSpeech tts;

    Location location;
    double mlatitude;
    double mlongitude;

    String japanadress;
    String chinaadress;
    String englishaddress;
    String koreaaddress;
    String result;
    String address;
    String trnaslatelocation;


    String[] REQUIRED_PERMISSIONS={Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소

    private BaseDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_recommend);

        pillphoto=findViewById(R.id.pillphoto);
        viewArea=findViewById(R.id.viewArea);
        warningArea=findViewById(R.id.warningArea);
        expectArea=findViewById(R.id.expectArea);
        expectexp=findViewById(R.id.expectexp);
        warningexp=findViewById(R.id.warningexp);
        arrowIc=findViewById(R.id.arrowIc);
        arrowIcWarning=findViewById(R.id.arrowIcWarning);
        expect=findViewById(R.id.expect);
        warning=findViewById(R.id.warning);
        expecttext=findViewById(R.id.expecttext);
        precautionstext=findViewById(R.id.precautionstext);
        backbtn=findViewById(R.id.backbtn);
        sos=findViewById(R.id.sos);
        pullimg=findViewById(R.id.pullimg);
        goPHbtn=findViewById(R.id.goPHbtn);
        sym=findViewById(R.id.symptom);
        speaker=findViewById(R.id.speaker);

        prcontext=this;

        pillphoto.setClipToOutline(true);

        tts=new TextToSpeech(this, this);

        ageS=getIntent().getStringExtra(INTE_INPUT_AGE);
        s1=getIntent().getStringExtra(INTE_SELECT_SYMPTOM1);
        s2=getIntent().getStringExtra(INTE_SELECT_SYMPTOM2);
        sumS=getIntent().getStringExtra(INTE_SYMPTOM_SUM);
        name=getIntent().getStringExtra(INTE_SELECT_PILLNAME);
        s1kr=getIntent().getStringExtra(INTE_SELECT_SYMPTOM1_KR);
        s2kr=getIntent().getStringExtra(INTE_SELECT_SYMPTOM2_KR);

        age=Integer.parseInt(ageS);

        if (sumS != null) {
            sum=Integer.parseInt(sumS);
        }

        if (sum >= 1 && sum <= 3 || name.equals(getString(R.string.penzal))) {
            pillphoto.setImageResource(R.drawable.penzal);
            pillname=getString(R.string.penzal);

            expecttext.setText(getString(R.string.penzal_expect));
            precautionstext.setText(R.string.penzal_precautions);
        } else if (sum >= 4 && sum <= 6 || name.equals(getString(R.string.tylenol))) {
            pillphoto.setImageResource(R.drawable.tylenol);
            pillname="Tylenol";
            expecttext.setText(R.string.tylenol_expect);
            precautionstext.setText(R.string.tylenol_precautions);
        } else if (sum == 7 && age >= 12 || name.equals(getString(R.string.strepsil))) {
            pillphoto.setImageResource(R.drawable.strepsil);
            pillname="Strepsil";
            expecttext.setText(R.string.strepsil_expect);
            precautionstext.setText(R.string.strepsil_precautions);
        } else if (sum == 7 && age < 12 || name.equals(getString(R.string.minol))) {
            pillphoto.setImageResource(R.drawable.minol);
            pillname="Minol-F Troche";
            expecttext.setText(R.string.minol_expect);
            precautionstext.setText(R.string.minol_precautions);
        } else if (sum == 8 && age >= 15 || sum == 15 && age >= 15 || name.equals(getString(R.string.mucopect_Tab))) {
            pillphoto.setImageResource(R.drawable.mucoj);
            pillname="Mucopect Tab";
            expecttext.setText(R.string.mucopect_Tab_expect);
            precautionstext.setText(R.string.mucopect_Tab_precautions);
        } else if (sum == 8 && age < 15 || sum == 15 && age < 15 || name.equals(getString(R.string.mucopect_Syrup))) {
            pillphoto.setImageResource(R.drawable.mucos);
            pillname="Mucopect Syrup";
            expecttext.setText(R.string.mucopect_Syrup_expect);
            precautionstext.setText(R.string.mucopect_Syrup_precautions);
        } else if (sum == 20 || name.equals(getString(R.string.lirexpen))) {
            pillphoto.setImageResource(R.drawable.lirexpen);
            pillname="Lirexpen Tab";
            expecttext.setText(R.string.lirexpen_expect);
            precautionstext.setText(R.string.lirexpen_precautions);
        } else if (sum == 25 || name.equals(getString(R.string.fucidin))) {
            pillphoto.setImageResource(R.drawable.whosidin);
            pillname="Fucidin Ointment";
            expecttext.setText(R.string.fucidin_expect);
            precautionstext.setText(R.string.fucidin_precautions);
        } else if (sum == 35 || name.equals(getString(R.string.ru))) {
            pillphoto.setImageResource(R.drawable.ru);
            pillname="RU-21";
            expecttext.setText(R.string.ru_expect);
            precautionstext.setText(R.string.ru_precautions);
        } else if (sum == 40 || sum == 90 || name.equals(getString(R.string.gas))) {
            pillphoto.setImageResource(R.drawable.sohwa);
            pillname="Gas Whal Myung Su";
            expecttext.setText(R.string.gas_expect);
            precautionstext.setText(R.string.gas_precautions);
        } else if (sum == 50 || name.equals(getString(R.string.buscopan))) {
            pillphoto.setImageResource(R.drawable.buscopan);
            pillname="Buscopan Plus Tab";
            expecttext.setText(R.string.buscopan_expect);
            precautionstext.setText(R.string.buscopan_precautions);
        } else if (sum == 60 || name.equals(getString(R.string.mebo))) {
            pillphoto.setImageResource(R.drawable.mibo);
            pillname="Mebo Ointment";
            expecttext.setText(R.string.mebo_expect);
            precautionstext.setText(R.string.mebo_precautions);
        } else if (sum >= 100 || name.equals(getString(R.string.ezn))) {
            pillphoto.setImageResource(R.drawable.easyn);
            pillname="EZN 6 Eve";
            expecttext.setText(R.string.ezn_expect);
            precautionstext.setText(R.string.ezn_precautions);
        }

        name=null;

        if (s2kr == null || s2kr.isEmpty()) {
            sym.setText(s1kr);
        } else {
            sym.setText(s1kr + "/" + s2kr);
        }

        pullimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullImagDialog dialog=new FullImagDialog(PillRecommendActivity.this);
                dialog.init(pillname);
                dialog.show();
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
                dialog=new BaseDialog(PillRecommendActivity.this);
                String canclecontents, confirm;
                canclecontents=getString(R.string.sos);
                confirm=getString(R.string.send);
                dialog.init(null, canclecontents, confirm);
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
                if (state == 2) {
                    if (expect.getCurrentTextColor() != getColor(R.color.cobalt)) {
                        expect.setTextColor(getColor(R.color.cobalt));
                        Typeface face=ResourcesCompat.getFont(prcontext, R.font.bold);
                        expect.setTypeface(face);
                    }
                } else if (state == 1) {
                    if (expect.getCurrentTextColor() != getColor(R.color.black)) {
                        expect.setTextColor(getColor(R.color.black));
                        Typeface face=ResourcesCompat.getFont(prcontext, R.font.medium);
                        expect.setTypeface(face);
                    }
                }
            }
        });

        warningexp.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                if (state == 2) {
                    if (warning.getCurrentTextColor() != getColor(R.color.cobalt)) {
                        warning.setTextColor(getColor(R.color.cobalt));
                        Typeface face=ResourcesCompat.getFont(prcontext, R.font.bold);
                        warning.setTypeface(face);
                    }
                } else if (state == 1) {
                    if (warning.getCurrentTextColor() != getColor(R.color.black)) {
                        warning.setTextColor(getColor(R.color.black));
                        Typeface face=ResourcesCompat.getFont(prcontext, R.font.medium);
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
                Intent intent=new Intent(PillRecommendActivity.this, PharmacyMap.class);
                startActivity(intent);
            }
        });

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speech();
            }
        });


        recyclerView=findViewById(R.id.recycle);

        layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        String[] main_text=s2 == null || s2.isEmpty() ? new String[]{s1} : new String[]{s1, s2};
        adapter=new SymptomRecommendAdpater(main_text);

        recyclerView.setAdapter(adapter);

        realm=Realm.getDefaultInstance();
        basicCRUD(realm);

        new BackgroundTask().execute();
    }


    public void check() {
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

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result=true;

            // 모든 퍼미션을 허용했는지 체크

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result=false;
                    break;
                }

            }

            if (check_result) {
                //모든 퍼미션을 허용했다면 위치 업데이트를 시작
            } else {
                if (
                        ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    // 거부 한 적이 있고 또 거부
                    Toast.makeText(this, R.string.snackbar_body, Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                } else {
                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우
                    Toast.makeText(this, R.string.preference_none_setting, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    Intent intent=new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                    startActivityForResult(intent, 0);

                }
            }

        }

    }


    @SuppressLint("MissingPermission")
    public Location getLocation() {

        try {
            LocationManager locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
            }else {
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,  this);

                    if (locationManager != null) {
                        location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            mlatitude=location.getLatitude();
                            mlongitude=location.getLongitude();
                        }
                    }
                }


                if (isGPSEnabled) {
                    if (location != null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                        if (locationManager != null) {
                            location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                mlatitude=location.getLatitude();
                                mlongitude=location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {

        }

        return location;
    }


    public String getCurrentAddress(double mlatitude, double mlongitude) {

        //GPS를 주소로 변환
        Geocoder geocoder=new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses=geocoder.getFromLocation(
                    mlatitude,
                    mlongitude,
                    1);  //조회개수
        } catch (IOException ioException) {
            //네트워크 x   GPS x
            Toast.makeText(this, R.string.fail_Address, Toast.LENGTH_LONG).show();
            return STRING_FAIL_ADDRESS;
        } catch (IllegalArgumentException illegalArgumentException) {
            //네트워크 x   GPS o  ||  네트워크 o   GPS x
            Toast.makeText(this, R.string.turn_gps_or_network, Toast.LENGTH_LONG).show();
            return STRING_FAIL_ADDRESS;
        }

        if (addresses == null || addresses.size() == 0) {  //주소 미발견
            Toast.makeText(this, R.string.fail_Address, Toast.LENGTH_LONG).show();
            return STRING_FAIL_ADDRESS;
        }

        Address address=addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";  //주소 전체 출력

    }

    public void messege() {
        ageS=getIntent().getStringExtra(INTE_INPUT_AGE);
        s1kr=getIntent().getStringExtra(INTE_SELECT_SYMPTOM1_KR);
        s2kr=getIntent().getStringExtra(INTE_SELECT_SYMPTOM2_KR);

        SosMessage sos=new SosMessage(PillRecommendActivity.this);
        sos.init(address, ageS, s1kr, s2kr);
        sos.intent();

    }


    private void Speech() {
        String text=sym.getText().toString().trim();
        tts.setPitch((float) 0.8);
        tts.setSpeechRate((float) 1.0);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int language=tts.setLanguage(Locale.KOREAN);
        }
    }

    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();

    }

    public String getTime() {
        now=System.currentTimeMillis();
        date=new Date(now);
        return format.format(date);
    }

    public String getTimes() {
        nows=System.currentTimeMillis();
        dates=new Date(nows);
        return formats.format(dates);
    }

    public String getTimeAsia() {
        nowaisa=System.currentTimeMillis();
        dateasia=new Date(nowaisa);
        return formatasia.format(dateasia);
    }


    public void basicCRUD(Realm realm) {
        Locale locale=getResources().getConfiguration().locale;
        String language=locale.getLanguage();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                PillDB pd=realm.createObject(PillDB.class, getTimes());
                pd.setName(pillname);
                pd.setS1(s1);
                pd.setS2(s2);
                pd.setAge(ageS);
                if (language == "ko" || language == "ja" || language == "zh") {
                    pd.setDate(getTimeAsia());
                } else {
                    pd.setDate(getTime());
                }
                pd.setS1kr(s1kr);
                pd.setS2kr(s2kr);
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }


    class  BackgroundTask extends AsyncTask<Integer, Integer, Integer>{
        protected void onPreExecute(){
            getLocation();

            koreaaddress=getCurrentAddress(mlatitude, mlongitude);

            Locale locale=getResources().getConfiguration().locale;
            String language=locale.getLanguage();


            if (language == "ja") {
                japanadress = koreaaddress.replace(", 大韓民国","");
                trnaslatelocation = japanadress;
            }else if(language == "zh"){
                chinaadress = koreaaddress.replace(", 韩国","");
                trnaslatelocation = chinaadress;

            }else if(language == "en"){
                englishaddress = koreaaddress.replace(", South Korea","");
                trnaslatelocation = englishaddress;
            }else{
                trnaslatelocation = koreaaddress;
            }

        }

        @Override
        protected Integer doInBackground(Integer ... arg0){
            StringBuilder output = new StringBuilder();
            String clientId = "Doo2VYpjkFiwoJwRz6Ux";
            String clientSecret = "EDtnwCxip_";

            try{

                String text = URLEncoder.encode(trnaslatelocation,"UTF-8");
                String apiUrl = "https://openapi.naver.com/v1/papago/n2mt";

                URL url = new URL(apiUrl);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                String postParams = "source=en&target=ko&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode == 200){
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }else{
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                while((inputLine = br.readLine()) != null){
                    output.append(inputLine);
                }
                br.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            result = output.toString();
            return null;
        }

        protected void onPostExecute(Integer a){
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            if(element.getAsJsonObject().get("errorMessage") != null){
            }else if(element.getAsJsonObject().get("message") != null){
                address = element.getAsJsonObject().get("message").getAsJsonObject().get("result").getAsJsonObject().get("translatedText").getAsString();
            }
        }
    }
}