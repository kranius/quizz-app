package com.kranius.quizz.users;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Optional;
import java.util.UUID;

public class UserSharedPreference {

    private static final String PREFERENCE = "scores";
    private static final String NAME_PREFERENCE = "name";
    private static final String SCORE_PREFERENCE = "score";

    private Context context;
    private SharedPreferences sharedPreferences;

    public void saveUserName(String userName) {
        setPreference(NAME_PREFERENCE, userName);
    }

    public void saveUserScore(String score) {
        setPreference(SCORE_PREFERENCE, score);
    }

    public String getUserName() {
        return getPreference(NAME_PREFERENCE, "");
    }

    public String getUserScore() {
        return getPreference(SCORE_PREFERENCE, "");
    }

    public void deleteUserName() {
        deletePreference(NAME_PREFERENCE);
    }

    public void deleteUserScore() {
        deletePreference(SCORE_PREFERENCE);
    }

    private boolean isPreferenceEmpty(String preferenceName) {
        return getPreference(preferenceName, "").isEmpty();
    }

    private String getPreference(String preferenceName, String defaultValue) {
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    private void setPreference(String preferenceName, String preferenceValue) {
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    private void deletePreference(String preferenceName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(preferenceName)) {
            editor.remove(preferenceName);
            editor.apply();
        }
    }

    public User makeUserFromPreference() {
        return new User(UUID.randomUUID().getMostSignificantBits(), getUserName(), getUserScore());
    }

    public UserSharedPreference(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    public UserSharedPreference() {
    }

    public static String getScorePreference() {
        return SCORE_PREFERENCE;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
