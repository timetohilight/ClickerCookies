package com.example.cookeisclickers.Model;
import android.content.Context;
import android.content.SharedPreferences;

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

    // Имя файла настроек
    private static final String PREF_NAME = "cookie_clicker_save";

    // --- Ключи для сохранения (уровни 1 и 2) ---
    private static final String KEY_COOKIES = "cookies";
    private static final String KEY_CLICK_POWER = "clickPower";
    private static final String KEY_MULTIPLIER = "multiplier";
    private static final String KEY_AUTO_CLICKERS = "autoClickers";
    private static final String KEY_COOKIE_FACTORIES = "cookieFactories";
    private static final String KEY_CLICK_UPGRADE_COST = "clickUpgradeCost";
    private static final String KEY_AUTO_CLICKER_COST = "autoClickerCost";
    private static final String KEY_FACTORY_COST = "factoryCost";
    private static final String KEY_MULTIPLIER_COST = "multiplierCost";

    private static final String KEY_LEVEL2_COOKIES = "level2Cookies";
    private static final String KEY_IS_LEVEL2_UNLOCKED = "isLevel2Unlocked";
    private static final String KEY_CLICK_POWER_LVL2 = "clickPowerLvl2";
    private static final String KEY_AUTO_CLICKERS_LVL2 = "autoClickersLvl2";
    private static final String KEY_SUPER_CLICKS_LVL2 = "superClicksLvl2";
    private static final String KEY_QUANTUM_GENERATORS_LVL2 = "quantumGeneratorsLvl2";
    private static final String KEY_CLICK_UPGRADE_COST_LVL2 = "clickUpgradeCostLvl2";
    private static final String KEY_AUTO_CLICKER_COST_LVL2 = "autoClickerCostLvl2";
    private static final String KEY_SUPER_CLICK_COST_LVL2 = "superClickCostLvl2";
    private static final String KEY_QUANTUM_GENERATOR_COST_LVL2 = "quantumGeneratorCostLvl2";
    private static final String KEY_MULTIPLIER_COST_LVL2 = "multiplierCostLvl2";

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

    // Метод для сохранения всех игровых данных во внутреннюю память
    public void saveProgress(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Данные 1 уровня
        editor.putInt(KEY_COOKIES, cookies);
        editor.putInt(KEY_CLICK_POWER, clickPower);
        editor.putInt(KEY_MULTIPLIER, multiplier);
        editor.putInt(KEY_AUTO_CLICKERS, autoClickers);
        editor.putInt(KEY_COOKIE_FACTORIES, cookieFactories);
        editor.putInt(KEY_CLICK_UPGRADE_COST, clickUpgradeCost);
        editor.putInt(KEY_AUTO_CLICKER_COST, autoClickerCost);
        editor.putInt(KEY_FACTORY_COST, factoryCost);
        editor.putInt(KEY_MULTIPLIER_COST, multiplierCost);

        // Данные 2 уровня
        editor.putInt(KEY_LEVEL2_COOKIES, level2Cookies);
        editor.putBoolean(KEY_IS_LEVEL2_UNLOCKED, isLevel2Unlocked);
        editor.putInt(KEY_CLICK_POWER_LVL2, clickPowerLvl2);
        editor.putInt(KEY_AUTO_CLICKERS_LVL2, autoClickersLvl2);
        editor.putInt(KEY_SUPER_CLICKS_LVL2, superClicksLvl2);
        editor.putInt(KEY_QUANTUM_GENERATORS_LVL2, quantumGeneratorsLvl2);
        editor.putInt(KEY_CLICK_UPGRADE_COST_LVL2, clickUpgradeCostLvl2);
        editor.putInt(KEY_AUTO_CLICKER_COST_LVL2, autoClickerCostLvl2);
        editor.putInt(KEY_SUPER_CLICK_COST_LVL2, superClickCostLvl2);
        editor.putInt(KEY_QUANTUM_GENERATOR_COST_LVL2, quantumGeneratorCostLvl2);
        editor.putInt(KEY_MULTIPLIER_COST_LVL2, multiplierCostLvl2);

        // Применяем изменения в фоновом режиме
        editor.apply();
    }

    /**
     * Метод для загрузки игровых данных из внутренней памяти
     */
    public void loadProgress(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Загружаем значения. В качестве второго параметра передаются значения по умолчанию
        cookies = prefs.getInt(KEY_COOKIES, 0);
        clickPower = prefs.getInt(KEY_CLICK_POWER, 1);
        multiplier = prefs.getInt(KEY_MULTIPLIER, 1);
        autoClickers = prefs.getInt(KEY_AUTO_CLICKERS, 0);
        cookieFactories = prefs.getInt(KEY_COOKIE_FACTORIES, 0);
        clickUpgradeCost = prefs.getInt(KEY_CLICK_UPGRADE_COST, 50);
        autoClickerCost = prefs.getInt(KEY_AUTO_CLICKER_COST, 150);
        factoryCost = prefs.getInt(KEY_FACTORY_COST, 800);
        multiplierCost = prefs.getInt(KEY_MULTIPLIER_COST, 300);

        level2Cookies = prefs.getInt(KEY_LEVEL2_COOKIES, 0);
        isLevel2Unlocked = prefs.getBoolean(KEY_IS_LEVEL2_UNLOCKED, false);
        clickPowerLvl2 = prefs.getInt(KEY_CLICK_POWER_LVL2, 20);
        autoClickersLvl2 = prefs.getInt(KEY_AUTO_CLICKERS_LVL2, 0);
        superClicksLvl2 = prefs.getInt(KEY_SUPER_CLICKS_LVL2, 0);
        quantumGeneratorsLvl2 = prefs.getInt(KEY_QUANTUM_GENERATORS_LVL2, 0);
        clickUpgradeCostLvl2 = prefs.getInt(KEY_CLICK_UPGRADE_COST_LVL2, 200);
        autoClickerCostLvl2 = prefs.getInt(KEY_AUTO_CLICKER_COST_LVL2, 1000);
        superClickCostLvl2 = prefs.getInt(KEY_SUPER_CLICK_COST_LVL2, 5000);
        quantumGeneratorCostLvl2 = prefs.getInt(KEY_QUANTUM_GENERATOR_COST_LVL2, 15000);
        multiplierCostLvl2 = prefs.getInt(KEY_MULTIPLIER_COST_LVL2, 2000);
    }


}