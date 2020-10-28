package com.example.tripill;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AgeActivity extends AppCompatActivity {
    EditText age;
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    SymptomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);

        age = (EditText)findViewById(R.id.age);
        TextView recobtn = (TextView)findViewById(R.id.recobtn);
        ImageView xbtn = (ImageView)findViewById(R.id.xbtn);

        age.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        recobtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (age.length() > 0) {
                    Integer i = Integer.parseInt(age.getText().toString());
                    if (i == 2) {
                        AgeDialog dialog = new AgeDialog(AgeActivity.this);
                        dialog.callFunction();
                    }
                }
            }
        });

        recyclerView = findViewById(R.id.recycle);

        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);


        recyclerView.setLayoutManager(layoutManager);



        String[] main_text =  {"머리","두통","어지럼증"};


        adapter = new SymptomAdapter(main_text);

        recyclerView.setAdapter(adapter);

    }
}