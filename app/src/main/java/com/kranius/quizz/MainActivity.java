package com.kranius.quizz;

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

/*
List.shuffle()
BackgroundTint
thread.sleep(3000) -> makeToast
class AlertDialog
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button start;
    private ImageView delete;
    private TextView name;
    private TextView banner;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.btnStart);
        delete = findViewById(R.id.imgDeleteName);
        name = findViewById(R.id.txtinputName);
        banner = findViewById(R.id.txtMainBanner);

        sharedPreferences = getSharedPreferences("scores", MODE_PRIVATE);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (name.getText().length() < 1) {
                    disableUI();
                } else {
                    enableUI();
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
        //sharedPreferences = getSharedPreferences("scores", MODE_PRIVATE);
        String nom = sharedPreferences.getString("name", "");
        String score = sharedPreferences.getString("score", "");

        if (1 <= nom.length()) {
            banner.setText("Bonjour " + nom + "\nVotre dernier score est " + score);
            enableUI();
        } else {
            disableUI();
        }
    }

    public void startQuizz(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name.getText().toString());
        editor.apply();
        Log.i(TAG, "Current user is " + name.getText());
        Intent intent = new Intent(MainActivity.this, QuizzActivity.class);
        startActivity(intent);
    }

    public void deleteName(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
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