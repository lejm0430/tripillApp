package com.mobileapp.tripill.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileapp.tripill.Adapter.SymptomAdapter;
import com.mobileapp.tripill.Dialog.BaseDialog;
import com.mobileapp.tripill.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.mobileapp.tripill.Props.INTE_INPUT_AGE;
import static com.mobileapp.tripill.Props.INTE_SELECT_PART;
import static com.mobileapp.tripill.Props.INTE_SELECT_PILLNAME;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM1;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM1_KR;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM2;
import static com.mobileapp.tripill.Props.INTE_SELECT_SYMPTOM2_KR;
import static com.mobileapp.tripill.Props.INTE_SYMPTOM_SUM;

public class AgeActivity extends AppCompatActivity {
    EditText age;

    TextView recommendbtn;

    ImageView xbtn;
    ImageView backbtn;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    SymptomAdapter adapter;

    String name;
    String s1kr;
    String s2kr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);

        age = findViewById(R.id.age);
        recommendbtn = findViewById(R.id.recommendbtn);
        xbtn = findViewById(R.id.xbtn);
        backbtn = findViewById(R.id.backbtn);


        age.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        String part = getIntent().getStringExtra(INTE_SELECT_PART);
        String symptom1 = getIntent().getStringExtra(INTE_SELECT_SYMPTOM1);
        String symptom2 = getIntent().getStringExtra(INTE_SELECT_SYMPTOM2);
        String sumS = getIntent().getStringExtra(INTE_SYMPTOM_SUM);
        s1kr = getIntent().getStringExtra(INTE_SELECT_SYMPTOM1_KR);
        s2kr = getIntent().getStringExtra(INTE_SELECT_SYMPTOM2_KR);
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

                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                setResult(RESULT_OK);
                finish();
            }
        });


        recommendbtn.setOnClickListener(new View.OnClickListener() { //점수랑 나이 비교해서 dialog띄우기
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                    Integer i = Integer.parseInt(age.getText().toString());
                    if (sum>=1 && sum<=3 && i < 15 || sum >=4 && sum <=6 && i<7 || sum ==8 && i <2 || sum ==15 && i<2 || sum == 20 && i<15 || sum == 35 && i<15 || sum == 50 && i<15 || sum >= 100 && i<8){
                        BaseDialog dialog = new BaseDialog(AgeActivity.this);
                        String contents,confirm;
                        contents = getString(R.string.not_recommend);
                        confirm = getString(R.string.confirm);
                        dialog.init(contents," ",confirm);
                        dialog.show();
                    }else{
                        if(sum>=1 && sum <=3) {
                            name = getString(R.string.penzal);
                        }else if(sum>=4 && sum<=6){
                            name = getString(R.string.tylenol);
                        }else if(sum ==7 && i>=12){
                            name = getString(R.string.strepsil);
                        }
                        else if(sum == 7 && i<12){
                            name = getString(R.string.minol);
                        }else if(sum ==8 && i >= 15 || sum == 15 && i >= 15)
                        {
                            name = getString(R.string.mucopect_Tab);
                        }
                        else if(sum ==8 && i < 15 || sum == 15 && i < 15){
                            name = getString(R.string.mucopect_Syrup);
                        }else if(sum == 20 ){
                            name = getString(R.string.lirexpen);
                        }else if(sum == 25){
                            name = getString(R.string.fucidin);
                        }else if(sum == 35){
                            name = getString(R.string.ru);
                        }else if(sum == 40 || sum == 90){
                            name = getString(R.string.gas);
                        }else if(sum == 50){
                            name = getString(R.string.buscopan);
                        }else if(sum == 60){
                            name = getString(R.string.mebo);
                        }else if(sum >= 100){
                            name = getString(R.string.ezn);
                        }
                        Intent intent = new Intent(getApplicationContext(), PillRecommendActivity.class);
                        intent.putExtra(INTE_INPUT_AGE,age.getText().toString());
                        intent.putExtra(INTE_SYMPTOM_SUM,sumS);
                        intent.putExtra(INTE_SELECT_SYMPTOM1,symptom1);
                        intent.putExtra(INTE_SELECT_SYMPTOM2,symptom2);
                        intent.putExtra(INTE_SELECT_PILLNAME,name);
                        intent.putExtra(INTE_SELECT_SYMPTOM1_KR,s1kr);
                        intent.putExtra(INTE_SELECT_SYMPTOM2_KR,s2kr);
                        startActivity(intent);
                    }
                }
        });

        recyclerView = findViewById(R.id.recycle);

        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);


        recyclerView.setLayoutManager(layoutManager);

        String[] main_text =  part == null ? new String[]{symptom1} : symptom2 == null ? new String[]{part, symptom1} : new String[]{part, symptom1, symptom2};
        adapter = new SymptomAdapter(main_text);
        recyclerView.setAdapter(adapter);


    }

    protected void onUserLeaveHint(){
        super.onUserLeaveHint();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}