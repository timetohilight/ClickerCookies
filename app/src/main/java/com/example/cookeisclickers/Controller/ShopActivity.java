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

    // Хендлер (таймер) для непрерывного обновления цен на экране
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // Получаем доступ к модели данных
        model = GameModel.getInstance();

        // Связываем элементы интерфейса с XML по ID
        shopTitle1 = findViewById(R.id.shopTitle1);
        buyClickPower = findViewById(R.id.buyClickPower);
        buyAuto = findViewById(R.id.buyAuto);
        buyFactory = findViewById(R.id.buyFactory);
        buyMultiplier = findViewById(R.id.buyMultiplier);
        backButton1 = findViewById(R.id.backButton1);

        // Обработка нажатия: Сила клика
        buyClickPower.setOnClickListener(v -> {
            if (model.buyClickPowerUpgrade()) {
                updateShopUi(); // Сразу обновляем текст после успешной покупки
            } else {
                showNoCookies();
            }
        });

        // Обработка нажатия: Автокликер
        buyAuto.setOnClickListener(v -> {
            if (model.buyAutoClicker()) {
                updateShopUi();
            } else {
                showNoCookies();
            }
        });

        // Обработка нажатия: Кондитерская фабрика
        buyFactory.setOnClickListener(v -> {
            if (model.buyCookieFactory()) {
                updateShopUi();
            } else {
                showNoCookies();
            }
        });

        // Обработка нажатия: Множитель x2
        buyMultiplier.setOnClickListener(v -> {
            if (model.buyMultiplierUpgrade()) {
                updateShopUi();
            } else {
                showNoCookies();
            }
        });

        // Кнопка выхода назад в игру
        backButton1.setOnClickListener(v -> finish());

        // Запускаем живое обновление цен каждую секунду
        startLivePriceUpdate();
    }

    // Метод, создающий бесконечный цикл обновления интерфейса
    private void startLivePriceUpdate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateShopUi(); // Постоянно запрашиваем свежие цены и баланс из модели
                handler.postDelayed(this, 1000); // Повторяем каждую 1 секунду
            }
        }, 0); // Первый запуск происходит мгновенно при открытии
    }

    // Функция, которая берет данные из модели и записывает их на кнопки
    private void updateShopUi() {
        // Показываем текущий баланс печенек игрока
        if (shopTitle1 != null) {
            shopTitle1.setText("БАЛАНС: " + model.getCookies() + " 🍪");
        }

        // Выводим стоимость каждого улучшения прямо на кнопки
        buyClickPower.setText("Улучшить клик: " + model.getClickUpgradeCost() + " 🍪");
        buyAuto.setText("Автокликер (" + model.getAutoClickers() + " шт): " + model.getAutoClickerCost() + " 🍪");
        buyFactory.setText("Фабрика (" + model.getCookieFactories() + " шт): " + model.getFactoryCost() + " 🍪");
        buyMultiplier.setText("Множитель клика: " + model.getMultiplierCost() + " 🍪");
    }

    // Сообщение, если у игрока не хватает печенек для покупки
    private void showNoCookies() {
        Toast.makeText(this, "Недостаточно печенек для покупки!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Обязательно останавливаем таймер при выходе из магазина, чтобы игра не лагала
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Автоматическое сохранение при выходе из магазина первого уровня
        model.saveProgress(getApplicationContext());
    }
}