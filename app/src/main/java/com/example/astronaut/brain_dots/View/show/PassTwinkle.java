package com.example.astronaut.brain_dots.View.show;

/*
 *Created by 魏兴源 on 2018-08-28
 *Time at 10:17
 *
 */


import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.astronaut.brain_dots.Activities.GameViewActivity;

/**
 *
 * 本类用于画出通关过后的小星星
 *  @x x坐标
 *  @y y坐标
 * */
public class PassTwinkle {
    private float x;
    private float y;

    public PassTwinkle(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void drawStar(Canvas canvas, GameViewActivity activity, Paint paint){
        if (canvas != null){
            canvas.save();
            canvas.drawBitmap(activity.pictures[0],x,y,paint);
        }
    }
}
