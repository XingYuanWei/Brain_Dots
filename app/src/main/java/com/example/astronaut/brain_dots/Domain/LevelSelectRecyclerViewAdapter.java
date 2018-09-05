package com.example.astronaut.brain_dots.Domain;

/*
 *Created by 魏兴源 on 2018-07-27
 *Time at 14:51
 *
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.astronaut.brain_dots.Activities.GameViewActivity;
import com.example.astronaut.brain_dots.Activities.LevelChoiceActivity;
import com.example.astronaut.brain_dots.Bean.LevelBean;
import com.example.astronaut.brain_dots.Bean.MoneyBean;
import com.example.astronaut.brain_dots.R;
import com.example.astronaut.brain_dots.Utils.Constant;
import com.example.astronaut.brain_dots.View.componentShow.LockDialog;

import java.util.List;


public class LevelSelectRecyclerViewAdapter extends RecyclerView.Adapter<LevelSelectRecyclerViewAdapter.ViewHolder> {
    private LevelChoiceActivity context;
    private List<LevelBean> levelSelectList;
    private MoneyBean moneyBean;

    //外部类的构造函数
    public LevelSelectRecyclerViewAdapter(List<LevelBean> list, MoneyBean moneyBean) {
        this.levelSelectList = list;
        this.moneyBean = moneyBean;
    }

    //内部类
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        //RecyclerView子项中ImageView
        ImageView recyclerItemImage;
        //RecyclerView子项中TextView
        TextView recyclerItemText;

        //内部类的构造函数
        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            recyclerItemImage = itemView.findViewById(R.id.level_recycler_item_image);
            recyclerItemText = itemView.findViewById(R.id.level_recycler_item_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = (LevelChoiceActivity) parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.level_recyclerview_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //第几关地图
                LevelBean level = levelSelectList.get(position);
                //如果关卡是被锁定的,则发出提示,提示的具体内容看关卡信息
                if (level.isLocked()) {
                    LockDialog lockDialog = new LockDialog(context, level, moneyBean);
                    lockDialog.show();
                } else {
                    //context是来自点承载这个RecyclerView的LevelChoiceActivity
                    Intent intent = new Intent(context, GameViewActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("levelData", level);
                    data.putSerializable("moneyBean", moneyBean);
                    intent.putExtras(data);
                    //跳转按钮的跳转方式(界面跳转动画)
//                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
                    context.startActivityForResult(intent, Constant.REQUEST_REFRESH_GOLD_NUM_CODE);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LevelBean level = levelSelectList.get(position);
        holder.recyclerItemText.setText(level.getLevelID());
        Glide.with(context).load(level.getLevelImage()).into(holder.recyclerItemImage);
    }

    @Override
    public int getItemCount() {
        return levelSelectList.size();
    }
}
