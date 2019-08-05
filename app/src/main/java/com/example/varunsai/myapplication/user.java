package com.example.varunsai.myapplication;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties

public class user implements Serializable {
    public String id;
    public String fname,lname,phn,pincode;
    public String mail;
    public String username;
    public String url=null,lat,lng;
    //public String firstname,lastname,phonenumber,pincode,address;
    public String longitude,latitude,address;
    public user(String uid,String username, String email,String url,String phn,String lat,String lng) {
        this.username = username;
        this.id=uid;
        this.mail = email;
        this.url=url;
        this.phn=phn;
        this.lat=lat;
        this.lng=lng;
    }

    public user(String uid,String username, String email,String url) {
        this.username = username;
        this.id=uid;
        this.mail = email;
        this.url=url;
    }

    public String getUid() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public String getMail() {
        return mail;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPhn() {
        return phn;
    }

    public String getPincode() {
        return pincode;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
