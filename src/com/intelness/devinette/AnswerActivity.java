package com.intelness.devinette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AnswerActivity extends Activity {

    Button          btnQuit;
    Button          btnContinue;

    private boolean aProcessAnswer;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.answeractivity );

        btnQuit = (Button) findViewById( R.id.btnQuit );
        btnContinue = (Button) findViewById( R.id.btnContinue );

        onClickBtnQuit();
        onClickBtnContinue();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if ( getBundleAnswer() == null ) {
            Toast.makeText( getApplicationContext(), "Null Bundle", Toast.LENGTH_LONG ).show();
            finish();
        }

        aProcessAnswer = getBundleAnswer().getBoolean( DevinetteActivity.PROCESS_ANSWER );
        Toast.makeText( getApplicationContext(), "Boolean Bundle : " + aProcessAnswer, Toast.LENGTH_LONG ).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }

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
}
