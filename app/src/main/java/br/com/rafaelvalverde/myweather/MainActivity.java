package br.com.rafaelvalverde.myweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txtTemperatura;
    private TextView txtMunicipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        txtTemperatura = ( TextView ) findViewById( R.id.txtTemperatura );
        txtMunicipio = ( TextView ) findViewById( R.id.txtMunicipio );

        txtMunicipio.setText( getIntent().getStringExtra( "cMunicipio" ) );
        txtTemperatura.setText( String.valueOf( getIntent().getIntExtra( "cTemperatura", 0 ) ) + " ºC" );
    }
}