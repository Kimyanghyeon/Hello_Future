package com.example.hello_future;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    BottomNavigationView navigation;
    private GoogleMap mMap;

    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch(item.getItemId()){
                    case R.id.menu_item1:
                        intent = new Intent(MapActivity.this,LocActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_item2:

                        break;

                }//end of switch

                return true;
            }//end of OnNavigationItemSelect
        });//ed of setOnNavigationItem listener

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude(); // 위도
                longitude = location.getLongitude(); // 경도

                //현재 위치를 받아서 내 위치 표시하기
                //에뮬레이터 현재 위치는 미국 바다..
            }//end of onLocationChanged

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }//end of onStatusChanged

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }//end of onProviderEnabled

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }//end of onProviderDisabled

        };//end of listener
//
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//
//        }//end of if
//
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,100,locationListener);

    }//end of onCreate


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        try {
            KmlLayer layer = new KmlLayer(mMap,R.raw.nokidsdata,MapActivity.this);
            layer.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LatLng SEOUL = new LatLng(latitude, longitude); //내 위치 위도 경

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("내 위치");
        markerOptions.snippet("현재 위치");
        mMap.addMarker(markerOptions);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10));
    }//end of map

}//end of class