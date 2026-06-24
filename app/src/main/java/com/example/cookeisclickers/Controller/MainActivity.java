package com.example.cookeisclickers.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;

public class MainActivity extends AppCompatActivity {

    private GameModel model;
    private TextView cookieView;
    private ImageButton mainCookie;
    private Button btnLevel2, btnShop;
    private Button btnProfile;

    private final Handler autoClickHandler = new Handler();
    private Runnable autoClickRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = GameModel.getInstance();
        model.loadProgress(getApplicationContext());

        cookieView = findViewById(R.id.cookieView);
        mainCookie = findViewById(R.id.mainCookie);
        btnLevel2 = findViewById(R.id.btnLevel2);
        btnShop = findViewById(R.id.btnShop);
        btnProfile = findViewById(R.id.btnProfile);

        mainCookie.setOnClickListener(v -> {
            model.addCookies();
            updateView();
        });

        btnShop.setOnClickListener(v -> startActivity(new Intent(this, ShopActivity.class)));

        btnLevel2.setOnClickListener(v -> {
            if (model.isLevel2Unlocked()) {
                startActivity(new Intent(this, SecondLevelActivity.class));
            }
            else if (model.getCookies() >= 500) {
                model.removeCookies(500);
                model.unlockLevel2();
                model.saveProgress(getApplicationContext());
                updateView();
                startActivity(new Intent(this, SecondLevelActivity.class));
            } else {
                android.widget.Toast.makeText(this, "Нужно 500 🍪 для разблокировки!", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        autoClickRunnable = new Runnable() {
            @Override
            public void run() {
                if (model.getAutoClickers() > 0 || model.getCookieFactories() > 0) {
                    model.autoClick();
                }

                if (model.getAutoClickersLvl2() > 0 || model.getQuantumGeneratorsLvl2() > 0) {
                    long incomeLvl2 = ((long) model.getAutoClickersLvl2() * 10) + ((long) model.getQuantumGeneratorsLvl2() * 100);
                    model.addPassiveLevel2Cookies(incomeLvl2);
                }

                updateView();
                autoClickHandler.postDelayed(this, 1000);
            }
        };
    }

    private void updateView() {
        cookieView.setText(model.getCurrentUser() + " 🍪: " + model.getCookies());

        if (!model.isLevel2Unlocked()) {
            btnLevel2.setText("2 Ур. (500 🍪)");
        } else {
            btnLevel2.setText("2 УРОВЕНЬ (" + model.getLevel2Cookies() + " 🍪)");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
        autoClickHandler.post(autoClickRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoClickHandler.removeCallbacks(autoClickRunnable);
        model.saveProgress(getApplicationContext());
    }
}