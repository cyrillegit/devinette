package com.intelness.devinette;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.intelness.beans.Devinette;
import com.intelness.db.DatabaseHandler;

public class HomeActivity extends Activity {
    Button                      btnPlay;
    Button                      btnScores;
    Button                      btnAbout;

    private boolean             countDown = false;
    private final static String TAG       = "HomeActivity";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.homeactivity );

        btnPlay = (Button) findViewById( R.id.btnPlay );
        btnScores = (Button) findViewById( R.id.btnScores );
        btnAbout = (Button) findViewById( R.id.btnAbout );

        onClickBtnPlay();
        onClickBtnAbout();
        onClickBtnScores();
    }

    @Override
    public void onBackPressed() {
        // When back button is clicked
        if ( countDown != true ) {
            // Notify the user to click a second time to exit
            Toast.makeText( this, R.string.back_btn_clicked, Toast.LENGTH_SHORT ).show();
            countDown = true;
        } else {
            // if the back button is clicked a second time
            finish();
        }
    }

    /**
     * Triggred when the button Play is clicked
     */
    private void onClickBtnPlay() {
        btnPlay.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                Intent i = new Intent( HomeActivity.this, DevinetteActivity.class );
                startActivity( i );
            }
        } );
    }

    /**
     * Triggered when the button Scores is clicked
     */
    private void onClickBtnScores() {
        btnScores.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                LoadDevinettes();
            }
        } );
    }

    /**
     * Triggered when the button About is clicked
     */
    private void onClickBtnAbout() {
        btnAbout.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                readDevinettes();
            }
        } );
    }

    private void LoadDevinettes() {
        DatabaseHandler db = new DatabaseHandler( this );

        Log.d( "Insert", "Inserting" );
        db.addDevinette( new Devinette( "Histoire d'elephant", "Elephant" ) );
        db.addDevinette( new Devinette( "Histoire de lion", "Lion" ) );
        db.addDevinette( new Devinette( "Histoire de singe", "Singe" ) );
        db.addDevinette( new Devinette( "Histoire de girafe", "Girafe" ) );
    }

    private void readDevinettes() {
        DatabaseHandler db = new DatabaseHandler( this );

        Log.d( "Reading", "Reading all devinettes" );
        List<Devinette> devinette = db.getAllDevinettes();
        for ( Devinette d : devinette ) {
            String log = "Id: " + d.getId() + " , Devinette: " + d.getDevinette() + " , Answer: " + d.getAnswer();
            Log.d( "Devinettes : ", log );
        }
        Log.d( TAG, "all devinettes: " + db.getNumberOfDevinettes() );
    }
}
