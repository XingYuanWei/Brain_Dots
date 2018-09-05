package com.example.astronaut.brain_dots.Utils.gameUtils;

/*
 *Created by 魏兴源 on 2018-07-23
 *Time at 17:27
 *
 */

import android.util.Log;

import com.example.astronaut.brain_dots.Activities.GameViewActivity;
import com.example.astronaut.brain_dots.Bean.LevelBean;
import com.example.astronaut.brain_dots.Domain.Creator;
import com.example.astronaut.brain_dots.Shapes.common.Ball;
import com.example.astronaut.brain_dots.Shapes.common.Polygon;
import com.example.astronaut.brain_dots.Shapes.common.Rectangle;
import com.example.astronaut.brain_dots.Utils.ColorUtil;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.Utils.MathUtil;
import com.example.astronaut.brain_dots.Utils.ReadFilesUtil;
import com.example.astronaut.brain_dots.View.gameShow.WebCantTouchArea;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapUtil {
    //游戏界面的引用,用于设置刚体的位置
    private static GameViewActivity activity;

    private static List<List<String>> getMapElement(GameViewActivity activity, String fileName) {
        //从文件中读取数据
        String mapText = ReadFilesUtil.getContentByAsset(activity, fileName);
        //对数据进行分割
        String[] dataArray = mapText.split("\\s+");
        //把字符串数组全部都放入到集合中 利用集合的方法进行分组 因为前面两个数据是无用数据
        List<String> dataList = new ArrayList<>(Arrays.asList(dataArray).subList(2, dataArray.length));
        //每5个数据为一个单位拆分所有数据
        int groupSize = 5;
        return MapUtil.splitList(dataList, groupSize);
    }

    //把一个集合按单位长度拆分 拆分成若干个小集合 再把拆分好的小集合放入集合
    private static List<List<String>> splitList(List<String> list, int metaLength) {
        int length = list.size();
        /*
           计算可以拆分多少组
          length % groupSize == 0 ? length / groupSize : length / groupSize +1
          这里使用 ( length + groupSize - 1 ) / groupSize则减少运算了。
          */
        int num = (length + metaLength - 1) / metaLength;
        List<List<String>> listStringList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            //开始的位置
            int fromIndex = i * metaLength;
            //结束的位置
            int toIndex = (i + 1) * metaLength < length ? (i + 1) * metaLength : length;
            listStringList.add(list.subList(fromIndex, toIndex));
        }
        return listStringList;
    }

    public static void setLevel(GameViewActivity activity, String fileName) {
        MapUtil.activity = activity;
        //先清空集合
        activity.shapesList.clear();
        List<List<String>> levelElement = getMapElement(activity, fileName);
        for (List<String> stringList : levelElement) {
            setBodyInGameView(stringList);
        }
    }

    //根据当前的ID获取下一关数据文件的名称
    public static String getNextMapName(String curryMapDataID) {
        int curryLevelNum;
        try {
            curryLevelNum = Integer.valueOf(curryMapDataID);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Tag!!", "格式化异常!");
            return null;
        }
        int nextLevelNum = curryLevelNum + 1;
        return "Level/level" + nextLevelNum + ".map";
    }


    private static void setBodyInGameView(List<String> list) {
        String bodyName = list.get(0);
        //图形的起始位置
        float posX = Constant.SCREEN_HEIGHT * Float.valueOf(list.get(1));
        float posY = Constant.SCREEN_WIDTH * Float.valueOf(list.get(2)) + 30;
        //图形的宽高
        float bodyWidth = Constant.SCREEN_HEIGHT * Float.valueOf(list.get(3));
        float bodyHeight = Constant.SCREEN_WIDTH * Float.valueOf(list.get(4));

        if (posX <= 10) {
            posX = 0;
        }
        if (Constant.SCREEN_WIDTH - posY <= 50) {
            posY = Constant.SCREEN_WIDTH;

        }
        switch (bodyName) {
            case "redBall":
                Ball redBall = Creator.createBall(posX + 100f, posY + 100f, 45f, false, activity.world,
                        ColorUtil.getRedBallColor(), activity);
                activity.shapesList.add(redBall);
                break;
            case "blueBall":
                Ball blueBall = Creator.createBall(posX + 100f, posY + 100f, 45f, false, activity.world,
                        ColorUtil.getBlueBallColor(), activity);
                activity.shapesList.add(blueBall);
                break;
            case "one1":
                Ball staticBall = Creator.createBall(posX, posY, bodyWidth, false, activity.world,
                        ColorUtil.getStaticBodyColor(), activity);
                activity.shapesList.add(staticBall);
                break;
            case "one2":
                Polygon polygon = Creator.createReact(MathUtil.getPositionByStartPointWidthHeight(posX, posY, bodyWidth, bodyHeight), activity.world);
                activity.shapesList.add(polygon);

                break;
            case "one3":
                Rectangle rectangleVertical = Creator.createRectangle(posX, bodyHeight / 2, bodyWidth / 2, bodyHeight / 2,
                        true, activity.world, ColorUtil.getStaticBodyColor());
                activity.shapesList.add(rectangleVertical);

                break;
            case "one4":
                Polygon square = Creator.createReact(MathUtil.getPositionByStartPointWidthHeight(posX, posY, bodyWidth, bodyHeight), activity.world);
                activity.shapesList.add(square);
            case "one5":
//                Triangle triangle = Creator.
                break;
            case "three3":
                WebCantTouchArea webCantTouchArea = Creator.createCantTouchArea(posX, posY, bodyWidth, bodyHeight);
                activity.webCantTouchAreaList.add(webCantTouchArea);
                break;
        }

    }

}
