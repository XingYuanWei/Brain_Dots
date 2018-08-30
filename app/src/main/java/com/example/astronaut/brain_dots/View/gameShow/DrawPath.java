package com.example.astronaut.brain_dots.View.gameShow;

/*
 *Created by 魏兴源 on 2018-07-11
 *Time at 23:02
 *
 */


/**
 * 此类用于封装画线的路径和画笔
 * 具体作用和原理如下：
 *  里面有Path对象和画笔对象
 *  当手指滑动的时候,生成这两个对象,组成新的DrawPath对象,
 *  把这个组合对象添加到集合中,遍历集合,绘出轨迹
 *
 * */
import android.graphics.Paint;
import android.graphics.Path;

public class DrawPath {
    public Path path;
    public Paint paint;
}
