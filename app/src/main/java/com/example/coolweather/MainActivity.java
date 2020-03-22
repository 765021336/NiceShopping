package com.example.coolweather;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.coolweather.activitys.BaseActivity;
import com.example.coolweather.fragments.ClassifyFragment;
import com.example.coolweather.fragments.HomeFragment;
import com.example.coolweather.fragments.MyFragment;
import com.example.coolweather.fragments.ShoppingFragment;

import androidx.fragment.app.FragmentTabHost;
import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_tabhost)
    FragmentTabHost mFragmentTabHost;
    private String tabName[] = {"首页", "分类", "购物车", "我的"};
    private int imageButton[] = {R.drawable.selector_icon_home,
            R.drawable.selector_icon_category, R.drawable.selector_icon_cart, R.drawable.selector_icon_mine};
    private Class fragmentArray[] = {HomeFragment.class, ClassifyFragment.class, ShoppingFragment.class
            , MyFragment.class};


    @Override
    protected int getContentResourseId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.content_fragment);
        for(int i =0; i<tabName.length; i++) {
            TabHost.TabSpec spec = mFragmentTabHost.newTabSpec(tabName[i]).setIndicator(getView(i));

            mFragmentTabHost.addTab(spec, fragmentArray[i], null);

            //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
            //mFragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.bt_selector);
        }
        //去掉分隔线
        mFragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mFragmentTabHost.setCurrentTab(0);//选中第一个
    }


    private View getView(int i) {
        //取得布局实例
        View view = View.inflate(MainActivity.this, R.layout.tabhost_content, null);

        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.tabhost_content_img);
        TextView textView = (TextView) view.findViewById(R.id.tabhost_content_text);

        //设置图标
        imageView.setImageResource(imageButton[i]);
        //设置标题
        textView.setText(tabName[i]);
        return view;
    }
}


















