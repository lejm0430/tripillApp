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

import com.example.tripill.Adapter.SymptomRecommendAdpater;
import com.example.tripill.Dialog.FullImagDialog;
import com.example.tripill.Dialog.SosDialog;
import com.example.tripill.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.Locale;

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
    String age;
    String s1;
    String s2;
    String pillname;

    public static Context mcontext;
    private TextToSpeech tts;

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

        pillphoto.setClipToOutline(true);

        tts = new TextToSpeech(this, this);


//        String hab = getIntent().getStringExtra("sum");
//
//        Integer sum = Integer.parseInt(hab);

        age = getIntent().getStringExtra("age");
        s1 = getIntent().getStringExtra("s1");
        s2 = getIntent().getStringExtra("s2");
        pillname = getIntent().getStringExtra("name");

            pillphoto.setImageResource(R.drawable.buscopan);//수정사항

        Integer i = Integer.parseInt(age);

        if(s2.isEmpty()){
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



        String[] main_text =  {s1,s2};


        adapter = new SymptomRecommendAdpater(main_text);

        recyclerView.setAdapter(adapter);
    }

    public void intent() {
        String address ="서울 강남구 신사동 123-123";
        age = getIntent().getStringExtra("age");
        s1 = getIntent().getStringExtra("s1");
        s2= getIntent().getStringExtra("s2");

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);

        String smsBody = "저는 외국인입니다." + "저의 위치는 " + address + "이고, 저의 나이는 " + age +"세 입니다. 저의 증상은 "+ s1+ "," + s2 +"입니다. 살려줘";

        sendIntent.putExtra("sms_body", smsBody); // 보낼 문자

        sendIntent.putExtra("address", "01011112222"); // 받는사람 번호

        sendIntent.setType("vnd.android-dir/mms-sms");

        startActivity(sendIntent);
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
}