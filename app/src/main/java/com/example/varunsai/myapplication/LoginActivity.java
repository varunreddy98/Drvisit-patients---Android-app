package com.example.varunsai.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.android.gms.common.util.DbUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks,View.OnClickListener {
    public  static  String key1;
    public static user userg;
    public static user usergg;
    static SharedPreferences preferences;
    int clock=0;
    private static final int RC_SIGN_IN = 0;
    static FirebaseAuth auth;
    static Uri url=null;
    public static DatabaseReference myRef;
    public static boolean sign_status=false;
    public static String usname="null";
    public static boolean fb=false;
    public static String name;
   FirebaseUser user;
   GoogleSignInAccount account;
   static DatabaseReference db;
    LoginButton loginButton;
    CallbackManager callbackManager;
    ConnectionResult mConnectionResult;
    Button b1;
    static GoogleApiClient mGoogleApiClient;
    private ProgressDialog progressBar;
    private SignInButton GoogleSignInButton;
    boolean signedInUser;
    boolean mIntentInProgress;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    static GoogleSignInClient mGoogleSignInClient;
    TextView t2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
     //   FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        db=FirebaseDatabase.getInstance().getReference("patient");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.google);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.google).setOnClickListener(this);
        findViewById(R.id.signout_button).setOnClickListener(this);
        findViewById(R.id.signout_button).setVisibility(View.GONE);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override

            public void onSuccess(LoginResult loginResult) {

                Log.d(TAG, "facebook:onSuccess:" + loginResult);

                handleFacebooktoken(loginResult.getAccessToken());

            }



            @Override

            public void onCancel() {

                Log.d(TAG, "facebook:onCancel");
                findViewById(R.id.login_button).setVisibility(View.VISIBLE);
                // [END_EXCLUDE]

            }



            @Override

            public void onError(FacebookException error) {

                Log.d(TAG, "facebook:onError", error);

                // [START_EXCLUDE]

                updateUI(null);

                // [END_EXCLUDE
            }

        });

    }



    public void openActivity(final FirebaseUser user) {

      final FirebaseUser user1=user;
        read(new Firebasecallback() {
            @Override
            public void onCallback(List<user> list) {
                int c=0;
                Toast.makeText(LoginActivity.this, "msg12",Toast.LENGTH_LONG).show();
                for (user e:list)
                {          // Toast.makeText(LoginActivity.this, "list",Toast.LENGTH_LONG).show();
                    if(e.mail.equals(user.getEmail().toString()))
                    {      //  Toast.makeText(LoginActivity.this, "msg5",Toast.LENGTH_LONG).show();
                        userg=new user(e.id,e.username,e.mail,e.url,e.phn,e.lat,e.lng);
                        c=1;
                        usname=e.username;
                        if(user.getPhotoUrl()==null)
                            url=null;
                        else
                            url=user.getPhotoUrl();
                        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("key",e.id);
                        edit.commit();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                }
                if(c==0) {
                    //Toast.makeText(LoginActivity.this, "msg6", Toast.LENGTH_LONG).show();
                    existingfb1(user1);
                }
            }
        });


    }


  void existingfb1(FirebaseUser user)
  {
      name = user.getEmail().toString();
      String img="null";
      usname=user.getDisplayName().toString();
      if(user.getPhotoUrl()==null)
      {
          img="null";
          url=null;
      }
      else
      {
          img=account.getPhotoUrl().toString();
          url=account.getPhotoUrl();
      }
      key1=db.push().getKey();
      userg=new user(key1,user.getDisplayName().toString(),name,img);
      db.child(key1).child("username").setValue(usname);
      db.child(key1).child("uid").setValue(key1);
      db.child(key1).child("mail").setValue(name);
      db.child(key1).child("url").setValue(img);
      preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
      SharedPreferences.Editor edit = preferences.edit();
      edit.putString("key",key1);
      edit.commit();
      Intent intent = new Intent(this, signup.class);
      intent.putExtra("name", user.getDisplayName()) ;
      intent.putExtra("email", user.getEmail());
      startActivity(intent);
  }
    private void existingfb(FirebaseUser user) {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent );
    }

    private void handleFacebooktoken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser myuser = auth.getCurrentUser();
                    updateUI(null);
                    sign_status=false;
                    openActivity(myuser);
                } else {
                    Toast.makeText(getApplicationContext(), "could not register to firebase", Toast.LENGTH_LONG).show();
                    updateUI(null);

                }
            }
        });

    }


    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
          //  Toast.makeText(LoginActivity.this,"google2" ,Toast.LENGTH_LONG ).show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //Toast.makeText(LoginActivity.this,"google3" ,Toast.LENGTH_LONG ).show();
            handleSignInResult(task);

        }
            else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void updateUI(FirebaseAuth user)
    {
        findViewById(R.id.login_button).setVisibility(View.GONE);
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {     // Toast.makeText(LoginActivity.this,"google4" ,Toast.LENGTH_LONG ).show();
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
        //  Toast.makeText(LoginActivity.this, "google5",Toast.LENGTH_LONG).show();
            // Signed in successfully, show authenticated UI.
            sign_status=true;
           // existing1(account);
           openActivity1(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
    public void signOut() {

        auth.signOut();
        LoginManager.getInstance().logOut();
    }

    public  void openActivity1(final GoogleSignInAccount account)
    {   final String email=account.getEmail().toString();
        final GoogleSignInAccount account1=account;
       // Toast.makeText(LoginActivity.this, "msg1",Toast.LENGTH_LONG).show();
            read(new Firebasecallback() {
                @Override
                public void onCallback(List<user> list) {
                    int c=0;
                  //  Toast.makeText(LoginActivity.this, "msg12",Toast.LENGTH_LONG).show();
                    for (user e:list)
                    {        //   Toast.makeText(LoginActivity.this, "list",Toast.LENGTH_LONG).show();
                        if(e.getMail().equals(email))
                        {      //  Toast.makeText(LoginActivity.this, "msg5",Toast.LENGTH_LONG).show();

                            c=1;
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putString("key",e.id);
                            edit.commit();
                            if(account.getPhotoUrl()==null)
                                url=null;
                            else
                               url=account.getPhotoUrl();
                            usname=e.username;
                            userg=new user(e.id,e.username,e.mail,e.url,e.phn,e.lat,e.lng);

                        }
                    }
                    if(c==0) {
                       // Toast.makeText(LoginActivity.this, "msg6", Toast.LENGTH_LONG).show();
                        existing2(account1);
                    }
                }
            });

    }




    public void existing2(final GoogleSignInAccount account1) {
        GoogleSignInAccount account=account1;
        name=account.getEmail();
        usname=account.getDisplayName();
        String img="null";
        if(account.getPhotoUrl()==null)
        {
            img="null";
            url=null;
        }
        else
        {   url=account.getPhotoUrl();
            img=account.getPhotoUrl().toString();
        }
        Toast.makeText(this,img ,Toast.LENGTH_LONG ).show();
        Intent intent = new Intent(this,signup.class);
        startActivity(intent);
        key1=db.push().getKey();
        userg=new user(key1,usname,name,img);
        db.child(key1).child("username").setValue(usname);
        db.child(key1).child("uid").setValue(key1);
        db.child(key1).child("mail").setValue(name);
        db.child(key1).child("url").setValue(img);
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("key",key1);
        edit.commit();
       // Toast.makeText(LoginActivity.this,"final" ,Toast.LENGTH_LONG ).show();

    }

    private void existing1(GoogleSignInAccount account) {
       name=account.getEmail().toString();
        usname=account.getDisplayName().toString();
        if(account.getPhotoUrl()==null) {
          //  Toast.makeText(LoginActivity.this, "null", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //userg=new user(account.getDisplayName().toString(),account.getEmail().toString(),account.getPhotoUrl().toString());
       // Toast.makeText(LoginActivity.this,"tesfinal" ,Toast.LENGTH_LONG ).show();

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
       // Toast.makeText(LoginActivity.this,"google0" ,Toast.LENGTH_LONG ).show();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.google:
                sign_status=true;
                signIn();
                break;
            // ...
            case  R.id.signout_button:
                signOut();
                break;
        }

    }
    public void read(final Firebasecallback firebasecallback){
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
                     // Toast.makeText(LoginActivity.this, "msg19",Toast.LENGTH_LONG).show();
                      String key=snapshot.getKey();
                      String mail = (String) snapshot.child("mail").getValue();
                      String url = (String) snapshot.child("url").getValue();
                      String name = (String) snapshot.child("username").getValue();
                      String phn=(String )snapshot.child("phn").getValue();
                      String lat=(String )snapshot.child("latitude").getValue();
                      String lng=(String )snapshot.child("longitude").getValue();
                      user user1 = new user(key,name, mail, url,phn,lat,lng);
                      list.add(user1);
                    //  Toast.makeText(LoginActivity.this, "msg20",Toast.LENGTH_LONG).show();

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
    public interface Firebasecallback {
      void onCallback(List<user> list);
    }


}