package com.kranius.quizz.users;

import androidx.annotation.NonNull;

public class User {
    private Long id;
    private String name;
    private String lastScore;

    public User(Long id, String name, String lastScore) {
        this.id = id;
        this.name = name;
        this.lastScore = lastScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastScore() {
        return lastScore;
    }

    public void setLastScore(String lastScore) {
        this.lastScore = lastScore;
    }

    public User() {
    }

    public boolean exists() {
        return !name.isEmpty() && !lastScore.isEmpty();
    }

    public String welcomeMessage() {
        return "Bonjour " + name + "\nVotre dernier score est " + lastScore;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastScore='" + lastScore + '\'' +
                '}';
    }
}
