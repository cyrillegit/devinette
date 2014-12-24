package com.intelness.devinette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.intelness.globals.AppManager;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.splashactivity );

        // calling the application class
        final AppManager app = (AppManager) getApplicationContext();
        app.setCurrentId( -1 );

        new Handler().postDelayed( new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent( SplashActivity.this, HomeActivity.class );
                startActivity( i );
                finish();
            }
        }, SPLASH_TIME_OUT );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
