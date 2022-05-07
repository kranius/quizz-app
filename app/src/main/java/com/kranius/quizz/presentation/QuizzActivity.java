package com.kranius.quizz.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kranius.quizz.R;
import com.kranius.quizz.questions.Question;
import com.kranius.quizz.questions.QuestionAdapter;
import com.kranius.quizz.questions.QuestionBank;
import com.kranius.quizz.users.UserSharedPreference;

import org.json.JSONArray;

public class QuizzActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = QuizzActivity.class.getSimpleName();

    private static final String QUESTION_TITLE = "Question ";

    private int correctIndex;
    private Integer currentIndex;
    private static int score = 0;

    private TextView b1, b2, b3, b4;
    private TextView title, description;

    private QuestionBank questionBank;

    private UserSharedPreference userSharedPreference;

    private String blob = null;
    private JSONArray json = null;
    private boolean fetchSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        userSharedPreference = new UserSharedPreference(QuizzActivity.this);

        title = findViewById(R.id.txtQuestionTitle);
        description = findViewById(R.id.txtQuestionDescription);
        b1 = findViewById(R.id.txtAnswerOne);
        b2 = findViewById(R.id.txtAnswerTwo);
        b3 = findViewById(R.id.txtAnswerThree);
        b4 = findViewById(R.id.txtAnswerFour);

        b1.setTag(1);
        b2.setTag(2);
        b3.setTag(3);
        b4.setTag(4);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        fetchRemoteQuestions();

    }

    @Override
    public void onClick(View view) {
        Integer responseIndex = (Integer) view.getTag();

        if (responseIndex == correctIndex) {
            score++;
            Toast.makeText(this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(
                () -> {
                    if (currentIndex == questionBank.getTotalNumberOfQuestions()) {
                        Log.i(TAG, "quizz ended");
                        displayResult();
                    } else {
                        displayQuestionAndAnswers();
                    }
                }, 2000
        );
    }

    private void displayResult() {
        String scorePrettyPrint = score + "/" + questionBank.getTotalNumberOfQuestions();

        userSharedPreference.saveUserScore(scorePrettyPrint);

        new AlertDialog.Builder(QuizzActivity.this)
                .setTitle("Quizz terminé")
                .setMessage("Votre score est de : " + scorePrettyPrint)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                })
                .show();
    }

    private void displayQuestionAndAnswers() {
        currentIndex = questionBank.getCurrentQuestion() + 1;
        int total = questionBank.getTotalNumberOfQuestions();
        Question question = questionBank.getNextQuestion();
        String[] answers = question.getAnswers();
        int correct = question.getCorrectAnswer();

        title.setText(QUESTION_TITLE + currentIndex.toString() + "/" + total + " :");
        description.setText(question.getQuestion());
        b1.setText(answers[0]);
        b2.setText(answers[1]);
        b3.setText(answers[2]);
        b4.setText(answers[3]);

        correctIndex = correct;
    }

    public void fetchRemoteQuestions() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.question_server);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonarray = new JSONArray(response);
                        questionBank = QuestionAdapter.prepareQuestionsFromJSON(jsonarray);
                        displayQuestionAndAnswers();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "json error", Toast.LENGTH_LONG).show();
                    }

                },
                error -> Toast.makeText(getApplicationContext(), "network error", Toast.LENGTH_LONG).show());

        queue.add(stringRequest);
    }
}