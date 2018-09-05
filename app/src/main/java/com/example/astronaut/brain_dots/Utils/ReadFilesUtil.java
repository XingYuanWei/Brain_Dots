package com.example.astronaut.brain_dots.Utils;

/*
 *Created by 魏兴源 on 2018-07-20
 *Time at 16:17
 *
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFilesUtil {

    public static String getContentByAsset(Context context, String fileName) {
        //获得字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取asset资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理打开文件并读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
                //打印提示正在读取数据
                Log.e("Tag!!", "getContentByAsset: "+"Reading Now" );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
