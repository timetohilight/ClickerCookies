package com.example.cookeisclickers.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;

public class SecondLevelActivity extends AppCompatActivity {

    private GameModel model;
    private TextView cookieView;
    private ImageButton mainCookie;
    private android.widget.Button btnBack, btnShop;
    private final Handler handler = new Handler();
    private Runnable updateRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_level);

        model = GameModel.getInstance();

        cookieView = findViewById(R.id.cookieViewLvl2);
        mainCookie = findViewById(R.id.mainCookieLvl2);
        btnBack = findViewById(R.id.btnBackToLvl1);
        btnShop = findViewById(R.id.btnShopLvl2);

        updateRunnable = new Runnable() {
            @Override
            public void run() {
                model.autoClickLvl2();
                updateView();
                handler.postDelayed(this, 1000);
            }
        };

        mainCookie.setOnClickListener(v -> {
            model.addLevel2Cookies();
            updateView();
        });

        btnBack.setOnClickListener(v -> finish());
        btnShop.setOnClickListener(v -> startActivity(new Intent(SecondLevelActivity.this, Level2ShopActivity.class)));
    }

    private void updateView() {
        cookieView.setText("🍪 LVL 2: " + model.getLevel2Cookies());
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
        handler.postDelayed(updateRunnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateRunnable);
    }
}