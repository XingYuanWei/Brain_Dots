package com.example.astronaut.brain_dots.Utils;

import com.example.astronaut.brain_dots.Utils.screenManagerUtils.ScreenScaleResult;
import com.example.astronaut.brain_dots.Utils.screenManagerUtils.ScreenScaleUtil;


/**
 * 该类定义的常量用于各种计算
 */
public class Constant {
    public static final float RATE = 10;//屏幕到现实世界的比例 10px：1m;
    public static final boolean DRAW_THREAD_FLAG = true;//绘制线程工作标志位
    public static final float TIME_STEP = 2.0f / 60.0f;//模拟的的频率
    public static final int ITERATIONS = 10;//迭代越大，模拟越精确，但性能越低
    public static int SCREEN_WIDTH = 720;//目标屏幕宽度
    public static int SCREEN_HEIGHT = 1280;//目标屏幕高度

    public static float SEGMENT_LENGTH = 20;
    public static float x;//声明当前屏幕左上方X值的变量
    public static float y;//声明当前屏幕左上方Y值的变量
    public static float ratio;//声明缩放比例的变量
    public static ScreenScaleResult screenScaleResult;//声明ScreenScaleResult类的引用

    public static void ScaleSR() {
        screenScaleResult = ScreenScaleUtil.calScale(SCREEN_WIDTH, SCREEN_HEIGHT);//屏幕自适应
        x = screenScaleResult.lucX;//获取当前屏幕左上方的X值
        y = screenScaleResult.lucY;//获取当前屏幕左上方的Y值
        ratio = screenScaleResult.ratio;//获取缩放比例
    }
}