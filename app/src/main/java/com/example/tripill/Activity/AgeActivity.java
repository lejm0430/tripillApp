package com.example.tripill.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripill.Adapter.SymptomAdapter;
import com.example.tripill.Dialog.AgeDialog;
import com.example.tripill.Dialog.ChoicedSymptomSlide;
import com.example.tripill.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AgeActivity extends AppCompatActivity {
    EditText age;
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    SymptomAdapter adapter;

    ImageView backbtn;

    public Context mcontext;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);

        age = (EditText)findViewById(R.id.age);
        TextView recobtn = (TextView)findViewById(R.id.recobtn);
        ImageView xbtn = (ImageView)findViewById(R.id.xbtn);
        backbtn = findViewById(R.id.backbtn);


        age.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        String part = getIntent().getStringExtra("part");
        String symptom1 = getIntent().getStringExtra("s1");
        String symptom2 = getIntent().getStringExtra("s2");
        String sumS = getIntent().getStringExtra("sum");
        int sum = Integer.parseInt(sumS);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                finish();

            }
        });

        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChoicedSymptomSlide bottomSheet = new ChoicedSymptomSlide();

                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                setResult(RESULT_OK);
                finish();
                bottomSheet.dismiss();
            }
        });


        recobtn.setOnClickListener(new View.OnClickListener() { //점수랑 나이 비교해서 dialog띄우기
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (age.length() > 0) {
                    Integer i = Integer.parseInt(age.getText().toString());
                    if (sum>=1 && sum<=3 && i < 15 || sum >=4 && sum <=6 && i<7 || sum ==8 && i <2 || sum ==15 && i<2 || sum == 20 && i<15 || sum == 35 && i<15 || sum == 50 && i<15 || sum >= 100 && i<8){
                        AgeDialog dialog = new AgeDialog(AgeActivity.this);
                        dialog.callFunction();
                    }else{
                        if(sum>=1 && sum <=3) {
                            name = "penzal";
                        }else if(sum>=4 && sum<=6){
                            name = "tylenol";
                        }else if(sum ==7){
                            name = "strepsil";
                        }
                        else if(sum == 7 && i < 12){
                            name = "minol";
                        }else if(sum ==8 || sum == 15) {
                            name = "mucoj";
                        }else if(sum ==8 || sum == 15 && i < 15){
                            name = "mucos";
                        }else if(sum == 20 ){
                            name = "lirexpen";
                        }else if(sum == 25){
                            name = "whosidin";
                        }else if(sum == 35){
                            name = "ru";
                        }else if(sum == 40 || sum == 90){
                            name = "sohwa";
                        }else if(sum == 50){
                            name = "buscopan";
                        }else if(sum == 60){
                            name = "mibo";
                        }else if(sum >= 100){
                            name = "easyn";
                        }
                        Intent intent = new Intent(getApplicationContext(), PillRecommendActivity.class);
                        intent.putExtra("age",age.getText().toString());
                        intent.putExtra("sum",sumS);
                        intent.putExtra("s1",symptom1);
                        intent.putExtra("s2",symptom2);
                        intent.putExtra("name",name);
                        startActivity(intent);
                    }
                }
            }
        });

        recyclerView = findViewById(R.id.recycle);

        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);


        recyclerView.setLayoutManager(layoutManager);


        if(symptom2 == null) {
            String[] main_text = {part, symptom1};
            adapter = new SymptomAdapter(main_text);

            recyclerView.setAdapter(adapter);
        }else{
            String[] main_text = {part, symptom1, symptom2};
            adapter = new SymptomAdapter(main_text);

            recyclerView.setAdapter(adapter);
        }

    }

    protected void onUserLeaveHint(){
        super.onUserLeaveHint();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}