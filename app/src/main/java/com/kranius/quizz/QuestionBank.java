package com.kranius.quizz;

import java.util.List;

public class QuestionBank {
    private final List<Question> questions;
    private final int totalNumberOfQuestions;
    private static int currentQuestion = 0;

    public QuestionBank(List<Question> questions) {
        this.questions = questions;
        totalNumberOfQuestions = this.questions.size();
    }

    public Question getNextQuestion() {
        if (currentQuestion == totalNumberOfQuestions)
            return null;
        return questions.get(currentQuestion++);
    }

    public int getTotalNumberOfQuestions() {
        return totalNumberOfQuestions;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }
}
