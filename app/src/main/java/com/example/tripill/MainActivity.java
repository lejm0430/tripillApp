package com.example.tripill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "푸쉬 테스트_yj", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"asdf",Toast.LENGTH_SHORT);
    }
}