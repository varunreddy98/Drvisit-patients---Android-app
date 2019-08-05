package com.example.varunsai.myapplication.dummy;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.media.Image;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.example.varunsai.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static  int COUNT=0;

    public static void addItem(DummyItem dm) {
        ITEMS.add(dm);
        ITEM_MAP.put(dm.name,dm);
        COUNT++;
    }


}


/**
 * A dummy item representing a piece of content.
 */
