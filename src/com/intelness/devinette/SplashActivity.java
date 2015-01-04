package com.intelness.devinette;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.intelness.beans.Devinette;
import com.intelness.beans.DevinetteDAO;
import com.intelness.globals.AppManager;
import com.intelness.utils.XMLParser;

public class SplashActivity extends Activity {
    public static final String TAG             = "SplashActivity";
    private static int         SPLASH_TIME_OUT = 3000;

    static final String        TEXT            = "text";
    static final String        ANSWER          = "answer";
    static final String        FIRST_HINT      = "first_hint";
    static final String        SECOND_HINT     = "second_hint";
    static final String        THIRD_HINT      = "third_hint";
    static final String        DESC_ANSWER     = "desc_answer";

    List<Devinette>            devinettes;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.splashactivity );

        // calling the application class
        final AppManager app = (AppManager) getApplicationContext();
        app.setCurrentId( -1 );
        app.setScores( 0 );

        // new Handler().postDelayed( new Runnable() {
        //
        // @Override
        // public void run() {
        // Intent i = new Intent( SplashActivity.this, HomeActivity.class );
        // startActivity( i );
        // finish();
        // }
        // }, SPLASH_TIME_OUT );
        // db = new DatabaseHandler( this );

        // Log.i( TAG, "start oncreate loading" );
        // loadDB();
        // Log.i( TAG, "finish oncreate loading" );
        new loadDevinettes().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class loadDevinettes extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground( Void... params ) {

            try {
                Thread.sleep( SPLASH_TIME_OUT );
            } catch ( InterruptedException e ) {
                Thread.interrupted();
            }
            Log.i( TAG, "start loading" );
            loadDB();
            Log.i( TAG, "finish loading" );
            return null;
        }

        @Override
        protected void onCancelled() {
        }

        @Override
        protected void onPostExecute( Void result ) {

            Intent i = new Intent( SplashActivity.this, HomeActivity.class );
            startActivity( i );
            finish();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate( Void... values ) {
        }
    }

    /**
     * load devinettes from xml file to db
     */
    public void loadDB() {

        DevinetteDAO dDao = new DevinetteDAO( this );
        int rows = dDao.getNumberOfDevinettes();
        Log.i( TAG, "rows : " + rows );

        XMLParser parser = new XMLParser();
        devinettes = parser.parse( getResources().openRawResource( R.raw.devinettes ) );

        for ( int i = 0; i < devinettes.size(); i++ ) {
            String name = devinettes.get( i ).getDevinette();
            // if db is empty, store every devinettes that is in xml file in db
            if ( rows <= 0 ) {
                dDao.addDevinette( devinettes.get( i ) );
                Log.i( TAG, "if : " + name );
                // else, check to not store two same devinette
            } else if ( !dDao.getDevinetteByName( name ).getDevinette().equals( name ) ) {
                Log.i( TAG, "else if : " + name );
                dDao.addDevinette( devinettes.get( i ) );
            }
        }
    }
}
