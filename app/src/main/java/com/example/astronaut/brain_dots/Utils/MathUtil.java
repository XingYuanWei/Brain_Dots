package com.example.astronaut.brain_dots.Utils;

/*
 *Created by 魏兴源 on 2018-08-12
 *Time at 16:15
 *
 */


import android.util.Log;

import static com.example.astronaut.brain_dots.Utils.Constant.RECTANGLE_WIDTH;
import static com.example.astronaut.brain_dots.Utils.Constant.RECTANGLE_HEIGHT;


/**
 * 该类为程序中用到的所有数学计算
 */
public class MathUtil {
    public static float[][] getPositionsByStartAndAngle(float startX, float startY, float angle) {
        float sinAngle = (float) Math.sin(Math.toRadians(angle));
        float cosAngle = (float) Math.cos(Math.toRadians(angle));
        Log.e("Tag!!", "getPotionForRect: sinAngle" + sinAngle);
        Log.e("Tag!!", "getPotionForRect: cosAngle" + cosAngle);
        float cosHeight = Constant.RECTANGLE_HEIGHT * cosAngle;
        float cosWidth = Constant.RECTANGLE_WIDTH * cosAngle;
        float sinHeight = Constant.RECTANGLE_HEIGHT * sinAngle;
        float sinWidth = Constant.RECTANGLE_WIDTH * sinAngle;
        //各个点的坐标
        float secondPointX = startX + cosWidth;
        float secondPointY = startY - sinWidth;
        float thirdPointX = secondPointX + sinHeight;
        float thirdPointY = secondPointY + cosHeight;
        float fourthPointX = startX + sinHeight;
        float fourthPointY = startY + cosHeight;
        //返回矩形坐标
        float[][] position = {
                {startX, startY},
                {secondPointX, secondPointY},
                {thirdPointX, thirdPointY},
                {fourthPointX, fourthPointY}
        };
        return position;
    }

    public static float[][] getRectPositionsByTwoPoint(float pointOneX, float pointOneY, float pointTwoX, float pointTwoY, float distance) {
        float dx = pointTwoX - pointOneX;
        float dy = pointTwoY - pointOneY;
        float sinAngle = Math.abs(dy / distance);
        float cosAngle = Math.abs(dx / distance);
//        float sinAngle = Math.abs(dy / Constant.RECTANGLE_WIDTH);
//        float cosAngle = Math.abs(dx / Constant.RECTANGLE_WIDTH);
//        float angleSin = (float) Math.toDegrees(Math.asin(sinAngle));
//        float angleCos = (float) Math.toDegrees(Math.acos(cosAngle));
        Log.e("Tag!!", "sinAngle: " + sinAngle);
        Log.e("Tag!!", "cosAngle: " + cosAngle);
//        Log.e("Tag!!", "AngleSin: " + angleSin);
//        Log.e("Tag!!", "AngleCos: " + angleCos);
        float thirdPointX = 0;
        float thirdPointY = 0;
        float fourthPointX = 0;
        float fourthPointY = 0;
        //sin、cos与Height的乘积
        float sinHeight = sinAngle * RECTANGLE_HEIGHT;
        float cosHeight = cosAngle * RECTANGLE_HEIGHT;
        //向左下或向右上滑动 通过两点坐标获得矩形另外两点坐标
        if (((dy < 0) && (dx > 0)) || ((dy > 0) && (dx < 0))) {
            //第三个点
            thirdPointX = pointTwoX + sinHeight;
            thirdPointY = pointTwoY + cosHeight;
            //第四个点
            fourthPointX = pointOneX + sinHeight;
            fourthPointY = pointOneY + cosHeight;
        }

        //向左上或右下滑动 通过两点坐标获得矩形另外两点坐标
        else if (((dy > 0) && (dx > 0)) || ((dy < 0) && (dx < 0))) {
            //第三个点
            thirdPointX = pointTwoX - sinHeight;
            thirdPointY = pointTwoY + cosHeight;
            //第四个点
            fourthPointX = pointOneX - sinHeight;
            fourthPointY = pointOneY + cosHeight;
        }
        //如果左右滑动
        else if (pointOneY == pointTwoY) {
            Log.e("Tag!!", "getPositionsByTwoPoint: 2" + 11111 );
            thirdPointX = pointTwoX;
            thirdPointY = pointTwoY + RECTANGLE_HEIGHT;
            fourthPointX = pointOneX;
            fourthPointY = pointOneY + RECTANGLE_HEIGHT;
        }
        //如果上下滑动
        else if (pointOneX == pointTwoX) {
            Log.e("Tag!!", "getPositionsByTwoPoint: 2" + 22222);
            thirdPointX = pointTwoX + RECTANGLE_HEIGHT;
            thirdPointY = pointTwoY;
            fourthPointX = pointOneX + RECTANGLE_WIDTH;
            fourthPointY = pointOneY;
        }
        float[][] point = {
                {pointOneX, pointOneY},
                {pointTwoX, pointTwoY},
                {thirdPointX, thirdPointY},
                {fourthPointX, fourthPointY}
        };
        return point;
    }

    /**
     * 两个点之间的距离
     * 四个个参数
     *
     * @param startX 起始X坐标
     * @param startY 起始Y坐标
     * @param endX   结束点X坐标
     * @param endY   结束点Y坐标
     */
    public static float getDistance(float startX, float startY, float endX, float endY) {
        float dx = Math.abs(endX - startX);
        float dy = Math.abs(endY - startY);
        return (float) Math.hypot(dx, dy);
    }

    public static float[][] getTrianglePositon(float startX,float startY,float width,float height){
        float leftX = startX + width;
        float leftY = startY + height;
        float centerX = (startX + width) / 2;
        float centerY = (startY + height) / 2;
        float[][] position = {};
        return position;
    }
}
