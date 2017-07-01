package com.example.max.esttaxi;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String url = "http://test.www.estaxi.ru/route.txt";
    ParseJsone  parsingJsonObject;
    LinkedList<Point> jsonPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void onClickDownload(View view) throws ExecutionException, InterruptedException {
        Toast.makeText(MapsActivity.this, getString(R.string.start_loading), Toast.LENGTH_LONG).show();
        if (parsingJsonObject == null) {
            parsingJsonObject = new ParseJsone(this);
            parsingJsonObject.execute(url);
            jsonPoint = parsingJsonObject.get();
        }
            addPoints(jsonPoint);
    }
    private void addPoints(LinkedList<Point> jsonPoint) {
        PolylineOptions polylineOptions = new PolylineOptions().color(Color.GREEN).width(5);
        for (Point point :jsonPoint){
            polylineOptions.add(new LatLng(point.getmXPoint(), point.getmYPoint()));
           }
            if (mMap!=null) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(jsonPoint.getFirst().getmXPoint(), jsonPoint.getFirst().getmYPoint()))
                        .title("Begin")
                        .draggable(false)
                        );
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(jsonPoint.getLast().getmXPoint(), jsonPoint.getLast().getmYPoint()))
                        .title("End")
                        .draggable(false)
                );
            }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                   .target(new LatLng(jsonPoint.getLast().getmXPoint(), jsonPoint.getLast().getmYPoint()))
                    .zoom(15)
                    .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cameraUpdate);
                mMap.addPolyline(polylineOptions);
        }
}
