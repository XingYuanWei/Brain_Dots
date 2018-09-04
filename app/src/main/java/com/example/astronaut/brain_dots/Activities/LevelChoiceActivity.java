package com.example.astronaut.brain_dots.Activities;



/*
 *Created by 魏兴源 on 2018-07-14
 *Time at 12:39
 *
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.astronaut.brain_dots.Bean.LevelBean;
import com.example.astronaut.brain_dots.DAO.LevelDAO;
import com.example.astronaut.brain_dots.DAO.MoneyDAO;
import com.example.astronaut.brain_dots.Domain.LevelSelectRecyclerViewAdapter;
import com.example.astronaut.brain_dots.R;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.Utils.gameUtils.BackgroundMusicUtil;
import com.example.astronaut.brain_dots.View.componentShow.SettingDialog;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

@SuppressLint("Registered")
public class LevelChoiceActivity extends AppCompatActivity {
    //伸缩菜单
    private DrawerLayout drawerLayout;
    //RecyclerView的适配器
    public LevelSelectRecyclerViewAdapter adapter;
    //RecyclerView的数据
    public List<LevelBean> levelSelectList = new ArrayList<>();

    //选关界面的解锁图片和未解锁图片的数组
    public int[] lockedImage =
            {
                    R.drawable.level1_lock, R.drawable.level2_lock,
                    R.drawable.level3_lock, R.drawable.level4_lock,
                    R.drawable.level5_lock, R.drawable.level6_lock
            };
    public int[] unlockedImage =
            {
                    R.drawable.level1_unlock, R.drawable.level2_unlock,
                    R.drawable.level3_unlock, R.drawable.level4_unlock,
                    R.drawable.level5_unlock, R.drawable.level6_unlock
            };
    public int lock_default = R.drawable.lock_default;
    public int unlock_default = R.drawable.unlock_default;

    private LevelBean[] levels = {
            new LevelBean("1", unlockedImage[0], "level1", false),
            new LevelBean("2", lockedImage[1], "level2", true),
            new LevelBean("3", lockedImage[2], "level3", true),
            new LevelBean("4", lockedImage[3], "level4", true),
            new LevelBean("5", lockedImage[4], "level5", true),
            new LevelBean("6", lockedImage[5], "level6", true)
    };
    //关卡的数据访问对象
    public LevelDAO levelDAO;
    //金钱的数据访问对象
    public MoneyDAO moneyDAO;
    //显示金币数量的TextView
    public TextView goldNumTextView;

    //更新线程的Handler
    public static Handler levelChoiceHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //关卡,金钱数据访问对象
        levelDAO = new LevelDAO(this);
        moneyDAO = new MoneyDAO(this);

        //初始化所有关卡数据和金钱数据
//        levelDAO.initData();
//        moneyDAO.initMoneyData();

        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置为横屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //为Activity设置布局
        this.setContentView(R.layout.levelchoice_layout_activity);
        //获取到显示金币数量的textView

        /*
         *  标题设置以及左侧滑动菜单
         * */
        BackgroundMusicUtil.initSound(this);
        //播放背景音乐
        BackgroundMusicUtil.playBackGroundMusic(true);
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
            actionBar.setIcon(R.drawable.brain_dots);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        }

        //对伸缩导航条点击事件的处理
        NavigationView navigationView = findViewById(R.id.nav_view);
        //获取头布局文件
        View headerView = navigationView.getHeaderView(0);
        goldNumTextView = headerView.findViewById(R.id.gold_money_num);
        String gold_num = moneyDAO.getMoneyBean().getMoneyNum() + "";
        goldNumTextView.setText(gold_num);
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
        //使用默认的动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        initList();
        adapter = new LevelSelectRecyclerViewAdapter(levelSelectList, moneyDAO.getMoneyBean());
        recyclerView.setAdapter(adapter);

        levelChoiceHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Constant.SUB_GOLD_NUM_HANDLER:
                        String sub_new_gold_num = moneyDAO.getMoneyBean().getMoneyNum() + "";
                        goldNumTextView.setText(sub_new_gold_num);
                        break;
                    case Constant.ADD_GOLD_NUM_HANDLER:
                        String add_new_gold_num = moneyDAO.getMoneyBean().getMoneyNum() + "";
                        goldNumTextView.setText(add_new_gold_num);
                        break;

                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        //退出或暂停界面时 音乐也暂停
        BackgroundMusicUtil.playBackGroundMusic(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //如果此时背景音乐被打开 则重新打开界面的时候背景音乐也打开
        if (SettingDialog.soundFlag) {
            BackgroundMusicUtil.playBackGroundMusic(true);
        }
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
            case R.id.options:
                break;
            case R.id.setting:
                //弹出设置对话框
                SettingDialog dialog = new SettingDialog(this);
                dialog.show();
                break;
        }
        return true;
    }

    //初始化集合
    private void initList() {
        levelSelectList.clear();
        levelSelectList = levelDAO.getAllLevelInfo();
    }


    //该方法用于回调显示金钱数量
    //当两个球相碰之后,金钱+8,则返回到这个页面的时候更新一个金币数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.REQUEST_REFRESH_GOLD_NUM_CODE:
                levelChoiceHandler.sendEmptyMessage(Constant.ADD_GOLD_NUM_HANDLER);
                Log.e("Tag!!", "onActivityResult: " + "更新金币数据啦！！");
        }

    }
}
