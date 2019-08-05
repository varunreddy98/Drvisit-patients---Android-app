package com.example.varunsai.myapplication;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class patient
{   public  String userid,pfname,plname,gender,dob,cause,docname,docid,fees,booktime,appdate,clinicname,apptime,lat,lng;

    public String getCause() {
        return cause;
    }

    public String getDocname() {
        return docname;
    }

    public String getFees() {
        return fees;
    }

    public String getAppdate() {
        return appdate;
    }
}