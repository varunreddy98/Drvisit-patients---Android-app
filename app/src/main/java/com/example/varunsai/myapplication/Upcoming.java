package com.example.varunsai.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureGroupInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.List;

public class Upcoming extends AppCompatActivity {


    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<Details> list = new ArrayList<>();

    RecyclerView recyclerView;
    String type;

    RecyclerView.Adapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
       type= getIntent().getStringExtra("type");
        Toast.makeText(Upcoming.this, type,Toast.LENGTH_LONG ).show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Upcoming.this));

        progressDialog = new ProgressDialog(Upcoming.this);

        progressDialog.setMessage("Loading");

        progressDialog.show();
        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String previously=prefs1.getString("key","null" );
      //  Toast.makeText(Upcoming.this, previously,Toast.LENGTH_LONG ).show();
        //progressDialog.dismiss();
      DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(previously).child("bookdate");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()) {
                   // Toast.makeText(Upcoming.this,"lll",Toast.LENGTH_LONG ).show();
                           String type1= (String) ds.child("type").getValue();
                    //Toast.makeText(Upcoming.this, type1, Toast.LENGTH_LONG).show();
                    //Toast.makeText(Upcoming.this, type, Toast.LENGTH_LONG).show();
                   if(type1.equals(type)) {
                               String pname = (String) ds.child("pfname").getValue();
                               String docname = (String) ds.child("docname").getValue();
                               String apdate = (String) ds.child("appdate").getValue();
                               String esttime = (String) ds.child("estimatedtime").getValue();
                               String lat = (String) ds.child("doclat").getValue();
                               String lng = (String) ds.child("doclng").getValue();
                                String token = (String) ds.child("tokenno").getValue();
                               String clinicname = (String) ds.child("clinicname").getValue();
                               String status=(String )ds.child("status").getValue();
                               Details details = new Details(pname, docname, apdate, esttime, lat, lng, token, clinicname,status);
                               list.add(details);
                              // Toast.makeText(Upcoming.this, "aaa333", Toast.LENGTH_LONG).show();
                               Toast.makeText(Upcoming.this, pname, Toast.LENGTH_LONG).show();
                      }

                }
           adapter=new RecyclerViewAdapter(Upcoming.this,list);
                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });   

    }
}