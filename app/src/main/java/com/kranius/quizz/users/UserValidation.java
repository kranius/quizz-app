package com.kranius.quizz.users;

import android.content.Context;
import android.widget.Toast;

public class UserValidation {

    public static boolean nameValidation(String name, Context context) {
        if (name.length() < 2 || 12 < name.length()) {
            Toast.makeText(context,"Le nom doit faire entre 2 et 12 lettres",Toast.LENGTH_LONG).show();
            return false;
        }
        if (!name.matches("[a-zA-Z]+")) {
            Toast.makeText(context,"Le nom doit contenir uniquement des lettres",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
