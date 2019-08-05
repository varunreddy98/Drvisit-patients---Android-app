package com.example.varunsai.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    EditText editText;
    int sp=0;
    DatabaseReference db;
    final String TAG = this.getClass().getName();
    Button b1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        //EditText editText1 = (EditText)((EditText) findViewById(R.id.phn));
        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String previously=prefs1.getString("key","null" );
        db= FirebaseDatabase.getInstance().getReference("patient").child(previously);
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp=0;
                String fname= String.valueOf(((EditText)findViewById(R.id.fname)).getText());
                if(fname.equals(""))
                {
                    EditText editText3= (EditText)((EditText) findViewById(R.id.fname));
                    editText3.setError("This field is necessary");
                    sp++;
                }
                String lname=  String.valueOf(((EditText)findViewById(R.id.lname)).getText());
                if(lname.equals(""))
                {
                    EditText editText4 = (EditText)((EditText) findViewById(R.id.lname));
                    editText4.setError("This field is necessary");
                    sp++;
                }
                String  phn=String.valueOf(((EditText)findViewById(R.id.phn)).getText());
                if(phn.length()!=10)
                {
                    EditText editText1 = (EditText)((EditText) findViewById(R.id.phn));
                    editText1.setError("not a valid phone number");
                    sp++;
                }
                String pin=String.valueOf(((EditText)findViewById(R.id.pincode)).getText());
                if(pin.length()!=6)
                {
                    EditText editText2 = (EditText)((EditText) findViewById(R.id.pincode));
                    editText2.setError("not a valid pincode");
                    sp++;
                }

                if(sp==0) {
                    LoginActivity.userg.phn=phn;
                    db.child("fname").setValue(fname);
                    db.child("lname").setValue(lname);
                    db.child("phn").setValue(phn);
                    db.child("pincode").setValue(pin);
                    db.child("bookdate").setValue("null");
                    Toast.makeText(signup.this,"map" ,Toast.LENGTH_LONG ).show();
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    boolean twice;

    @Override
    public void onBackPressed() {

        Log.d(TAG, "click");
        if (twice == true) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(intent.CATEGORY_HOME);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);


        }
        twice = true;
        Log.d(TAG, "twice" + twice);
        Toast.makeText(this, "press back again to exit", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
                Log.d(TAG, "twice" + twice);
            }
        }, 3000);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
}


