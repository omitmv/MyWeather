package br.com.rafaelvalverde.myweather.YahooWeather;

import org.json.JSONObject;

public class Condition implements JSON {
    private int iCode;
    private String sDate;
    private int iTemp;
    private String sText;

    public int getCode() {
        return iCode;
    }

    public String getDate() {
        return sDate;
    }

    public int getTemp() {
        return iTemp;
    }

    public String getText() {
        return sText;
    }

    @Override
    public void json(JSONObject dados) {
        iCode = dados.optInt( "code" );
        sDate = dados.optString( "date" );
        iTemp = dados.optInt( "temp" );
        sText = dados.optString( "text" );
    }
}