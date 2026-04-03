package com.example.cookeisclickers.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;

public class MainActivity extends AppCompatActivity {

    private GameModel model;

    private TextView cookieView;
    private Button clickButton;
    private Button upgradeButton;
    private Button shopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = GameModel.getInstance();

        cookieView = findViewById(R.id.cookieView);
        clickButton = findViewById(R.id.clickButton);
        upgradeButton = findViewById(R.id.upgradeButton);
        shopButton = findViewById(R.id.shopButton);

        updateView();

        clickButton.setOnClickListener(v -> {
            model.addCookies();
            updateView();
        });

        upgradeButton.setOnClickListener(v -> {
            if (model.buyClickPowerUpgrade()) {
                updateView();
                Toast.makeText(this, "Апгрейд куплен!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Недостаточно печенек!", Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ ВОТ ЗДЕСЬ ДОЛЖЕН БЫТЬ КОД
        shopButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShopActivity.class);
            startActivity(intent);
        });
    }

    private void updateView() {
        cookieView.setText("Печеньки: " + model.getCookies());
    }
}