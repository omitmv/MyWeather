package br.com.rafaelvalverde.myweather.service;

import java.util.concurrent.ExecutionException;

import br.com.rafaelvalverde.myweather.YahooWeather.Channel;

public interface YahooWeatherCallback {
    void sucesso(Channel channel);
    void erro(Exception exception);
}