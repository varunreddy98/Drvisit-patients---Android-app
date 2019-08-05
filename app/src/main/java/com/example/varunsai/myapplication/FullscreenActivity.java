package  com.example.varunsai.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class FullscreenActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_fullscreen);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                    Intent mainIntent = new Intent(FullscreenActivity.this,MainActivity.class);
                    FullscreenActivity.this.startActivity(mainIntent);
                    FullscreenActivity.this.finish();
                }
        }, SPLASH_DISPLAY_LENGTH);
    }
}