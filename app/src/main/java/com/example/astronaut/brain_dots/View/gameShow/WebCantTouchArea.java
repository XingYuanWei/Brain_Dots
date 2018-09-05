package com.example.astronaut.brain_dots.View.gameShow;

/*
 *Created by 魏兴源 on 2018-09-04
 *Time at 23:01
 *
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 此类用于描述在游戏界面时候,不能用手绘制线条的区域
 * 就是手绘制的过程是不会有反应的
 */
public class WebCantTouchArea {
    private float startX = 0;
    private float startY = 0;
    private float width = 0;
    private float height = 0;

    public WebCantTouchArea(float startX, float startY, float width, float height) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    public void drawWebArea(Canvas canvas, Bitmap bitmap, Paint paint) {
        if (canvas != null) {
            canvas.drawBitmap(bitmap, startX, startY, paint);
        }
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
