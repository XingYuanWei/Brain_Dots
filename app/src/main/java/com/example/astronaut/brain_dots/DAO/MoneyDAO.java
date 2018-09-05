package com.example.astronaut.brain_dots.DAO;

/*
 *Created by 魏兴源 on 2018-09-02
 *Time at 17:34
 *
 */


import com.example.astronaut.brain_dots.Activities.LevelChoiceActivity;
import com.example.astronaut.brain_dots.Bean.MoneyBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 玩家金钱的数据访问对象
 */
public class MoneyDAO {
    private MoneyBean money;
    private LevelChoiceActivity activity;

    public MoneyDAO(LevelChoiceActivity activity) {
        this.activity = activity;
        money = getMoneyBeanFromDataBase();
    }

    //插入数据
    private void insertMoneyData() {
        MoneyBean newMoney = new MoneyBean(200);
        newMoney.save();
    }

    //初始化金钱对象
    public void initMoneyData() {
        DataSupport.deleteAll(MoneyBean.class);
        insertMoneyData();
    }

    //开启一关就减去10元
    public void subMoneyData() {
        money.setMoneyNum(money.getMoneyNum() - 10);
        money.save();

    }

    //通关就给8金币
    public void addMoneyData() {
        money.setMoneyNum(money.getMoneyNum() + 8);
        money.save();
    }

    //返回当前的金钱数量
    public int getMoneyNum() {
        int moneyNum = 0;
        List<MoneyBean> moneyBeanList = DataSupport.findAll(MoneyBean.class);
        for (MoneyBean money : moneyBeanList) {
            moneyNum = money.getMoneyNum();
        }
        return moneyNum;
    }

    //返回一个MoneyBean对象
    private MoneyBean getMoneyBeanFromDataBase() {
        List<MoneyBean> moneyBeanList = DataSupport.findAll(MoneyBean.class);
        if (!moneyBeanList.isEmpty()){
            return moneyBeanList.get(0);
        }
        return null;
    }

    public MoneyBean getMoneyBean() {
        return money;
    }
}
