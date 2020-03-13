package com.example.coolweather.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private LocalBinder mLocalBinder=new LocalBinder();
    private int mCount;

    /**
     * des
     * 把bindr返回给客户端。
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return mLocalBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //模拟数据
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                // 每间隔一秒count加1 ，直到quit为true。
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCount++;
                }
            }
        });
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"command--flags===="+flags);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"destroy");
    }

    /**
     * des
     *创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口
     */
    public class LocalBinder extends Binder{
        // 声明一个方法，getService。（提供给客户端调用）
       public MyService getService() {
            // 返回当前对象LocalService,这样我们就可在客户端端调用Service的公共方法了
            return MyService.this;
        }

    }

    /**
     * 公共方法
     * @return
     */
    public int getCount(){
        return mCount;
    }

    /**
     * des
     * 解除绑定
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}






















