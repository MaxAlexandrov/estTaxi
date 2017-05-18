package com.example.max.esttaxi;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity  implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    GoogleMap map;
    final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"___________Создание приложения");
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Log.d(TAG,"___________Создание map fragment"+mapFragment.toString());
        mapFragment.getMapAsync(this);
        init();
    }
    private void init() {
        Log.d(TAG,"___________Вызвана функция init()");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"__________Начало установки маркера");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
        Log.d(TAG,"___________Маркер установлен");
    }

    public void onClickDownload(View view) {
        Toast.makeText(MainActivity.this,"Загрузка координат",Toast.LENGTH_LONG).show();
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}
