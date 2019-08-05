package com.example.varunsai.myapplication;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AndroidException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.varunsai.myapplication.dummy.DummyContent;
import com.example.varunsai.myapplication.dummy.DummyItem;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Locale;

import static com.example.varunsai.myapplication.ItemDetailFragment.ARG_ITEM_ID;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {
    private static int count = 0;
    static String getitemid;
    static  String docname,docid,fees;
    public static List<DummyItem> mValues = new ArrayList<DummyItem>();
    ;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    AppBarLayout appBarLayout;
    static int Count = 0;
    Context context;
    static List<DummyItem> mValue;
    static View recyclerView;
    static SimpleItemRecyclerViewAdapter ss;
    LayoutInflater inflater;
    static ArrayList<DummyItem> arraylist;
    static String condition = null;
    static EditText editText;
    public static int c = 0;
    static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        editText = (EditText) findViewById(R.id.inputsearch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final Intent intent = getIntent();
        condition = intent.getStringExtra("sp");
      //  appBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        //getActionBar().setTitle(condition);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemListActivity.this, MainActivity.class));
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        arraylist = (ArrayList<DummyItem>) splash.items;
        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                if (editText.getText().toString() == "" || editText.getText().toString() == null || count == 0) {   // ItemListActivity.count--;
                    setupRecyclerView((RecyclerView) recyclerView);
                    count++;

                } else {
                    String text = editText.getText().toString().toLowerCase(Locale.getDefault());
                    ss.filter(text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        ss = new SimpleItemRecyclerViewAdapter(this, splash.items, mTwoPane);
        recyclerView.setAdapter(ss);

    }

    public void setup(RecyclerView recyclerView, List<DummyItem> items, int a) {
        ss = new SimpleItemRecyclerViewAdapter(this, items, mTwoPane, a);
        recyclerView.setAdapter(ss);

    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
        Context context;
        private ItemListActivity mParentActivity = null;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyItem item = (DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ARG_ITEM_ID, item.name);
                    //arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID,item.bitmap);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    getitemid=item.name;
                    intent.putExtra(ARG_ITEM_ID, item.name);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<DummyItem> items,
                                      boolean twoPane) {
            final int size = mValues.size();
            mValues.clear();
            notifyItemRangeRemoved(0, size);
            for (DummyItem e : items) {
                if (e.spec.equals(condition)&&(!e.clinics.isEmpty())) {
                    ItemListActivity.mValues.add(e);
                }

            }
            mParentActivity = parent;
            mTwoPane = twoPane;
            notifyDataSetChanged();
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            int size = mValues.size();
            mValues.clear();
            notifyItemRangeRemoved(0, size);
            if (charText == null) {
                mValues.addAll(arraylist);
            } else {
                for (DummyItem wp : ItemListActivity.arraylist) {
                    if ((wp.name.toLowerCase(Locale.getDefault()).contains(charText)) && (wp.spec.equals(condition))) {
                        mValues.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<DummyItem> items,
                                      boolean twoPane, int a) {

            int size = mValues.size();
            mValues.clear();
            notifyItemRangeRemoved(0, size);
            for (DummyItem e : items) {
                if ((e.spec.equals(condition))&&(!e.clinics.isEmpty())) {
                    ItemListActivity.mValues.add(e);
                }

            }
            mParentActivity = parent;
            mTwoPane = twoPane;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            DummyItem item;
            int c = 0;
            if ((item = ItemListActivity.mValues.get(position)).spec.equals(condition)) {
                for (DummyItem dm : mValues) {
                    if (dm.name.equals(item.name)) {
                        if (c != 0) {
                            mValues.remove(dm);
                        }
                        c++;
                    }

                }
                holder.mIdView.setText("Dr. "+(docname=mValues.get(position).name));
                if(mValues.get(position).url!=null) {
                   /* Glide.with(getApplicationContext())
                            .load(mValues.get(position).url)
                            .into(holder.imageview);    */
                    Glide.with(getApplicationContext()).load(mValues.get(position).url)
                            .crossFade()
                            .thumbnail(0.5f)
                            .bitmapTransform(new CircleTransform(getApplicationContext()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.imageview);
                }
                holder.t2.setText("Experience: "+mValues.get(position).exp);
                holder.mContentView.setText(mValues.get(position).spec);
                holder.itemView.setTag(mValues.get(position));
                holder.itemView.setOnClickListener(mOnClickListener);
            }

        }

        @Override
        public int getItemCount() {
            return (ItemListActivity.mValues.size());
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView, t2, t3;
            final TextView mContentView;
            final ImageView imageview;


            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.content);
                imageview = (ImageView) view.findViewById(R.id.imageView);
                t2 = (TextView) view.findViewById(R.id.textView2);
                t3 = (TextView) view.findViewById(R.id.textView3);
                mContentView = (TextView) view.findViewById(R.id.id_text);
            }
        }


    }

}
