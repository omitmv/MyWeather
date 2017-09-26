package br.com.rafaelvalverde.myweather;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.rafaelvalverde.myweather.YahooWeather.Channel;
import br.com.rafaelvalverde.myweather.YahooWeather.Item;
import br.com.rafaelvalverde.myweather.service.YahooWeather;
import br.com.rafaelvalverde.myweather.service.YahooWeatherCallback;

public class SplashScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, YahooWeatherCallback {
    private GoogleApiClient googleApiClient;
    private static int TEMPO_SPLASH = 3000;
    private int cTemperatura;
    private String cMunicipio;

    private YahooWeather yahooWeather;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );

        callConnection();
    }

    private synchronized void callConnection() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog alertDialog = new AlertDialog.Builder( this ).create();
            alertDialog.setTitle( "GPS" );
            alertDialog.setMessage( "Aplicativo sem permissão para acesso ao GPS." );
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.show();
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if( location != null ){
            List<Address> list = new ArrayList<Address>();
            Geocoder geocoder = new Geocoder( this, Locale.getDefault() );
            try {
                list = ( ArrayList<Address> ) geocoder.getFromLocation( location.getLatitude(), location.getLongitude(), 1 );
                Log.i( "cLatitude ", String.valueOf( location.getLatitude() ) );
                Log.i( "cLongitude ", String.valueOf( location.getLongitude() ) );
                if( list != null ){
                    Address a = list.get( 0 );
                    yahooWeather = new YahooWeather( this );
                    cMunicipio = a.getLocality() + " - " + a.getAdminArea();

                    dialog = new ProgressDialog( this );
                    dialog.setTitle( "Aguarde" );
                    dialog.setMessage( "Carregando..." );
                    dialog.show();

                    yahooWeather.refreshWeater( cMunicipio );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder( this ).create();
            alertDialog.setTitle( "GPS" );
            alertDialog.setMessage( "GPS desabilitado." );
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i( "LOG", "onConnectionSuspended(" + i + ")" );
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i( "LOG", "onConnectionFailed(" + connectionResult + ")" );
    }

    @Override
    public void sucesso(Channel channel) {
        dialog.dismiss();
        Item item = channel.getItem();
        cTemperatura = item.getCondition().getTemp();
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent( SplashScreen.this, MainActivity.class );
                i.putExtra( "cMunicipio", cMunicipio );
                i.putExtra( "cTemperatura", cTemperatura );
                startActivity( i );

                finish();
            }
        }, TEMPO_SPLASH );
    }

    @Override
    public void erro(Exception exception) {
        dialog.dismiss();
        Toast.makeText( this, exception.getMessage(), Toast.LENGTH_LONG ).show();
    }
}