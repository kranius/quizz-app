package com.kranius.quizz.questions;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionBank {
    private final List<Question> questions;
    private final int totalNumberOfQuestions;
    private int currentQuestion;

    public QuestionBank(List<Question> questions) {
        this.currentQuestion = 0;
        this.questions = questions;
        Collections.shuffle(this.questions, new Random());
        totalNumberOfQuestions = this.questions.size();
    }

    public Question getNextQuestion() {
        return currentQuestion == totalNumberOfQuestions ? null : questions.get(currentQuestion++);
    }

    public int getTotalNumberOfQuestions() {
        return totalNumberOfQuestions;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }
}
