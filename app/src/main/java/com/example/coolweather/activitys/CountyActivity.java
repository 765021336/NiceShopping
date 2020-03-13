package com.example.coolweather.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.coolweather.R;
import com.example.coolweather.adapter.ProvinceAdapter;
import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.net.RetrofitHelper;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class CountyActivity extends AppCompatActivity {
    private static final String TAG="CountyActivity";
    private String mId;
    private String mName;
    private RecyclerView mRecyclerView;

    private ArrayList<String> mDatas=new ArrayList<>();
    private List<County> mList;
    private ProvinceAdapter mAdapter;
    private String mProvinceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
        Intent intent=getIntent();
        mId = intent.getStringExtra("id");
        mName = intent.getStringExtra("name");
        mProvinceId = intent.getStringExtra("provinceId");
        mRecyclerView = findViewById(R.id.county_list);
        TextView textView=findViewById(R.id.textView2);
        textView.setText(mName);

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //初始化分隔线、添加分隔线（系统自带）
        DividerItemDecoration mDivider = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDivider);

        mAdapter = new ProvinceAdapter(CountyActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ProvinceAdapter.OnRecyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CountyActivity.this, WeatherActivity.class);
                intent.putExtra("weather_id",mList.get(position).getWeather_id());
                intent.putExtra("name",mDatas.get(position));
                startActivity(intent);

            }
        });

        mList= LitePal.where("cityId=?",mId).find(County.class);
        if(mList.size()>0){
            Log.i(TAG,"county执行数据库");
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
        RetrofitHelper retrofitHelper = new RetrofitHelper(CountyActivity.this);
        retrofitHelper.getCountyData(mProvinceId,mId)
                .subscribeOn(Schedulers.io())//IO线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<County>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG,mProvinceId+"/"+mId);
                    }
                    @Override
                    public void onNext(ArrayList<County> countys) {
                        mList=countys;
                        for (int i = 0; i < countys.size();i++) {
                            countys.get(i).setCityId(Integer.parseInt(mId));
                            countys.get(i).save();//保存到数据库
                            mDatas.add(countys.get(i).getName());
                            Log.i(TAG,countys.get(i).getName()+""+countys.get(i).getId());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,""+e.getMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }
}
