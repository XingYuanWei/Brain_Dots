package com.example.astronaut.brain_dots.Bean;

/*
 *Created by 魏兴源 on 2018-07-26
 *Time at 22:26
 *
 */


import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class LevelBean extends DataSupport implements Serializable {
    private String levelID;
    private int levelImage;
    private String levelName;
    //这个关卡是否已经解锁
    private boolean isLocked;
    //下一关地图文件的名称
    private String nextLevelMapName;

    public LevelBean(String levelID, int levelImage, String levelName, boolean isLocked) {
        this.levelID = levelID;
        this.levelImage = levelImage;
        this.levelName = levelName;
        this.isLocked = isLocked;
    }


    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }


    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public int getLevelImage() {
        return levelImage;
    }

    public void setLevelImage(int levelImage) {
        this.levelImage = levelImage;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
