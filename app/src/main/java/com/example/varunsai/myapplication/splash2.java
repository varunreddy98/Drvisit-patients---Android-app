package com.example.varunsai.myapplication;
import android.app.Activity;
        import android.content.Intent;
import android.os.Bundle;
        import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Locale;

public class splash2 extends Activity implements TextToSpeech.OnInitListener{

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private TextToSpeech tts;
    int id;
    String speak;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_bookapp);
        tts = new TextToSpeech(this, this);
        ImageView imageView=(ImageView)findViewById(R.id.imageview33);
        Glide.with(this).load(R.drawable.checker).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(imageView);
      //  Glide.with(this).load(R.drawable.checker).into(imageView);
        //Intent intent=getIntent();
        //id=intent.getIntExtra("docid", 0);
      //  String nam=intent.getStringExtra("name");
        //DummyItem item = null;
        //ArrayList<DummyItem> dm= new ArrayList<DummyItem>() ;
       // dm.addAll(DummyContent.ITEMS);
      /*  ImageView imageView=(ImageView)findViewById(R.id.splash2);
        Glide.with(this).load(ItemDetailFragment.pic)
                .into(imageView);
        TextView t1=(TextView)findViewById(R.id.tvt1);
        t1.setText("Dr."+ItemDetailFragment.docname);
      //  TextView t2=(TextView)findViewById(R.id.tvt2) ;  */

        speak="Appointment booked";
        speakOut();
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(splash2.this,MainActivity.class);
                    startActivity(mainIntent);
                    splash2.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.UK);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

        tts.speak(speak.toString(), TextToSpeech.QUEUE_FLUSH, null);
    }
}