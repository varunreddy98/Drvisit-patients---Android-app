package com.example.varunsai.myapplication;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

import static com.example.varunsai.myapplication.Clientservice.pname;

public class ongoing extends AppCompatActivity implements OnMapReadyCallback {
     static TextView pname1,docname1,clinicname1,apdate1,token1,esttime1,current_token;
     double lat,lng;
    static boolean on=false;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        on=true;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       Intent intent=getIntent();
        String appdate=intent.getStringExtra("app_date");
        int token=intent.getExtras().getInt("token");
        String pname=intent.getStringExtra("pname");
        String clinicname=intent.getStringExtra("clinicname");
        String docname=intent.getStringExtra("docname");
        int hours=intent.getExtras().getInt("hours");
        int mins=intent.getExtras().getInt("mins");
        lat=intent.getDoubleExtra("lat",-34);
        lng=intent.getDoubleExtra("lng",15 );
        pname1 = (TextView) findViewById(R.id.pname);
        docname1 = (TextView) findViewById(R.id.docname);
        clinicname1= (TextView)findViewById(R.id.clinicname);
        apdate1=(TextView)findViewById(R.id.apdate);
        token1=(TextView)findViewById(R.id.token);
        esttime1=(TextView)findViewById(R.id.esttime);
        current_token=(TextView)findViewById(R.id.currenttoken);
        pname1.setText("Patient: "+pname);
        docname1.setText("Dr."+docname);
        clinicname1.setText("clinic: "+clinicname);
        apdate1.setText("Date:"+appdate);
        token1.setText("Token no: "+token);
        esttime1.setText("Estimated time: "+hours+":"+mins);
        if(token==0)
             current_token.setText("Current Token: "+" Appointments not yet started");
        else

            current_token.setText("current Token: "+token);
       // lat= Clientservice.lat;
        //lng=Clientservice.lng;
        //lat=;
        //lng=33.7;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Clinic"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 16));

    }

   @Override
    protected void onStop() {
        super.onStop();
        on=false;
    }

    @Override
    protected void onDestroy() {
        on=false;
        super.onDestroy();
    }
}
