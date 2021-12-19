package com.teamhawks.googlemapsdirections;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Main_Activity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    Button btnGetDirection;
    MarkerOptions place1, place2;
    Polyline currentPolyline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetDirection = findViewById(R.id.btnGetDirection);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfrag);
        mapFragment.getMapAsync(this);

        place1= new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
        place2= new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");

        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getUrl(place1.getPosition(), place2.getPosition(), "driving");
                new FetchUrl(Main_Activity.this).execute(url, "driving");
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;
        map.addMarker(place1);
        map.addMarker(place2);
    }

    private  String getUrl(LatLng origin, LatLng dest, String directionMode){
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode="+ directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?"+ parameters + "&key="+ "AIzaSyAp4xN4TlG6rWMCQgg0hnDW4j-MTADUZNU";
        return url;

    }

    @Override
    public void onTaskDone(Object... values){
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline=map.addPolyline((PolylineOptions) values[0]);
    }

}

//youtube video used: https://www.youtube.com/watch?v=wRDLjUK8nyU
