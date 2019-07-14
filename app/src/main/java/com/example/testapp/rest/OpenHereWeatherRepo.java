package com.example.testapp.rest;

import com.example.testapp.rest.entities.IHereOpenWeather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenHereWeatherRepo {
    private static OpenHereWeatherRepo singleton = null;
    private IHereOpenWeather API;

    private OpenHereWeatherRepo() {
        API = createAdapter();
    }

    public static OpenHereWeatherRepo getSingleton() {
        if(singleton == null) {
            singleton = new OpenHereWeatherRepo();
        }

        return singleton;
    }

    public IHereOpenWeather getAPI() {
        return API;
    }

    private IHereOpenWeather createAdapter() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return adapter.create(IHereOpenWeather.class);
    }
}
