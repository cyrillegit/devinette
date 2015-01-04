package com.intelness.globals;

import android.app.Application;

public class AppManager extends Application {

    private int currentId;
    private int scores;

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

}
