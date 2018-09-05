package com.example.astronaut.brain_dots.DAO;

/*
 *Created by 魏兴源 on 2018-09-02
 *Time at 16:35
 *
 */


import com.example.astronaut.brain_dots.Activities.LevelChoiceActivity;
import com.example.astronaut.brain_dots.Bean.LevelBean;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.logging.Level;

/**
 * 关卡信息的数据访问对象
 */
public class LevelDAO {
    LevelChoiceActivity activity;

    public LevelDAO(LevelChoiceActivity activity) {
        this.activity = activity;
    }

    //插入所有数据
    private void insertAllData() {
        LevelBean level1 = new LevelBean("1", activity.unlockedImage[0], "level1", false);
        LevelBean level2 = new LevelBean("2", activity.lockedImage[1], "level2", true);
        LevelBean level3 = new LevelBean("3", activity.lockedImage[2], "level3", true);
        LevelBean level4 = new LevelBean("4", activity.lockedImage[3], "level4", true);
        LevelBean level5 = new LevelBean("5", activity.lockedImage[4], "level5", true);
        LevelBean level6 = new LevelBean("6", activity.lockedImage[5], "level6", true);
        LevelBean level7 = new LevelBean("7", activity.lock_default, "level7", true);
        LevelBean level8 = new LevelBean("8", activity.lock_default, "level8", true);
        LevelBean level9 = new LevelBean("9", activity.lock_default, "level9", true);
        LevelBean level10 = new LevelBean("10", activity.lock_default, "level10", true);
        LevelBean level11 = new LevelBean("11", activity.lock_default, "level11", true);
        LevelBean level12 = new LevelBean("12", activity.lock_default, "level12", true);
        LevelBean level13 = new LevelBean("13", activity.lock_default, "level13", true);
        LevelBean level14 = new LevelBean("14", activity.lock_default, "level14", true);
        LevelBean level15 = new LevelBean("15", activity.lock_default, "level15", true);
        LevelBean level16 = new LevelBean("16", activity.lock_default, "level16", true);
        LevelBean level17 = new LevelBean("17", activity.lock_default, "level17", true);
        LevelBean level18 = new LevelBean("18", activity.lock_default, "level18", true);
        level1.save();
        level2.save();
        level3.save();
        level4.save();
        level5.save();
        level6.save();
        level7.save();
        level8.save();
        level9.save();
        level10.save();
        level11.save();
        level12.save();
        level13.save();
        level14.save();
        level15.save();
        level16.save();
        level17.save();
        level18.save();
    }

    //返回所有关卡List集合
    public List<LevelBean> getAllLevelInfo() {
        return DataSupport.findAll(LevelBean.class);
    }

    public void initData() {
        //删去所有数据
        DataSupport.deleteAll(LevelBean.class);
        //再次插入数据
        insertAllData();
    }
}
