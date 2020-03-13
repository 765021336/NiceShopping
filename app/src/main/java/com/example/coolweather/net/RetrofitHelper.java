package com.example.coolweather.net;

import android.content.Context;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.WeatherBean;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    Context mContext;
    public RetrofitHelper(Context context){
        mContext = context;
    }

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20000, TimeUnit.MINUTES)
            .writeTimeout(20000, TimeUnit.MINUTES)
            .readTimeout(20000, TimeUnit.MINUTES)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://guolin.tech/api/")//基地址
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    Net_api serviceApi = retrofit.create(Net_api.class);

    public Observable<ArrayList<Province>> getData(){
        return serviceApi.postProvince();
    }
    public Observable<ArrayList<City>> getCityData(String id){
        return serviceApi.postCity(id);
    }
    public Observable<ArrayList<County>> getCountyData(String id,String id2){
        return serviceApi.postCounty(id,id2);
    }

    public Observable<WeatherBean> getWeatherData(String id, String key){
        return serviceApi.postWeather(id,key);
    }
}

