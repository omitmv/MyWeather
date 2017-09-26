# MyWeather
Projeto desenvolvido no Android Studio, GPS do Google Service e Yahoo Weather.

Aplicativo realiza uma conexão com o serviço do Yahoo Weather para recuperar dados pertinentes a previsão utilizando a localização do aparelho.

Etapas

1. Liberar permissões

Editar o Andoid Manifest
  ...
  <uses-permission android:name="android.permission.INTERNET"/>
  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
  ...

Editar o build.gradle(Module: app)
  ...
  compile 'com.google.android.gms:play-services:11.0.4'
  ...

2. Resgatar posição atual do aparelho

Implementar a biblioteca do "GoogleApiClient" à activity
  ... 
  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
  ...

3. Realizar requisição ao servidor do Yahoo Weather
  Para isso criei as seguintes class para tratar os dados JSON retornados :
  - Channel;
    - Item;
      -Condition;
    - Units;
  Criei também a Classe que realiza a conexão (YahooWeather) e uma Interface com os retornos (YahooWeatherCallback).
