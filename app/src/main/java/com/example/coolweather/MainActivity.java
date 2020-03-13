package com.example.coolweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.activitys.CityActivity;
import com.example.coolweather.adapter.ProvinceAdapter;
import com.example.coolweather.db.City;
import com.example.coolweather.db.Province;
import com.example.coolweather.mvp_test.MvpActivity;
import com.example.coolweather.net.RetrofitHelper;
import com.google.gson.Gson;

import org.litepal.LitePal;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private RecyclerView mRecyclerView;
    private ArrayList<String> mDatas=new ArrayList<>();
    private List<Province> mList;
    private ProvinceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        TextView textView=findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MvpActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView = findViewById(R.id.province_list);

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //初始化分隔线、添加分隔线（系统自带）
        DividerItemDecoration mDivider = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDivider);

        mAdapter = new ProvinceAdapter(MainActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ProvinceAdapter.OnRecyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                intent.putExtra("id",String.valueOf(position+1));
                intent.putExtra("name",mDatas.get(position));
                startActivity(intent);
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });



        mList= LitePal.findAll(Province.class);
        if(mList.size()>0){
            Log.i(TAG,"执行数据库");
           mDatas.clear();
           for (int i = 0; i < mList.size();i++) {
                mDatas.add(mList.get(i).getName());
           }
           mAdapter.notifyDataSetChanged();
        }else {
            queryProvince();
        }



    }

    private void queryProvince() {
        RetrofitHelper retrofitHelper = new RetrofitHelper(MainActivity.this);
        retrofitHelper.getData()
                .subscribeOn(Schedulers.io())//IO线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Province>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Province> provinces) {
                        for (int i = 0; i < provinces.size();i++) {
                            provinces.get(i).save();//保存到数据库
                            mDatas.add(provinces.get(i).getName());
                            Log.i(TAG,provinces.get(i).getName());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }
}
