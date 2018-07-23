package com.example.astronaut.brain_dots.Bean;

/*
 *Created by 魏兴源 on 2018-07-22
 *Time at 15:05
 *
 */

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class LevelBean extends DataSupport implements Serializable{
    private String[] obstaclesName;
    private String posX_ratio;
    private String poxY_ratio;
    private String height_ratio;
    private String width_ratio;

    public String[] getObstaclesName() {
        return obstaclesName;
    }

    public void setObstaclesName(String[] obstaclesName) {
        this.obstaclesName = obstaclesName;
    }

    public String getPosX_ratio() {
        return posX_ratio;
    }

    public void setPosX_ratio(String posX_ratio) {
        this.posX_ratio = posX_ratio;
    }

    public String getPoxY_ratio() {
        return poxY_ratio;
    }

    public void setPoxY_ratio(String poxY_ratio) {
        this.poxY_ratio = poxY_ratio;
    }

    public String getHeight_ratio() {
        return height_ratio;
    }

    public void setHeight_ratio(String height_ratio) {
        this.height_ratio = height_ratio;
    }

    public String getWidth_ratio() {
        return width_ratio;
    }

    public void setWidth_ratio(String width_ratio) {
        this.width_ratio = width_ratio;
    }
}
