package com.example.cookeisclickers.Controller;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.cookeisclickers.R;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity implements LeaderboardPresenter.View {
    private LeaderboardPresenter presenter;
    private ListView listViewLeaderboard;
    private TextView textViewLocalUserStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_leaderboard);

            listViewLeaderboard = findViewById(R.id.listViewLeaderboard);
            textViewLocalUserStatus = findViewById(R.id.textViewLocalUserStatus);

            AppCompatButton btnUploadScore = findViewById(R.id.btnUploadScore);
            AppCompatButton btnDownloadScore = findViewById(R.id.btnDownloadScore);
            AppCompatButton btnLeaderboardBack = findViewById(R.id.btnLeaderboardBack);

            presenter = new LeaderboardPresenter(this);

            if (btnUploadScore != null) {
                btnUploadScore.setOnClickListener(v -> {
                    if (presenter != null) presenter.uploadCurrentRecord();
                });
            }
            if (btnDownloadScore != null) {
                btnDownloadScore.setOnClickListener(v -> {
                    if (presenter != null) presenter.downloadArchiveRecord(getApplicationContext());
                });
            }
            if (btnLeaderboardBack != null) {
                btnLeaderboardBack.setOnClickListener(v -> finish());
            }

        } catch (Exception e) {
            Toast.makeText(this, "Ошибка onCreate: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (presenter != null) {
                presenter.loadCurrentUserData();
                presenter.loadGlobalLeaderboard();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка на входе (onResume): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void renderLeaderboard(List<String> formattedScores) {
        if (listViewLeaderboard != null && formattedScores != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.leaderboard_item, formattedScores);
            listViewLeaderboard.setAdapter(adapter);
        }
    }

    @Override
    public void updateLocalUserData(String username, long lvl1, long lvl2) {
        if (textViewLocalUserStatus != null) {
            textViewLocalUserStatus.setText("Текущая сессия: " + username + " [Печеньки: " + lvl1 + " | Oreo: " + lvl2 + "]");
        }
    }

    @Override
    public void showSyncSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}