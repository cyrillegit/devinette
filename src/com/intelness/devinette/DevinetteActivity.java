package com.intelness.devinette;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.intelness.beans.Devinette;
import com.intelness.db.DatabaseHandler;
import com.intelness.globals.AppManager;

public class DevinetteActivity extends Activity {
    Button                           btnHint;
    Button                           btnValidate;

    AutoCompleteTextView             etAnswer;
    TextView                         tvDevinette;

    private String                   hintMessage    = "Hint message";
    private String                   answer         = "";
    // devinette current id
    private int                      currentId;
    // current devinette
    Devinette                        devinette;

    AppManager                       app;
    private final static String      TAG            = "DevinetteActivity";
    public final static String       PROCESS_ANSWER = "processAnswer";
    private static ArrayList<String> ANSWERS;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.devinetteactivity );

        // get the current id of deveinette from application class
        app = (AppManager) getApplicationContext();
        currentId = app.getCurrentId();

        btnHint = (Button) findViewById( R.id.btnHint );
        btnValidate = (Button) findViewById( R.id.btnValidate );

        etAnswer = (AutoCompleteTextView) findViewById( R.id.etAnswer );
        tvDevinette = (TextView) findViewById( R.id.tvDevinette );

        ANSWERS = getAllDevinettesAnswers();
        Log.i( TAG, "ANSWERS: " + ANSWERS.toString() );
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_dropdown_item_1line,
                ANSWERS );

        etAnswer.setAdapter( adapter );

        onClickBtnHint();
        onClickValidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // get the devinette to display
        devinette = getDevinetteToDisplay();
        // store the current id of devinette
        app = (AppManager) getApplicationContext();
        app.setCurrentId( (int) devinette.getId() );
        Log.e( TAG, "current id : " + devinette.getId() );
        // display the devinette
        tvDevinette.setText( (String) devinette.getDevinette() );
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    private void onClickBtnHint() {
        btnHint.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                openAlertBtnHint();
            }
        } );
    }

    private void openAlertBtnHint() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( DevinetteActivity.this );
        alertDialogBuilder.setTitle( R.string.hint_title );
        alertDialogBuilder.setMessage( hintMessage );

        alertDialogBuilder.setNeutralButton( R.string.close, new DialogInterface.OnClickListener() {

            @Override
            public void onClick( DialogInterface dialog, int which ) {
                dialog.dismiss();
            }
        } );

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void onClickValidate() {

        btnValidate.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                answer = (String) etAnswer.getText().toString();
                if ( TextUtils.isEmpty( answer ) ) {
                    Toast.makeText( getApplicationContext(), R.string.empty_answer, Toast.LENGTH_LONG ).show();
                } else {
                    boolean aProcessAnswer = processAnswer( answer );

                    Bundle bundle = new Bundle();
                    bundle.putBoolean( PROCESS_ANSWER, aProcessAnswer );

                    Intent intent = new Intent( getApplicationContext(), AnswerActivity.class );
                    intent.putExtras( bundle );
                    startActivity( intent );
                    finish();
                }
            }
        } );
    }

    /**
     * Check if the answer is correct
     * 
     * @return boolean
     */
    private boolean processAnswer( String answer ) {
        boolean res = false;
        if ( !TextUtils.isEmpty( answer ) ) {
            answer = answer.trim().toLowerCase( Locale.FRENCH );
            if ( answer.equals( devinette.getAnswer().trim().toLowerCase( Locale.FRENCH ) ) ) {
                res = true;
            } else {
                res = false;
            }
        } else {
            res = false;
        }
        return res;
    }

    /**
     * Generate a random number between min and max and different from currentId
     * 
     * @param min
     * @param max
     * @param currentId
     * @return
     */
    private int generateRandomNumber( int min, int max, int currentId ) {
        int nb;
        Random r = new Random();
        if ( currentId < 0 ) {
            nb = r.nextInt( max - min + 1 ) + min;
        } else {
            do {
                nb = r.nextInt( max - min + 1 ) + min;
            } while ( nb == currentId );
        }
        return nb;
    }

    /**
     * Get the devinette to display
     * 
     * @return
     */
    private Devinette getDevinetteToDisplay() {
        DatabaseHandler db = new DatabaseHandler( this );
        int count = db.getNumberOfDevinettes();
        int devinetteId = generateRandomNumber( 0, count, currentId );
        Devinette devinette = db.getDevinette( devinetteId );
        return devinette;
    }

    private ArrayList<String> getAllDevinettesAnswers() {
        DatabaseHandler db = new DatabaseHandler( this );
        ArrayList<String> answers = new ArrayList<String>();
        Log.d( "Reading", "Reading all devinettes" );
        List<Devinette> devinette = db.getAllDevinettes();
        for ( Devinette d : devinette ) {
            answers.add( d.getAnswer().trim() );
        }
        return answers;
    }
}
