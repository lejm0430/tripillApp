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
//        String symptom1 = getIntent().getStringExtra("s1");
//        String symptom2 = getIntent().getStringExtra("s2");
//        String sum = getIntent().getStringExtra("sum");


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

                finish();
            }
        });


        recobtn.setOnClickListener(new View.OnClickListener() { //점수랑 나이 비교해서 dialog띄우기
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (age.length() > 0) {
                    Integer i = Integer.parseInt(age.getText().toString());
                    if (i == 2) {
                        AgeDialog dialog = new AgeDialog(AgeActivity.this);
                        dialog.callFunction();
                    }else{
                        Intent intent = new Intent(getApplicationContext(), PillRecommendActivity.class);
                        intent.putExtra("age",String.valueOf(i.intValue()));
                        startActivity(intent);
                    }
                }
            }
        });

        recyclerView = findViewById(R.id.recycle);

        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);


        recyclerView.setLayoutManager(layoutManager);



        String[] main_text =  {part};


        adapter = new SymptomAdapter(main_text);

        recyclerView.setAdapter(adapter);

    }

    protected void onUserLeaveHint(){
        super.onUserLeaveHint();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}