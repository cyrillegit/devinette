package com.intelness.devinette;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DevinetteActivity extends Activity {
    Button                     btnHint;
    Button                     btnValidate;

    EditText                   etAnswer;

    private String             hintMessage    = "Hint message";
    private String             answer         = "";

    public final static String PROCESS_ANSWER = "processAnswer";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.devinetteactivity );

        btnHint = (Button) findViewById( R.id.btnHint );
        btnValidate = (Button) findViewById( R.id.btnValidate );

        etAnswer = (EditText) findViewById( R.id.etAnswer );

        onClickBtnHint();
        onClickValidate();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        super.onStart();
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
                boolean aProcessAnswer = processAnswer();

                Bundle bundle = new Bundle();
                bundle.putBoolean( PROCESS_ANSWER, aProcessAnswer );

                Intent intent = new Intent( getApplicationContext(), AnswerActivity.class );
                intent.putExtras( bundle );
                startActivity( intent );
                finish();
            }
        } );
    }

    private boolean processAnswer() {
        answer = (String) etAnswer.getText().toString();
        if ( TextUtils.isEmpty( answer ) ) {
            return false;
        } else {
            return true;
        }
    }

}
