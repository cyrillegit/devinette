package com.intelness.beans;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.intelness.db.DatabaseDAO;

public class DevinetteDAO extends DatabaseDAO {

    public static final String  TABLE_DEVINETTE        = "devinettes";
    public static final String  KEY_ID                 = "id";
    public static final String  KEY_DEVINETTE          = "devinette";
    public static final String  KEY_ANSWER             = "answer";
    public static final String  KEY_FIRST_HINT         = "firstHint";
    public static final String  KEY_SECOND_HINT        = "secondHint";
    public static final String  KEY_THIRD_HINT         = "thirdHint";
    public static final String  KEY_DESCRIPTION_ANSWER = "descriptionAnswer";

    private static final String TAG                    = "DevinetteDAO";

    public DevinetteDAO( Context context ) {
        super( context );
    }

    /**
     * Adding a riddle
     * 
     * @param devinette
     */
    public void addDevinette( Devinette devinette ) {
        SQLiteDatabase db = open();

        ContentValues values = new ContentValues();
        values.put( KEY_DEVINETTE, devinette.getDevinette() );
        values.put( KEY_ANSWER, devinette.getAnswer() );
        values.put( KEY_FIRST_HINT, devinette.getFirstHint() );
        values.put( KEY_SECOND_HINT, devinette.getSecondHint() );
        values.put( KEY_THIRD_HINT, devinette.getThirdHint() );
        values.put( KEY_DESCRIPTION_ANSWER, devinette.getDescriptionAnswer() );

        db.insert( TABLE_DEVINETTE, null, values );
    }

    /**
     * Getting single riddle
     * 
     * @param id
     * @return
     */
    public Devinette getDevinetteById( int id ) {
        SQLiteDatabase db = open();

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
                cursor.getString( 2 ),
                cursor.getString( 3 ),
                cursor.getString( 4 ),
                cursor.getString( 5 ),
                cursor.getString( 6 ) );

        cursor.close();
        return devinette;
    }

    /**
     * Getting single riddle
     * 
     * @param id
     * @return
     */
    public Devinette getDevinetteByName( String name ) {
        SQLiteDatabase db = open();

        Cursor cursor = db.query( TABLE_DEVINETTE,
                new String[] { KEY_ID,
                        KEY_DEVINETTE,
                        KEY_ANSWER,
                        KEY_FIRST_HINT,
                        KEY_SECOND_HINT,
                        KEY_THIRD_HINT,
                        KEY_DESCRIPTION_ANSWER },
                KEY_DEVINETTE + "=?",
                new String[] { name },
                null,
                null,
                null,
                null );

        if ( cursor != null ) {
            cursor.moveToFirst();
        }

        Devinette devinette = new Devinette( Integer.parseInt( cursor.getString( 0 ) ),
                cursor.getString( 1 ),
                cursor.getString( 2 ),
                cursor.getString( 3 ),
                cursor.getString( 4 ),
                cursor.getString( 5 ),
                cursor.getString( 6 ) );

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
        SQLiteDatabase db = open();
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
        // cursor.close();
        return devinetteList;
    }

    /**
     * Getting riddles count
     * 
     * @return int
     */
    public int getNumberOfDevinettes() {
        SQLiteDatabase db = open();
        if ( db == null ) {
            return -1;
        }
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
        SQLiteDatabase db = open();

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
        SQLiteDatabase db = open();
        db.delete( TABLE_DEVINETTE, KEY_ID + " = ?", new String[] { String.valueOf( devinette.getId() ) } );
    }
}
