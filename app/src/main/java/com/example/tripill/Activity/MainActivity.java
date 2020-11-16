
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

    private Realm realm;

    public Context context;


    View.OnClickListener onClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomInfoShow((String) view.getTag());
        }
    };

    View.OnClickListener onClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            test((SympTomModel) view.getTag());
        }
    };

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
            pillList.add(new PillList(pill.getS1(), pill.getS2(), pill.getAge(), pill.getDate(), pill.getName(), pill.getS1kr(), pill.getS2kr()));
            if (pill.getName() != null) {
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




        headBtn.setOnClickListener(onClickListener1);
        neckBtn.setOnClickListener(onClickListener1);
        stomachBtn.setOnClickListener(onClickListener1);


        musclePainBtn.setOnClickListener(onClickListener2);
        burnBtn.setOnClickListener(onClickListener2);
        woundBtn.setOnClickListener(onClickListener2);
        beerBtn.setOnClickListener(onClickListener2);
        musclePainBtn.setTag(new SympTomModel(getString(R.string.muscle_pain),getString(R.string.muscle_pain),"근육통","20"));
        burnBtn.setTag(new SympTomModel(getString(R.string.burn),getString(R.string.burn),"화상","60"));
        woundBtn.setTag(new SympTomModel(getString(R.string.wound),getString(R.string.wound),"상처","25"));
        beerBtn.setTag(new SympTomModel(getString(R.string.hangover),getString(R.string.hangover),"숙취","35"));

    }

    void test(SympTomModel md){
        Intent intent = new Intent(getApplicationContext(), AgeActivity.class);

        String s = md.getS();
        intent.putExtra(INTE_SELECT_SYMPTOM1,md.getS1());
        intent.putExtra(INTE_SYMPTOM_SUM,md.getSum());
        intent.putExtra(INTE_SELECT_SYMPTOM1_KR,md.getS1kr());
        startActivity(intent);
    }

    class SympTomModel{
        String s,s1,s1kr,sum;

        public SympTomModel(String s, String s1, String s1kr, String sum) {
            this.s=s;
            this.s1=s1;
            this.s1kr=s1kr;
            this.sum=sum;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s=s;
        }

        public String getS1() {
            return s1;
        }

        public void setS1(String s1) {
            this.s1=s1;
        }

        public String getS1kr() {
            return s1kr;
        }

        public void setS1kr(String s1kr) {
            this.s1kr=s1kr;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum=sum;
        }
    }

    void bottomInfoShow(String str){
        bottomSheet.title = str;
        bottomSheet.show(getSupportFragmentManager(), TAG);
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

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        drawer_recycler.setLayoutManager(linearLayoutManager);

        pillList= new ArrayList<PillList>();
        historyadapter = new PillHistoryAdapter(pillList,this);
        drawer_recycler.setAdapter(historyadapter);


        RealmResults<PillDB> result = realm.where(PillDB.class).findAll();
        for (PillDB pill : result) {
            pillList.add(new PillList(pill.getS1(), pill.getS2(), pill.getAge(), pill.getDate(), pill.getName(), pill.getS1kr(), pill.getS2kr()));
            if (pill.getName() != null) {
                nonehistory.setVisibility(View.GONE);
            }
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }



}