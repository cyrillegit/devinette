package com.intelness.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.intelness.beans.Devinette;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION       = 1;
    private static final String DATABASE_NAME          = "DevinetteDB";
    private static final String TABLE_DEVINETTE        = "devinettes";

    private static final String KEY_ID                 = "id";
    private static final String KEY_DEVINETTE          = "devinette";
    private static final String KEY_ANSWER             = "answer";
    private static final String KEY_FIRST_HINT         = "firstHint";
    private static final String KEY_SECOND_HINT        = "secondHint";
    private static final String KEY_THIRD_HINT         = "thirdHint";
    private static final String KEY_DESCRIPTION_ANSWER = "descriptionAnswer";

    private static final String TAG                    = "DatabaseHandler";

    public DatabaseHandler( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        String CREATE_DEVINETTES_TABLE = "CREATE TABLE " + TABLE_DEVINETTE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DEVINETTE + " TEXT,"
                + KEY_ANSWER + " TEXT,"
                + KEY_FIRST_HINT + " TEXT,"
                + KEY_SECOND_HINT + " TEXT,"
                + KEY_THIRD_HINT + " TEXT,"
                + KEY_DESCRIPTION_ANSWER + " TEXT"
                + ")";
        Log.d( TAG, CREATE_DEVINETTES_TABLE );
        db.execSQL( CREATE_DEVINETTES_TABLE );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_DEVINETTE );
        onCreate( db );
    }

    public void reset() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete( TABLE_DEVINETTE, null, null );
        // this.onCreate( db );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_DEVINETTE );
        onCreate( db );
    }

    /**
     * Adding a riddle
     * 
     * @param devinette
     */
    public void addDevinette( Devinette devinette ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_DEVINETTE, devinette.getDevinette() );
        values.put( KEY_ANSWER, devinette.getAnswer() );

        db.insert( TABLE_DEVINETTE, null, values );
    }

    /**
     * Getting single riddle
     * 
     * @param id
     * @return
     */
    public Devinette getDevinette( int id ) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query( TABLE_DEVINETTE,
                new String[] { KEY_ID,
                        KEY_DEVINETTE,
                        KEY_ANSWER,
                        KEY_FIRST_HINT,
                        KEY_SECOND_HINT,
                        KEY_THIRD_HINT,
                        KEY_DESCRIPTION_ANSWER },
                KEY_ID + "=?",
                new String[] { String.valueOf( id ) },
                null,
                null,
                null,
                null );

        if ( cursor != null ) {
            cursor.moveToFirst();
        }

        Devinette devinette = new Devinette( Integer.parseInt( cursor.getString( 0 ) ),
                cursor.getString( 1 ),
                cursor.getString( 2 ) );

        return devinette;
    }

    /**
     * Getting all riddles
     * 
     * @return
     */
    public List<Devinette> getAllDevinettes() {
        List<Devinette> devinetteList = new ArrayList<Devinette>();

        String selectQuery = "SELECT * FROM " + TABLE_DEVINETTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( selectQuery, null );

        // Looping through all row and adding to list
        if ( cursor.moveToFirst() ) {
            do {
                Devinette devinette = new Devinette();
                devinette.setId( Integer.parseInt( cursor.getString( 0 ) ) );
                devinette.setDevinette( cursor.getString( 1 ) );
                devinette.setAnswer( cursor.getString( 2 ) );
                devinette.setFirstHint( cursor.getString( 3 ) );
                devinette.setSecondHint( cursor.getString( 4 ) );
                devinette.setThirdHint( cursor.getString( 5 ) );
                devinette.setDescriptionAnswer( cursor.getString( 6 ) );

                // Adding devinettes to list
                devinetteList.add( devinette );
            } while ( cursor.moveToNext() );
        }
        return devinetteList;
    }

    /**
     * Getting riddles count
     * 
     * @return int
     */
    public int getNumberOfDevinettes() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries( db, TABLE_DEVINETTE );
        Log.e( TAG, "number rows: " + numRows );
        return numRows;
    }

    /**
     * Updating single riddle
     * 
     * @param devinette
     * @return
     */
    public int updateDevinette( Devinette devinette ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_DEVINETTE, devinette.getDevinette() );
        values.put( KEY_ANSWER, devinette.getAnswer() );
        values.put( KEY_FIRST_HINT, devinette.getFirstHint() );
        values.put( KEY_SECOND_HINT, devinette.getSecondHint() );
        values.put( KEY_THIRD_HINT, devinette.getThirdHint() );
        values.put( KEY_DESCRIPTION_ANSWER, devinette.getDescriptionAnswer() );

        return db.update( TABLE_DEVINETTE, values, KEY_ID + " = ?",
                new String[] { String.valueOf( devinette.getId() ) } );
    }

    /**
     * Deleting single riddle
     * 
     * @param devinette
     */
    public void deleteDevinette( Devinette devinette ) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( TABLE_DEVINETTE, KEY_ID + " = ?", new String[] { String.valueOf( devinette.getId() ) } );
    }
}
