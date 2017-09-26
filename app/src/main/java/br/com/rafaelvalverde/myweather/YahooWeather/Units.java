package br.com.rafaelvalverde.myweather.YahooWeather;

import org.json.JSONObject;

public class Units implements JSON {
    private String sDistance;
    private String sPressure;
    private String sSpeed;
    private String sTemperature;

    public String getsDistance() {
        return sDistance;
    }

    public String getsPressure() {
        return sPressure;
    }

    public String getsSpeed() {
        return sSpeed;
    }

    public String getsTemperature() {
        return sTemperature;
    }

    @Override
    public void json(JSONObject dados) {
        sDistance = dados.optString( "distance" );
        sPressure = dados.optString( "pressure" );
        sSpeed = dados.optString( "speed" );
        sTemperature = dados.optString( "temperature" );
    }
}