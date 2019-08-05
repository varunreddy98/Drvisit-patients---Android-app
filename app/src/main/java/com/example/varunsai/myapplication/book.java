package com.example.varunsai.myapplication;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.app.DialogFragment;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.varunsai.myapplication.dummy.DummyItem;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.varunsai.myapplication.ItemDetailFragment.ARG_ITEM_ID;

public class book extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    Button b1;
    int cou=1;
    Date date;
    FirebaseDatabase firebaseDatabase;
    String reportDate,reportDate1;
    boolean check=false;
    SimpleDateFormat simpleDateFormat1;
    FirebaseOptions firebaseOptions;
    Spinner spec;
    RadioButton r1, r2;
    String name;
    String appdate;
    int age;
    int id;
    String gender;
    Date d1,d2;
    DatabaseReference ref;
    String previously;
    static String docname1,docexp1,docspec1;
    DatabaseReference db;
    DatabaseReference references,reff=null;
    EditText txt, edt, edt1,cause,txt1;
    String des[] = {"Cardiologist", "Dentist", "General Physician"};
    private int mYear, mMonth, mDay;
    private int mYear1, mMonth1, mDay1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        firebaseOptions=new FirebaseOptions.Builder()
                .setApplicationId("1:720142389475:android:920875d8334fb1c3")
                .setApiKey("AIzaSyCTT78tupL7oOhSkIeHgNN6hyLWgxWMcs8 ")
                .setDatabaseUrl("https://doctorlog-ac4d6.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(this,firebaseOptions,"secondary");
        FirebaseApp app=FirebaseApp.getInstance("secondary");
        firebaseDatabase=FirebaseDatabase.getInstance(app);
         references=firebaseDatabase.getReference("doctor").child(ItemDetailFragment.dockey).child("clinics").child(ItemDetailFragment.clinickey).child("appointments");
        //reference.push().child("aaa").setValue("bbb");
        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        previously=prefs1.getString("key","null" );
        db= FirebaseDatabase.getInstance().getReference("patient").child(previously);
        Intent intent = getIntent();
        id = intent.getIntExtra("docid", 0);
        edt = (EditText) findViewById(R.id.fname);
        edt1=(EditText)findViewById(R.id.lname);
        b1 = (Button) findViewById(R.id.next1);
        txt1=(EditText)findViewById(R.id.appdate);
        cause=(EditText)findViewById(R.id.cause);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(view);
            }
        });
        r1 = (RadioButton) findViewById(R.id.radio1);
        r2 = (RadioButton) findViewById(R.id.radio2);
        Button dt = (Button) findViewById(R.id.date);
        txt = (EditText) findViewById(R.id.textdate);
        dt.setOnClickListener(this);
        Button dt1=(Button)findViewById(R.id.date2);
        dt1.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio1:
                if (checked)
                    r1.setBackgroundColor(getResources().getColor(R.color.btn_logut_bg));
                    gender="male";
                Log.d("name", "hhh");
                break;
            case R.id.radio2:
                if (checked)
                    r1.setBackgroundColor(getResources().getColor(R.color.btn_logut_bg));
                    gender="female";
                Log.d("ff", "ggggg");
                break;


        }
    }

    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(book.this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to book appointment");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                       // Toast.makeText(book.this,"abba",Toast.LENGTH_LONG ).show();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        d1 = new Date();
                        try {
                            d2=df.parse(txt.getText().toString()) ;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        reportDate = txt.getText().toString();
                        long age = d1.getYear() - d2.getYear();
                   //     Toast.makeText(book.this,"aaaa1",Toast.LENGTH_LONG ).show();
                        DateFormat ddd=new SimpleDateFormat("HH:mm:ss");
                        DateFormat df1= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Date today=Calendar.getInstance().getTime();
                        reportDate1=df1.format(today);
                     //   Toast.makeText(book.this,"aaa2",Toast.LENGTH_LONG ).show();
                        String time=ddd.format(today);
                   //     String pattern = "dd-MM-yyyy HH:mm:ss";
                        String pattern1="dd-MM-yyyy";
                        date=null;
                     //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        simpleDateFormat1 = new SimpleDateFormat(pattern1);
                        try {
                            date= simpleDateFormat1.parse(txt1.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                       // references.child(simpleDateFormat1.format(date).toString()).setValue("--");
                      /*  reff=firebaseDatabase.getReference("doctor").child(ItemDetailFragment.dockey).child("clinics").child(ItemDetailFragment.clinickey).child("appointments").child(simpleDateFormat1.format(date)).getRef();    */
                       // Toast.makeText(book.this,"aaaa",Toast.LENGTH_LONG ).show();
                          //  references.child(simpleDateFormat1.format(date).toString()).setValue("null1");
                        //    references.child(simpleDateFormat1.format(date).toString()).child(previously).setValue("aaaa");
                            //references = references.child(simpleDateFormat1.format(date).toString()).getRef();
                           //Toast.makeText(book.this,"aaaa1",Toast.LENGTH_LONG ).show();
                            read3(new Firebasecallback6() {
                                @Override
                                public void onCallback6(List<String> list) {
                                    if(cou==1)
                                    {
                                        references.child(simpleDateFormat1.format(date).toString()).setValue(String.valueOf(0));
                                        references.child(simpleDateFormat1.format(date).toString()).child("doctoken").setValue(String .valueOf(1));
                                        references.child(simpleDateFormat1.format(date).toString()).child("status").setValue("false");
                                        references.child(simpleDateFormat1.format(date).toString()).child("delay").setValue(String.valueOf(0));

                                    }
                                  if ((!list.isEmpty())&&list.contains(previously)) {
                                        check = true;
                                        Toast.makeText(book.this, "You already have an appointment at this time", Toast.LENGTH_LONG).show();
                                    }
                                    if (cou > Integer.parseInt(ItemDetailFragment.maxpatient))

                                    {
                                        Toast.makeText(book.this, "slots are full", Toast.LENGTH_LONG).show();
                                    }
                                    Toast.makeText(book.this, "check", Toast.LENGTH_LONG).show();
                                   if ((check != true) && (cou <= Integer.parseInt(ItemDetailFragment.maxpatient))) {
                                        int esttime = Integer.parseInt(ItemDetailFragment.avgtime) * (cou-1);
                                        String a[] = ItemDetailFragment.apptime.split("\\s");
                                        String myTime = a[0];
                                     //  Toast.makeText(book.this, a[0], Toast.LENGTH_LONG).show();
                                       String pattern1="hh:mma";
                                        SimpleDateFormat df = new SimpleDateFormat(pattern1);
                                        Date d = null;
                                        try {
                                            d = df.parse(myTime);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                     //  Toast.makeText(book.this,df.format(d),Toast.LENGTH_LONG).show();
                                         Calendar cal=Calendar.getInstance();
                                       // cal.set(Calendar.HOUR,d );
                                          cal.setTime(d);
                                          cal.add(Calendar.MINUTE, esttime);
                                          String es = df.format(cal.getTime());
                                         ref = FirebaseDatabase.getInstance().getReference("patient").child(previously).child("bookdate");
                                        String id = db.push().getKey();
                                        Intent intent = new Intent(book.this, splash2.class);
                                        ref.child(id).child("userid").setValue(previously);
                                        ref.child(id).child("pfname").setValue(edt.getText().toString());
                                        ref.child(id).child("plname").setValue(edt1.getText().toString());
                                        ref.child(id).child("dob").setValue(reportDate.toString());
                                        ref.child(id).child("booktime").setValue(reportDate1.toString());
                                        ref.child(id).child("cause").setValue(cause.getText().toString());
                                        ref.child(id).child("gender").setValue(gender);
                                        ref.child(id).child("docname").setValue(ItemDetailFragment.docname);
                                        ref.child(id).child("fees").setValue(ItemDetailFragment.fees);
                                        ref.child(id).child("doclat").setValue(ItemDetailFragment.doclat);
                                        ref.child(id).child("doclng").setValue(ItemDetailFragment.doclng);
                                        ref.child(id).child("apptime").setValue(ItemDetailFragment.apptime);
                                        ref.child(id).child("appdate").setValue(appdate);
                                        ref.child(id).child("clinicname").setValue(ItemDetailFragment.clinicname);
                                        ref.child(id).child("estimatedtime").setValue(es);
                                        ref.child(id).child("status").setValue("not completed");
                                        ref.child(id).child("tokenno").setValue(String.valueOf(cou));
                                        ref.child(id).child("type").setValue("upcoming");
                                        references.child(simpleDateFormat1.format(date)).child(previously).setValue(String.valueOf(cou));
                                        DatabaseReference databaseReference = references.child(simpleDateFormat1.format(date)).child(previously).getRef();
                                        databaseReference.child("pfname").setValue(edt.getText().toString());
                                        databaseReference.child("plname").setValue(edt1.getText().toString());
                                        databaseReference.child("dob").setValue(reportDate.toString());
                                        databaseReference.child("booktime").setValue(reportDate1.toString());
                                        databaseReference.child("cause").setValue(cause.getText().toString());
                                        databaseReference.child("gender").setValue(gender);
                                        databaseReference.child("tokenno").setValue(String.valueOf(cou));
                                        databaseReference.child("status").setValue("not completed");
                                        databaseReference.child("phoneno").setValue(LoginActivity.userg.phn.toString());
                                        databaseReference.child("lat").setValue(LoginActivity.userg.lat.toString());
                                        databaseReference.child("lng").setValue(LoginActivity.userg.lng.toString());
                                        databaseReference.child("estimatedtime").setValue(String.valueOf(es));
                                      /*  setalarm( previously,id,simpleDateFormat1.format(date).toString(),ItemDetailFragment.dockey,ItemDetailFragment.clinickey,es);                        */
                                       /* Toast.makeText(book.this, "Appointment Booked", Toast.LENGTH_LONG).show();SharedPreferences prefs1= PreferenceManager.getDefaultSharedPreferences(getBaseContext());      */
                                       SharedPreferences prefs1= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                       int alm_id = prefs1.getInt("alarmid", 1);
                                       alm_id++;
                                       Intent myIntent = new Intent(getApplicationContext() ,Broadcastservice.class);
                                       myIntent.putExtra("patient_id", previously);
                                       myIntent.putExtra("app_id", id);
                                       myIntent.putExtra("docname",ItemDetailFragment.docname );
                                       myIntent.putExtra("app_date",simpleDateFormat1.format(date));
                                       myIntent.putExtra("alarmId", alm_id);
                                       myIntent.putExtra("apptime",ItemDetailFragment.apptime );
                                       Toast.makeText(book.this,String .valueOf(alm_id), Toast.LENGTH_LONG).show();
                                       myIntent.putExtra("pname", edt.getText().toString());
                                       myIntent.putExtra("clinicname", ItemDetailFragment.clinicname);
                                       myIntent.putExtra("dockey",ItemDetailFragment.dockey);
                                       myIntent.putExtra("clinickey",ItemDetailFragment.clinickey );
                                       myIntent.putExtra("avgtime",ItemDetailFragment.avgtime );
                                       myIntent.putExtra("token",cou);
                                       myIntent.putExtra("estimatedtime",es);
                                       myIntent.putExtra("lat",Double.parseDouble(ItemDetailFragment.doclat) );
                                       myIntent.putExtra("lng",Double.parseDouble(ItemDetailFragment.doclng) );
                                      // myIntent.setAction("uniqueCode");
                                      // myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                       AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                                       PendingIntent alarmIntent = PendingIntent.getBroadcast(book.this,alm_id, myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                                       Calendar calendar = Calendar.getInstance();
                                       calendar.set(Calendar.HOUR_OF_DAY,0);
                                       // calendar.set(Calendar.HOUR_OF_DAY,8);
                                       calendar.set(Calendar.MINUTE,00);
                                       //calendar.set(Calendar.MINUTE,00);
                                       calendar.set(Calendar.SECOND,00 );
                                     alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                               1000 * 60 *60*24, alarmIntent);
                                      /* alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                               1000 * 60 *60*24, alarmIntent);   */
                                    // alarmMgr.cancel(alarmIntent);
                                       /*AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                                       PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),alm_id , myIntent, 0);
                                       Calendar calendar = Calendar.getInstance();
                                       calendar.setTimeInMillis(System.currentTimeMillis());
                                       calendar.set(Calendar.HOUR_OF_DAY, 18);
                                       alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                               AlarmManager.INTERVAL_DAY, pendingIntent);      */
                                       //Toast.makeText(book.this, "lmn",Toast.LENGTH_LONG ).show();
                                       SharedPreferences.Editor edit=prefs1.edit();
                                       edit.putInt("alarmid",alm_id);
                                       edit.commit();
                                      startActivity(intent);
                                    }

                                }

                            });
                            //Toast.makeText(book.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date:
                final java.util.Calendar ch1= Calendar.getInstance();
                mYear= ch1.get(Calendar.YEAR);
                mMonth = ch1.get(Calendar.MONTH);
                mDay= ch1.get(Calendar.DAY_OF_MONTH);
                android.app.DatePickerDialog datePickerDialog= new android.app.DatePickerDialog(this,
                        new android.app.DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txt.setText((mDay1=dayOfMonth) + "-" + (mMonth1=(monthOfYear + 1)) + "-" +(mYear1= year));
                                //appdate=new String(mDay1+"/"+mMonth1+"/"+mYear1);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                //   datePickerDialog1.getDatePicker().setMaxDate();
                datePickerDialog.show();
            break;
            case R.id.date2:

                final java.util.Calendar ch = Calendar.getInstance();
                mYear1= ch.get(Calendar.YEAR);
                mMonth1 = ch.get(Calendar.MONTH);
                mDay1= ch.get(Calendar.DAY_OF_MONTH);
              
                android.app.DatePickerDialog datePickerDialog1= new android.app.DatePickerDialog(this,
                        new android.app.DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txt1.setText((mDay1=dayOfMonth) + "-" + (mMonth1=(monthOfYear + 1)) + "-" +(mYear1= year));
                                appdate=new String(mDay1+"/"+mMonth1+"/"+mYear1);

                            }
                        }, mYear1, mMonth1, mDay1);
                datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
             //   datePickerDialog1.getDatePicker().setMaxDate();
                datePickerDialog1.show();
                break;

        }

    }

    public void setalarm(String pid,String appid,String appdate,String dockey,String clinickey,String es)
    {
        SharedPreferences prefs1= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int alm_id = prefs1.getInt("alaramid", 0);
        alm_id++;
        Intent myIntent = new Intent(book.this ,Broadcastservice.class);
        myIntent.putExtra("patient_id", pid);
        myIntent.putExtra("app_id", appid);
        myIntent.putExtra("app_date",appdate );
        myIntent.putExtra("alarmId", alm_id);
        myIntent.putExtra("dockey",dockey );
        myIntent.putExtra("clinickey",clinickey );
        myIntent.putExtra("estimatedtime",es);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(book.this,alm_id , myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,16);
        calendar.set(Calendar.MINUTE,9);
        calendar.set(Calendar.SECOND, 00);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
        SharedPreferences.Editor edit=prefs1.edit();
        edit.putInt("alarmid",alm_id);
        edit.commit();


    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),des[i] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object",LoginActivity.userg);
        outState.putBundle("b",bundle );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Bundle bundle = savedInstanceState.getBundle("b");
        LoginActivity.userg = (user) bundle.getSerializable("object");


    }
   void read3(final Firebasecallback6 firebasecallback6){
       Toast.makeText(book.this, "msg0",Toast.LENGTH_LONG).show();
        final List<String> list=new ArrayList<>();
        list.clear();
       Toast.makeText(book.this, "msg3",Toast.LENGTH_LONG).show();
       firebaseDatabase.getReference("doctor").child(ItemDetailFragment.dockey).child("clinics").child(ItemDetailFragment.clinickey).child("appointments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if(dataSnapshot==null)
                   {

                   }
                   else {
                       Toast.makeText(book.this, "msg18", Toast.LENGTH_LONG).show();
                       for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           String pid =(String)snapshot.getKey();
                          // Toast.makeText(book.this, pid, Toast.LENGTH_LONG).show();
                           if(pid.equals(simpleDateFormat1.format(date)))
                           {   for(DataSnapshot d:snapshot.getChildren())
                                          {   String mmt=(String )d.getKey();
                                          if((!mmt.equals("doctoken")&&(!mmt.equals("status")&&(!mmt.equals("delay"))))) {
                                              // Toast.makeText(book.this,"kkkk", Toast.LENGTH_LONG).show();
                                              list.add(d.getKey());
                                              cou++;
                                             }
                                          }



                           } 
                          // Toast.makeText(book.this, "msg19", Toast.LENGTH_LONG).show();

                       }
                   }
                firebasecallback6.onCallback6(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(book.this, ItemDetailActivity.class);
        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID,ItemListActivity.getitemid);
        startActivity(intent);
    }

    public interface Firebasecallback6{
        void onCallback6(List<String> list);
                    }

}

