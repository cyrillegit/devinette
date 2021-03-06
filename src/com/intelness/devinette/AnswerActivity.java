package com.intelness.devinette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.intelness.globals.AppManager;

public class AnswerActivity extends Activity {

    Button             btnQuit;
    Button             btnContinue;

    private int        pointsAnswer;
    private int        scores;
    private AppManager app;
    private TextView   tvScores;
    private TextView   tvPointsGained;
    private TextView   tvDescAnswer;
    private String     descAnswer = "";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.answeractivity );

        // get the current id of deveinette from application class
        app = (AppManager) getApplicationContext();
        scores = app.getScores();
        btnQuit = (Button) findViewById( R.id.btnQuit );
        btnContinue = (Button) findViewById( R.id.btnContinue );
        tvPointsGained = (TextView) findViewById( R.id.tvPointsGained );
        tvScores = (TextView) findViewById( R.id.tvScores );
        tvDescAnswer = (TextView) findViewById( R.id.tvDescriptionAnswer );

    }

    @Override
    protected void onStart() {
        super.onStart();
        tvScores.setText( getResources().getString( R.string.scores ) + String.valueOf( scores ) );

        onClickBtnQuit();
        onClickBtnContinue();

        if ( getBundleAnswer() == null ) {
            Toast.makeText( getApplicationContext(), "Null Bundle", Toast.LENGTH_LONG ).show();
            finish();
        }

        pointsAnswer = getBundleAnswer().getInt( DevinetteActivity.PROCESS_ANSWER );
        descAnswer = getBundleAnswer().getString( DevinetteActivity.DESC_ANSWER );

        tvPointsGained.setText( getResources().getString( R.string.pointsGained ) + pointsAnswer );
        displayAnswer( pointsAnswer );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * triggered when button quit is clicked
     */
    private void onClickBtnQuit() {
        btnQuit.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                Intent i = new Intent( AnswerActivity.this, HomeActivity.class );
                startActivity( i );
                finish();
            }
        } );
    }

    /**
     * triggered when button continue is clicked
     */
    private void onClickBtnContinue() {
        btnContinue.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                Intent i = new Intent( AnswerActivity.this, DevinetteActivity.class );
                startActivity( i );
                finish();
            }
        } );
    }

    /**
     * retrieve a bundle
     * 
     * @return
     */
    private Bundle getBundleAnswer() {
        Bundle bundle = null;
        if ( this.getIntent() == null ) {
            return null;
        }

        bundle = this.getIntent().getExtras();
        if ( bundle == null ) {
            return null;
        }
        return bundle;
    }

    /**
     * display a response according to the giving answer
     * 
     * @param answer
     */
    private void displayAnswer( int answer ) {
        tvDescAnswer.setText( descAnswer );
        if ( answer > 0 ) {
            Toast.makeText( getApplicationContext(), "Bonne reponse", Toast.LENGTH_LONG ).show();
        } else {
            Toast.makeText( getApplicationContext(), "Mauvaise reponse", Toast.LENGTH_LONG ).show();
        }
    }
}
