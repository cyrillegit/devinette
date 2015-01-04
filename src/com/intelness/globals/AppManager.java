package com.intelness.globals;

import java.util.ArrayList;

import android.app.Application;

public class AppManager extends Application {

    private int                currentId;
    private int                scores;
    private ArrayList<Integer> playedId;
    private ArrayList<String>  answers;

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId( int currentId ) {
        this.currentId = currentId;
    }

    public int getScores() {
        return scores;
    }

    public void setScores( int scores ) {
        this.scores = scores;
    }

    public ArrayList<Integer> getPlayedId() {
        return playedId;
    }

    public void setPlayedId( ArrayList<Integer> playedId ) {
        this.playedId = playedId;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers( ArrayList<String> answers ) {
        this.answers = answers;
    }

}
