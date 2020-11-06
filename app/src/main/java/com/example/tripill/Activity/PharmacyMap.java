package com.example.tripill.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.tripill.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class PharmacyMap extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_CODE=100;  //권환 허용 코드
    private static final String[] PERMISSION={
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource LocationSource;
    private NaverMap mNaverMap;
    private GpsTracker gpsTracker;

    double mlatitude;
    double mlongitude;
//    locationListener locationListener1 = new locationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_map);

        ImageView gpsBtn=findViewById(R.id.gpsBtn);
        RelativeLayout head=findViewById(R.id.head);

        FragmentManager fm=getSupportFragmentManager();
        MapFragment mapFragment=(MapFragment) fm.findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment=MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        LocationSource=new FusedLocationSource(this, PERMISSION_REQUEST_CODE);   //위치를 받아오는 구현체

        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker = new GpsTracker(PharmacyMap.this);

                if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){  //권환 획득 여부
                /*if(ActivityCompat.shouldShowRequestPermissionRationale(PharmacyMap.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(PharmacyMap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

                }*/
                    mlongitude=gpsTracker.getLongitude();
                    mlatitude = gpsTracker.getLatitude();

                    CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(
                            new LatLng(mlatitude, mlongitude),15)
                            .animate(CameraAnimation.Fly, 3000);

                    mNaverMap.moveCamera(cameraUpdate);

                    Log.d("makerlongitude"," : " +mlatitude);
                    Log.d("makerlatitude"," : " +mlongitude);//위치 좌표

                   /* Marker marker = new Marker();  //마커 생성
                    marker.setIcon(OverlayImage.fromResource(R.drawable.ic_my_marker));

                    marker.setWidth(Marker.SIZE_AUTO);  //마커 사이즈
                    marker.setHeight(Marker.SIZE_AUTO);
                    marker.setFlat(false);
                    marker.setIconPerspectiveEnabled(false);

                    marker.setPosition(new LatLng(mlatitude, mlongitude));


                    marker.setMap(mNaverMap);  //마커 지도에 표시*/

                }

            }

        });





                
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PharmacyMap.this, PillRecommendActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public void onProviderEnabled(String provider) {  //O

    }

    public void onProviderDisabled(String provider) { //X

    }
    
    
    
    
    
    

    /*public void getCurrentLocation() {

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



        if ( ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions( PharmacyMap.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    PERMISSION_REQUEST_CODE );  //권한 안되어있으면 해라
        }
        else{
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);   //가장 최근에 받은 위치 -> 리스너 한번 실행 후(안하면 널값)
            if(location != null){
//                String provider = location.getProvider();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                *//*Log.d("longitude"," : " +location.getLongitude());
                Log.d("latitude"," : " +location.getLatitude());*//*

            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    500,
                    1,
                    locationListener1);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    500,
                    1,
                    locationListener1);
        }


    }
    
    
    
    
    
    
    

    private class locationListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {


            Marker marker = new Marker();  //마커 생성
            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_my_marker));

            marker.setWidth(Marker.SIZE_AUTO);  //마커 사이즈
            marker.setHeight(Marker.SIZE_AUTO);

                    marker.setMap(mNaverMap);  //마커 지도에 표시
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){  //권환 획득 여부
                *//*if(ActivityCompat.shouldShowRequestPermissionRationale(PharmacyMap.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(PharmacyMap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

                }*//*
                double longitude=location.getLongitude();
                double latitude=location.getLatitude();

                marker.setPosition(new LatLng(latitude, longitude));   //위치 좌표

                Log.d("longitude"," : " +location.getLongitude());
                Log.d("latitude"," : " +location.getLatitude());



            }else {
            }



        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {

        }

        public void onProviderDisabled(String provider) {

        }
    }
*/

    

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        mNaverMap = naverMap;
        mNaverMap.setLocationSource(LocationSource);
        mNaverMap.setMaxZoom(18.0);  //최대


        ActivityCompat.requestPermissions(this,PERMISSION,PERMISSION_REQUEST_CODE);  // 권한확인. 결과는 onRequestPermissionsResult 콜백 매서드 호출

        /*Marker marker = new Marker();  //마커 생성
        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_my_marker));

        marker.setWidth(Marker.SIZE_AUTO);  //마커 사이즈
        marker.setHeight(Marker.SIZE_AUTO);

        marker.setPosition(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));   //위치 좌표
          //마커 지도에 표시*/

        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(false); // 기본값 : true
        uiSettings.setScaleBarEnabled(false); // 기본값 : true
        uiSettings.setZoomControlEnabled(false); // 기본값 : true
        uiSettings.setLogoClickEnabled(false);
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ///권환 획득 여부 확인
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        /*if(requestCode == PERMISSION_REQUEST_CODE){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){  //권환 획득 여부

            }else{

            }

        }*/


        if (LocationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            return;
        }






    }


}

