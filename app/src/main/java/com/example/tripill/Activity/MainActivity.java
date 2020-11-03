
package com.example.tripill.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Adapter.PillHistoryAdapter;
import com.example.tripill.Adapter.PillList;
import com.example.tripill.Dialog.ChoicedSymptomSlide;
import com.example.tripill.Dialog.SosDialog;
import com.example.tripill.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity{

    static final int SYMPTOMCODE = 1111;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private final int MY_PERMISSION_REQUEST_SMS = 1001;
    private Realm realm;

    public static Context mcontext;

    DrawerLayout mainDrawer;
    LinearLayout menuDrawer;

    ArrayList<PillList> pillList;

    ChoicedSymptomSlide bottomSheet = new ChoicedSymptomSlide();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("info");
                builder.setMessage("asdf");

                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SMS);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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

        RecyclerView drawer_recycler = findViewById(R.id.drawer_recycler);

        pillList = new ArrayList<PillList>();

        //리스트 추가
        pillList.add(new PillList("두통","발열","21","2020.09.11","약이름1"));
//        pillList.add(new PillList("어지럼증","두통","15","2020.18.05","약이름2"));
//        pillList.add(new PillList("상처","","37","2020.26.03","약이름3"));

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
}