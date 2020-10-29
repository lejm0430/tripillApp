
package com.example.tripill.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.Dialog.ChoicedSymptomSlide;
import com.example.tripill.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity{

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    DrawerLayout mainDrawer;
    LinearLayout menuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView head = findViewById(R.id.head);
        TextView neck = findViewById(R.id.neck);
        TextView stomach = findViewById(R.id.stomach);
        TextView arm = findViewById(R.id.arm);
        TextView leg = findViewById(R.id.leg);
        TextView muscle_pain = findViewById(R.id.muscle_pain);
        TextView burn = findViewById(R.id.burn);
        TextView wound = findViewById(R.id.wound);
        TextView hangover = findViewById(R.id.hangover);


        ImageView menuBtn = findViewById(R.id.menuBtn);
        final ImageView headBtn = findViewById(R.id.headBtn_ring);
        ImageView neckBtn = findViewById(R.id.neckBtn_ring);
        ImageView stomachBtn = findViewById(R.id.stomachBtn_ring);
        ImageView armBtn = findViewById(R.id.armBtn_ring);
        ImageView legBtn = findViewById(R.id.legBtn_ring);
        ImageView musclePainBtn = findViewById(R.id.musclePainBtn_ring);
        ImageView burnBtn = findViewById(R.id.burnBtn_ring);
        ImageView woundBtn = findViewById(R.id.woundBtn_ring);
        ImageView beerBtn = findViewById(R.id.beerBtn_ring);
        mainDrawer = findViewById(R.id.main_drawer);
        menuDrawer = findViewById(R.id.menu_drawer);

        menuDrawer.setOnClickListener((v)->{});
        mainDrawer.closeDrawer(menuDrawer);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawer.openDrawer(menuDrawer);
            }
        });

        final Intent GoAge = new Intent(getApplicationContext(), AgeActivity.class);

        headBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoicedSymptomSlide bottomSheet = new ChoicedSymptomSlide();
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");
            }
        });

        neckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");

                GoAge.putExtra("part", neck.getText().toString());
                startActivity(GoAge);

            }
        });

        stomachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");


                GoAge.putExtra("part", stomach.getText().toString());
                startActivity(GoAge);
            }
        });

        armBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");


                GoAge.putExtra("part", arm.getText().toString());
                startActivity(GoAge);
            }
        });

        legBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");


                GoAge.putExtra("part", leg.getText().toString());
                startActivity(GoAge);
            }
        });

        musclePainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");


                GoAge.putExtra("part", muscle_pain.getText().toString());
                startActivity(GoAge);
            }
        });

        burnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");


                GoAge.putExtra("part", burn.getText().toString());
                startActivity(GoAge);
            }
        });

        woundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");


                GoAge.putExtra("part", wound.getText().toString());
                startActivity(GoAge);
            }
        });

        beerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChoicedSymptomSlide bottomSheet=new ChoicedSymptomSlide();
                bottomSheet.show(getSupportFragmentManager(), "ChoicedSymptomSlide");


                GoAge.putExtra("part", hangover.getText().toString());
                startActivity(GoAge);
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
                Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }

    }


}