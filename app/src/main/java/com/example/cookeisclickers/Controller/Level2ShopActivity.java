package com.example.cookeisclickers.Controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;
import androidx.appcompat.widget.AppCompatButton;

public class Level2ShopActivity extends AppCompatActivity {

    private GameModel model;
    private TextView shopTitle2;
    private Button buyClickPower2;
    private Button buyAuto2;
    private Button buySuperClick2;
    private Button buyQuantum2;
    private Button buyClick2;
    private Button backButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop2);

        Button btnShopHelpLvl2 = findViewById(R.id.btnShopHelpLvl2);

        if (btnShopHelpLvl2 != null) {
            btnShopHelpLvl2.setOnClickListener(v -> {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                builder.setTitle("Справка: Космический магазин (Уровень 2)");

                StringBuilder helpMessage = new StringBuilder();
                helpMessage.append("Добро пожаловать в продвинутый магазин! Расчеты ведутся строго в валюте OREO 🍪.\n\n");

                helpMessage.append("1. УЛУЧШИТЬ КЛИК\n");
                helpMessage.append("• Начальная цена: 200 OREO\n");
                helpMessage.append("• Что даёт: Прибавляет +15 единиц к базовой силе клика за покупку.\n\n");

                helpMessage.append("2. КВАНТОВЫЙ АВТОКЛИКЕР\n");
                helpMessage.append("• Начальная цена: 1 000 OREO\n");
                helpMessage.append("• Что даёт: Приносит +20 OREO в секунду в пассивном режиме.\n\n");

                helpMessage.append("3. СУПЕР-КЛИК\n");
                helpMessage.append("• Начальная цена: 5 000 OREO\n");
                helpMessage.append("• Что даёт: Добавляет мощный бонус +50 очков к каждому активному нажатию.\n\n");

                helpMessage.append("4. КВАНТОВЫЙ ГЕНЕРАТОР\n");
                helpMessage.append("• Начальная цена: 15 000 OREO\n");
                helpMessage.append("• Что даёт: Дает колоссальный пассивный доход +300 OREO в секунду.\n\n");

                helpMessage.append("5. МНОЖИТЕЛЬ\n");
                helpMessage.append("• Начальная цена: 2 000 OREO\n");
                helpMessage.append("• Что даёт: Мгновенно удваивает (x2) текущую базовую силу клика 2 уровня.\n\n");

                helpMessage.append("⚠️ Цены растут при покупке: Клик (+50%), Автокликер (+40%), Супер-клик (+70%), Генератор (+80%), Множитель (+200%).");

                builder.setMessage(helpMessage.toString());
                builder.setPositiveButton("назад", (dialog, which) -> dialog.dismiss());
                builder.show();
            });
        }

        model = GameModel.getInstance();

        shopTitle2 = findViewById(R.id.shopTitle2);
        buyClickPower2 = findViewById(R.id.buyClickPower2);
        buyAuto2 = findViewById(R.id.buyAuto2);
        buySuperClick2 = findViewById(R.id.buySuperClick2);
        buyQuantum2 = findViewById(R.id.buyQuantum2);
        buyClick2 = findViewById(R.id.buyClick2);
        backButton2 = findViewById(R.id.backButton2);

        updateShopUi();

        buyClickPower2.setOnClickListener(v -> {
            if (model.buyClickPowerUpgradeLvl2()) { updateShopUi(); }
            else { showNoCookies(); }
        });

        buyAuto2.setOnClickListener(v -> {
            if (model.buyAutoClickerLvl2()) { updateShopUi(); }
            else { showNoCookies(); }
        });

        buySuperClick2.setOnClickListener(v -> {
            if (model.buySuperClickLvl2()) { updateShopUi(); }
            else { showNoCookies(); }
        });

        buyQuantum2.setOnClickListener(v -> {
            if (model.buyQuantumGeneratorLvl2()) { updateShopUi(); }
            else { showNoCookies(); }
        });

        buyClick2.setOnClickListener(v -> {
            if (model.buyMultiplierUpgradeLvl2()) { updateShopUi(); }
            else { showNoCookies(); }
        });

        backButton2.setOnClickListener(v -> finish());
    }

    private void updateShopUi() {
        if (shopTitle2 != null) {
            shopTitle2.setText("OREO: " + model.getLevel2Cookies() + " 🍪");
        }

        buyAuto2.setText("Квант. Автокликер (" + model.getAutoClickersLvl2() + " шт)\nЦена: " + model.getAutoClickerCostLvl2() + " 🍪");

        buyQuantum2.setText("Квант. Ген. (" + model.getQuantumGeneratorsLvl2() + " шт)\nЦена: " + model.getQuantumGeneratorCostLvl2() + " 🍪");

        buyClickPower2.setText("Улучшить клик\nЦена: " + model.getClickUpgradeCostLvl2() + " 🍪");
        buySuperClick2.setText("Супер-клик\nЦена: " + model.getSuperClickCostLvl2() + " 🍪");
        buyClick2.setText("Множитель\nЦена: " + model.getMultiplierCostLvl2() + " 🍪");
    }

    private void showNoCookies() {
        Toast.makeText(this, "Недостаточно печенек 2 уровня!", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateShopUi();
    }

    @Override
    protected void onPause() {
        super.onPause();


        model.saveProgress(getApplicationContext());
    }
}