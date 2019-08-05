package com.example.varunsai.myapplication;
import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.CameraPosition;
import com.example.varunsai.myapplication.dummy.DummyContent;
import com.example.varunsai.myapplication.dummy.DummyItem;
import com.example.varunsai.myapplication.clinic;
import com.google.zxing.client.result.BookmarkDoCoMoResultParser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.COMPANION_DEVICE_SERVICE;
import static com.example.varunsai.myapplication.ItemListActivity.mTwoPane;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener,AdapterView.OnItemSelectedListener{
    private MapView mMapView;
    ArrayList<String > a;
    ArrayList<String> clinicids=new ArrayList<String>();
    Double lat=17.380060;
    Double lng=78.382580;
    static String clinicname,docname,doclat,doclng,fees,apptime,dockey,clinickey,maxpatient,avgtime;
    private GoogleMap mMap;
    String addr;
    clinic clinic1;
    private Button button;
    static Uri pic;
    View rootView;
    int c;
    SupportMapFragment mapFragment;
    /*
     * The fragmbent argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyItem mItem;
    Button change;
    Spinner spec;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   Toast.makeText(getApplicationContext(),"lllll" ,Toast.LENGTH_LONG ).show();
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = splash.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
           // Toast.makeText(getApplicationContext(),mItem.name,Toast.LENGTH_LONG ).show();
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Activity activity=this.getActivity();
            final CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                   appBarLayout.setTitle(mItem.name);
                Glide
                        .with(getApplicationContext())
                        .load(mItem.url)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(200,300) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                Drawable dr = new BitmapDrawable(resource);
                                appBarLayout.setBackgroundDrawable(dr);
                                // Possibly runOnUiThread()
                            }
                        });
                 //appBarLayout.setCollapsedTitleTextColor(Color.BLACK);
               // ImageView img=(ImageView)appBarLayout.findViewById(R.id.pic);
                //TextView t1=(TextView)appBarLayout.findViewById(R.id.t1);
                //TextView t2=(TextView)appBarLayout.findViewById(R.id.t2);
                //TextView t3=(TextView)appBarLayout.findViewById(R.id.t3);
              //  t1.setText(mItem.name);
                //t2.setText(mItem.spec);
                //t3.setText(mItem.exp);
                pic=mItem.url;
                docname=mItem.name;
                dockey=mItem.id;
                Toast.makeText(getApplicationContext(),mItem.name ,Toast.LENGTH_LONG ).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_detail, container, false);
        spec=(Spinner)rootView.findViewById(R.id.clinic);
      //  Toast.makeText(getContext(),"frag77" ,Toast.LENGTH_LONG ).show();
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
       if(mapFragment==null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            mapFragment=SupportMapFragment.newInstance();
            transaction.replace(R.id.map,mapFragment ).commit();
        } 
       mapFragment.getMapAsync(this);
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ArrayList<clinic> list = mItem.clinics;
            a = new ArrayList<>();
            for (clinic clinic : list) {
                a.add(clinic.name+" - "+clinic.timings.get(0));
                clinicids.add(clinic.cid);
             //   Toast.makeText(getContext(), clinic.name, Toast.LENGTH_LONG).show();
            }
            ArrayAdapter<String> aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,a);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spec.setAdapter(aa);
            spec.setOnItemSelectedListener(this);
            //Toast.makeText(getContext(),"frag2" ,Toast.LENGTH_LONG ).show();
             final clinic clinic=mItem.clinics.get(0);
            ((TextView) rootView.findViewById(R.id.text_view2)).setText(mItem.spec);
            ((TextView) rootView.findViewById(R.id.text_view3)).setText(mItem.exp);
            ((TextView) rootView.findViewById(R.id.text_view4)).setText(clinic.fees);
            ((TextView) rootView.findViewById(R.id.text_view6)).setText(clinic.address);
           // Toast.makeText(getContext(),"frag3" ,Toast.LENGTH_LONG ).show();
            lat=new Double(clinic.lat);
            lng=new Double(clinic.lng);
            maxpatient= clinic.maxpatient;
            avgtime=clinic.avgtime;
            addr=clinic.address;
            clinicname=clinic.name;
            clinickey=clinic.cid;
            clinic1=clinic;
            docname=mItem.name;
            doclat=clinic.lat;
            doclng=clinic.lng;
            fees=clinic.fees;
            apptime=clinic.timings.get(0);
            pic=mItem.url;
             FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            mapFragment=SupportMapFragment.newInstance();
            transaction.replace(R.id.map,mapFragment ).commit();
             mapFragment.getMapAsync(this);
            ((TextView) rootView.findViewById(R.id.text_view7)).setText(clinic.phn);
            Toast.makeText(getContext(),"frag4" ,Toast.LENGTH_LONG ).show();
            ((TextView) rootView.findViewById(R.id.text_view7)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String numberToDial = clinic.phn;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));

                }
            });

        }
        
        return rootView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title(addr));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),16));

    }

    @Override
    public void onClick(View view) {
       Intent intent=new Intent(getActivity(),book.class);
        intent.putExtra("docid",mItem.id);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.clinic: {
               // String clinic_id= clinicids.get(i);
                clinickey=clinicids.get(i);
                for (clinic clinic : mItem.clinics) {
                    if (clinickey.equals(clinic.cid)) {
                        clinic1 = clinic;
                        break;
                    }
                }
                LatLng sydney = new LatLng(new Double(clinic1.lat), new Double(clinic1.lng));
                mMap.addMarker(new MarkerOptions().position(sydney).title(clinic1.address));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                ((TextView) rootView.findViewById(R.id.text_view4)).setText("Rs."+clinic1.fees);
                ((TextView) rootView.findViewById(R.id.text_view6)).setText(clinic1.address);
                ((TextView) rootView.findViewById(R.id.text_view7)).setText(clinic1.phn);
                lat=new Double(clinic1.lat);
                lng=new Double(clinic1.lng);
                maxpatient= clinic1.maxpatient;
                avgtime=clinic1.avgtime;
                addr=clinic1.address;
                clinicname=clinic1.name;
                docname=mItem.name;
                doclat=clinic1.lat;
                doclng=clinic1.lng;
                fees=clinic1.fees;
                pic=mItem.url;
                apptime=clinic1.timings.get(0);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                mapFragment=SupportMapFragment.newInstance();
                transaction.replace(R.id.map,mapFragment ).commit();
                mapFragment.getMapAsync(this);
                break;
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        clinic1=mItem.clinics.get(0);
        pic=mItem.url;
        lat=new Double(clinic1.lat);
        lng=new Double(clinic1.lng);
        maxpatient= clinic1.maxpatient;
        avgtime=clinic1.avgtime;
        addr=clinic1.address;
        clinicname=clinic1.name;
        docname=mItem.name;
        doclat=clinic1.lat;
        doclng=clinic1.lng;
        fees=clinic1.fees;
        apptime=clinic1.timings.get(0);

    }
    
}

