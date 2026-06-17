package com.example.cookeisclickers.Controller;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;

public class ShopActivity extends AppCompatActivity {

    private GameModel model;
    private TextView shopTitle1;
    private Button buyClickPower;
    private Button buyAuto;
    private Button buyFactory;
    private Button buyMultiplier;
    private Button backButton1;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        model = GameModel.getInstance();

        shopTitle1 = findViewById(R.id.shopTitle1);
        buyClickPower = findViewById(R.id.buyClickPower);
        buyAuto = findViewById(R.id.buyAuto);
        buyFactory = findViewById(R.id.buyFactory);
        buyMultiplier = findViewById(R.id.buyMultiplier);
        backButton1 = findViewById(R.id.backButton1);

        buyClickPower.setOnClickListener(v -> {
            if (model.buyClickPowerUpgrade()) {
                updateShopUi();
            } else {
                showNoCookies();
            }
        });

        buyAuto.setOnClickListener(v -> {
            if (model.buyAutoClicker()) {
                updateShopUi();
            } else {
                showNoCookies();
            }
        });

        buyFactory.setOnClickListener(v -> {
            if (model.buyCookieFactory()) {
                updateShopUi();
            } else {
                showNoCookies();
            }
        });

        buyMultiplier.setOnClickListener(v -> {
            if (model.buyMultiplierUpgrade()) {
                updateShopUi();
            } else {
                showNoCookies();
            }
        });

        backButton1.setOnClickListener(v -> finish());

        startLivePriceUpdate();
    }

    private void startLivePriceUpdate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateShopUi();
                handler.postDelayed(this, 1000);
            }
        }, 0);
    }

    private void updateShopUi() {
        if (shopTitle1 != null) {
            shopTitle1.setText("БАЛАНС: " + model.getCookies() + " 🍪");
        }

        buyClickPower.setText("Улучшить клик: " + model.getClickUpgradeCost() + " 🍪");
        buyAuto.setText("Автокликер (" + model.getAutoClickers() + " шт): " + model.getAutoClickerCost() + " 🍪");
        buyFactory.setText("Фабрика (" + model.getCookieFactories() + " шт): " + model.getFactoryCost() + " 🍪");
        buyMultiplier.setText("Множитель клика: " + model.getMultiplierCost() + " 🍪");
    }

    private void showNoCookies() {
        Toast.makeText(this, "Недостаточно печенек для покупки!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        model.saveProgress(getApplicationContext());
    }
}