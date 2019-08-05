package  com.example.varunsai.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.example.varunsai.myapplication.dummy.DummyContent;
import com.example.varunsai.myapplication.dummy.DummyItem;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 10000;
    public static SharedPreferences prefs;
    static int c=-1;
    DatabaseReference reference;
    Uri url1=null;
    static ArrayList<DummyItem> items=new ArrayList<DummyItem>();
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
    static  int count=0;
    static boolean check=false;
    StorageReference mStorageRef;
    DatabaseReference db;
    String previously;
    StorageReference reference1;
    private FirebaseOptions firebaseOptions1,firebaseOptions2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
     //FirebaseApp.initializeApp(getApplicationContext());
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //FirebaseApp customApp = FirebaseApp.initializeApp(this);
       // FirebaseStorage storage = FirebaseStorage.getInstance(customApp);
    //    reference1= FirebaseStorage.getInstance("gs://doctorlog-ac4d6.appspot.com").getReference();
       // FirebaseStorage firebaseStorage1=FirebaseStorage.getInstance(app);
        //reference1=firebaseStorage1.getReference();
          firebaseOptions2=new FirebaseOptions.Builder()
                .setApplicationId("1:720142389475:android:920875d8334fb1c3")
                .setApiKey("AIzaSyCTT78tupL7oOhSkIeHgNN6hyLWgxWMcs8 ")
                .setDatabaseUrl("https://doctorlog-ac4d6.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(this,firebaseOptions2,"third");
        FirebaseApp app1=FirebaseApp.getInstance("third");
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance(app1);
         reference=firebaseDatabase.getReference("doctor");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Uri uri = null;
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                boolean previouslyStarted = prefs.getBoolean("isFirstRun", false);
                read1(new Firebasecallback1() {
                    @Override
                    public void onCallback1(List<DummyItem> list) {

                        Toast.makeText(splash.this,"completed" ,Toast.LENGTH_LONG ).show();
                    }

                });

                if(!previouslyStarted) {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("isFirstRun", Boolean.TRUE);
                    edit.commit();
                    Intent mainIntent = new Intent(splash.this,LoginActivity.class);
                    splash.this.startActivity(mainIntent);
                    splash.this.finish();
                }

                else
                    {     db= FirebaseDatabase.getInstance().getReference("patient");
                        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        previously=prefs1.getString("key","null" );
                        Toast.makeText(getBaseContext(),"my "+previously ,Toast.LENGTH_LONG ).show();
                        read(new LoginActivity.Firebasecallback() {
                            @Override
                            public void onCallback(List<user> list) {
                                // Toast.makeText(getActivity(), "msg12",Toast.LENGTH_LONG).show();
                                for (user e:list)
                                {          // Toast.makeText(LoginActivity.this, "list",Toast.LENGTH_LONG).show();
                                    if(previously.equals(e.id))
                                    {   Toast.makeText(splash.this, e.url,Toast.LENGTH_LONG).show();
                                        LoginActivity.userg=new user(e.id,e.username,e.mail,e.url,e.phn,e.lat,e.lng);
                                        LoginActivity.usname=e.username;
                                        LoginActivity.name=e.mail;
                                        if(e.url==null)
                                            LoginActivity.url=null;
                                        else
                                            LoginActivity.url=Uri.parse(e.url);
                                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                        SharedPreferences.Editor edit = preferences.edit();
                                        edit.putString("key",e.id);
                                        edit.commit();
                                        break;

                                    }
                                }

                                check=true;
                                Intent mainIntent = new Intent(splash.this, MainActivity.class);
                                splash.this.startActivity(mainIntent);
                                splash.this.finish();
                              //  Toast.makeText(splash.this, "mmmm", Toast.LENGTH_SHORT).show();
                                //LoginActivity.usname=null;


                            }

                        });
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
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
                        Toast.makeText(splash.this, "msg",Toast.LENGTH_LONG).show();
                        String key=snapshot.getKey();
                        String mail = (String) snapshot.child("mail").getValue();
                        String url = (String) snapshot.child("url").getValue();
                        String phn=(String )snapshot.child("phn").getValue();
                        String lat=(String )snapshot.child("latitude").getValue();
                        String lng=(String )snapshot.child("longitude").getValue();
                         //Toast.makeText(splash.this, url,Toast.LENGTH_LONG).show();
                        String name = (String) snapshot.child("username").getValue();
                        Toast.makeText(splash.this,"mmm" +key,Toast.LENGTH_LONG).show();
                        user user1 = new user(key,name, mail, url,phn,lat,lng);
                        list.add(user1);

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


    public void read1(final Firebasecallback1 firebasecallback){
        final List<DummyItem> list=new ArrayList<DummyItem>();
        list.clear();
        Toast.makeText(splash.this, "msg3",Toast.LENGTH_LONG).show();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot==null){

                    Toast.makeText(splash.this, "msg10",Toast.LENGTH_LONG).show();

                }
                else {
                    //  Toast.makeText(LoginActivity.this, "msg18",Toast.LENGTH_LONG).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                      //  Toast.makeText(splash.this, "msg", Toast.LENGTH_LONG).show();
                        String keys = snapshot.getKey();
                        //Toast.makeText(splash.this, keys, Toast.LENGTH_LONG).show();
                        String mail = (String) snapshot.child("mail").getValue();
                        String name = (String) snapshot.child("fname").getValue();
                        String spec = (String) snapshot.child("specialisation").getValue();
                        String exp = (String) snapshot.child("experience").getValue();
                        String urls=(String )snapshot.child("imageurl").getValue();
                        String degree = (String) snapshot.child("degree").getValue();
                        ArrayList<clinic> clinics = new ArrayList<>();
                        for (DataSnapshot dp : snapshot.child("clinics").getChildren()) {
                            String ckey=dp.getKey();
                            String address = (String) dp.child("address").getValue();
                            String lat = (String) dp.child("latitude").getValue();
                            String lng = (String) dp.child("longitude").getValue();
                            String clinicname = (String) dp.child("clinicname").getValue();
                            String fee = (String) dp.child("fee").getValue();
                            String clinicphn = (String) dp.child("clinicphn").getValue();
                            String starttime = (String) dp.child("starttime").getValue();
                            String endtime = (String) dp.child("endtime").getValue();
                            String avgtime=(String)dp.child("avgtime").getValue();
                            String maxpatient=(String)dp.child("maxpatient").getValue();
                            ArrayList<String> timings = new ArrayList<>();
                            String pattern = "HH:mm";
                            String pattern1="hh:mma";
                            Date date=null;
                            Date date1=null;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
                            try {
                                date= simpleDateFormat.parse(starttime);
                                date1=simpleDateFormat.parse(endtime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            timings.add(simpleDateFormat1.format(date).toString()+" "+"to"+" "+simpleDateFormat1.format(date1).toString());
                            ArrayList<String> days = new ArrayList<>();
                            for (DataSnapshot da : dp.child("workdays").getChildren()) {
                                days.add(da.getKey());
                                //Toast.makeText(splash.this, String.valueOf(da), Toast.LENGTH_LONG).show();
                            }
                           //  Toast.makeText(splash.this, clinicname, Toast.LENGTH_LONG).show();
                            clinics.add(new clinic(ckey,clinicname, fee, timings, address, lat, lng, clinicphn,maxpatient,avgtime));

                        }

                        if(urls.equals("null"))
                             url1=null;
                        else
                            url1=Uri.parse(urls);
                        DummyItem dm= new DummyItem(keys,name, mail,spec,exp,degree,clinics,url1);
                        list.add(dm);
                       // Toast.makeText(splash.this, dm.name, Toast.LENGTH_LONG).show();
                        items.add(dm);
                        ITEM_MAP.put(dm.name,dm);
                      // DummyContent.addItem(dm);
                         //Toast.makeText(splash.this, DummyContent.COUNT,Toast.LENGTH_LONG).show();

                    }

                }

                // Toast.makeText(LoginActivity.this, "msg4",Toast.LENGTH_LONG).show();
                firebasecallback.onCallback1(list);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface Firebasecallback1 {
        void onCallback1(List<DummyItem> list);
    }


}