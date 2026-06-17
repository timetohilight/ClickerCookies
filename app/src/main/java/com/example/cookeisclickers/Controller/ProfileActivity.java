package com.example.cookeisclickers.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cookeisclickers.Model.GameModel;
import com.example.cookeisclickers.R;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private GameModel model;
    private TextView tvCurrentProfile;
    private EditText etNewUsername;
    private Button btnCreateProfile, btnDeleteProfile, btnBack;
    private ListView lvProfiles;

    private List<ProfileData> profileList;
    private ProfileAdapter adapter;
    private int selectedPosition = -1;

    private static class ProfileData {
        String name;
        int cookiesLvl1;
        int cookiesLvl2;

        ProfileData(String name, int cookiesLvl1, int cookiesLvl2) {
            this.name = name;
            this.cookiesLvl1 = cookiesLvl1;
            this.cookiesLvl2 = cookiesLvl2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        model = GameModel.getInstance();

        tvCurrentProfile = findViewById(R.id.tvCurrentProfile);
        etNewUsername = findViewById(R.id.etNewUsername);
        btnCreateProfile = findViewById(R.id.btnCreateProfile);
        btnDeleteProfile = findViewById(R.id.btnDeleteProfile);
        btnBack = findViewById(R.id.btnBack);
        lvProfiles = findViewById(R.id.lvProfiles);

        profileList = new ArrayList<>();
        adapter = new ProfileAdapter(this, profileList);
        lvProfiles.setAdapter(adapter);

        updateUi();

        lvProfiles.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            adapter.notifyDataSetChanged();
        });

        btnCreateProfile.setOnClickListener(v -> {
            String username = etNewUsername.getText().toString().trim();

            if (!TextUtils.isEmpty(username)) {
                model.changeUser(username, getApplicationContext());
                etNewUsername.setText("");
                selectedPosition = -1;
                updateUi();
                Toast.makeText(this, "Профиль " + username + " активен!", Toast.LENGTH_SHORT).show();
            } else if (selectedPosition != -1) {
                String selectedUser = profileList.get(selectedPosition).name;
                model.changeUser(selectedUser, getApplicationContext());
                selectedPosition = -1;
                updateUi();
                Toast.makeText(this, "Переключено на: " + selectedUser, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Введите имя или выберите профиль из списка!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteProfile.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                String selectedUser = profileList.get(selectedPosition).name;

                if (selectedUser.equals(model.getCurrentUser())) {
                    Toast.makeText(this, "Нельзя удалить текущий активный профиль!", Toast.LENGTH_SHORT).show();
                    return;
                }

                model.deleteUser(selectedUser, getApplicationContext());
                selectedPosition = -1;
                updateUi();
                Toast.makeText(this, "Профиль " + selectedUser + " удален!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Выберите профиль из списка для удаления!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void updateUi() {
        tvCurrentProfile.setText("Текущий профиль: " + model.getCurrentUser());
        profileList.clear();

        for (String user : model.getUserList()) {
            SharedPreferences userPrefs = getSharedPreferences("cookies_save_" + user, Context.MODE_PRIVATE);
            int c1 = userPrefs.getInt("cookies", 0);
            int c2 = userPrefs.getInt("level2_cookies", 0);

            profileList.add(new ProfileData(user, c1, c2));
        }

        adapter.notifyDataSetChanged();
    }


    private class ProfileAdapter extends BaseAdapter {
        private final Context context;
        private final List<ProfileData> items;

        ProfileAdapter(Context context, List<ProfileData> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() { return items.size(); }
        @Override
        public Object getItem(int position) { return items.get(position); }
        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
            }

            RadioButton rbSelect = convertView.findViewById(R.id.rbSelect);
            TextView tvProfileInfo = convertView.findViewById(R.id.tvProfileInfo);

            ProfileData data = items.get(position);

            tvProfileInfo.setText(data.name + "  [ Lvl 1: " + data.cookiesLvl1 + " 🍪 | Lvl 2: " + data.cookiesLvl2 + " 🍪 ]");

            rbSelect.setChecked(position == selectedPosition);

            return convertView;
        }
    }
}