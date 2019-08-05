package com.example.varunsai.myapplication;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
public class tab1 extends Fragment implements View.OnClickListener {


    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.text5);
        tv.setOnClickListener(this);
        TextView tv1 = (TextView) rootView.findViewById(R.id.text6);
        tv1.setOnClickListener(this);
        TextView tv2 = (TextView) rootView.findViewById(R.id.text7);
        tv2.setOnClickListener(this);
        TextView tv3 = (TextView) rootView.findViewById(R.id.text8);
        tv3.setOnClickListener(this);
        TextView tv4 = (TextView) rootView.findViewById(R.id.text8);
        tv4.setOnClickListener(this);
        TextView tv5 = (TextView) rootView.findViewById(R.id.text9);
        tv5.setOnClickListener(this);
        TextView tv6 = (TextView) rootView.findViewById(R.id.text10);
        tv6.setOnClickListener(this);
        TextView tv7 = (TextView) rootView.findViewById(R.id.text11);
        tv7.setOnClickListener(this);
        return rootView;
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.text5:
                Intent intent = new Intent(getActivity(), ItemListActivity.class);
                intent.putExtra("sp", "Cardiologist");
                startActivity(intent);
                break;
            case R.id.text6:
                Intent intent1 = new Intent(getActivity(), ItemListActivity.class);
                intent1.putExtra("sp", "Dentist");
                startActivity(intent1);
                break;
            case R.id.text7:
                Intent intent2 = new Intent(getActivity(), ItemListActivity.class);
                intent2.putExtra("sp", "General Physician");
                startActivity(intent2);
                break;
            case R.id.text8:
                Intent intent3 = new Intent(getActivity(), ItemListActivity.class);
                intent3.putExtra("sp", "Orthopaedician");
                startActivity(intent3);
                break;
            case R.id.text9:
                Intent intent4 = new Intent(getActivity(), ItemListActivity.class);
                intent4.putExtra("sp", "Pediatrician");
                startActivity(intent4);
                break;
            case R.id.text10:
                Intent intent5 = new Intent(getActivity(), ItemListActivity.class);
                intent5.putExtra("sp", "Dermetalogist");
                startActivity(intent5);
                break;
            case R.id.text11:
                Intent intent6 = new Intent(getActivity(), ItemListActivity.class);
                intent6.putExtra("sp", "Neurologist");
                startActivity(intent6);
                break;


        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
