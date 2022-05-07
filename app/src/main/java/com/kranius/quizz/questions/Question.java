package com.kranius.quizz.questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class Question {

    private final String question;
    private final String[] answers;
    private final int correctAnswer;


    public Question(JSONObject questionObject) throws JSONException {
        this.question = new String(questionObject.getString("question").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        this.correctAnswer = questionObject.getInt("correct");

        JSONArray array = questionObject.getJSONArray("answers");
        answers = new String[array.length()];
        for (int i=0; i<array.length(); i++) {
            answers[i] = new String(array.getString(i).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
    }

    public Question(String question, String[] answers, int correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
