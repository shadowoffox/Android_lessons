package com.example.testapp;

public class Weather {
    private String res;
    private int weatherResource;

    public Weather(String res, int weth){

        this.res=res;
        this.weatherResource=weth;
    }

    public String getRes() {
        return this.res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public int getWeatherResource() {
        return this.weatherResource;
    }

    public void setWeatherResource(int weatherResource) {
        this.weatherResource = weatherResource;
    }
}
