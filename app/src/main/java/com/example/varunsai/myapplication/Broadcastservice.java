package com.example.varunsai.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.varunsai.myapplication.App.CHANNEL_ID;

public class Broadcastservice extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.N)
    NotificationManager nm;
    int diffInDays;
    String reqdays;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        String pid=intent.getStringExtra("patient_id");
        String apptime=intent.getStringExtra("apptime");
        String  appid=intent.getStringExtra("app_id");
        Double lat=intent.getDoubleExtra("lat",-32);
        Double lng=intent.getDoubleExtra("lng",17);
        String appdate=intent.getStringExtra("app_date");
        int alarmId = intent.getExtras().getInt("alarmId");
        int token=intent.getExtras().getInt("token");
        int avgtime=Integer.parseInt(intent.getStringExtra("avgtime"));
       // Toast.makeText(context,"hey test"+alarmId,Toast.LENGTH_LONG ).show();
        String dockey=intent.getStringExtra("dockey");
        String clinickey=intent.getStringExtra("clinickey");
        String estimatedtime=intent.getStringExtra("estimatedtime");
        String docname=intent.getStringExtra("docname");
        String pname=intent.getStringExtra("pname");
        String clinicname=intent.getStringExtra("clinicname");
        DateFormat df1= new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
       Date today= new Date();
        try {
            diffInDays = (int)((df1.parse(appdate)).getDate()-today.getDate()) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(diffInDays==0)
        {
            reqdays="you have an appointment Today";
        }
        else
        {
            reqdays="Appointment in "+String .valueOf(diffInDays)+" days ";
        }
        /*push notifications here*/
      //  Toast.makeText(context,"hey test"+alarmId,Toast.LENGTH_LONG ).show();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,CHANNEL_ID )
                        .setSmallIcon(R.drawable.appointment_icon_150x150)
                        .setContentTitle("Dr.Drive ")
                        .setContentText(reqdays.toString())
                        .setColor(Color.parseColor("#f9d7d7"))
                         .setAutoCancel(true)
                         .setSound(alarmSound);
        Intent notificationIntent = new Intent(context,Upcoming.class);
        notificationIntent.putExtra("type","upcoming");
        PendingIntent contentIntent = PendingIntent.getActivity(context, alarmId, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(alarmId, builder.build());
        String reportDate1=df1.format(today).toString();
        if(reportDate1.equals(appdate)){

            Intent myIntent = new Intent(context.getApplicationContext() ,Broadcastservice.class);
            myIntent.putExtra("patient_id",pid);
            myIntent.putExtra("app_id", appid);
            myIntent.putExtra("docname",ItemDetailFragment.docname );
            myIntent.putExtra("app_date",appdate);
            myIntent.putExtra("alarmId", alarmId);
            myIntent.putExtra("apptime",apptime );
            myIntent.putExtra("pname", pname);
            myIntent.putExtra("lat", lat);
            myIntent.putExtra("lng",lng);
            myIntent.putExtra("clinicname", clinicname);
        //    Toast.makeText(book.this,String .valueOf(alm_id), Toast.LENGTH_LONG).show();
            myIntent.putExtra("dockey",dockey);
            myIntent.putExtra("clinickey",clinickey );
            myIntent.putExtra("estimatedtime",estimatedtime);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            //Toast.makeText(context, "hey executed ---" + String .valueOf(alarmId), Toast.LENGTH_LONG).show();
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, alarmId, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.set(java.util.Calendar.HOUR_OF_DAY,10);
            calendar.set(java.util.Calendar.MINUTE,35);
            calendar.set(java.util.Calendar.SECOND,00 );
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    1000 * 60 *2, alarmIntent);
            alarmManager.cancel(alarmIntent);
          //  Toast.makeText(context,"test ended"+alarmId,Toast.LENGTH_LONG ).show();
        int alm_id2= alarmId+3566;
        alm_id2++;
        Intent myIntent2 = new Intent(context.getApplicationContext() ,Broadcastservice2.class);
        myIntent2.putExtra("patient_id",pid);
        myIntent2.putExtra("app_id",appid);
        myIntent2.putExtra("docname",docname );
        myIntent2.putExtra("app_date",appdate);
        myIntent2.putExtra("alarmId2", alm_id2);
        myIntent2.putExtra("addtime",(token-1)*avgtime );
        myIntent2.putExtra("token",token );
        myIntent2.putExtra("avgtime",avgtime );
        myIntent2.putExtra("apptime",apptime );
            myIntent2.putExtra("lat", lat);
            myIntent2.putExtra("lng",lng);
            myIntent2.putExtra("pname", pname);
            myIntent2.putExtra("clinicname", clinicname);
        Toast.makeText(context,String .valueOf(alm_id2), Toast.LENGTH_LONG).show();
        myIntent2.putExtra("dockey",dockey);
        myIntent2.putExtra("clinickey",clinickey );
        myIntent2.putExtra("estimatedtime",estimatedtime);
        // myIntent.setAction("uniqueCode");
        // myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      /*  java.util.Calendar calendar2 = java.util.Calendar.getInstance();
        calendar2.set(java.util.Calendar.HOUR_OF_DAY,10);
        calendar2.set(java.util.Calendar.MINUTE,35);
        calendar2.set(java.util.Calendar.SECOND,00 );   */
             String p[]=apptime.split("\\s");
            String pattern2 = "HH:mm";
            String pattern1="hh:mma";
            Date date=null;
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
            try {
                date= simpleDateFormat1.parse(p[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
           String  req= simpleDateFormat2.format(date).toString();
            String myTime[]=req.split(":");
          //  Toast.makeText(context, a[0], Toast.LENGTH_LONG).show();
            java.util.Calendar cal= java.util.Calendar.getInstance();
            // cal.set(Calendar.HOUR,d );
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(java.util.Calendar.HOUR_OF_DAY,Integer.parseInt(myTime[0]));
            cal.set(java.util.Calendar.MINUTE,Integer.parseInt(myTime[1]) );
           // cal.add(java.util.Calendar.HOUR_OF_DAY, -3);
            cal.add(java.util.Calendar.MINUTE,-30);
        //    Toast.makeText(context,simpleDateFormat.format(cal.getTime()).toString(),Toast.LENGTH_LONG).show();
            myIntent2.putExtra("apptime",apptime);
            AlarmManager alarmMgr2=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent alarmIntent2 = PendingIntent.getBroadcast(context,alm_id2, myIntent2,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr2.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                1000 * 60 *30, alarmIntent2);
        // alarmMgr.cancel(alarmIntent);
                                       /*AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                                       PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),alm_id , myIntent, 0);
                                       Calendar calendar = Calendar.getInstance();
                                       calendar.setTimeInMillis(System.currentTimeMillis());
                                       calendar.set(Calendar.HOUR_OF_DAY, 18);
                                       alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                               AlarmManager.INTERVAL_DAY, pendingIntent);      */
      //  Toast.makeText(context, "lmlm--"+String .valueOf(alm_id2),Toast.LENGTH_LONG ).show();
        /*
            SharedPreferences prefs2= PreferenceManager.getDefaultSharedPreferences(context);
            int alm_id2 = prefs2.getInt("alaramid2",3555);
            alm_id2++;
            Intent intent2 = new Intent(context ,Broadcastservice.class);
            intent2.putExtra("patient_id", pid);
            intent2.putExtra("app_id", appid);
            intent2.putExtra("app_date",appdate );
            intent2.putExtra("alarmId", alm_id2);
             AlarmManager alarmManager2= (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(context,alm_id2,intent2, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY,8);
            calendar.set(Calendar.MINUTE,30);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*60*30, pendingIntent);
            SharedPreferences.Editor edit=prefs2.edit();
            edit.putInt("alarmid",alm_id2);
            edit.commit();    */


        }

    }
}
