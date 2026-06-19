package com.example.cookeisclickers.Controller;

import androidx.annotation.NonNull;
import com.example.cookeisclickers.Model.GameModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardPresenter {

    private final View view;
    private final GameModel model;
    private DatabaseReference databaseReference;

    public interface View {
        void renderLeaderboard(List<String> formattedScores);
        void showSyncSuccess(String message);
        void showNetworkError(String error);
        void updateLocalUserData(String username, int lvl1, int lvl2);
    }

    public LeaderboardPresenter(View view) {
        this.view = view;
        this.model = GameModel.getInstance();

        try {
            FirebaseDatabase database;

            try {
                // Пытаемся взять стандартное приложение
                database = FirebaseDatabase.getInstance();
            } catch (IllegalStateException e) {
                // Если дефолтное приложение не инициализировано, инициализируем его ВРУЧНУЮ программно:
                com.google.firebase.FirebaseOptions options = new com.google.firebase.FirebaseOptions.Builder()
                        .setApiKey("AIzaSyA-ПOДCТAВЬ_CВOЙ_AРI_КЛЮЧ_ИЗ_ПAНEЛИ") // Опционально, но лучше указать
                        .setApplicationId("com.example.cookeisclickers")
                        .setDatabaseUrl("https://leader-bord-cookie-default-rtdb.europe-west1.firebasedatabase.app")
                        .build();

                // Инициализируем контекстом приложения
                android.content.Context appContext = null;
                if (view instanceof android.content.Context) {
                    appContext = ((android.content.Context) view).getApplicationContext();
                }

                if (appContext != null) {
                    com.google.firebase.FirebaseApp.initializeApp(appContext, options);
                    database = FirebaseDatabase.getInstance();
                } else {
                    // Если контекст не достали, стучимся напрямую по URL
                    database = FirebaseDatabase.getInstance("https://leader-bord-cookie-default-rtdb.europe-west1.firebasedatabase.app");
                }
            }

            this.databaseReference = database.getReference("leaderboard");
        } catch (Exception e) {
            this.databaseReference = null;
            view.showNetworkError("Критическая ошибка инициализации: " + e.getMessage());
        }
    }

    public void loadCurrentUserData() {
        if (model != null) {
            String username = model.getCurrentUser() != null ? model.getCurrentUser() : "Игрок";
            view.updateLocalUserData(username, model.getCookies(), model.getLevel2Cookies());
        }
    }

    public void uploadCurrentRecord() {
        if (databaseReference == null) {
            view.showNetworkError("Ошибка: База данных недоступна");
            return;
        }
        String currentUsername = model.getCurrentUser();
        if (currentUsername == null || currentUsername.trim().isEmpty()) {
            view.showNetworkError("Ошибка: Сначала введите имя пользователя в профиле!");
            return;
        }

        databaseReference.child(currentUsername).setValue(model.getFirebaseDataMap())
                .addOnSuccessListener(aVoid -> view.showSyncSuccess("Рекорд успешно выгружен в облако!"))
                .addOnFailureListener(e -> view.showNetworkError("Ошибка выгрузки: " + e.getMessage()));
    }

    public void downloadArchiveRecord(android.content.Context context) {
        if (databaseReference == null) {
            view.showNetworkError("Ошибка: База данных недоступна");
            return;
        }
        String currentUsername = model.getCurrentUser();
        if (currentUsername == null || currentUsername.trim().isEmpty()) {
            view.showNetworkError("Ошибка: Имя пользователя не найдено");
            return;
        }
        databaseReference.child(currentUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Long lvl1 = snapshot.child("cookiesLvl1").getValue(Long.class);
                    Long lvl2 = snapshot.child("cookiesLvl2").getValue(Long.class);
                    if (lvl1 != null && lvl2 != null) {
                        int cloudLvl1 = lvl1.intValue();
                        int cloudLvl2 = lvl2.intValue();

                        if ((cloudLvl1 + cloudLvl2) > (model.getCookies() + model.getLevel2Cookies())) {
                            int deltaLvl1 = cloudLvl1 - model.getCookies();
                            int deltaLvl2 = cloudLvl2 - model.getLevel2Cookies();

                            if (deltaLvl1 > 0) {
                                for (int i = 0; i < deltaLvl1; i++) {
                                    model.addCookies();
                                }
                            }
                            if (deltaLvl2 > 0) {
                                model.addPassiveLevel2Cookies(deltaLvl2);
                            }

                            model.saveProgress(context);
                            view.showSyncSuccess("Архивный прогресс успешно восстановлен из облака!");
                            loadCurrentUserData();
                        } else {
                            view.showSyncSuccess("Локальный прогресс актуальнее или равен облачному.");
                        }
                    }
                } else {
                    view.showNetworkError("В облаке отсутствуют записи для данного аккаунта.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.showNetworkError("Ошибка загрузки данных: " + error.getMessage());
            }
        });
    }

    public void loadGlobalLeaderboard() {
        if (databaseReference == null) return;

        databaseReference.orderByChild("totalScore").limitToLast(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> scoresList = new ArrayList<>();
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            String user = userSnapshot.child("username").getValue(String.class);
                            Long score = userSnapshot.child("totalScore").getValue(Long.class);
                            if (user != null && score != null) {
                                scoresList.add(user + " — 🏆 " + score);
                            }
                        }
                        Collections.reverse(scoresList);
                        view.renderLeaderboard(scoresList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        view.showNetworkError("Ошибка обновления лидерборда: " + error.getMessage());
                    }
                });
    }
}