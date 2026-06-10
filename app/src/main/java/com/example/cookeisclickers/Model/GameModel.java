package com.example.cookeisclickers.Model;

public class GameModel {

    private static GameModel instance;

    // --- Ресурсы 1 уровня ---
    private int cookies = 0;
    private int clickPower = 1;
    private int multiplier = 1;
    private int autoClickers = 0;
    private int cookieFactories = 0;

    private int clickUpgradeCost = 50;
    private int autoClickerCost = 150;
    private int factoryCost = 800;
    private int multiplierCost = 300;

    // --- Ресурсы 2 уровня ---
    private int level2Cookies = 0;
    private boolean isLevel2Unlocked = false;

    private int clickPowerLvl2 = 20;
    private int autoClickersLvl2 = 0;
    private int superClicksLvl2 = 0;
    private int quantumGeneratorsLvl2 = 0;

    private int clickUpgradeCostLvl2 = 200;
    private int autoClickerCostLvl2 = 1000;
    private int superClickCostLvl2 = 5000;
    private int quantumGeneratorCostLvl2 = 15000;
    private int multiplierCostLvl2 = 2000;

    private GameModel() {}

    public static GameModel getInstance() {
        if (instance == null) instance = new GameModel();
        return instance;
    }
    public void addLevel2Cookies() {
        this.level2Cookies += (clickPowerLvl2 + superClicksLvl2 * 50);
    }

    public void autoClickLvl2() {
        this.level2Cookies += (autoClickersLvl2 * 20 + quantumGeneratorsLvl2 * 300);
    }
    // --- Общие методы (публичные для доступа из Activity) ---
    public boolean isLevel2Unlocked() { return isLevel2Unlocked; }
    public void unlockLevel2() { this.isLevel2Unlocked = true; }
    public void removeCookies(int amount) { this.cookies -= amount; }

    // --- Методы 1 уровня ---
    public int getCookies() { return cookies; }
    public void addCookies() { cookies += clickPower * multiplier; }
    public void autoClick() { cookies += (autoClickers * 1 + cookieFactories * 15) * multiplier; }



    public int getAutoClickers() { return autoClickers; }
    public int getCookieFactories() { return cookieFactories; }
    public int getClickUpgradeCost() { return clickUpgradeCost; }
    public int getAutoClickerCost() { return autoClickerCost; }
    public int getFactoryCost() { return factoryCost; }
    public int getMultiplierCost() { return multiplierCost; }

    // --- Методы 2 уровня ---
    public int getLevel2Cookies() { return level2Cookies; }

    // ПРАВИЛЬНЫЙ МЕТОД ДЛЯ АВТОКЛИКЕРА В MainActivity
    public void addPassiveLevel2Cookies(int amount) { this.level2Cookies += amount; }

    public int getAutoClickersLvl2() { return autoClickersLvl2; }
    public int getQuantumGeneratorsLvl2() { return quantumGeneratorsLvl2; }

    // --- Цены 2 уровня (Геттеры) ---
    public int getClickUpgradeCostLvl2() { return clickUpgradeCostLvl2; }
    public int getAutoClickerCostLvl2() { return autoClickerCostLvl2; }
    public int getSuperClickCostLvl2() { return superClickCostLvl2; }
    public int getQuantumGeneratorCostLvl2() { return quantumGeneratorCostLvl2; }
    public int getMultiplierCostLvl2() { return multiplierCostLvl2; }

    // --- Покупки 1 уровня ---
    public boolean buyClickPowerUpgrade() { if (cookies >= clickUpgradeCost) { cookies -= clickUpgradeCost; clickPower++; clickUpgradeCost = (int)(clickUpgradeCost * 1.5); return true; } return false; }
    public boolean buyAutoClicker() { if (cookies >= autoClickerCost) { cookies -= autoClickerCost; autoClickers++; autoClickerCost = (int)(autoClickerCost * 1.4); return true; } return false; }
    public boolean buyCookieFactory() { if (cookies >= factoryCost) { cookies -= factoryCost; cookieFactories++; factoryCost = (int)(factoryCost * 1.6); return true; } return false; }
    public boolean buyMultiplierUpgrade() { if (cookies >= multiplierCost) { cookies -= multiplierCost; multiplier *= 2; multiplierCost = (int)(multiplierCost * 2.5); return true; } return false; }

    // --- Покупки 2 уровня ---
    public boolean buyClickPowerUpgradeLvl2() { if (level2Cookies >= clickUpgradeCostLvl2) { level2Cookies -= clickUpgradeCostLvl2; clickPowerLvl2 += 15; clickUpgradeCostLvl2 = (int)(clickUpgradeCostLvl2 * 1.5); return true; } return false; }
    public boolean buyAutoClickerLvl2() { if (level2Cookies >= autoClickerCostLvl2) { level2Cookies -= autoClickerCostLvl2; autoClickersLvl2++; autoClickerCostLvl2 = (int)(autoClickerCostLvl2 * 1.4); return true; } return false; }
    public boolean buySuperClickLvl2() { if (level2Cookies >= superClickCostLvl2) { level2Cookies -= superClickCostLvl2; superClicksLvl2++; superClickCostLvl2 = (int)(superClickCostLvl2 * 1.7); return true; } return false; }
    public boolean buyQuantumGeneratorLvl2() { if (level2Cookies >= quantumGeneratorCostLvl2) { level2Cookies -= quantumGeneratorCostLvl2; quantumGeneratorsLvl2++; quantumGeneratorCostLvl2 = (int)(quantumGeneratorCostLvl2 * 1.8); return true; } return false; }
    public boolean buyMultiplierUpgradeLvl2() { if (level2Cookies >= multiplierCostLvl2) { level2Cookies -= multiplierCostLvl2; clickPowerLvl2 *= 2; multiplierCostLvl2 = (int)(multiplierCostLvl2 * 3.0); return true; } return false; }
}