package com.example.astronaut.brain_dots.View.gameShow;

/*
 *Created by 魏兴源 on 2018-08-28
 *Time at 23:59
 *
 */


import android.graphics.Canvas;
import android.graphics.Paint;

/**
 *
 * 该类用于显示游戏失败后的图标
 *
 * */
public class UnPassIcon {

    private float x;
    private float y;

    public UnPassIcon(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void drawUnSmile(Canvas canvas, ShowResultSurfaceView resultView, Paint paint){
        if (canvas != null){
            canvas.save();
            canvas.drawBitmap(resultView.pictures[1],x,y,paint);
        }
    }
}
