package com.example.coolweather.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coolweather.R;
import com.example.coolweather.utils.EnjoyshopToolBar;
import com.google.android.material.tabs.TabLayout;

/**
 * ***************************
 * des
 * @author zdn
 * 深圳市优讯信息技术有限公司
 * @time 2020/3/14 19:43.
 * ***************************
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.toolbar)
    EnjoyshopToolBar mToolBar;
    @BindView(R.id.tabLayout)
    TabLayout mLayout;
    @BindView(R.id.viewPage)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);

        // 关联TabLayout与ViewPager，且适配器必须重写getPageTitle()方法
        mLayout.setupWithViewPager(mViewPager);

        mLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getActivity(),tab.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Toast.makeText(getActivity(), "onTabUnselected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @OnClick(R.id.toolbar_searchview)
    public void setToolBar(){
        Toast.makeText(getActivity(), "登录了里面", Toast.LENGTH_SHORT).show();
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        private final String[] title = new String[]{
                "推荐", "热点", "视频", "深圳", "通信",
                "互联网", "问答", "图片", "电影",
                "网络安全", "软件"};

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", title[i]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}






















