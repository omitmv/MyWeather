package br.com.rafaelvalverde.myweather.YahooWeather;

import org.json.JSONObject;

public class Item implements JSON {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void json(JSONObject dados) {
        condition = new Condition();
        condition.json( dados.optJSONObject( "condition" ) );
    }
}