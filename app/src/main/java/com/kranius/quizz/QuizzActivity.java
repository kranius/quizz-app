package com.kranius.quizz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class QuizzActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = QuizzActivity.class.getSimpleName();

    private static final String QUESTION_TITLE = "Question ";

    private int correctIndex;
    private Integer currentIndex;
    private static int score = 0;

    TextView b1, b2, b3, b4;
    TextView title, description;

    private QuestionBank questionBank = DataQuizz.generateQuestions();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        sharedPreferences = getSharedPreferences("scores", MODE_PRIVATE);

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
        displayQuestionAndAnswers();
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
                new Runnable() {
                    @Override
                    public void run() {
                        if (currentIndex == questionBank.getTotalNumberOfQuestions()) {
                            Log.i(TAG,"quizz ended");
                            displayResult();
                        } else {
                            displayQuestionAndAnswers();
                        }
                    }
                }, 2000
        );
    }

    private void displayResult() {
        String scorePrettyPrint = score + "/" + questionBank.getTotalNumberOfQuestions();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("score", scorePrettyPrint);
        editor.apply();

        AlertDialog alert = new AlertDialog.Builder(QuizzActivity.this)
                .setTitle("Quizz terminé")
                .setMessage("Votre score est de : " + scorePrettyPrint)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                })
                .show();
    }

    private void displayQuestionAndAnswers() {
        currentIndex = questionBank.getCurrentQuestion() + 1;
        Integer tot = questionBank.getTotalNumberOfQuestions();
        Question question = questionBank.getNextQuestion();
        String[] answers = question.getAnswers();
        int correct = question.getCorrectAnswer();

        Log.i("display questions", currentIndex.toString());

        StringBuilder sbTitle = new StringBuilder();
        sbTitle.append(QUESTION_TITLE).append(currentIndex.toString()).append("/").append(tot.toString()).append(" :");
        title.setText(sbTitle.toString());
        description.setText(question.getQuestion());
        b1.setText(answers[0]);
        b2.setText(answers[1]);
        b3.setText(answers[2]);
        b4.setText(answers[3]);

        correctIndex = correct;
    }
}