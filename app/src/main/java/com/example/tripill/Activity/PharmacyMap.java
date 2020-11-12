package com.example.tripill.Activity;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tripill.DataBase.pharmacyList;
import com.example.tripill.Dialog.NaverMapBottomSheet;
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

public class PharmacyMap extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, PlacesListener {
    private GoogleMap mMap;
    private Marker currentMarker = null;
    private Marker lastClicked;

    List<Marker> previous_marker = null;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소

    LatLng currentPosition;
    Location location;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;

    private View mLayout;  // Snackbar 사용하기 위해서는 View

    String markerSnippet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_pharmacy_map);

        mLayout = findViewById(R.id.layout_main);

        previous_marker = new ArrayList<Marker>();

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        RelativeLayout head = findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                }
        });
    }




    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        setDefaultLocation();

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) { //퍼미션 허용 O

            startLocationUpdates(); // 위치 업데이트 시작

        }else {  //퍼미션 허용x


            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                Snackbar.make(mLayout, R.string.snackbar_body,
                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions( PharmacyMap.this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);
                    }
                }).show();
            } else {
                ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);
            }

        }

        ImageView gpsBtn = findViewById(R.id.gpsBtn);
        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentPosition, 17);
                mMap.moveCamera(cameraUpdate);
                showPlaceInformation(currentPosition);
            }
        });

        mMap.getUiSettings().setMyLocationButtonEnabled(false); //gps버튼


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                if(lastClicked != null){
                    lastClicked.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker));
                }
                lastClicked = null;
            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        NaverMapBottomSheet naverMapBottomSheet= new NaverMapBottomSheet();

                if(lastClicked != null){
                    lastClicked.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker));
                }
                lastClicked = marker;


                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_choice));
                naverMapBottomSheet.name = marker.getTitle();
                naverMapBottomSheet.Snippet = marker.getSnippet();

                View include = findViewById(R.id.include);

                include.setVisibility(View.VISIBLE);


//                naverMapBottomSheet.show(getSupportFragmentManager(),"naverMapBottomSheet");
                return true;
            }

        });





    }



    public void setDefaultLocation() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ){

        }else{
            LatLng DEFAULT_LOCATION = new LatLng(37.566614, 126.977919);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 17);
            mMap.moveCamera(cameraUpdate);
        }


    }




    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

                location = location;


            }


        }

    };



    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

        }else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);



            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {

                return;
            }
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            if (checkPermission())
                mMap.setMyLocationEnabled(true);

        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
            return true;
        }

        return false;

    }

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
            Toast.makeText(this, R.string.no_gocode, Toast.LENGTH_LONG).show();
            return String.valueOf(R.string.no_gocode);
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, R.string.Invalid_GPS, Toast.LENGTH_LONG).show();
            return String.valueOf(R.string.Invalid_GPS);
        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, R.string.cant_find_address, Toast.LENGTH_LONG).show();
            return String.valueOf(R.string.cant_find_address);

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }




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

                // 퍼미션을 허용했다면 위치 업데이트를 시작
                startLocationUpdates();
            }
            else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                            Snackbar.make(mLayout, getString(R.string.preference_none),
                            Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions( PharmacyMap.this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);
//                            finish();
                        }
                    }).show();

                }else {
                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우
                    Snackbar.make(mLayout, getString(R.string.preference_none_setting),
                            Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent= new Intent(Settings.ACTION_SETTINGS);
                            startActivityForResult(intent,0);
                        }
                    }).show();
                }
            }

        }
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (!checkLocationServicesStatus()) {
                    Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
                    if (checkLocationServicesStatus()) {

                        return;
                    }
                }
                break;
        }
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

    }

    public void showPlaceInformation(LatLng location)
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

}
    
    