package com.example.tripill.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Adapter.PillHistoryAdapter;
import com.example.tripill.Adapter.PillList;
import com.example.tripill.Adapter.SymptomRecommendAdpater;
import com.example.tripill.DataBase.PillDB;
import com.example.tripill.Dialog.FullImagDialog;
import com.example.tripill.Dialog.SosDialog;
import com.example.tripill.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    TextView text;
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

    long now;
    long nows;
    Date date;
    Date dates;
    SimpleDateFormat format = new SimpleDateFormat("MM. dd. yyyy");
    SimpleDateFormat formats = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");



    public static Realm realm; //--

    public static Context prcontext; //--
    private TextToSpeech tts;

//    private static PillRecommendActivity instance = new PillRecommendActivity();
//    public static PillRecommendActivity getInstance(){return instance;}

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
        text = findViewById(R.id.tv1);
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

        age = Integer.parseInt(ageS);

        if(sumS != null){
            sum = Integer.parseInt(sumS);
        }

        if(sum>=1 && sum <=3 || name.equals("penzal")) {
            pillphoto.setImageResource(R.drawable.penzal);
            pillname = "penzal";
        }else if(sum>=4 && sum<=6 || name.equals("tylenol")){
            pillphoto.setImageResource(R.drawable.tylenol);
            pillname = "tylenol";
        }else if(sum ==7 || name.equals("strepsil")){
            pillphoto.setImageResource(R.drawable.strepsil);
            pillname = "strepsil";
        }
        else if(sum == 7 && age < 12 || name.equals("minol")){
            pillphoto.setImageResource(R.drawable.minol);
            pillname = "minol";
        }else if(sum ==8 || sum == 15 || name.equals("mucoj")) {
            pillphoto.setImageResource(R.drawable.mucoj);
            pillname = "mucoj";
        }else if(sum ==8 || sum == 15 && age < 15 || name.equals("mucos")){
            pillphoto.setImageResource(R.drawable.mucos);
            pillname = "mucos";
        }else if(sum == 20 || name.equals("lirexpen")){
            pillphoto.setImageResource(R.drawable.lirexpen);
            pillname = "lirexpen";
        }else if(sum == 25 || name.equals("whosidin")){
            pillphoto.setImageResource(R.drawable.whosidin);
            pillname = "whosidin";
        }else if(sum == 35 ||  name.equals("ru")){
            pillphoto.setImageResource(R.drawable.ru);
            pillname = "ru";
        }else if(sum == 40 || sum == 90 || name.equals("sohwa")){
            pillphoto.setImageResource(R.drawable.sohwa);
            pillname = "sohwa";
        }else if(sum == 50 || name.equals("buscopan")){
            pillphoto.setImageResource(R.drawable.buscopan);
            pillname = "buscopan";
        }else if(sum == 60 || name.equals("mibo")){
            pillphoto.setImageResource(R.drawable.mibo);
            pillname = "mibo";
        }else if(sum >= 100 || name.equals("easyn")){
            pillphoto.setImageResource(R.drawable.easyn);
            pillname = "easyn";
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
                SosDialog dialog = new SosDialog(PillRecommendActivity.this);
                dialog.callFunction();
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

    public void intent() {
        String address ="서울 강남구 신사동 123-123";
        ageS = getIntent().getStringExtra("age");
        s1 = getIntent().getStringExtra("s1");
        s2= getIntent().getStringExtra("s2");


        Intent sendIntent = new Intent(Intent.ACTION_VIEW);

        if(s2 != null){
            String smsBody = "저는 외국인입니다." + "저의 위치는 " + address + "이고, 저의 나이는 " + ageS +"세 입니다. 저의 증상은 "+ s1+ "," + s2 +"입니다. 살려줘";

            sendIntent.putExtra("sms_body", smsBody); // 보낼 문자

            sendIntent.putExtra("address", "01011112222"); // 받는사람 번호

            sendIntent.setType("vnd.android-dir/mms-sms");

            startActivity(sendIntent);
        }else{
            String smsBody = "저는 외국인입니다." + "저의 위치는 " + address + "이고, 저의 나이는 " + ageS +"세 입니다. 저의 증상은 "+ s1+"입니다. 살려줘";

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
                if(pd.getName() == null){
                    ((MainActivity)MainActivity.mcontext).drawer_recycler.setVisibility(View.GONE);
                }else{
                    RealmResults<PillDB> result = realm.where(PillDB.class).findAll();

                    for (PillDB pill : result) {
                        ((MainActivity)MainActivity.mcontext).pillList.add(new PillList(pill.getS1(),pill.getS2(),pill.getAge(),pill.getDate(),pill.getName()));
                    }
                    ((MainActivity)MainActivity.mcontext).nonehistory.setVisibility(View.GONE);
                }
            }
        });
    }
}