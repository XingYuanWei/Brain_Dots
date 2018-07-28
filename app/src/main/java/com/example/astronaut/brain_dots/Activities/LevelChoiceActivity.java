package com.example.astronaut.brain_dots.Activities;



/*
 *Created by 魏兴源 on 2018-07-14
 *Time at 12:39
 *
 */

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.astronaut.brain_dots.Bean.LevelBean;
import com.example.astronaut.brain_dots.Domain.LevelSelectRecyclerViewAdapter;
import com.example.astronaut.brain_dots.R;
;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("Registered")
public class LevelChoiceActivity extends AppCompatActivity {
    //伸缩菜单
    private DrawerLayout drawerLayout;
    //RecyclerView的适配器
    LevelSelectRecyclerViewAdapter adapter;
    //RecyclerView的数据
    private List<LevelBean> levelSelectList = new ArrayList<>();

    private LevelBean[] levels = {
            new LevelBean("1", R.drawable.level1, "level1"),
            new LevelBean("2", R.drawable.level2, "level2"),
            new LevelBean("3", R.drawable.level3, "level3"),
            new LevelBean("4", R.drawable.level4, "level4"),
            new LevelBean("5", R.drawable.level5, "level5"),
            new LevelBean("6", R.drawable.level6, "level6")
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置为横屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //为Activity设置布局
        this.setContentView(R.layout.levelchoice_layout_activity);

        /**
         *  标题设置以及左侧滑动菜单
         * */

        //设置Material Design的标题栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //获取DrawerLayout控件
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //HomeAsUp控件
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.brain_dots2);
//            actionBar.setIcon(R.drawable.brain_dots);
        }

        //对伸缩导航条点击事件的处理
        NavigationView navigationView = findViewById(R.id.nav_view);
        //默认选择这项
        navigationView.setCheckedItem(R.id.level_1_6);
        //导航中选项被选择事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });


        /**
         * 为RecyclerView设置Adapter
         * */

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        initList();
        adapter = new LevelSelectRecyclerViewAdapter(levelSelectList);
        recyclerView.setAdapter(adapter);


    }

    //显示标题中一些项目
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    //初始化集合
    private void initList(){
        levelSelectList.clear();
        levelSelectList.addAll(Arrays.asList(levels));
    }
}
