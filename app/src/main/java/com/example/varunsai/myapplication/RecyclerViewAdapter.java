package com.example.varunsai.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.client.result.BookmarkDoCoMoResultParser;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    SupportMapFragment mapFragment;
    FragmentManager manager;
    private GoogleMap mMap;
    List<Details> MainImageUploadInfoList;
    private double lat,lng;
    public RecyclerViewAdapter(Context context, List<Details> TempList) {

        this.MainImageUploadInfoList = TempList;
        // this.manager=manager;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Details details = MainImageUploadInfoList.get(position);
        holder.pname.setText("Patient: "+details.pname);
        holder.docname1.setText("Dr."+details.docname);
        holder.clinicname.setText("clinic: "+details.clinicname);
        holder.apdate.setText("Date:"+details.apdate);
        holder.token.setText("Token no: "+details.tokenno);
        holder.esttime.setText("Estimated time: "+details.esttime);
        holder.status.setText("Status: "+details.status);
        lat=Double.parseDouble(details.lat);
        lng=Double.parseDouble(details.lng);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FF5722"));
                Intent intent=new Intent(context, bookmap.class);
                intent.putExtra("lat",lat );
                intent.putExtra("lng",lng );
                context.startActivity(intent);
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

            }
        });
     /*  if(mapFragment==null)
        {
            FragmentManager fragmentManager = manager;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            mapFragment=SupportMapFragment.newInstance();
            transaction.replace(R.id.map,mapFragment ).commit();
        }

        mapFragment.getMapAsync(this);
        FragmentManager fragmentManager =manager;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mapFragment=SupportMapFragment.newInstance();
        transaction.replace(R.id.map,mapFragment ).commit();
        mapFragment.getMapAsync(this);
        //  mapFragment = (SupportMapFragment)holderfindFragmentById(R.id.map);   */
     //  holder.mapview.onCreate(null);
       //holder.mapview.getMapAsync(this);

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }
/*
@Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("address"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),16));
    }
         */

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pname,docname1,clinicname,apdate,token,esttime,status;
       // MapView mapview;
        public ViewHolder(View itemView) {
            super(itemView);
            pname = (TextView) itemView.findViewById(R.id.pname);
            docname1 = (TextView) itemView.findViewById(R.id.docname);
            clinicname= (TextView) itemView.findViewById(R.id.clinicname);
            apdate=(TextView)itemView.findViewById(R.id.apdate);
            token=(TextView)itemView.findViewById(R.id.token);
            esttime=(TextView)itemView.findViewById(R.id.esttime);
             status=(TextView)itemView.findViewById(R.id.status);
          // mapview=(MapView)itemView.findViewById(R.id.mapview);
           /*  mapFragment = (SupportMapFragment)
                  .findFragmentById(R.id.map3);
          //  mapFragment = (SupportMapFragment)itemView.fin(R.id.map); */

        }
    }
}
