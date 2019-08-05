package com.example.varunsai.myapplication;
import java.util.ArrayList;
public class clinic{

    public String fees,phn,address,lat,lng,name,cid,maxpatient,avgtime;
    public ArrayList<String> timings;
    ArrayList<String> days;
    clinic(String cid,String name,String fees,ArrayList<String> timings,String address,String lat,String lng,String phn,String maxpatient,String avgtime)
    {   this.fees=fees;
    this.maxpatient=maxpatient;
    this.avgtime=avgtime;
    this.name=name;
    this.cid=cid;
         this.phn=phn;
         this.timings=timings;
        this.address=address;
        this.lat=lat;
        this.lng=lng;
    }

}
