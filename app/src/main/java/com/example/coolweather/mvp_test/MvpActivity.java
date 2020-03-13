package com.example.coolweather.mvp_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.coolweather.R;
import com.example.coolweather.service.ForegroundService;
import com.example.coolweather.service.MyService;


public class MvpActivity extends AppCompatActivity implements ActivityInterface{
    private static final String TAG = "MvpActivity";
    private Button mButton;
    private TextView mTextView;
    private MvpPresenter mPresenter;
    private Intent mIntent;
    private MyService mService;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        mButton = findViewById(R.id.button);
        mTextView = findViewById(R.id.textView10);
        initView();
    }

    private void initView() {
        //启动服务
        mIntent = new Intent(MvpActivity.this, ForegroundService.class);
        mPresenter = new MvpPresenter();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getData(MvpActivity.this);
                createPoPu();
                //启动服务
                //startService(mIntent);
                //bindService(mIntent,mConnection, Service.BIND_AUTO_CREATE);

                mIntent.putExtra("cmd",0);
                startService(mIntent);
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopService(mIntent);
                //Log.i(TAG,""+ mService.getCount());

                // 解除绑定
                /*if(mService!=null) {
                    mService = null;
                    unbindService(mConnection);
                }*/
                mIntent.putExtra("cmd",1);
                startService(mIntent);
            }
        });
    }

    @Override
    public void backData(String data) {
        mTextView.setText(data);
    }


    ServiceConnection mConnection=new ServiceConnection() {
        /**
         * 与服务器端交互的接口方法 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
         * 通过这个IBinder对象，实现宿主和Service的交互。
         */
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.LocalBinder binder= (MyService.LocalBinder) iBinder;
            mService = binder.getService();
        }
        /**
         * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
         * 例如内存的资源不足时这个方法才被自动调用。
         */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService=null;
        }
    };

    private void createPoPu(){
        View view= LayoutInflater.from(this).inflate(R.layout.popuwindow_layout,null);
        //宽高必须在代码中设置（透明度在布局中的根布局设置）
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT
                , WindowManager.LayoutParams.MATCH_PARENT);

        mPopupWindow.setTouchable(true);//设置是否能获取touch事件
        mPopupWindow.setFocusable(true);//设置能否获取焦点
        //外部是否可以点击
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());//必须设置采起作用
        mPopupWindow.setOutsideTouchable(true);

        TextView textView=view.findViewById(R.id.textView11);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        //设置动画
        mPopupWindow.setAnimationStyle(R.style.anim_popu);
        //显示
        View rootView=LayoutInflater.from(this).inflate(R.layout.activity_mvp,null);
        mPopupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
    }
}























