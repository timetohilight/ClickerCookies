package com.example.cookeisclickers.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;

public class MainActivity extends AppCompatActivity {

    private GameModel model;
    private TextView cookieView;
    private ImageButton mainCookie;
    private android.widget.Button btnLevel2, btnShop;

    private final Handler autoClickHandler = new Handler();
    private Runnable autoClickRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = GameModel.getInstance();
        // Загрузка прогресса при запуске
        model.loadProgress(getApplicationContext());

        cookieView = findViewById(R.id.cookieView);
        mainCookie = findViewById(R.id.mainCookie);
        btnLevel2 = findViewById(R.id.btnLevel2);
        btnShop = findViewById(R.id.btnShop);

        mainCookie.setOnClickListener(v -> {
            model.addCookies();
            updateView();
        });

        btnShop.setOnClickListener(v -> startActivity(new Intent(this, ShopActivity.class)));

        btnLevel2.setOnClickListener(v -> {
            if (model.isLevel2Unlocked()) {
                startActivity(new Intent(this, SecondLevelActivity.class));
            } else if (model.getCookies() >= 500) {
                model.removeCookies(500);
                model.unlockLevel2();
                updateView();
                startActivity(new Intent(this, SecondLevelActivity.class));
            }
        });

        autoClickRunnable = new Runnable() {
            @Override
            public void run() {
                // ЛОГИКА 1 УРОВНЯ
                if (model.getAutoClickers() > 0 || model.getCookieFactories() > 0) {
                    model.autoClick();
                }

                // ЛОГИКА 2 УРОВНЯ (исправлена)
                if (model.getAutoClickersLvl2() > 0 || model.getQuantumGeneratorsLvl2() > 0) {

                    int incomeLvl2 = (model.getAutoClickersLvl2() * 10) + (model.getQuantumGeneratorsLvl2() * 100);
                    model.addPassiveLevel2Cookies(incomeLvl2);
                }

                updateView();
                autoClickHandler.postDelayed(this, 1000);
            }
        };
    }

    private void updateView() {
        cookieView.setText("🍪: " + model.getCookies());
        if (!model.isLevel2Unlocked()) {
            btnLevel2.setText("2 Ур. (500 🍪)");
        } else {
            btnLevel2.setText("2 УРОВЕНЬ (" + model.getLevel2Cookies() + " 🍪)");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoClickHandler.post(autoClickRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoClickHandler.removeCallbacks(autoClickRunnable);
        // Автоматическое сохранение при уходе с главного экрана или его сворачивании
        model.saveProgress(getApplicationContext());
    }

    // Автосейв


}