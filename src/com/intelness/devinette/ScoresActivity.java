package com.intelness.devinette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.intelness.globals.AppManager;

public class ScoresActivity extends Activity {

    private Button     btnRestart;
    private TextView   tvScores;
    private AppManager app;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.scoresactivity );

        app = (AppManager) getApplicationContext();
        int scores = app.getScores();

        btnRestart = (Button) findViewById( R.id.btnRestart );
        tvScores = (TextView) findViewById( R.id.tvScores );

        tvScores.setText( getResources().getString( R.string.scores ) + "  " + scores + "   " );

    }

    @Override
    protected void onStart() {
        super.onStart();

        onClickBtnRestart();
    }

    private void onClickBtnRestart() {

        btnRestart.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                Intent i = new Intent( getApplicationContext(), SplashActivity.class );
                startActivity( i );
                finish();
            }
        } );
    }
}
