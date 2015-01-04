package com.intelness.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.intelness.beans.DevinetteDAO;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG                     = "DatabaseHandler";

    public static final int     DATABASE_VERSION        = 1;
    public static final String  DATABASE_NAME           = "DevinetteDB";
    public static final String  CREATE_DEVINETTES_TABLE = "CREATE TABLE " + DevinetteDAO.TABLE_DEVINETTE + "("
                                                                + DevinetteDAO.KEY_ID + " INTEGER PRIMARY KEY,"
                                                                + DevinetteDAO.KEY_DEVINETTE + " TEXT,"
                                                                + DevinetteDAO.KEY_ANSWER + " TEXT,"
                                                                + DevinetteDAO.KEY_FIRST_HINT + " TEXT,"
                                                                + DevinetteDAO.KEY_SECOND_HINT + " TEXT,"
                                                                + DevinetteDAO.KEY_THIRD_HINT + " TEXT,"
                                                                + DevinetteDAO.KEY_DESCRIPTION_ANSWER + " TEXT"
                                                                + ")";
    public static final String  DROP_DEVINETTE_TABLE    = "DROP TABLE IF EXISTS " + DevinetteDAO.TABLE_DEVINETTE;

    public DatabaseHandler( Context context, String dbName, CursorFactory factory, int version ) {
        super( context, dbName, null, version );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        Log.d( TAG, CREATE_DEVINETTES_TABLE );
        db.execSQL( CREATE_DEVINETTES_TABLE );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( DROP_DEVINETTE_TABLE );
        onCreate( db );
    }

    public void reset() throws SQLException {

    }

}
