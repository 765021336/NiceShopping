package com.example.coolweather.mvp_test;

public class MvpPresenter {

    private final MvpModle mMvpModle;

    public MvpPresenter(){
        mMvpModle = new MvpModle();
    }

    public void getData(final ActivityInterface ai){
        mMvpModle.getData("sdf", new CallBackData<String,String>() {
            @Override
            public void onSuccess(String data) {
                ai.backData(data);
            }

            @Override
            public void onFail(String data) {

            }
        });
    }
}
