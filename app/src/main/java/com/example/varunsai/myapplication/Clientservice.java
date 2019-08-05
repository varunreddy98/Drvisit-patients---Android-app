package com.example.varunsai.myapplication;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.webkit.ClientCertRequest;
import android.widget.Toast;

import com.example.varunsai.myapplication.dummy.DummyItem;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.varunsai.myapplication.App.CHANNEL_ID;

public class Clientservice extends Service{
    MediaPlayer myPlayer;
    static int ch=0,i=1;
    static int kl=0,dt=1;
    Thread t,dat;
  static Boolean delay_stat=false,stop_status=false;
    static int hours=0,mins=0;
    static boolean chceksevice=false;
    static String docname="",appdate="",pname="",clinicname="";
    static Double lat,lng;
    Handler mhandler;
    String dockey,clinickey,pid;
   static String stat,status_string,token_status;
    boolean status=false;
    boolean apps=false;
    static int hh=0;
    int mm=0;
    String previously,appid;
    Calendar calendar;
    static  int token,avgtime;
    private FirebaseOptions firebaseOptions;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference references;
    Date date;
    SimpleDateFormat simpleDateFormat1,simpleDateFormat;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        firebaseOptions=new FirebaseOptions.Builder()
                .setApplicationId("1:720142389475:android:920875d8334fb1c3")
                .setApiKey("AIzaSyCTT78tupL7oOhSkIeHgNN6hyLWgxWMcs8 ")
                .setDatabaseUrl("https://doctorlog-ac4d6.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(this,firebaseOptions,"four");
        FirebaseApp app=FirebaseApp.getInstance("four");
        firebaseDatabase= FirebaseDatabase.getInstance(app);       
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //do heavy work on a background thread
        //stopSelf();
        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        previously=prefs1.getString("key","null" );
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        pid=intent.getStringExtra("patient_id");
        appid=intent.getStringExtra("app_id");
        appdate=intent.getStringExtra("app_date");
        token=intent.getExtras().getInt("token");
      //  Toast.makeText(Clientservice.this, token,Toast.LENGTH_LONG ).show();
        avgtime=intent.getExtras().getInt("avgtime");
        dockey=intent.getStringExtra("dockey");
        int addtime=intent.getExtras().getInt("addtime");
        clinickey=intent.getStringExtra("clinickey");
        String estimatedtime=intent.getStringExtra("estimatedtime");
         docname=intent.getStringExtra("docname");
        String apptime=intent.getStringExtra("apptime");
        lat=intent.getDoubleExtra("lat",-34);
        lng=intent.getDoubleExtra("lng",17);
        String pattern1="dd-MM-yyyy";
        simpleDateFormat=new SimpleDateFormat("HH:mm");
        simpleDateFormat1=new SimpleDateFormat(pattern1);
          pname=intent.getStringExtra("pname");
         clinicname=intent.getStringExtra("clinicname");
        int hours=intent.getExtras().getInt("hours");
        int mins=intent.getExtras().getInt("mins");
        hh=mins;
        mm=(token-1)*avgtime;
        calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE,mins);
        try {
            date= simpleDateFormat1.parse(appdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        chceksevice=true;
        Intent notificationIntent = new Intent(this, ongoing.class);
        notificationIntent.putExtra("docname", docname);
        notificationIntent.putExtra("app_date", appdate);
        notificationIntent.putExtra("token",i);
        notificationIntent.putExtra("hours",hours );
        notificationIntent.putExtra("mins",mins );
        notificationIntent.putExtra("pname", pname);
        notificationIntent.putExtra("clinicname", clinicname);
        notificationIntent.putExtra("lat",lat);
        notificationIntent.putExtra("lng",lng );
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
       Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Dr. Drive ")
                .setContentText(" Ur Appointment in "+hours+":"+mins)
                .setSmallIcon(R.drawable.appointment_icon_150x150)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setColor(Color.parseColor("#f9d7d7"))
                .build();
        startForeground(1, notification);
        t = new Thread(new MyRunnable());
        t.start();
        dat = new Thread(new Data());
        dat.start();
       return START_STICKY;
    }

    private void stopper() {
        Uri alarmSound1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Intent notificationIntent1 = new Intent(this, ongoing.class);
        notificationIntent1.putExtra("docname", docname);
        notificationIntent1.putExtra("app_date", appdate);
        notificationIntent1.putExtra("token", i);
        notificationIntent1.putExtra("hours", hours);
        notificationIntent1.putExtra("mins", mins);
        notificationIntent1.putExtra("pname", pname);
        notificationIntent1.putExtra("clinicname", clinicname);
        notificationIntent1.putExtra("lat", lat);
        notificationIntent1.putExtra("lng", lng);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(this,
                0, notificationIntent1, 0);
        Notification notification1 = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Dr. Drive")
                .setContentText("appointmnts stopped/completed")
                .setColor(Color.parseColor("#f9d7d7"))
                .setSound(alarmSound1)
                .setSmallIcon(R.drawable.appointment_icon_150x150)
                .setContentIntent(pendingIntent1)
                .build();
        NotificationManager mNotificationManager1 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager1.notify(1, notification1);
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // stopForeground(true);
        stopSelf();
    }

    private void incremeter() {
        int j;
        int k,ll=0;
        for (i = 1; i < token; i++) {
            j = 0;
            k = 0;
           // Toast.makeText(Clientservice.this,"57 "+String.valueOf(apps) ,Toast.LENGTH_LONG ).show();
            while (!apps) {
             //  Toast.makeText(Clientservice.this,"57 "+String.valueOf(apps) ,Toast.LENGTH_LONG ).show();
                try {
                    Thread.sleep(1000 * 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
              if(ll%60==0){
                  j = j + 1;
                  //  int lh = (int)Math.ceil((mm / 60));
                  //    int lm =(int) Math.ceil(mm % 60);
                  int lh = mm / 60;
                  int lm = mm % 60;
                  String ms = "";
                  if (lh > 0) {
                      ms = lh + " hours " + lm + " mins";
                  } else {
                      ms = lm + " mins ";
                  }
                  Intent notificationIntent = new Intent(this, MainActivity.class);
                  PendingIntent pendingIntent = PendingIntent.getActivity(this,
                          0, notificationIntent, 0);
                  Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                          .setContentTitle("Dr. Drive " + " current token:" + String.valueOf(i))
                          .setContentText("Ur Appointment in " + ms)
                          .setColor(Color.parseColor("#f9d7d7"))
                          .setSmallIcon(R.drawable.appointment_icon_150x150)
                          .setContentIntent(pendingIntent)
                          .build();
                  NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                  mNotificationManager.notify(1, notification);
                  //   calendar.add(Calendar.MINUTE,-1 );
                  if (j >= avgtime) {      // calendar.add(Calendar.MINUTE,1);
                      if ((k % avgtime) == 0) {
                          mm = mm + avgtime;
                          // j=j+5;
                      }
                      k++;
                    /*   if(ongoing.on==true)
                        {
                            ongoing.esttime1.setText(simpleDateFormat.format(calendar.getTime()));
                        }      */
                      //add j mins to calendar
                  }
                  mm = mm - 1;
              }
              /*  Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(Clientservice.this,CHANNEL_ID )
                                .setSmallIcon(R.drawable.appointment_icon_150x150)
                                .setContentTitle("Dr.Drive ")
                                .setContentText("apps")
                                .setColor(Color.parseColor("#f9d7d7"))
                                .setAutoCancel(true)
                                .setSound(alarmSound);
                Intent notificationIntent3 = new Intent(Clientservice.this,Upcoming.class);
                notificationIntent3.putExtra("type","upcoming");
                PendingIntent contentIntent = PendingIntent.getActivity(Clientservice.this, 808, notificationIntent3,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(808, builder.build());        */
              ll++;

            }
            apps=false;
          //  Toast.makeText(Clientservice.this,"77 "+String.valueOf(apps) ,Toast.LENGTH_LONG ).show();
            if ((k != 0) && ((k % avgtime) < avgtime)) {
                int ss = (k % avgtime) - avgtime;
                //  calendar.add(Calendar.MINUTE,ss );
                //calendar.add(Calendar.MINUTE,1);
                // j=j-ss;
                mm = mm +ss;
                /* if(ongoing.on==true)
                 {
                     ongoing.esttime1.setText(simpleDateFormat.format(calendar.getTime()));
                 } */

            }
           /* SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
           /* String msg1[]=simpleDateFormat.format(calendar.getTime()).split(":");
            hours =mm / 60;
            mins =mm % 60;
            String msg = "";
            if (hours > 0) {
                msg = "your appointment in " + hours + " hours " + mins + " mins";
            } else {
                msg = "your appointment in " + mins + " mins";
            }
          /* NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.appointment_icon_150x150)
                            .setAutoCancel(true)
                            .setContentTitle("Dr.Drive "+" current token no"+i)
                            .setContentText(msg);
            Intent notificationIntent = new Intent(this,ongoing.class);
            notificationIntent.putExtra("docname", docname);
            notificationIntent.putExtra("app_date", appdate);
            notificationIntent.putExtra("token",i);
            notificationIntent.putExtra("hours",hours );
            notificationIntent.putExtra("mins",mins );
            notificationIntent.putExtra("pname", pname);
            notificationIntent.putExtra("clinicname", clinicname);
            notificationIntent.putExtra("lat",lat);
            notificationIntent.putExtra("lng",lng );
            PendingIntent contentIntent = PendingIntent.getActivity(this,8989, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);  */
          /*  Intent notificationIntent = new Intent(this, ongoing.class);
            notificationIntent.putExtra("docname", docname);
            notificationIntent.putExtra("app_date", appdate);
            notificationIntent.putExtra("token", i);
            notificationIntent.putExtra("hours", hours);
            notificationIntent.putExtra("mins", mins);
            notificationIntent.putExtra("pname", pname);
            notificationIntent.putExtra("clinicname", clinicname);
            notificationIntent.putExtra("lat", lat);
            notificationIntent.putExtra("lng", lng);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Dr. Drive" + " current token:" + String.valueOf(i))
                    .setContentText("Ur Appointment in " + msg)
                    .setColor(Color.parseColor("#f9d7d7"))
                    .setSmallIcon(R.drawable.appointment_icon_150x150)
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, notification);     */
        }
        apps = false;
       /* Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.appointment_icon_150x150)
                        .setAutoCancel(true)
                        .setSound(alarmSound)
                        .setContentTitle("Dr.Drive ")
                        .setContentText("Its ur turn for the appointment");
        Intent notificationIntent1 = new Intent(this,ongoing.class);
        notificationIntent1.putExtra("docname", docname);
        notificationIntent1.putExtra("app_date", appdate);
        notificationIntent1.putExtra("token",i);
        notificationIntent1.putExtra("hours",0);
        notificationIntent1.putExtra("mins",0);
        notificationIntent1.putExtra("pname", pname);
        notificationIntent1.putExtra("clinicname", clinicname);
        notificationIntent1.putExtra("lat",lat );
        notificationIntent1.putExtra("lng",lng);
        /*PendingIntent contentIntent = PendingIntent.getActivity(this,6677, notificationIntent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(6677, builder.build());*/
       while (!apps) {
           Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
           Intent notificationIntent = new Intent(this, ongoing.class);
           notificationIntent.putExtra("docname", docname);
           notificationIntent.putExtra("app_date", appdate);
           notificationIntent.putExtra("token", i);
           notificationIntent.putExtra("hours", hours);
           notificationIntent.putExtra("mins", mins);
           notificationIntent.putExtra("pname", pname);
           notificationIntent.putExtra("clinicname", clinicname);
           notificationIntent.putExtra("lat", lat);
           notificationIntent.putExtra("lng", lng);
           PendingIntent pendingIntent = PendingIntent.getActivity(this,
                   0, notificationIntent, 0);
           Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                   .setContentTitle("Dr. Drive")
                   .setContentText("It's ur turn for appointment")
                   .setColor(Color.parseColor("#f9d7d7"))
                   .setSound(alarmSound)
                   .setSmallIcon(R.drawable.appointment_icon_150x150)
                   .setContentIntent(pendingIntent)
                   .build();
           NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
           mNotificationManager.notify(1, notification);
           try {
               Thread.sleep(1000*60);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
        apps=false;
       /* Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Intent notificationIntent = new Intent(this, ongoing.class);
        notificationIntent.putExtra("docname", docname);
        notificationIntent.putExtra("app_date", appdate);
        notificationIntent.putExtra("token", i);
        notificationIntent.putExtra("hours", hours);
        notificationIntent.putExtra("mins", mins);
        notificationIntent.putExtra("pname", pname);
        notificationIntent.putExtra("clinicname", clinicname);
        notificationIntent.putExtra("lat", lat);
        notificationIntent.putExtra("lng", lng);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Dr. Drive")
                .setContentText("Appointment completed")
                .setColor(Color.parseColor("#f9d7d7"))
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.appointment_icon_150x150)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification);
        try {
            Thread.sleep(1000*20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     /*   while (i==token) {

            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Uri alarmSound1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Intent notificationIntent1 = new Intent(this, ongoing.class);
            notificationIntent1.putExtra("docname", docname);
            notificationIntent1.putExtra("app_date", appdate);
            notificationIntent1.putExtra("token", i);
            notificationIntent1.putExtra("hours", hours);
            notificationIntent1.putExtra("mins", mins);
            notificationIntent1.putExtra("pname", pname);
            notificationIntent1.putExtra("clinicname", clinicname);
            notificationIntent1.putExtra("lat", lat);
            notificationIntent1.putExtra("lng", lng);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(this,
                    0, notificationIntent1, 0);
            Notification notification1 = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Dr. Drive")
                    .setContentText("ur appointment is going on")
                    .setColor(Color.parseColor("#f9d7d7"))
                    .setSound(alarmSound1)
                    .setSmallIcon(R.drawable.appointment_icon_150x150)
                    .setContentIntent(pendingIntent1)
                    .build();
            NotificationManager mNotificationManager1 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager1.notify(1, notification1);

        }

        endup();*/
       read4(new Firebasecallback4() {
            @Override
            public void onCallback4(String comp) {

                Toast.makeText(Clientservice.this,comp,Toast.LENGTH_LONG ).show();
         Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
           Intent notificationIntent = new Intent(Clientservice.this, ongoing.class);
           notificationIntent.putExtra("docname", docname);
           notificationIntent.putExtra("app_date", appdate);
           notificationIntent.putExtra("token", i);
           notificationIntent.putExtra("hours", hours);
           notificationIntent.putExtra("mins", mins);
           notificationIntent.putExtra("pname", pname);
           notificationIntent.putExtra("clinicname", clinicname);
           notificationIntent.putExtra("lat", lat);
           notificationIntent.putExtra("lng", lng);
           PendingIntent pendingIntent = PendingIntent.getActivity(Clientservice.this,
                   0, notificationIntent, 0);
           Notification notification = new NotificationCompat.Builder(Clientservice.this, CHANNEL_ID)
                   .setContentTitle("Dr. Drive")
                   .setContentText("Appointment "+comp.toString())
                   .setColor(Color.parseColor("#f9d7d7"))
                   .setSound(alarmSound)
                   .setSmallIcon(R.drawable.appointment_icon_150x150)
                   .setContentIntent(pendingIntent)
                   .build();
           NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
           mNotificationManager.notify(1, notification);
              // DatabaseReference ref = FirebaseDatabase.getInstance().getReference("patient").child(pid.toString()).child("bookdate").child(appid).child("status");
                //ref.setValue(comp.toString());
                //FirebaseDatabase.getInstance().getReference("patient").child(pid.toString()).child("bookdate").child(appid).child("type").setValue("past");
        stopForeground(true);
        stopSelf();
            }

        });
      // stopForeground(true);
       //stopSelf();
    }
    public void endup(){
      /*  ValueEventListener postListener4 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                stat= (String)dataSnapshot.getValue();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("patient").child(previously).child("bookdate").child(appid).child("status");
                FirebaseDatabase.getInstance().getReference("patient").child(previously).child("bookdate").child(appid).child("type").setValue("past");
                ref.setValue(stat);
                chceksevice=false;
                apps=false;
                ch=0;
                kl=0;
                try {
                    Thread.sleep(1000*15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // stopForeground(true);
                stopSelf();
                Toast.makeText(Clientservice.this,"tosatify" ,Toast.LENGTH_LONG ).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        references.child(previously).child("status").addValueEventListener(postListener4);     */
      //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("patient").child(previously).child("bookdate").child(appid).child("status");
        //FirebaseDatabase.getInstance().getReference("patient").child(previously).child("bookdate").child(appid).child("type").setValue("past");
        //ref.setValue(stat);
        chceksevice=false;
        ch=0;
        kl=0;
        stopSelf();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }
    class MyRunnable implements Runnable {
        @Override
        public void run() {
            int ll=0;
            while ((hh>0)&&(status==false)) {
                try {
                    Thread.sleep(1000*1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String msg="";
                int hl=hh/60;
                int ml=hh%60;
                if(hl>0)
                {
                    msg=hl+"hours"+ml+"mins";
                }
                else {
                    msg=ml+"mins";
                }
                if(ll%60==0) {
                Intent notificationIntent = new Intent(Clientservice.this,ongoing.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(Clientservice.this,
                        0, notificationIntent, 0);
                Notification notification = new NotificationCompat.Builder(Clientservice.this, CHANNEL_ID)
                        .setContentTitle("Dr. Drive"+" current token:0")
                        .setContentText("Ur Appointment in "+msg)
                        .setColor(Color.parseColor("#f9d7d7"))
                        .setSmallIcon(R.drawable.appointment_icon_150x150)
                        .setContentIntent(pendingIntent)
                        .build();
             /*   if(ongoing.on==true) {
                    ongoing.pname1.setText("Patient: " + pname);
                    ongoing.docname1.setText("Dr." + docname);
                    ongoing.clinicname1.setText("clinic: " + clinicname);
                    ongoing.apdate1.setText("Date:" + appdate);
                    ongoing.token1.setText("Token no: " + token);
                    ongoing.esttime1.setText("Estimated time: " + msg);
                    if (Clientservice.i == 0)
                        ongoing.current_token.setText("Current Token: " + " Appointments not yet started");
                    else

                        ongoing.current_token.setText("current Token: " + i);
                }    */
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, notification);
                    hh--;
                }
                ll++;
            }
            incremeter();
        }
    }


   class Data implements Runnable
   {

       @Override
       public void run() {
           while(true)
           {    read(new Firebasecallback1() {
               @Override
               public void onCallback1() {

                  // Toast.makeText(Clientservice.this,"completed-2" ,Toast.LENGTH_LONG ).show();
               }

           });
             if(stop_status==true)
                 break;
               try {
                   Thread.sleep(1000*1);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

           }
            stopForeground(true);
           stopSelf();
       }
   }
    public void read(final Firebasecallback1 firebasecallback){
       // final List<user> list=new ArrayList<user>();
      //  list.clear();
        // Toast.makeText(LoginActivity.this, "msg3",Toast.LENGTH_LONG).show();
        firebaseDatabase.getReference("doctor").child(dockey).child("clinics").child(clinickey).child("appointments").child(simpleDateFormat1.format(date)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot==null){
                    //Toast.makeText(LoginActivity.this, "msg10",Toast.LENGTH_LONG).show();

                }
                else {
                 status_string=(String)dataSnapshot.child("status").getValue();
                 if(status_string.equals("true"))
                    {
                        status=true;
                    }
                    else
                        if(status_string.equals("stop"))
                        {   status=false;
                            stop_status=true;
                            stopper();
                        }
                 //   Toast.makeText(Clientservice.this, status_string,Toast.LENGTH_LONG).show();
                 String del=(String )dataSnapshot.child("delay").getValue();
                 int temp=Integer.parseInt(del);
                 if((!delay_stat)&&(temp!=0))
                 {         hh=hh+temp;
                           delay_stat=true;
                 }
                 token_status=(String)dataSnapshot.child("doctoken").getValue();
               //  Toast.makeText(Clientservice.this,token_status,Toast.LENGTH_LONG).show();
                 if(dt<Integer.parseInt(token_status))
                 {
                     dt=Integer.parseInt(token_status);
                     apps=true;
                 }

                }
                // Toast.makeText(LoginActivity.this, "msg4",Toast.LENGTH_LONG).show();
                firebasecallback.onCallback1();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public  void read4(final Firebasecallback4 firebasecallback4)
    { firebaseDatabase.getReference("doctor").child(dockey).child("clinics").child(clinickey).child("appointments").child(simpleDateFormat1.format(date)).child(previously).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String comp="";
                if(dataSnapshot==null){
                    //Toast.makeText(LoginActivity.this, "msg10",Toast.LENGTH_LONG).show();

                }
                else {

                        comp=(String)dataSnapshot.child("status").getValue();
                      //  Toast.makeText(Clientservice.this,comp ,Toast.LENGTH_LONG ).show();
                }
                firebasecallback4.onCallback4(comp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public interface Firebasecallback1{
        void onCallback1();
    }
    public interface Firebasecallback4{
        void onCallback4(String comp);
    }
}
