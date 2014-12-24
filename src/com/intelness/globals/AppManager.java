package com.intelness.globals;

import android.app.Application;

public class AppManager extends Application {

    private int currentId;

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId( int currentId ) {
        this.currentId = currentId;
    }

}
