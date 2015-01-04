package com.intelness.devinette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends Activity {

    private Button btnHome;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.aboutactivity );

        btnHome = (Button) findViewById( R.id.btnHome );
    }

    @Override
    protected void onStart() {
        super.onStart();
        onClickBtnHome();
    }

    private void onClickBtnHome() {
        btnHome.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                Intent i = new Intent( getApplicationContext(), HomeActivity.class );
                startActivity( i );
                finish();
            }
        } );
    }
}
