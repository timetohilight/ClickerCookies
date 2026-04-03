package com.example.cookeisclickers.Model;

public class GameModel {

    private static GameModel instance;

    private int cookies = 0;
    private int clickPower = 1;
    private int multiplier = 1;

    // цены
    private int clickUpgradeCost = 50;
    private int autoClickerCost = 150;
    private int multiplierCost = 300;

    // singleton (один объект на всю игру)
    public static GameModel getInstance() {
        if (instance == null) {
            instance = new GameModel();
        }
        return instance;
    }

    // клик
    public void addCookies() {
        cookies += clickPower * multiplier;
    }

    public int getCookies() {
        return cookies;
    }

    // апгрейд клика
    public boolean buyClickPowerUpgrade() {
        if (cookies >= clickUpgradeCost) {
            cookies -= clickUpgradeCost;
            clickPower++;
            clickUpgradeCost += 50;
            return true;
        }
        return false;
    }

    // автокликер (пока просто добавляет печеньки)
    public boolean buyAutoClicker() {
        if (cookies >= autoClickerCost) {
            cookies -= autoClickerCost;
            cookies += 10; // простой эффект
            return true;
        }
        return false;
    }

    // множитель
    public boolean buyMultiplierUpgrade() {
        if (cookies >= multiplierCost) {
            cookies -= multiplierCost;
            multiplier *= 2;
            multiplierCost += 200;
            return true;
        }
        return false;
    }
}