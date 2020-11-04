
package com.example.tripill.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Adapter.PillHistoryAdapter;
import com.example.tripill.Adapter.PillList;
import com.example.tripill.DataBase.PillDB;
import com.example.tripill.Dialog.ChoicedSymptomSlide;
import com.example.tripill.Dialog.SosDialog;
import com.example.tripill.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity{

    static final int SYMPTOMCODE = 1111;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private final int MY_PERMISSION_REQUEST_SMS = 1001;
    public static final String TAG = MainActivity.class.getName();
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private Realm realm;
    String name;
    int age;
    TextView nonehistory;

    RecyclerView drawer_recycler;

    public static Context mcontext;

    DrawerLayout mainDrawer;
    LinearLayout menuDrawer;

    ArrayList<PillList> pillList;

    ChoicedSymptomSlide bottomSheet = new ChoicedSymptomSlide();

    long now;
    long nows;
    Date date;
    Date dates;
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat formats = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SMS);
            }else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SMS);
            }
        }

        TextView head = findViewById(R.id.head);
        TextView neck = findViewById(R.id.neck);
        TextView stomach = findViewById(R.id.stomach);
        TextView muscle_pain = findViewById(R.id.muscle_pain);
        TextView burn = findViewById(R.id.burn);
        TextView wound = findViewById(R.id.wound);
        TextView hangover = findViewById(R.id.hangover);


        ImageView menuBtn = findViewById(R.id.menuBtn);
        final ImageView headBtn = findViewById(R.id.headBtn_ring);
        ImageView neckBtn = findViewById(R.id.neckBtn_ring);
        ImageView stomachBtn = findViewById(R.id.stomachBtn_ring);
        ImageView musclePainBtn = findViewById(R.id.musclePainBtn_ring);
        ImageView burnBtn = findViewById(R.id.burnBtn_ring);
        ImageView woundBtn = findViewById(R.id.woundBtn_ring);
        ImageView beerBtn = findViewById(R.id.beerBtn_ring);
        mainDrawer = findViewById(R.id.main_drawer);
        menuDrawer = findViewById(R.id.menu_drawer);

        menuDrawer.setOnClickListener((v)->{});
        mainDrawer.closeDrawer(menuDrawer);

        drawer_recycler = findViewById(R.id.drawer_recycler);
        nonehistory = findViewById(R.id.nonehistory);

        pillList = new ArrayList<PillList>();

        realm = Realm.getDefaultInstance();
        basicCRUD(realm);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        drawer_recycler.setLayoutManager(linearLayoutManager);

        PillHistoryAdapter adapter = new PillHistoryAdapter(pillList,this);
        drawer_recycler.setAdapter(adapter);



        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawer.openDrawer(menuDrawer);
            }
        });


        headBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_head = head.getText().toString();

                ChoicedSymptomSlide bottomSheet = new ChoicedSymptomSlide();

                bottomSheet.title = s_head;
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");



            }
        });

        neckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_neck = neck.getText().toString();

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();

                bottomSheet.title = s_neck;
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");



            }
        });

        stomachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_stomach = stomach.getText().toString();

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();

                bottomSheet.title = s_stomach;
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");



            }
        });

        musclePainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_musclePain = muscle_pain.getText().toString();

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();

                bottomSheet.title = s_musclePain;
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");



            }
        });

        burnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_burn = burn.getText().toString();

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();

                bottomSheet.title = s_burn;
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");



            }
        });

        woundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_wound = wound.getText().toString();

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();

                bottomSheet.title = s_wound;
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");



            }
        });

        beerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_beer = hangover.getText().toString();

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();

                bottomSheet.title = s_beer;
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");



            }
        });
    }

    private String getTime(){
        now = System.currentTimeMillis();
        date = new Date(now);
        return format.format(date);
    }

    private String getTimes(){
        nows = System.currentTimeMillis();
        dates = new Date(nows);
        return formats.format(dates);
    }




    @Override
    public void onBackPressed() {
        if(mainDrawer.isDrawerOpen(menuDrawer)){
            mainDrawer.closeDrawer(menuDrawer);
        }else {
            long temptime = System.currentTimeMillis();
            long intervalTime = temptime - backPressedTime;
            if(0<= intervalTime && FINISH_INTERVAL_TIME >= intervalTime){
                finish();
            }
            else{
                backPressedTime = temptime;
                Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }


    private void basicCRUD(Realm realm){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                PillDB pd = realm.createObject(PillDB.class,getTimes());
                pd.setName("타이레놀");
                pd.setS1("두통");
                pd.setS2("어지러움");
                pd.setAge(6);

                if(pd.getName().isEmpty()){
                    drawer_recycler.setVisibility(View.GONE);
                }else{
                    pillList.add(new PillList(pd.getS1(), pd.getS2(), String.valueOf(pd.getAge()), getTime(), pd.getName()));
                    nonehistory.setVisibility(View.GONE);
                }
                }
        });

        final PillDB pd = realm.where(PillDB.class).findFirst();
    }
}