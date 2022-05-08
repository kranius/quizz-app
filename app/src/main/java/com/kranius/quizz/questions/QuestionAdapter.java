package com.kranius.quizz.questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class QuestionAdapter {

    private final static String TAG = QuestionAdapter.class.getSimpleName();

    public static QuestionBank prepareQuestionsFromJSON(JSONArray json) throws JSONException {
        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject questionObject = json.getJSONObject(i);
            Question question = new Question(questionObject);
            questions.add(question);
        }

        return new QuestionBank(questions);
    }

    public static List<Question> getRandomQuestions(List<Question> questions) {
        Random rand = new Random();
        List<Question> result = new ArrayList<>();

        int maxElems = 5;

        for (int i=0; i<maxElems; i++) {
            int index = rand.nextInt(questions.size());
            Question randomElement = questions.get(index);
            result.add(randomElement);
            questions.remove(randomElement);
        }

        return result;
    }
}
