package com.example.tripill.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.tripill.R;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;

import androidx.annotation.NonNull;

public class PharmacyMap extends Activity implements OnMapReadyCallback {

    private MapView navermap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

        navermap = findViewById(R.id.navermap);
        navermap.onCreate(savedInstanceState);

        naverMapBasicSettings();

    }

    public void naverMapBasicSettings() {
        navermap.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(false);

        naverMap.setMapType(NaverMap.MapType.Basic);  //Satellite - 지도 어떻게표현
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}