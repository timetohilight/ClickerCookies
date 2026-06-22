package com.example.cookeisclickers.Controller;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;
import androidx.appcompat.widget.AppCompatButton;

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


        Button btnShopHelpLvl1 = findViewById(R.id.btnShopHelpLvl1);

        if (btnShopHelpLvl1 != null) {
            btnShopHelpLvl1.setOnClickListener(v -> {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                builder.setTitle("Справка: Магазин улучшений (Уровень 1)");

                StringBuilder helpMessage = new StringBuilder();
                helpMessage.append("Здесь вы тратите обычные Печеньки 🍪 для автоматизации и ускорения добычи.\n\n");

                helpMessage.append("1. УЛУЧШИТЬ КЛИК\n");
                helpMessage.append("• Начальная цена: 50 🍪\n");
                helpMessage.append("• Что даёт: Добавляет +1 к базовой силе клика за каждую покупку.\n\n");

                helpMessage.append("2. КУПИТЬ АВТОКЛИКЕР\n");
                helpMessage.append("• Начальная цена: 150 🍪\n");
                helpMessage.append("• Что даёт: Приносит +1 печеньку в секунду в пассивном режиме.\n\n");

                helpMessage.append("3. КУПИТЬ ФАБРИКУ\n");
                helpMessage.append("• Начальная цена: 800 🍪\n");
                helpMessage.append("• Что даёт: Приносит сразу +15 печенек в секунду автоматически.\n\n");

                helpMessage.append("4. МНОЖИТЕЛЬ КЛИКА\n");
                helpMessage.append("• Начальная цена: 300 🍪\n");
                helpMessage.append("• Что даёт: Удваивает (x2) текущий общий множитель вашего клика.\n\n");

                helpMessage.append("⚠️ Цены растут при покупке: Клик (+50%), Автокликер (+40%), Фабрика (+60%), Множитель (+150%).");

                builder.setMessage(helpMessage.toString());
                builder.setPositiveButton("назад", (dialog, which) -> dialog.dismiss());
                builder.show();
            });
        }

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