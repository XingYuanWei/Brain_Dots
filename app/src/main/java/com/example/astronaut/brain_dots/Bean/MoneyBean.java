package com.example.astronaut.brain_dots.Bean;

/*
 *Created by 魏兴源 on 2018-09-02
 *Time at 17:25
 *
 */

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 此类用于表示玩用户有多少钱
 */
public class MoneyBean extends DataSupport implements Serializable {
    private int moneyNum;

    public MoneyBean(int moneyNum) {
        this.moneyNum = moneyNum;
    }

    public int getMoneyNum() {
        return moneyNum;
    }

    public void setMoneyNum(int moneyNum) {
        this.moneyNum = moneyNum;
    }
}
