package com.example.astronaut.brain_dots.View.componentShow;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.astronaut.brain_dots.Activities.GameViewActivity;
import com.example.astronaut.brain_dots.Activities.LevelChoiceActivity;
import com.example.astronaut.brain_dots.Bean.LevelBean;
import com.example.astronaut.brain_dots.R;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.Utils.ReadFilesUtil;

import org.w3c.dom.Text;


/*
 *Created by 魏兴源 on 2018-08-31
 *Time at 17:42
 *
 */

public class LockDialog extends Dialog {
    private LevelChoiceActivity context;
    private LevelBean level;

    public LockDialog(Context context, LevelBean level) {
        super(context);
        //获取LevelChoiceActivity的context,等等可以读到Data.dat中数据
        this.context = (LevelChoiceActivity) context;
        this.level = level;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //不显示标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //对话框的布局
        setContentView(R.layout.alter_layout_dialog);
        //获取文本框和确定按钮控件
        Button btn_confirm = findViewById(R.id.btn_confirm_dialog);
        Button btn_cancel = findViewById(R.id.btn_cancel_dialog);
        TextView textView = findViewById(R.id.alter_dialog_textView);
        //提示显示在文本框内的文本
        String tipText = "";
        //这个state用于表示当前字符串的状态的,1为"是否花费10金币开启本关,2为..."
        int state = 0;
        if (level.isLocked()) {
            if (Constant.MONEY >= 10) {
                tipText = "您的金币足够,是否花费10金币开启本关卡？！";
                state = 1;
            } else {
                tipText = "本关卡未解锁,您的金币不足,请通过赚取金币或充值解锁！";
                state = 2;
            }
        }

        final int finalState = state;
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalState == 1) {
                    //如果是可以花钱打开并且愿意花钱打开,则启动游戏界面并且将数据更新
                    startGameView(context, level);
                    //关闭对话框
                    dismiss();
                    //更新关卡是否打开数据和金币总额数据
                    update(level);
                }
                if (finalState == 2) {
                    //如果此时金币不足,则点击确定或取消都是消失对话框
                    dismiss();
                }
            }
        });
        //给文本框设置提示文本
        textView.setText(tipText);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对话框消失
                dismiss();
            }
        });
    }

    //启动游戏界面
    private void startGameView(Context context, LevelBean level) {
        //context是来自点承载这个RecyclerView的Activity
        Intent intent = new Intent(context, GameViewActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("levelData", level);
        intent.putExtras(data);
        //跳转按钮的跳转方式(界面跳转动画)
//                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
        context.startActivity(intent);
    }


    private void update(LevelBean level) {
        //更新选关界面
        updateLevelChoice(level);
        //下次打开时候关卡打开要记得某关卡已经被解锁,更新一下永久化的数据
        updateData(level);
    }

    //并将选关的图片替换成解锁的图片
    private void updateLevelChoice(LevelBean level) {
        int index = getIndex(level);
        LevelBean updateLevel = new LevelBean(level.getLevelID(), context.unlockedImage[index], level.getLevelName(), false);
        //在将要移去的位置上添加对象
        context.levelSelectList.set(index, updateLevel);
        //移去对象
        context.levelSelectList.remove(level);
        //更新页面选关页面
        context.adapter.notifyDataSetChanged();
    }

    //扣费后将数据持久的更新到某个地方,
    private void updateData(LevelBean level) {
        level.setLocked(false);
        level.setLevelImage(context.unlockedImage[getIndex(level)]);
        level.save();
    }

    private int getIndex(LevelBean level) {
        String id = level.getLevelID();
        //关卡的ID
        int IDNum = Integer.valueOf(id);
        //ID-1为图片的索引
        return IDNum - 1;
    }
}
