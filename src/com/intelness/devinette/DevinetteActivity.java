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
import com.intelness.beans.DevinetteDAO;
import com.intelness.globals.AppManager;

public class DevinetteActivity extends Activity {
    Button                           btnHint;
    Button                           btnValidate;

    AutoCompleteTextView             etAnswer;
    TextView                         tvDevinette;
    TextView                         tvScores;

    private String                   hintMessage           = "";
    private String                   answer                = "";
    // devinette current id
    private int                      currentId;
    // current devinette
    private Devinette                devinette;
    // current scores
    private int                      scores;

    AppManager                       app;
    private final static String      TAG                   = "DevinetteActivity";
    public final static String       PROCESS_ANSWER        = "processAnswer";
    private static final int         NUMBER_MAX_HINT       = 3;
    private static ArrayList<String> ANSWERS;
    private int                      number_hint           = 0;
    private final static int         MARK_WITHOUT_HINT     = 8;
    private final static int         MARK_WITH_FIRST_HINT  = 5;
    private final static int         MARK_WITH_SECOND_HINT = 3;
    private final static int         MARK_WITH_THIRD_HINT  = 2;
    private final static int         MARK_AFTER_THIRD_HINT = 1;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.devinetteactivity );

        // get the current id of deveinette from application class
        app = (AppManager) getApplicationContext();
        currentId = app.getCurrentId();
        scores = app.getScores();

        btnHint = (Button) findViewById( R.id.btnHint );
        btnValidate = (Button) findViewById( R.id.btnValidate );

        etAnswer = (AutoCompleteTextView) findViewById( R.id.etAnswer );
        tvDevinette = (TextView) findViewById( R.id.tvDevinette );
        tvScores = (TextView) findViewById( R.id.tvScores );
        tvScores.setText( getResources().getString( R.string.scores ) + String.valueOf( scores ) );

        // get all the answers of devinettes
        ANSWERS = getAllDevinettesAnswers();
        Log.i( TAG, "ANSWERS: " + ANSWERS.toString() );
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1,
                ANSWERS );

        // get the devinette to display
        devinette = getDevinetteToDisplay();

        etAnswer.setAdapter( adapter );

        number_hint = 0;
        onClickBtnHint();
        onClickValidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // get the devinette to display
        // devinette = getDevinetteToDisplay();
        // store the current id of devinette
        app = (AppManager) getApplicationContext();
        app.setCurrentId( (int) devinette.getId() );
        Log.i( TAG, "current id : " + devinette.getId() );
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

    /**
     * get hint message according to level
     * 
     * @param num
     * @return
     */
    private String getHint( int num ) {
        String hint = "";
        switch ( num ) {
        case 1:
            hint = devinette.getFirstHint();
            break;

        case 2:
            hint = devinette.getSecondHint();
            break;

        case 3:
            hint = devinette.getThirdHint();
            break;

        default:
            break;
        }
        Log.i( TAG, "num   , hint : " + num + "    ," + hint );
        return hint;
    }

    /**
     * 
     */
    private void openAlertBtnHint() {
        number_hint++;
        Log.i( TAG, "number_hint : " + number_hint );
        if ( number_hint > 0 && number_hint <= NUMBER_MAX_HINT ) {
            hintMessage = getHint( number_hint );
        } else {
            hintMessage = getResources().getString( R.string.number_hint_over );
        }
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

    /**
     * 
     */
    private void onClickValidate() {

        btnValidate.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                answer = (String) etAnswer.getText().toString();
                if ( TextUtils.isEmpty( answer ) ) {
                    Toast.makeText( getApplicationContext(), R.string.empty_answer, Toast.LENGTH_LONG ).show();
                } else {
                    int pointsAnswer = processAnswer( answer );

                    Bundle bundle = new Bundle();
                    bundle.putInt( PROCESS_ANSWER, pointsAnswer );

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
    private int processAnswer( String answer ) {
        int points = 0;
        if ( !TextUtils.isEmpty( answer ) ) {
            answer = answer.trim().toLowerCase( Locale.FRENCH );
            if ( answer.equalsIgnoreCase( ( devinette.getAnswer().trim() ) ) ) {
                switch ( number_hint ) {
                case 0:
                    scores += MARK_WITHOUT_HINT;
                    points = MARK_WITHOUT_HINT;
                    break;
                case 1:
                    scores += MARK_WITH_FIRST_HINT;
                    points = MARK_WITH_FIRST_HINT;
                    break;
                case 2:
                    scores += MARK_WITH_SECOND_HINT;
                    points = MARK_WITH_SECOND_HINT;
                    break;
                case 3:
                    scores += MARK_WITH_THIRD_HINT;
                    points = MARK_WITH_THIRD_HINT;
                    break;
                default:
                    points = MARK_AFTER_THIRD_HINT;
                    break;
                }
            } else {
                points = 0;
            }
        } else {
            points = 0;
        }
        app = (AppManager) getApplicationContext();
        app.setScores( scores );
        return points;
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
        DevinetteDAO dDao = new DevinetteDAO( this );
        int count = dDao.getNumberOfDevinettes();
        int devinetteId = generateRandomNumber( 0, count, currentId );
        Devinette devinette = dDao.getDevinetteById( devinetteId );
        return devinette;
    }

    /**
     * get answers of all devinettes
     * 
     * @return
     */
    private ArrayList<String> getAllDevinettesAnswers() {
        DevinetteDAO dDao = new DevinetteDAO( this );
        ArrayList<String> answers = new ArrayList<String>();
        Log.d( "Reading", "Reading all devinettes" );
        List<Devinette> devinette = dDao.getAllDevinettes();
        for ( Devinette d : devinette ) {
            answers.add( d.getAnswer().trim() );
        }
        return answers;
    }
}
