package br.com.rafaelvalverde.myweather.YahooWeather;

import org.json.JSONObject;

public class Channel implements JSON {
    private Item item;
    private Units units;

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public void json(JSONObject dados) {
        units = new Units();
        units.json( dados.optJSONObject( "units" ) );

        item = new Item();
        item.json( dados.optJSONObject( "item" ) );
    }
}