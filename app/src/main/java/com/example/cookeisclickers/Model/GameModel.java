package com.example.cookeisclickers.Model;
public class GameModel {

    private int cookies = 0;
    private int clickPower = 1;

    public int getCookies() {
        return cookies;
    }

    public int getClickPower() {
        return clickPower;
    }

    public void addCookies() {
        cookies += clickPower;
    }

    public boolean upgradeClickPower() {
        int cost = 50;

        if (cookies >= cost) {
            cookies -= cost;
            clickPower++;
            return true;
        }
        return false;
    }
}
