
package com.example.tripill.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.tripill.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity{

    static final int SYMPTOMCODE = 1111;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    public static final String TAG = MainActivity.class.getName();

    String name;
    int age;

    DrawerLayout mainDrawer;
    LinearLayout menuDrawer;

    //static 수정
    public TextView nonehistory;

    RecyclerView drawer_recycler;

    ArrayList<PillList> pillList;

    public static Context mcontext;

    PillHistoryAdapter historyadapter;

    public ChoicedSymptomSlide bottomSheet = new ChoicedSymptomSlide();

    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mcontext = this;

        /*if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SMS);
            }else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SMS);
            }
        }
>>>>>>> Stashed changes

*/
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

        realm = Realm.getDefaultInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        drawer_recycler.setLayoutManager(linearLayoutManager);

        pillList= new ArrayList<PillList>();
        historyadapter = new PillHistoryAdapter(pillList,this);
        drawer_recycler.setAdapter(historyadapter);


        RealmResults<PillDB> result = realm.where(PillDB.class).findAll();
        for (PillDB pill : result) {
            pillList.add(new PillList(pill.getS1(),pill.getS2(),pill.getAge(),pill.getDate(),pill.getName(), pill.getS1kr(), pill.getS2kr()));
            if(pill.getName() != null){
                nonehistory.setVisibility(View.GONE);
            }
        }

        Intent intent = new Intent(getApplicationContext(), AgeActivity.class);

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

                intent.putExtra("part",s_musclePain);
                intent.putExtra("s1",getString(R.string.muscle_pain));
                intent.putExtra("sum","20");
                intent.putExtra("s1kr","근육통");
                startActivity(intent);

            }
        });

        burnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_burn = burn.getText().toString();

                intent.putExtra("part",s_burn);
                intent.putExtra("s1",getString(R.string.burn));
                intent.putExtra("sum","60");
                intent.putExtra("s1kr","화상");
                startActivity(intent);



            }
        });

        woundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_wound = wound.getText().toString();

                intent.putExtra("part",s_wound);
                intent.putExtra("s1",getString(R.string.wound));
                intent.putExtra("sum","25");
                intent.putExtra("s1kr","상처");
                startActivity(intent);



            }
        });

        beerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_beer = hangover.getText().toString();

                intent.putExtra("part",s_beer);
                intent.putExtra("s1",getString(R.string.hangover));
                intent.putExtra("sum","35");
                intent.putExtra("s1kr","숙취");
                startActivity(intent);



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
                Toast.makeText(getApplicationContext(), R.string.onemoretime_destory, Toast.LENGTH_SHORT).show();
            }
        }

    }

    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }

}