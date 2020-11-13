
package com.example.tripill.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Adapter.PillHistoryAdapter;
import com.example.tripill.DataBase.PillList;
import com.example.tripill.DataBase.PillDB;
import com.example.tripill.Dialog.ChoicedSymptomSlide;
import com.example.tripill.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.tripill.Props.FINISH_INTERVAL_TIME;
import static com.example.tripill.Props.INTE_SELECT_SYMPTOM1;
import static com.example.tripill.Props.INTE_SELECT_SYMPTOM1_KR;
import static com.example.tripill.Props.INTE_SYMPTOM_SUM;
import static com.example.tripill.Props.REQUEST_CODE;


public class MainActivity extends AppCompatActivity{
    private long backPressedTime = 0;

    String name;
    int age;

    DrawerLayout mainDrawer;
    LinearLayout menuDrawer;

    String TAG = "ChoicedSymptomSlide";

    //static 수정
    TextView nonehistory;

    RecyclerView drawer_recycler;

    ArrayList<PillList> pillList;

    PillHistoryAdapter historyadapter;

    public ChoicedSymptomSlide bottomSheet = new ChoicedSymptomSlide();

    Realm realm;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        TextView head = findViewById(R.id.head);
        TextView neck = findViewById(R.id.neck);
        TextView stomach = findViewById(R.id.stomach);
        TextView muscle_pain = findViewById(R.id.muscle_pain);
        TextView burn = findViewById(R.id.burn);
        TextView wound = findViewById(R.id.wound);
        TextView hangover = findViewById(R.id.hangover);


        ImageView menuBtn = findViewById(R.id.menuBtn);
        ImageView headBtn = findViewById(R.id.headBtn_ring);
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
                bottomSheet.show(getSupportFragmentManager(), TAG);

            }
        });

        neckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_neck = neck.getText().toString();

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();

                bottomSheet.title = s_neck;
                bottomSheet.show(getSupportFragmentManager(), TAG);



            }
        });

        stomachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_stomach = stomach.getText().toString();

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();

                bottomSheet.title = s_stomach;
                bottomSheet.show(getSupportFragmentManager(), TAG);



            }
        });

        musclePainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_musclePain = muscle_pain.getText().toString();

                intent.putExtra(INTE_SELECT_SYMPTOM1,getString(R.string.muscle_pain));
                intent.putExtra(INTE_SYMPTOM_SUM,"20");
                intent.putExtra(INTE_SELECT_SYMPTOM1_KR,"근육통");
                startActivity(intent);

            }
        });

        burnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_burn = burn.getText().toString();

                intent.putExtra(INTE_SELECT_SYMPTOM1,getString(R.string.burn));
                intent.putExtra(INTE_SYMPTOM_SUM,"60");
                intent.putExtra(INTE_SELECT_SYMPTOM1_KR,"화상");
                startActivity(intent);



            }
        });

        woundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_wound = wound.getText().toString();

                intent.putExtra(INTE_SELECT_SYMPTOM1,getString(R.string.wound));
                intent.putExtra(INTE_SYMPTOM_SUM,"25");
                intent.putExtra(INTE_SELECT_SYMPTOM1_KR,"상처");
                startActivity(intent);



            }
        });

        beerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_beer = hangover.getText().toString();

                intent.putExtra(INTE_SELECT_SYMPTOM1,getString(R.string.hangover));
                intent.putExtra(INTE_SYMPTOM_SUM,"35");
                intent.putExtra(INTE_SELECT_SYMPTOM1_KR,"숙취");
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