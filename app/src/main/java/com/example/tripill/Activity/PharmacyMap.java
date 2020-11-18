package com.example.tripill.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripill.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

import static com.example.tripill.Props.FASTEST_UPDATE_INTERVAL_MS;
import static com.example.tripill.Props.GPS_ENABLE_REQUEST_CODE;
import static com.example.tripill.Props.PERMISSIONS_REQUEST_CODE;
import static com.example.tripill.Props.UPDATE_INTERVAL_MS;

public class PharmacyMap extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, PlacesListener {

    private GoogleMap mMap;
    private Marker lastClicked;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private View mLayout;  // Snackbar
    List<Marker> previous_marker=null;

    String[] REQUIRED_PERMISSIONS={Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소

    LatLng currentPosition;
    Location location;
    String markerSnippet;
    ImageView gpsBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_pharmacy_map);

        mLayout=findViewById(R.id.layout_main);

        previous_marker=new ArrayList<Marker>();

        locationRequest=new LocationRequest()
                .setInterval(UPDATE_INTERVAL_MS)  //위치가 업데이트 되는 주기
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)  //위치 획득 후 업데이트 되는 주기
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


        LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        mFusedLocationClient=LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        RelativeLayout head=findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        LinearLayout bottom_sheet = findViewById(R.id.bottom_sheet);
        bottom_sheet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });




    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap=googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false); //기본 gps버튼

        LinearLayout bottomsheet=findViewById(R.id.bottom_sheet);

        setDefaultLocation();

        gpsBtn=findViewById(R.id.gpsBtn);
        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition != null){
                    CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(currentPosition, 17);
                    mMap.moveCamera(cameraUpdate);
                    showPlaceInformation(currentPosition);
                    lastClicked = null;
                    bottomsheet.setVisibility(View.GONE);
                }else {
                    gpsBtn.setSelected(false);
                }

            }
        });



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                if (lastClicked != null) {
                    lastClicked.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker));
                }
                lastClicked=null;

                bottomsheet.setVisibility(View.INVISIBLE);

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (lastClicked != null) {
                    lastClicked.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker));
                }
                lastClicked=marker;
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_choice));

                bottomsheet.setVisibility(View.VISIBLE);

                TextView pharmacyName_kr=findViewById(R.id.pharmacyName_kr);
                TextView pharmacyName_en=findViewById(R.id.pharmacyName_en);
                TextView findroadBtn=findViewById(R.id.findroadBtn);

                pharmacyName_kr.setText(marker.getTitle());
                pharmacyName_en.setText(R.string.pharmacy);


                findroadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LatLng marker_position = marker.getPosition();
                        getCurrentAddress(marker_position);
                        double markerlat = marker_position.latitude;
                        double markerlong = marker_position.longitude;

                        String url = "nmap://route/walk?dlat="+markerlat+"&dlng="+markerlong+"&dname="+marker.getTitle()+"&appname=com.example.tripill";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                        if (list == null || list.isEmpty()) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")));
                        } else {
                            startActivity(intent);
                        }
                    }
                });

                return true;
            }

        });


    }


    public void setDefaultLocation() {

        int hasFineLocationPermission=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            startLocationUpdates(); // 위치 업데이트 시작

        } else {

            LatLng DEFAULT_LOCATION=new LatLng(37.566614, 126.977919);
            CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
            mMap.moveCamera(cameraUpdate);

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                //완전 맨 처음 거부시
                Snackbar.make(mLayout, R.string.snackbar_body,
                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(PharmacyMap.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                    }
                }).show();
            } else {
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }


    }  //지도 default 위치


    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if ( check_result ) {
                //모든 퍼미션을 허용했다면 위치 업데이트를 시작
                startLocationUpdates();
            }
            else {
                if (
                        ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    // 거부 한 적이 있고 또 거부
                    Snackbar.make(mLayout, getString(R.string.preference_none),
                            Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions( PharmacyMap.this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);
                        }
                    }).show();

                }else {
                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우
                    Snackbar.make(mLayout, getString(R.string.preference_none_setting),
                            Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent= new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                            startActivityForResult(intent,0);
                        }
                    }).show();
                }
            }

        }
    } //한번 거부 후 위치권한 다이얼로그

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (!checkLocationServicesStatus()) {
                    Intent callGPSSettingIntent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
                    if (checkLocationServicesStatus()) {
                        return;
                    }
                }
                break;
        }
    }//gps 네트워크




    LocationCallback locationCallback=new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {   //요청한 결과
            super.onLocationResult(locationResult);

            List<Location> locationList=locationResult.getLocations();

            if (locationList.size() > 0) {
                location=locationList.get(locationList.size() - 1);
//                location = locationList.get(0);

                currentPosition=new LatLng(location.getLatitude(), location.getLongitude());

                location=location;

                CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(currentPosition, 15);
                mMap.moveCamera(cameraUpdate);
                mFusedLocationClient.removeLocationUpdates(this);

            }


        }

    };


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }   //gps, 네트워크 여부 boolean


    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

        } else {

            if (true){
                mMap.setMyLocationEnabled(true);
            }
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());  //콜백한테 요청만함 //비동기

        }

    }  //사용자위치 업데이트



    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, R.string.fail_Address, Toast.LENGTH_LONG).show();
            return "'주소 미발견'";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, R.string.fail_location_network, Toast.LENGTH_LONG).show();
            return "'주소 미발견'";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, R.string.fail_location_gps, Toast.LENGTH_LONG).show();
            return "'주소 미발견'";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }  //지오코더

    @Override
    public void onPlacesSuccess(final List<Place> places) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (noman.googleplaces.Place place : places) {



                    LatLng latLng_place = new LatLng(place.getLatitude(), place.getLongitude());

                    markerSnippet = getCurrentAddress(latLng_place);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng_place);
                    markerOptions.title(place.getName());
                    markerOptions.snippet(markerSnippet);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker));
                    Marker item = mMap.addMarker(markerOptions);
                    previous_marker.add(item);

                }
                //중복 마커 제거
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(previous_marker);
                previous_marker.clear();
                previous_marker.addAll(hashSet);

            }
        });

    }  //약국 place

    public void showPlaceInformation(LatLng location)  //약국 api
    {
        mMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(PharmacyMap.this)
                .key("AIzaSyDKS1jSRPdpY014AguQ5ORacQQLc4e3nyo")
                .latlng(location.latitude, location.longitude)//현재 위치
                .radius(1000) //1키로 내에서 검색
                .type(PlaceType.PHARMACY) //약국
                .build()
                .execute();

    }


    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

    }


    @Override
    public void onPlacesFinished() {

    }

}
    
    