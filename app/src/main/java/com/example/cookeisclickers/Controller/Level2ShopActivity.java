package com.example.cookeisclickers.Controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;

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