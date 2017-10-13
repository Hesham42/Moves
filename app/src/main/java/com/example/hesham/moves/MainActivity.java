package com.example.hesham.moves;

import android.content.Context;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hesham.moves.Utilities.InternetConnection;
import com.example.hesham.moves.Utilities.MoviesAPI;
import com.example.hesham.moves.model.modelaLLmovesdata.MovesModel;
import com.example.hesham.moves.model.modelaLLmovesdata.ResultModel;
import com.example.hesham.moves.page.Favourit;
import com.example.hesham.moves.page.Popular;
import com.example.hesham.moves.page.TopRate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    MoviesAPI moviesAPI;
    MovesModel PoplarModel;
    MovesModel TopRateModel;

    Toolbar toolbar;
    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    TabLayout.Tab tab;
    List<ResultModel> PopularResult = new ArrayList<>();
    List<ResultModel> TopRateResult = new ArrayList<>();
    List<ResultModel> Favourit = new ArrayList<>();
    public static final String API_KEY = BuildConfig.API_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: 10/13/2017  search about semaphore or Mutex excution and remove sleep
        CallApi();
        init();
        Log.e("Guinness", "API KEY " +API_KEY);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void CallApi() {
        if (InternetConnection.checkConnection(MainActivity.this)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesAPI = retrofit.create(MoviesAPI.class);


            Call<MovesModel> PopularRecall = moviesAPI.getAllMovesPopular();
            PopularRecall.enqueue(new Callback<MovesModel>() {
                @Override
                public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                    if (response.isSuccessful()) {
                        PoplarModel = response.body();
                        Log.e("Guinness", "p main" + PoplarModel.toString());

                        PopularResult = PoplarModel.getResults();
//                        Log.e("Guinness", response.toString());

                    } else {
                        Log.d("Guinness", " the respons code of popular " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {
                    Log.d("Guinness", "Respons get onFailure popular");

                }
            });


            Call<MovesModel> TopRate = moviesAPI.getAllMovestop_rated();
            TopRate.enqueue(new Callback<MovesModel>() {
                @Override
                public void onResponse(Call<MovesModel> call, Response<MovesModel> response) {
                    if (response.isSuccessful()) {
                        TopRateModel = response.body();
                        Log.e("Guinness", "top " + TopRateModel.toString());

                        TopRateResult = TopRateModel.getResults();
//                        Log.d("Guinness", response.toString());

                    } else {
                        Log.d("Guinness", " the respons code of TopRate " + response.code());

                    }

                }

                @Override
                public void onFailure(Call<MovesModel> call, Throwable t) {
                    Log.d("Guinness", "Respons get onFailure TopRate");

                }
            });
        }
    }


    private void init() {
        SystemClock.sleep(2000);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        Log.e("Guiness", String.valueOf(tabLayout.getTabCount()));
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Pouplar) {
            viewPager.setCurrentItem(0);
        }
        if (id == R.id.Favourit) {
            viewPager.setCurrentItem(1);

        }
        if (id == R.id.TopRate) {
            viewPager.setCurrentItem(2);

        }

        return super.onOptionsItemSelected(item);

    }


    class PagerAdapter extends FragmentPagerAdapter {
        String tabTitles[] = new String[]{"popular", "Favourit", "TopRate"};
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {

                fragment = new Popular();
                Log.e("Guinness", "to popular page");
//                return fragment;

            } else if (position == 1) {

                fragment = new Favourit();
                Log.e("Guinness", "to favorit page");

//                return fragment;
            } else if (position == 2) {
                fragment = new TopRate();
                Log.e("Guinness", "to toprate page");

//                return fragment;
            }


            return fragment;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }
    }

    public List<ResultModel> getPopularResult() {
        return PopularResult;
    }

    public List<ResultModel> getTopRateResult() {
        return TopRateResult;
    }

    public List<ResultModel> getFavourit() {
        return Favourit;
    }


}
