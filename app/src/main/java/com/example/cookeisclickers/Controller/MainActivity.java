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

    // Новая переменная для кнопки профилей
    private Button btnProfile;

    private final Handler autoClickHandler = new Handler();
    private Runnable autoClickRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = GameModel.getInstance();

        // При самом первом запуске приложения загружаем последний активный профиль и его прогресс
        model.loadProgress(getApplicationContext());

        cookieView = findViewById(R.id.cookieView);
        mainCookie = findViewById(R.id.mainCookie);
        btnLevel2 = findViewById(R.id.btnLevel2);
        btnShop = findViewById(R.id.btnShop);

        // Привязываем кнопку профилей из XML
        btnProfile = findViewById(R.id.btnProfile);

        // Клик по большой печеньке
        mainCookie.setOnClickListener(v -> {
            model.addCookies();
            updateView();
        });

        // Открытие магазина первого уровня
        btnShop.setOnClickListener(v -> startActivity(new Intent(this, ShopActivity.class)));

        btnLevel2.setOnClickListener(v -> {
            // 1. Если этот конкретный профиль уже открывал 2 уровень ранее
            if (model.isLevel2Unlocked()) {
                startActivity(new Intent(this, SecondLevelActivity.class));
            }
            // 2. Если уровень закрыт, но у текущего игрока накоплено 500+ печенек
            else if (model.getCookies() >= 500) {
                model.removeCookies(500); // Вычитаем оплату
                model.unlockLevel2();     // Ставим флаг открытия в модели
                model.saveProgress(getApplicationContext()); // Записываем в файл этого юзера, чтобы не сбросилось!
                updateView();             // Обновляем циферки на главном экране
                startActivity(new Intent(this, SecondLevelActivity.class));
            } else {
                // Если печенек не хватает — выводим предупреждение
                android.widget.Toast.makeText(this, "Нужно 500 🍪 для разблокировки!", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        // ОБРАБОТЧИК КЛИКА: открываем экран управления профилями
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Фоновый поток для автокликера
        autoClickRunnable = new Runnable() {
            @Override
            public void run() {
                // Логика 1 уровня
                if (model.getAutoClickers() > 0 || model.getCookieFactories() > 0) {
                    model.autoClick();
                }

                // Логика 2 уровня
                if (model.getAutoClickersLvl2() > 0 || model.getQuantumGeneratorsLvl2() > 0) {
                    int incomeLvl2 = (model.getAutoClickersLvl2() * 10) + (model.getQuantumGeneratorsLvl2() * 100);
                    model.addPassiveLevel2Cookies(incomeLvl2);
                }

                updateView();
                autoClickHandler.postDelayed(this, 1000);
            }
        };
    }

    // Обновление текстов на экране
    private void updateView() {
        // Добавляем к выводу имя текущего игрока, чтобы всегда видеть, под кем мы зашли
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
        // ВАЖНО: обновляем интерфейс, так как игрок мог смениться на экране ProfileActivity
        updateView();
        autoClickHandler.post(autoClickRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoClickHandler.removeCallbacks(autoClickRunnable);
        // При сворачивании приложения или уходе на другой экран — сохраняем текущего игрока
        model.saveProgress(getApplicationContext());
    }
}