package com.example.coolweather.mvp_test;

public interface CallBackData<k,v> {
    void onSuccess(k data);
    void onFail(v data);
}
