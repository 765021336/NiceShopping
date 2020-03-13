package com.example.coolweather.activitys;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coolweather.MainActivity;
import com.example.coolweather.R;
import com.example.coolweather.gson.ResultBean;
import com.example.coolweather.gson.WeatherBean;
import com.example.coolweather.net.RetrofitHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Response;


public class WeatherActivity extends AppCompatActivity {
    private static final String TAG="WeatherActivity";
    private String mWeather_id;
    private String mName;
    private TextView mTem_tv;
    private TextView mWind_tv;
    private TextView mSsd_tv;
    private TextView mXc_tv;
    private TextView mYd_tv;
    private WeatherActivity mMthis;
    private LinearLayout mLinearLayout;
    private TextView mUpdate_tv;
    private TextView mAqi_tv;
    private TextView mPm_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        mMthis = this;
        mWeather_id = getIntent().getStringExtra("weather_id");
        mName = getIntent().getStringExtra("name");
        initView();


    }

    private void initView() {
        TextView title=findViewById(R.id.textView3);
        mUpdate_tv = findViewById(R.id.textView4);

        mTem_tv = findViewById(R.id.wt_tem);
        mWind_tv = findViewById(R.id.wt_wind);
        mSsd_tv = findViewById(R.id.textView47);
        mXc_tv = findViewById(R.id.textView48);
        mYd_tv = findViewById(R.id.textView49);

        mAqi_tv = findViewById(R.id.textView42);
        mPm_tv = findViewById(R.id.textView43);

        mLinearLayout = findViewById(R.id.day_layout);

        title.setText(mName);
        queryProvince();
    }

    private void queryProvince() {
        RetrofitHelper retrofitHelper = new RetrofitHelper(WeatherActivity.this);
        retrofitHelper.getWeatherData(mWeather_id,"bc0418b57b2d4918819d3974ac1285d9")
                .subscribeOn(Schedulers.io())//IO线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(WeatherBean weathers) {
                        Log.i(TAG,new Gson().toJson(weathers));
                        Log.i(TAG,weathers.getHeWeather().get(0).daily_forecast.get(3).date);
                        //Log.i(TAG,""+getResponseBody(weathers));
                        mUpdate_tv.setText(weathers.HeWeather.get(0).update.loc.split(" ")[1]);
                        mAqi_tv.setText(weathers.HeWeather.get(0).aqi.aqi);
                        mPm_tv.setText(weathers.HeWeather.get(0).aqi.pm25);
                        mTem_tv.setText(weathers.HeWeather.get(0).now.tmp+"℃");
                        mWind_tv.setText(weathers.HeWeather.get(0).now.cond_txt
                                +" "+weathers.HeWeather.get(0).now.wind_dir
                                +weathers.HeWeather.get(0).now.wind_sc+"级");
                        mSsd_tv.setText("舒适度："+weathers.HeWeather.get(0).suggestion.getComf().txt);
                        mXc_tv.setText("洗车指数："+weathers.HeWeather.get(0).suggestion.getCw().txt);
                        mYd_tv.setText("运动建议："+weathers.HeWeather.get(0).suggestion.getSport().txt);

                        List<WeatherBean.HeWeathers.Baily> list=weathers.getHeWeather().get(0).daily_forecast;
                        for (int i = 0; i <list.size() ;i++) {
                            View view= LayoutInflater.from(mMthis).inflate(R.layout.forday_layout,mLinearLayout,false);
                            TextView textView5=view.findViewById(R.id.textView5);
                            TextView textView6=view.findViewById(R.id.textView6);
                            TextView textView7=view.findViewById(R.id.textView7);
                            TextView textView8=view.findViewById(R.id.textView8);
                            textView5.setText(list.get(i).getDate());
                            textView6.setText(list.get(i).cond.txt_d);
                            textView7.setText(list.get(i).tmp.max);
                            textView8.setText(list.get(i).tmp.min);
                            mLinearLayout.addView(view);
                        }

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,""+e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        Log.i(TAG,"请求完成");
                    }
                });
    }

    public static String getResponseBody(ResponseBody response) {

        Charset UTF8 = Charset.forName("UTF-8");
        BufferedSource source = response.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = response.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        return buffer.clone().readString(charset);
    }

}





















