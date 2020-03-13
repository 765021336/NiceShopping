package com.example.coolweather.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.MainActivity;
import com.example.coolweather.R;
import com.example.coolweather.adapter.ProvinceAdapter;
import com.example.coolweather.db.City;
import com.example.coolweather.db.Province;
import com.example.coolweather.net.RetrofitHelper;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CityActivity extends AppCompatActivity {
    private static final String TAG="CityActivity";
    private RecyclerView mRecyclerView;
    private String mId;
    private String mName;

    private ArrayList<String> mDatas=new ArrayList<>();
    private List<City> mList;
    private ProvinceAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);
        Intent intent=getIntent();
        mId = intent.getStringExtra("id");
        mName = intent.getStringExtra("name");
        mRecyclerView = findViewById(R.id.city_list);
        TextView textView=findViewById(R.id.city_title);
        textView.setText(mName);

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //初始化分隔线、添加分隔线（系统自带）
        DividerItemDecoration mDivider = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDivider);

        mAdapter = new ProvinceAdapter(CityActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ProvinceAdapter.OnRecyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CityActivity.this, CountyActivity.class);
                intent.putExtra("id",String.valueOf(mList.get(position).getIds()));
                intent.putExtra("name",mDatas.get(position));
                intent.putExtra("provinceId",mId);
                startActivity(intent);
                //Toast.makeText(CityActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });

        mList= LitePal.where("provinceId=?",mId).find(City.class);
        if(mList.size()>0){
            Log.i(TAG,"city执行数据库");
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
        RetrofitHelper retrofitHelper = new RetrofitHelper(CityActivity.this);
        retrofitHelper.getCityData(mId)
                .subscribeOn(Schedulers.io())//IO线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<City>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<City> cities) {
                        mList=cities;
                        for (int i = 0; i < cities.size();i++) {
                            cities.get(i).setProvinceId(Integer.parseInt(mId));
                            cities.get(i).save();//保存到数据库
                            mDatas.add(cities.get(i).getName());
                            Log.i(TAG,cities.get(i).getName()+"==="+cities.get(i).getIds());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,""+e.toString());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }
}
