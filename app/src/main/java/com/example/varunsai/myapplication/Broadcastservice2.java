package com.example.varunsai.myapplication;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.varunsai.myapplication.App.CHANNEL_ID;
import static java.lang.StrictMath.abs;

public class Broadcastservice2 extends BroadcastReceiver {
    static boolean ch=false;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String pid=intent.getStringExtra("patient_id");
        String appid=intent.getStringExtra("app_id");
        String appdate=intent.getStringExtra("app_date");
        Double lat=intent.getDoubleExtra("lat",-34 );
        Double lng=intent.getDoubleExtra("lng",17);
        // Toast.makeText(context,"hey test"+alarmId,Toast.LENGTH_LONG ).show();
        int token=intent.getExtras().getInt("token");
        int avgtime=intent.getExtras().getInt("avgtime");
        String dockey=intent.getStringExtra("dockey");
        int addtime=intent.getExtras().getInt("addtime");
        String pname=intent.getStringExtra("pname");
        String clinicname=intent.getStringExtra("clinicname");
        String clinickey=intent.getStringExtra("clinickey");
        String estimatedtime=intent.getStringExtra("estimatedtime");
        String docname=intent.getStringExtra("docname");
        String apptime=intent.getStringExtra("apptime");
        DateFormat df1= new SimpleDateFormat("dd-MM-yyyy");
        int alarmId=intent.getExtras().getInt("alarmId2");
        Toast.makeText(context, String .valueOf(alarmId) ,Toast.LENGTH_LONG ).show();
        SimpleDateFormat ss=new SimpleDateFormat("hh:mma");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
       /* long d1= 0;
        long d3=0;
        try {
            d1 = simpleDateFormat.parse(simpleDateFormat.format(new Date())).getHours();
            d3 =  simpleDateFormat.parse(simpleDateFormat.format(new Date())).getMinutes();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long d2=0;
        long d4=0;
        try {
            d2=simpleDateFormat.parse(ss.format( ss.parse(apptime))).getHours();
            d4 = simpleDateFormat.parse(ss.format( ss.parse(apptime))).getMinutes();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff=(abs(d2-d1))%24;
        long diff2=(d4-d3)%60;
        String msg="";
        int hours= (int) diff;
        int mins=(int)diff2;   */
        int f1=addtime%60;
        int f2=addtime/60;
        String p1[]=apptime.split("\\s");
        String pattern3= "HH:mm";
        String pattern4="hh:mma";
        Date dates=null;
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(pattern3);
        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(pattern4);
        try {
            dates= simpleDateFormat4.parse(p1[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String  reqs= simpleDateFormat3.format(dates).toString();
        String myTimes[]=reqs.split(":");
        int h1=Integer.parseInt(myTimes[0])+f2;
        int m1=Integer.parseInt(myTimes[1])+f1;
        Calendar c1=Calendar.getInstance();
        c1.setTimeInMillis(System.currentTimeMillis());
        c1.set(Calendar.HOUR_OF_DAY,h1 );
        c1.set(Calendar.MINUTE, m1);
        Calendar c2=Calendar.getInstance();
        c2.setTimeInMillis(System.currentTimeMillis());
        long mm=c1.getTimeInMillis()-c2.getTimeInMillis();
        int hours=(int)(mm/(1000*60*60)/60);
        int mins=(int)(mm/(1000*60)%60);
        /*
        int f1=addtime%60;
        int f2=addtime/60;
        Calendar calendar = Calendar.getInstance();
        String current=simpleDateFormat.format(calendar.getTime());
        String  x[]=current.split(":");
        int h=0-Integer.parseInt(x[0]);
        int m=0-Integer.parseInt(x[1]);
        String cl=apptime.substring(0,5);
        Toast.makeText(context,cl+"split" ,Toast.LENGTH_LONG ).show();
        String  b[]=cl.split(":");
        int h1=Integer.parseInt(b[0]);
        int m1=Integer.parseInt(b[1]);
        Calendar cc=Calendar.getInstance();
        cc.set(Calendar.HOUR_OF_DAY,h1);
        cc.set(Calendar.MINUTE,m1);
        cc.set(Calendar.SECOND,00 );
        cc.add(Calendar.HOUR_OF_DAY,h+f2);
        cc.add(Calendar.MINUTE,m+f1);
        String msg1[]=simpleDateFormat.format(cc.getTime()).split(":");
        int hours=Integer.parseInt(msg1[0]);
        int mins=Integer.parseInt(msg1[1]);   */
        String msg="";
        if(hours>0)
        {
            msg="your appointment in "+hours+"hours "+mins+"mins";
        }
        else
        {
            msg="your appointment in "+mins+"mins";
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,CHANNEL_ID )
                        .setSmallIcon(R.drawable.appointment_icon_150x150)
                        .setContentTitle("Dr.Drive ")
                        .setContentText(msg)
                        .setColor(Color.parseColor("#f9d7d7"))
                        .setAutoCancel(true)
                         .setSound(alarmSound);
        Intent notificationIntent = new Intent(context,Upcoming.class);
        notificationIntent.putExtra("type","upcoming");
        PendingIntent contentIntent = PendingIntent.getActivity(context, alarmId, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(alarmId, builder.build());
       /* Date today= new Date();
        String reportDate1=df1.format(today).toString();
        String a[] = apptime.split("\\s");
        String myTime = a[0];
      Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
        String pattern1="hh:mma";
        SimpleDateFormat dfl= new SimpleDateFormat(pattern1);
        Date d = null;
        try {
            d = dfl.parse(myTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }      */
      String p[]=apptime.split("\\s");
        String pattern2= "HH:mm";
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
        // Toast.makeText(context, myTime[0], Toast.LENGTH_LONG).show();
        // cal.set(Calendar.HOUR,d );
        for (int i=-10;i<=10;i++) {
           Calendar cal= Calendar.getInstance();
          //  cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(myTime[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(myTime[1]));
            // cal.add(java.util.Calendar.HOUR_OF_DAY, -3);
            cal.add(Calendar.MINUTE, -30 + i);
           // Toast.makeText(context, simpleDateFormat2.format(cal.getTime()).toString(), Toast.LENGTH_LONG).show();
           //Toast.makeText(context, simpleDateFormat2.format(Calendar.getInstance().getTime()).toString() + "llm", Toast.LENGTH_LONG).show();
            String s1=simpleDateFormat2.format(cal.getTime());
             String  s2=simpleDateFormat2.format(Calendar.getInstance().getTime());
             if(s1.equals(s2))
             {
                ch = true;
                break;
            }
        }
        if (ch)
        {    ch=false;
            // Add as notification
            Intent myIntent2 = new Intent(context.getApplicationContext(), Broadcastservice2.class);
            myIntent2.putExtra("patient_id", pid);
            myIntent2.putExtra("app_id", appid);
            myIntent2.putExtra("docname", docname);
            myIntent2.putExtra("app_date", appdate);
            myIntent2.putExtra("alarmId2", alarmId);
            myIntent2.putExtra("dockey", dockey);
            myIntent2.putExtra("clinickey", clinickey);
            myIntent2.putExtra("estimatedtime", estimatedtime);
            myIntent2.putExtra("pname", pname);
            myIntent2.putExtra("clinicname", clinicname);
            // myIntent.setAction("uniqueCode");
            // myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(context, "lllll" , Toast.LENGTH_LONG).show();
            AlarmManager alarmMgr2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent alarmIntent2 = PendingIntent.getBroadcast(context, alarmId, myIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.HOUR_OF_DAY, 10);
            calendar2.set(Calendar.MINUTE, 35);
            calendar2.set(Calendar.SECOND, 00);
            alarmMgr2.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),
                    1000 * 60 * 30, alarmIntent2);
            alarmMgr2.cancel(alarmIntent2);
            Toast.makeText(context,"success", Toast.LENGTH_LONG).show();
            Intent myIntent3=new Intent(context,Clientservice.class);
            myIntent3.putExtra("patient_id", pid);
            myIntent3.putExtra("app_id", appid);
            myIntent3.putExtra("apptime",apptime );
            myIntent3.putExtra("docname", docname);
            myIntent3.putExtra("app_date", appdate);
            myIntent3.putExtra("alarmId2", alarmId);
            myIntent3.putExtra("addtime",addtime );
            myIntent3.putExtra("dockey", dockey);
            myIntent3.putExtra("clinickey", clinickey);
            myIntent3.putExtra("estimatedtime", estimatedtime);
            myIntent3.putExtra("token",token );
            myIntent3.putExtra("avgtime",avgtime );
            myIntent3.putExtra("hours",hours );
            myIntent3.putExtra("mins",mins );
            myIntent3.putExtra("pname", pname);
            myIntent3.putExtra("clinicname", clinicname);
            myIntent3.putExtra("lat", lat);
            myIntent3.putExtra("lng",lng);
            ContextCompat.startForegroundService(context,myIntent3 );
            Toast.makeText(context, "lllll" , Toast.LENGTH_LONG).show();
       }
    }
}
