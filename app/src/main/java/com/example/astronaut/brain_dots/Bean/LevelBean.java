package com.example.astronaut.brain_dots.Bean;

/*
 *Created by 魏兴源 on 2018-07-26
 *Time at 22:26
 *
 */

import java.io.Serializable;

public class LevelBean implements Serializable{
    private String levelID;
    private int levelImage;
    private String levelName;

    public LevelBean(String levelID, int levelImage, String levelName) {
        this.levelID = levelID;
        this.levelImage = levelImage;
        this.levelName = levelName;
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
