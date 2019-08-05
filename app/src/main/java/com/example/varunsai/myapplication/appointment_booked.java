package com.example.varunsai.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.strictmode.CleartextNetworkViolation;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class appointment_booked extends AppCompatActivity {
      DatabaseReference db;
      String previously;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booked);
        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        previously=prefs1.getString("key","null" );
        Button b1=(Button)findViewById(R.id.b1);
        Button b2=(Button)findViewById(R.id.b2);
        Button b3=(Button)findViewById(R.id.b3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(appointment_booked.this,Upcoming.class);
                intent.putExtra("type", "upcoming");
                startActivity(intent);
             //   Toast.makeText(appointment_booked.this, "1",Toast.LENGTH_LONG ).show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Clientservice.chceksevice==true)
                {    Toast.makeText(appointment_booked.this, "success-2",Toast.LENGTH_LONG ).show();
                    Intent intent=new Intent(appointment_booked.this,ongoing.class);
                   /* intent.putExtra("docname", Clientservice.docname);
                    intent.putExtra("app_date",Clientservice.appdate);
                    intent.putExtra("token",Clientservice.i);
                    intent.putExtra("hours",Clientservice.hours);
                    intent.putExtra("mins",Clientservice.mins);
                    intent.putExtra("pname", Clientservice.pname);
                    intent.putExtra("clinicname", Clientservice.clinicname);
                    intent.putExtra("lat",Double.parseDouble(Clientservice.lat) );
                    intent.putExtra("lng",Double.parseDouble(Clientservice.lng) ); */
                    startActivity(intent);

                }
                else {
                    Toast.makeText(appointment_booked.this, "2", Toast.LENGTH_LONG).show();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(appointment_booked.this,Upcoming.class);
                intent.putExtra("type", "past");
                startActivity(intent);
                Toast.makeText(appointment_booked.this, "3",Toast.LENGTH_LONG ).show();
            }
        });
        db= FirebaseDatabase.getInstance().getReference("patient").child(previously);
        read(new LoginActivity.Firebasecallback() {
            @Override
            public void onCallback(List<user> list) {
                // Toast.makeText(getActivity(), "msg12",Toast.LENGTH_LONG).show();
                for (user e:list)
                {          // Toast.makeText(LoginActivity.this, "list",Toast.LENGTH_LONG).show();
                    if(previously.equals(e.id))
                    {      //  Toast.makeText(LoginActivity.this, "msg5",Toast.LENGTH_LONG).show();

                        break;

                    }
                }
                

            }

        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(appointment_booked.this,MainActivity.class));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void read(final LoginActivity.Firebasecallback firebasecallback){
        final List<user> list=new ArrayList<user>();
        list.clear();
        // Toast.makeText(LoginActivity.this, "msg3",Toast.LENGTH_LONG).show();
        FirebaseDatabase.getInstance().getReference("patient").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot==null){

                    //Toast.makeText(LoginActivity.this, "msg10",Toast.LENGTH_LONG).show();

                }
                else {
                    //  Toast.makeText(LoginActivity.this, "msg18",Toast.LENGTH_LONG).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Toast.makeText(appointment_booked.this, "msg",Toast.LENGTH_LONG).show();
                        String key=snapshot.getKey();
                        String mail = (String) snapshot.child("mail").getValue();
                        String url = (String) snapshot.child("url").getValue();
                        String name = (String) snapshot.child("username").getValue();
                        user user1 = new user(key,name, mail, url);
                        list.add(user1);
                        // Toast.makeText(appointment_booked.this, "msg20",Toast.LENGTH_LONG).show();

                    }

                }

                // Toast.makeText(LoginActivity.this, "msg4",Toast.LENGTH_LONG).show();
                firebasecallback.onCallback(list);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
