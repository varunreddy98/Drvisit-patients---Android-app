
    package com.example.varunsai.myapplication;
    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.drawable.BitmapDrawable;
    import android.graphics.drawable.Drawable;
    import android.net.Uri;
    import android.os.Build;
    import android.os.Parcel;
    import android.os.Parcelable;
    import android.preference.PreferenceManager;
    import android.support.annotation.NonNull;
    import android.support.annotation.Nullable;
    import android.support.annotation.RequiresApi;
    import android.support.design.widget.TabLayout;
    import android.support.design.widget.FloatingActionButton;
    import android.support.design.widget.Snackbar;
    import android.support.v4.content.ContextCompat;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;

    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.app.FragmentPagerAdapter;
    import android.support.v4.view.ViewPager;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;
    import java.io.*;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.widget.Toast;

    import java.net.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.Executor;

    import com.bumptech.glide.Glide;
    import com.bumptech.glide.load.Transformation;
    import com.bumptech.glide.load.engine.DiskCacheStrategy;
    import com.example.varunsai.myapplication.LoginActivity;
    import com.facebook.login.Login;
    import com.facebook.login.LoginManager;
    import com.firebase.client.Firebase;
    import com.google.android.gms.common.util.DbUtils;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import static android.os.Build.VERSION_CODES.JELLY_BEAN;
    import static android.os.Build.VERSION_CODES.LOLLIPOP;
    import static com.facebook.FacebookSdk.getApplicationContext;

    public class tab3 extends Fragment implements View.OnClickListener{
                                   String a,b;
                                   String key;
                                   TextView t3;
                                  String previouslyStarted="null";
                                   user user1;
                                   static boolean status=false;
                                   ImageView profile_photo;
                                   DatabaseReference db;
                                   private String pref_userName;
                                   SharedPreferences preferences;
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.tab3, container, false);
            Button button = (Button) rootView.findViewById(R.id.button2);
            final TextView t1 = (TextView) rootView.findViewById(R.id.textView2);
            profile_photo = (ImageView) rootView.findViewById(R.id.imageView2);
           //db=FirebaseDatabase.getInstance().getReference("patient");
           //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
           //previouslyStarted=prefs.getString("key","null" );
           //Toast.makeText(getActivity(),previouslyStarted ,Toast.LENGTH_LONG ).show();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    open(rootView);
                }
            });

               t1.setText(LoginActivity.usname);
              if (!LoginActivity.userg.url.equals("null")) {
                    Glide.with(getApplicationContext())
                            .load(Uri.parse(LoginActivity.userg.url))
                            .into(profile_photo);
                } else {
                    profile_photo.setBackgroundResource(R.drawable.com_facebook_profile_picture_blank_portrait);
                }

               /* read(new LoginActivity.Firebasecallback() {
                    @Override
                    public void onCallback(List<user> list) {
                       // Toast.makeText(getActivity(), "msg12",Toast.LENGTH_LONG).show();
                        for (user e:list)
                        {          // Toast.makeText(LoginActivity.this, "list",Toast.LENGTH_LONG).show();
                            if(previouslyStarted.equals(e.uid))
                            {      //  Toast.makeText(LoginActivity.this, "msg5",Toast.LENGTH_LONG).show();
                                LoginActivity.userg=new user(e.uid,e.username,e.mail,e.url);
                                LoginActivity.usname=e.username;
                                preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                SharedPreferences.Editor edit = preferences.edit();
                                edit.putString("key",e.uid);
                                edit.commit();
                                t1.setText(LoginActivity.usname);
                                if (!LoginActivity.userg.url.equals("null")) {
                                    Uri url = Uri.parse(LoginActivity.userg.url);
                                    Glide.with(getApplicationContext())
                                            .load(url)
                                            .into(profile_photo);
                                } else {
                                    profile_photo.setBackgroundResource(R.drawable.com_facebook_profile_picture_blank_portrait);
                                }

                            }
                        }

                        }

                });   */
            return rootView;
        }
        public void open(View view){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Are you sure,You wanted to LOGOUT");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //FirebaseAuth.getInstance().signOut();
                            splash.c=0;
                           // FirebaseAuth auth = FirebaseAuth.getInstance();
                           // Toast.makeText(getActivity(),"mag3" ,Toast.LENGTH_LONG ).show();
                            //auth.signOut();
                            if (LoginActivity.sign_status == false) {
                               // Toast.makeText(getActivity(),"mag6" ,Toast.LENGTH_LONG ).show();
                                LoginManager.getInstance().logOut();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                            } else {
                                //Toast.makeText(getActivity(),"mag9" ,Toast.LENGTH_LONG ).show();
                                LoginActivity.mGoogleSignInClient.signOut()
                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // ...
                                            }
                                        });
                                LoginActivity.mGoogleSignInClient.revokeAccess()
                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // ...
                                            }
                                        });
                            }
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_LONG).show();
                            SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getContext());
                            SharedPreferences.Editor edit = prefs.edit();
                            edit.putBoolean("isFirstRun", Boolean.FALSE);
                            edit.commit();
                            splash.c=0;
                        }
                        });
            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        @Override
        public void onClick(View view) {
                    open(view);
        }


        public void read(final LoginActivity.Firebasecallback firebasecallback){
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
                            Toast.makeText(getActivity(), "msg",Toast.LENGTH_LONG).show();
                            String key=snapshot.getKey();
                            String mail = (String) snapshot.child("mail").getValue();
                            String url = (String) snapshot.child("url").getValue();
                            String name = (String) snapshot.child("username").getValue();
                            user user1 = new user(key,name, mail, url);
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

    }
