package com.example.varunsai.myapplication.dummy;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import com.example.varunsai.myapplication.R;
import com.example.varunsai.myapplication.clinic;

import java.util.ArrayList;

public class DummyItem extends AppCompatActivity{
    public String name,mail;
    public  final String id;
    public final String spec;
    public String exp,degree;
    public Uri url;
    public  final ArrayList<clinic> clinics;
    //public final String timings;
   // public final String address;
    //public final String phn;
    //public Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.baloon);
    public DummyItem(String id, String nam, String mail, String spec,String exp, String degree, ArrayList<clinic> clinics, Uri url) {
       this.id=id;
        this.name= nam;
        this.mail=mail;
        this.spec = spec;
        this.exp=exp;
        this.degree=degree;
        this.clinics=clinics;
        this.url=url;
       // this.timings=time;
        //this.address=address;
        //this.phn=phn;
    }



}


