package com.example.cookeisclickers.Model;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class GameModel {

    private static GameModel instance;

    // --- Ресурсы 1 уровня ---
    private long cookies = 0;
    private long clickPower = 1;
    private long multiplier = 1;
    private int autoClickers = 0;
    private int cookieFactories = 0;

    private long clickUpgradeCost = 50;
    private long autoClickerCost = 150;
    private long factoryCost = 800;
    private long multiplierCost = 300;

    // --- Ресурсы 2 уровня ---
    private long level2Cookies = 0;
    private boolean isLevel2Unlocked = false;

    private long clickPowerLvl2 = 20;
    private int autoClickersLvl2 = 0;
    private int superClicksLvl2 = 0;
    private int quantumGeneratorsLvl2 = 0;

    private long clickUpgradeCostLvl2 = 200;
    private long autoClickerCostLvl2 = 1000;
    private long superClickCostLvl2 = 5000;
    private long quantumGeneratorCostLvl2 = 15000;
    private long multiplierCostLvl2 = 2000;

    // --- Системные переменные для управления профилями ---
    private String currentUser = "DefaultPlayer";
    private Set<String> userList = new HashSet<>();

    private static final String MAIN_CONFIG_PREF = "cookie_clicker_config";
    private static final String KEY_CURRENT_USER = "current_user";
    private static final String KEY_USER_LIST = "user_list";

    // --- Ключи для сохранения прогресса внутри профиля ---
    private static final String KEY_COOKIES = "cookies";
    private static final String KEY_CLICK_POWER = "click_power";
    private static final String KEY_MULTIPLIER = "multiplier";
    private static final String KEY_AUTO_CLICKERS = "auto_clickers";
    private static final String KEY_COOKIE_FACTORIES = "cookie_factories";
    private static final String KEY_CLICK_UPGRADE_COST = "click_upgrade_cost";
    private static final String KEY_AUTO_CLICKER_COST = "auto_clicker_cost";
    private static final String KEY_FACTORY_COST = "factory_cost";
    private static final String KEY_MULTIPLIER_COST = "multiplier_cost";

    private static final String KEY_LEVEL2_COOKIES = "level2_cookies";
    private static final String KEY_IS_LEVEL2_UNLOCKED = "is_level2_unlocked";
    private static final String KEY_CLICK_POWER_LVL2 = "click_power_lvl2";
    private static final String KEY_AUTO_CLICKERS_LVL2 = "auto_clickers_lvl2";
    private static final String KEY_SUPER_CLICKS_LVL2 = "super_clicks_lvl2";
    private static final String KEY_QUANTUM_GENERATORS_LVL2 = "quantum_generators_lvl2";
    private static final String KEY_CLICK_UPGRADE_COST_LVL2 = "click_upgrade_cost_lvl2";
    private static final String KEY_AUTO_CLICKER_COST_LVL2 = "auto_clicker_cost_lvl2";
    private static final String KEY_SUPER_CLICK_COST_LVL2 = "super_click_cost_lvl2";
    private static final String KEY_QUANTUM_GENERATOR_COST_LVL2 = "quantum_generator_cost_lvl2";
    private static final String KEY_MULTIPLIER_COST_LVL2 = "multiplier_cost_lvl2";

    private GameModel() {}

    public static synchronized GameModel getInstance() {
        if (instance == null) {
            instance = new GameModel();
        }
        return instance;
    }

    // --- Управление пользователями ---
    public String getCurrentUser() { return currentUser; }
    public Set<String> getUserList() { return userList; }

    public void changeUser(String username, Context context) {
        saveProgress(context);
        this.currentUser = username;
        this.userList.add(username);

        SharedPreferences mainPrefs = context.getSharedPreferences(MAIN_CONFIG_PREF, Context.MODE_PRIVATE);
        mainPrefs.edit()
                .putString(KEY_CURRENT_USER, currentUser)
                .putStringSet(KEY_USER_LIST, userList)
                .apply();

        loadProgress(context);
    }

    public void deleteUser(String username, Context context) {
        if (userList.contains(username) && !currentUser.equals(username)) {
            userList.remove(username);
            SharedPreferences userPrefs = context.getSharedPreferences("cookies_save_" + username, Context.MODE_PRIVATE);
            userPrefs.edit().clear().apply();

            SharedPreferences mainPrefs = context.getSharedPreferences(MAIN_CONFIG_PREF, Context.MODE_PRIVATE);
            mainPrefs.edit().putStringSet(KEY_USER_LIST, userList).apply();
        }
    }

    // --- Сохранение и загрузка ---
    public void saveProgress(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("cookies_save_" + currentUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong(KEY_COOKIES, cookies);
        editor.putLong(KEY_CLICK_POWER, clickPower);
        editor.putLong(KEY_MULTIPLIER, multiplier);
        editor.putInt(KEY_AUTO_CLICKERS, autoClickers);
        editor.putInt(KEY_COOKIE_FACTORIES, cookieFactories);
        editor.putLong(KEY_CLICK_UPGRADE_COST, clickUpgradeCost);
        editor.putLong(KEY_AUTO_CLICKER_COST, autoClickerCost);
        editor.putLong(KEY_FACTORY_COST, factoryCost);
        editor.putLong(KEY_MULTIPLIER_COST, multiplierCost);

        editor.putLong(KEY_LEVEL2_COOKIES, level2Cookies);
        editor.putBoolean(KEY_IS_LEVEL2_UNLOCKED, isLevel2Unlocked);
        editor.putLong(KEY_CLICK_POWER_LVL2, clickPowerLvl2);
        editor.putInt(KEY_AUTO_CLICKERS_LVL2, autoClickersLvl2);
        editor.putInt(KEY_SUPER_CLICKS_LVL2, superClicksLvl2);
        editor.putInt(KEY_QUANTUM_GENERATORS_LVL2, quantumGeneratorsLvl2);
        editor.putLong(KEY_CLICK_UPGRADE_COST_LVL2, clickUpgradeCostLvl2);
        editor.putLong(KEY_AUTO_CLICKER_COST_LVL2, autoClickerCostLvl2);
        editor.putLong(KEY_SUPER_CLICK_COST_LVL2, superClickCostLvl2);
        editor.putLong(KEY_QUANTUM_GENERATOR_COST_LVL2, quantumGeneratorCostLvl2);
        editor.putLong(KEY_MULTIPLIER_COST_LVL2, multiplierCostLvl2);

        editor.apply();
    }

    public void loadProgress(Context context) {
        SharedPreferences mainPrefs = context.getSharedPreferences(MAIN_CONFIG_PREF, Context.MODE_PRIVATE);
        currentUser = mainPrefs.getString(KEY_CURRENT_USER, "DefaultPlayer");

        Set<String> savedList = mainPrefs.getStringSet(KEY_USER_LIST, null);
        if (savedList != null) {
            userList = new HashSet<>(savedList);
        } else {
            userList = new HashSet<>();
            userList.add(currentUser);
        }

        SharedPreferences prefs = context.getSharedPreferences("cookies_save_" + currentUser, Context.MODE_PRIVATE);
        cookies = prefs.getLong(KEY_COOKIES, 0L);
        clickPower = prefs.getLong(KEY_CLICK_POWER, 1L);
        multiplier = prefs.getLong(KEY_MULTIPLIER, 1L);
        autoClickers = prefs.getInt(KEY_AUTO_CLICKERS, 0);
        cookieFactories = prefs.getInt(KEY_COOKIE_FACTORIES, 0);
        clickUpgradeCost = prefs.getLong(KEY_CLICK_UPGRADE_COST, 50L);
        autoClickerCost = prefs.getLong(KEY_AUTO_CLICKER_COST, 150L);
        factoryCost = prefs.getLong(KEY_FACTORY_COST, 800L);
        multiplierCost = prefs.getLong(KEY_MULTIPLIER_COST, 300L);

        level2Cookies = prefs.getLong(KEY_LEVEL2_COOKIES, 0L);
        isLevel2Unlocked = prefs.getBoolean(KEY_IS_LEVEL2_UNLOCKED, false);
        clickPowerLvl2 = prefs.getLong(KEY_CLICK_POWER_LVL2, 20L);
        autoClickersLvl2 = prefs.getInt(KEY_AUTO_CLICKERS_LVL2, 0);
        superClicksLvl2 = prefs.getInt(KEY_SUPER_CLICKS_LVL2, 0);
        quantumGeneratorsLvl2 = prefs.getInt(KEY_QUANTUM_GENERATORS_LVL2, 0);
        clickUpgradeCostLvl2 = prefs.getLong(KEY_CLICK_UPGRADE_COST_LVL2, 200L);
        autoClickerCostLvl2 = prefs.getLong(KEY_AUTO_CLICKER_COST_LVL2, 1000L);
        superClickCostLvl2 = prefs.getLong(KEY_SUPER_CLICK_COST_LVL2, 5000L);
        quantumGeneratorCostLvl2 = prefs.getLong(KEY_QUANTUM_GENERATOR_COST_LVL2, 15000L);
        multiplierCostLvl2 = prefs.getLong(KEY_MULTIPLIER_COST_LVL2, 2000L);
    }

    // --- Логика 1 уровня ---
    public void addCookies() { cookies += clickPower * multiplier; }
    public void autoClick() { cookies += ((long) autoClickers * 1) + ((long) cookieFactories * 15); }
    public void removeCookies(long amount) { this.cookies -= amount; }

    public boolean buyClickPowerUpgrade() {
        if (cookies >= clickUpgradeCost) { cookies -= clickUpgradeCost; clickPower++; clickUpgradeCost *= 1.5; return true; }
        return false;
    }
    public boolean buyAutoClicker() {
        if (cookies >= autoClickerCost) { cookies -= autoClickerCost; autoClickers++; autoClickerCost *= 1.4; return true; }
        return false;
    }
    public boolean buyCookieFactory() {
        if (cookies >= factoryCost) { cookies -= factoryCost; cookieFactories++; factoryCost *= 1.6; return true; }
        return false;
    }
    public boolean buyMultiplierUpgrade() {
        if (cookies >= multiplierCost) { cookies -= multiplierCost; multiplier *= 2; multiplierCost *= 2.5; return true; }
        return false;
    }

    public boolean unlockLevel2() {
        this.isLevel2Unlocked = true;
        return true;
    }

    // --- Логика 2 уровня ---
    public void addLevel2Cookies() { level2Cookies += (clickPowerLvl2 + ((long) superClicksLvl2 * 50)); }
    public void autoClickLvl2() { level2Cookies += ((long) autoClickersLvl2 * 20) + ((long) quantumGeneratorsLvl2 * 300); }
    public void addPassiveLevel2Cookies(long amount) { level2Cookies += amount; }

    public boolean buyClickPowerUpgradeLvl2() {
        if (level2Cookies >= clickUpgradeCostLvl2) { level2Cookies -= clickUpgradeCostLvl2; clickPowerLvl2 += 15; clickUpgradeCostLvl2 *= 1.5; return true; }
        return false;
    }
    public boolean buyAutoClickerLvl2() {
        if (level2Cookies >= autoClickerCostLvl2) { level2Cookies -= autoClickerCostLvl2; autoClickersLvl2++; autoClickerCostLvl2 *= 1.4; return true; }
        return false;
    }
    public boolean buySuperClickLvl2() {
        if (level2Cookies >= superClickCostLvl2) { level2Cookies -= superClickCostLvl2; superClicksLvl2++; superClickCostLvl2 *= 1.7; return true; }
        return false;
    }
    public boolean buyQuantumGeneratorLvl2() {
        if (level2Cookies >= quantumGeneratorCostLvl2) { level2Cookies -= quantumGeneratorCostLvl2; quantumGeneratorsLvl2++; quantumGeneratorCostLvl2 *= 1.8; return true; }
        return false;
    }
    public boolean buyMultiplierUpgradeLvl2() {
        if (level2Cookies >= multiplierCostLvl2) { level2Cookies -= multiplierCostLvl2; clickPowerLvl2 *= 2; multiplierCostLvl2 *= 3.0; return true; }
        return false;
    }

    public void setCookies(long cookies) { this.cookies = cookies; }
    public void setLevel2Cookies(long level2Cookies) { this.level2Cookies = level2Cookies; }

    public java.util.Map<String, Object> getFirebaseDataMap() {
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("username", currentUser);
        data.put("cookiesLvl1", cookies);
        data.put("cookiesLvl2", level2Cookies);
        data.put("totalScore", cookies + level2Cookies);
        data.put("timestamp", System.currentTimeMillis());
        return data;
    }

    // --- Геттеры ---
    public long getCookies() { return cookies; }
    public long getClickPower() { return clickPower; }
    public long getMultiplier() { return multiplier; }
    public int getAutoClickers() { return autoClickers; }
    public int getCookieFactories() { return cookieFactories; }
    public long getClickUpgradeCost() { return clickUpgradeCost; }
    public long getAutoClickerCost() { return autoClickerCost; }
    public long getFactoryCost() { return factoryCost; }
    public long getMultiplierCost() { return multiplierCost; }

    public long getLevel2Cookies() { return level2Cookies; }
    public boolean isLevel2Unlocked() { return isLevel2Unlocked; }
    public long getClickPowerLvl2() { return clickPowerLvl2; }
    public int getAutoClickersLvl2() { return autoClickersLvl2; }
    public int getSuperClicksLvl2() { return superClicksLvl2; }
    public int getQuantumGeneratorsLvl2() { return quantumGeneratorsLvl2; }
    public long getClickUpgradeCostLvl2() { return clickUpgradeCostLvl2; }
    public long getAutoClickerCostLvl2() { return autoClickerCostLvl2; }
    public long getSuperClickCostLvl2() { return superClickCostLvl2; }
    public long getQuantumGeneratorCostLvl2() { return quantumGeneratorCostLvl2; }
    public long getMultiplierCostLvl2() { return multiplierCostLvl2; }
}