package com.example.coolweather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.R;
import com.example.coolweather.db.Province;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProvinceAdapter extends RecyclerView.Adapter {
    private static final String TAG="ProvinceAdapter";
    private Context mContext;
    private ArrayList<String> mDatas;
    private OnRecyItemClickListener mOnItemClickListener;//声明接口
    private NormalHolder mNormalHolder;

    public ProvinceAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view=inflater.inflate(R.layout.item_layout, parent, false);

        return new NormalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        mNormalHolder = (NormalHolder) holder;
        mNormalHolder.mTV.setText(mDatas.get(position));

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        public TextView mTV;
        public NormalHolder(View itemView) {
            super(itemView);
            mTV =itemView.findViewById(R.id.item_tv);
        }
    }

    public interface OnRecyItemClickListener{//通过接口实现点击事件
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnRecyItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
