package com.example.cookeisclickers.Controller;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookeisclickers.R;
import com.example.cookeisclickers.Model.GameModel;

public class MainActivity extends AppCompatActivity {

    private GameModel model;

    private TextView cookieView;
    private Button clickButton;
    private Button upgradeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new GameModel();

        cookieView = findViewById(R.id.cookieView);
        clickButton = findViewById(R.id.clickButton);
        upgradeButton = findViewById(R.id.upgradeButton);

        updateView();

        clickButton.setOnClickListener(v -> {
            model.addCookies();
            updateView();
        });

        upgradeButton.setOnClickListener(v -> {
            if (model.upgradeClickPower()) {
                updateView();
                Toast.makeText(this, "Апгрейд куплен!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Недостаточно печенек!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateView() {
        cookieView.setText("Печеньки: " + model.getCookies());
    }
}
