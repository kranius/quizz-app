package com.kranius.quizz.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kranius.quizz.R;
import com.kranius.quizz.users.User;
import com.kranius.quizz.users.UserSharedPreference;
import com.kranius.quizz.users.UserValidation;


import org.json.JSONObject;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button start;
    private ImageView delete;
    private TextView name;
    private TextView banner;

    private User currentUser;
    private UserSharedPreference userSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSharedPreference = new UserSharedPreference(MainActivity.this);

        start = findViewById(R.id.btnStart);
        delete = findViewById(R.id.imgDeleteName);
        banner = findViewById(R.id.txtMainBanner);
        name = findViewById(R.id.txtinputName);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (UserValidation.nameValidation(name.getText().toString(), MainActivity.this)) {
                    enableUI();
                } else {
                    disableUI();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = userSharedPreference.makeUserFromPreference();

        if (currentUser.exists()) {
            banner.setText(currentUser.welcomeMessage());
            name.setText(currentUser.getName());
            enableUI();
        } else {
            disableUI();
        }
    }

    public void startQuizz(View view) {
            currentUser.setName(name.getText().toString());
            userSharedPreference.saveUserName(currentUser.getName());
        Intent intent = new Intent(MainActivity.this, QuizzActivity.class);
        startActivity(intent);
    }

    public void deleteName(View view) {
        banner.setText(R.string.txt_main_banner);
        name.setText("");
    }

    private void disableUI() {
        delete.setVisibility(View.INVISIBLE);
        start.setEnabled(false);
        start.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey)));
    }

    private void enableUI() {
        delete.setVisibility(View.VISIBLE);
        start.setEnabled(true);
        start.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.intenseGreen)));
    }
}