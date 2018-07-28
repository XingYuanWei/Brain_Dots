package com.example.astronaut.brain_dots.Domain;

/*
 *Created by 魏兴源 on 2018-07-27
 *Time at 14:51
 *
 */

import android.content.Context;
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
import com.example.astronaut.brain_dots.R;

import java.util.List;


public class LevelSelectRecyclerViewAdapter extends RecyclerView.Adapter<LevelSelectRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<LevelBean> levelSelectList;

    //外部类的构造函数
    public LevelSelectRecyclerViewAdapter(List<LevelBean> list) {
        levelSelectList = list;
    }

    //内部类
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        //RecyclerView子项中ImageView
        ImageView recyclerItemImage;
        //RecyclerView子项中TextView
        TextView recyclerItemText;

        //内部类的构造函数
        public ViewHolder(View itemView) {
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
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.level_recyclerview_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //第几关地图
                LevelBean level = levelSelectList.get(position);
                //context是来自
                Intent intent = new Intent(context, GameViewActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("levelData", level);
                intent.putExtras(data);
                context.startActivity(intent);
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
