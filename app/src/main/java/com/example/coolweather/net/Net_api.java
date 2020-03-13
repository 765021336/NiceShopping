package com.example.coolweather.net;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.WeatherBean;

import java.util.ArrayList;

import io.reactivex.Observable;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Net_api {
    @GET("china")
    Observable<ArrayList<Province>> postProvince();

    @GET("china/{id}")
    Observable<ArrayList<City>> postCity(@Path("id") String value);
    @GET("china/{id}/{id2}")
    Observable<ArrayList<County>> postCounty(@Path("id") String value,@Path("id2") String value2);
    @GET("weather")
    Observable<WeatherBean> postWeather(@Query("cityid") String value, @Query("key") String key);
}
