package com.example.cookeisclickers.Controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;

public class ShopActivity extends AppCompatActivity {

    private GameModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        model = GameModel.getInstance();

        Button clickPower = findViewById(R.id.buyClickPower);
        Button autoClicker = findViewById(R.id.buyAutoClicker);
        Button multiplier = findViewById(R.id.buyMultiplier);

        clickPower.setOnClickListener(v -> {
            if (model.buyClickPowerUpgrade()) {
                Toast.makeText(this, "Сила клика увеличена!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Недостаточно печенек!", Toast.LENGTH_SHORT).show();
            }
        });

        autoClicker.setOnClickListener(v -> {
            if (model.buyAutoClicker()) {
                Toast.makeText(this, "Автокликер куплен!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Недостаточно печенек!", Toast.LENGTH_SHORT).show();
            }
        });

        multiplier.setOnClickListener(v -> {
            if (model.buyMultiplierUpgrade()) {
                Toast.makeText(this, "Множитель x2!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Недостаточно печенек!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}