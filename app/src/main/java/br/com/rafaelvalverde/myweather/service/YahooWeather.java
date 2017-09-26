package br.com.rafaelvalverde.myweather.service;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import br.com.rafaelvalverde.myweather.YahooWeather.Channel;

public class YahooWeather {
    private YahooWeatherCallback callback;
    private String localizacao;
    private Exception erro;

    public YahooWeather( YahooWeatherCallback callback ){
        this.callback = callback;
    }

    public String getLocalizacao(){
        return localizacao;
    }

    public void refreshWeater(final String localizacao ){
        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                String YQL = String.format( "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", localizacao );
                String endpoint = String.format( "https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode( YQL ) );
                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader( inputStream ) );
                    StringBuilder result = new StringBuilder();
                    String line;
                    while( ( line = bufferedReader.readLine() ) != null ){
                        result.append( line );
                    }
                    return result.toString();
                }catch ( Exception e ){
                    erro = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if( s == null && erro != null ){
                    callback.erro( erro );
                    return;
                }
                try{
                    JSONObject dados = new JSONObject( s );
                    JSONObject qry = dados.optJSONObject( "query" );
                    int i = qry.optInt( "count" );
                    if( i == 0 ){
                        callback.erro( new YahooWeatherException( "Nenhum registro encontrado para a localização: " + localizacao ) );
                        return;
                    }else{
                        Channel channel = new Channel();
                        channel.json( qry.optJSONObject( "results" ).optJSONObject( "channel" ) );
                        callback.sucesso( channel );
                    }
                }catch ( Exception e ){
                    e.printStackTrace();
                }
            }
        }.execute( localizacao );
    }

    public class YahooWeatherException extends Exception{
        public YahooWeatherException(String msg){
            super( msg );
        }
    }
}