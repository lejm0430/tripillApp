
package com.mobileapp.tripill.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileapp.tripill.Adapter.PillHistoryAdapter;
import com.mobileapp.tripill.DataBase.PillList;
import com.mobileapp.tripill.DataBase.PillDB;
import com.mobileapp.tripill.Dialog.ChoicedSymptomSlide;
import com.mobileapp.tripill.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.mobileapp.tripill.Props.FINISH_INTERVAL_TIME;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM1;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM1_KR;
import static com.mobileapp.tripill.Props.INTE_SYMPTOM_SUM;


public class MainActivity extends AppCompatActivity{

    TextView head;
    TextView neck;
    TextView stomach;
    TextView muscle_pain;
    TextView burn;
    TextView wound;
    TextView hangover;

    ImageView menuBtn;
    ImageView headBtn;
    ImageView neckBtn;
    ImageView stomachBtn;
    ImageView musclePainBtn;
    ImageView burnBtn;
    ImageView woundBtn;
    ImageView beerBtn;

    private long backPressedTime = 0;

    DrawerLayout mainDrawer;
    LinearLayout menuDrawer;

    String TAG = "ChoicedSymptomSlide";

    TextView nonehistory;

    RecyclerView drawer_recycler;

    ArrayList<PillList> pillList;

    PillHistoryAdapter historyadapter;

    public ChoicedSymptomSlide choicedSymptomSlide = new ChoicedSymptomSlide();

    private Realm realm;

    public Context context;


    View.OnClickListener onClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentchoicedSymptomSlide((String) view.getTag());
        }
    };

    View.OnClickListener onClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentAge((IntentAge_sytmptom) view.getTag());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        head = findViewById(R.id.head);
        neck = findViewById(R.id.neck);
        stomach = findViewById(R.id.stomach);
        muscle_pain = findViewById(R.id.muscle_pain);
        burn = findViewById(R.id.burn);
        wound = findViewById(R.id.wound);
        hangover = findViewById(R.id.hangover);

        menuBtn = findViewById(R.id.menuBtn);
        headBtn = findViewById(R.id.headBtn_ring);
        neckBtn = findViewById(R.id.neckBtn_ring);
        stomachBtn = findViewById(R.id.stomachBtn_ring);
        musclePainBtn = findViewById(R.id.musclePainBtn_ring);
        burnBtn = findViewById(R.id.burnBtn_ring);
        woundBtn = findViewById(R.id.woundBtn_ring);
        beerBtn = findViewById(R.id.beerBtn_ring);
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
        musclePainBtn.setTag(new IntentAge_sytmptom(getString(R.string.muscle_pain),"근육통","20"));
        burnBtn.setTag(new IntentAge_sytmptom(getString(R.string.burn),"화상","60"));
        woundBtn.setTag(new IntentAge_sytmptom(getString(R.string.wound),"상처","25"));
        beerBtn.setTag(new IntentAge_sytmptom(getString(R.string.hangover),"숙취","35"));
    }

    void IntentchoicedSymptomSlide(String title){
        choicedSymptomSlide.title = title;
        choicedSymptomSlide.show(getSupportFragmentManager(), TAG);
    }

    void IntentAge(IntentAge_sytmptom intentAge_sytmptom){
        Intent intent = new Intent(getApplicationContext(), AgeActivity.class);

        intent.putExtra(INTE_SELECT_SYMPTOM1,intentAge_sytmptom.getSytmptom_name());
        intent.putExtra(INTE_SYMPTOM_SUM,intentAge_sytmptom.getSum());
        intent.putExtra(INTE_SELECT_SYMPTOM1_KR,intentAge_sytmptom.getSytmptom_name_kr());
        startActivity(intent);
    }

    class IntentAge_sytmptom{
        String sytmptom_name,sytmptom_name_kr,sum;

        public IntentAge_sytmptom(String sytmptom_name, String sytmptom_name_kr, String sum) {
            this.sytmptom_name=sytmptom_name;
            this.sytmptom_name_kr=sytmptom_name_kr;
            this.sum=sum;
        }

        public String getSytmptom_name() {
            return sytmptom_name;
        }

        public String getSytmptom_name_kr() {
            return sytmptom_name_kr;
        }

        public String getSum() {
            return sum;
        }
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