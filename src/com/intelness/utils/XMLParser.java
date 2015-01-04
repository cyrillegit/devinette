package com.intelness.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.intelness.beans.Devinette;

public class XMLParser {

    static final String         DEVINETTE      = "devinette";
    static final String         DEVINETTE_TEXT = "text";
    static final String         ANSWER         = "answer";
    static final String         FIRST_HINT     = "first_hint";
    static final String         SECOND_HINT    = "second_hint";
    static final String         THIRD_HINT     = "third_hint";
    static final String         DESC_ANSWER    = "desc_answer";
    private static final String TAG            = "XMLPaser";

    List<Devinette>             devinettes;
    private Devinette           devinette;
    private String              text;

    public XMLParser() {
        devinettes = new ArrayList<Devinette>();
    }

    /**
     * return the list of all the devineettes parsed
     * 
     * @return
     */
    public List<Devinette> getDevinettes() {
        return devinettes;
    }

    /**
     * Parse devinettes from an inputStream
     * 
     * @param is
     * @return
     */
    public List<Devinette> parse( InputStream is ) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware( true );
            parser = factory.newPullParser();

            parser.setInput( is, null );

            int eventType = parser.getEventType();
            while ( eventType != XmlPullParser.END_DOCUMENT ) {
                String tagName = parser.getName();

                switch ( eventType ) {
                case XmlPullParser.START_TAG:
                    if ( tagName.equalsIgnoreCase( DEVINETTE ) ) {
                        // create a new instance of devinette
                        devinette = new Devinette();
                    }
                    break;

                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if ( tagName.equalsIgnoreCase( DEVINETTE ) ) {
                        // add devinette object to the list
                        devinettes.add( devinette );
                    } else if ( tagName.equalsIgnoreCase( DEVINETTE_TEXT ) ) {
                        Log.i( TAG, "devinette : " + text );
                        devinette.setDevinette( text );
                    } else if ( tagName.equalsIgnoreCase( ANSWER ) ) {
                        Log.i( TAG, "answer : " + text );
                        devinette.setAnswer( text );
                    } else if ( tagName.equalsIgnoreCase( FIRST_HINT ) ) {
                        Log.i( TAG, "first hint : " + text );
                        devinette.setFirstHint( text );
                    } else if ( tagName.equalsIgnoreCase( SECOND_HINT ) ) {
                        Log.i( TAG, "second hint : " + text );
                        devinette.setSecondHint( text );
                    } else if ( tagName.equalsIgnoreCase( THIRD_HINT ) ) {
                        Log.i( TAG, "third hint : " + text );
                        devinette.setThirdHint( text );
                    } else if ( tagName.equalsIgnoreCase( DESC_ANSWER ) ) {
                        Log.i( TAG, "desc answer : " + text );
                        devinette.setDescriptionAnswer( text );
                    }
                    break;
                default:
                    break;
                }
                eventType = parser.next();
            }
        } catch ( XmlPullParserException e ) {
            e.printStackTrace();
            Log.e( TAG, "XmlPullParserException parser" );
        } catch ( Exception e ) {
            e.printStackTrace();
            Log.e( TAG, "Exception parser" );
        }
        return devinettes;
    }
}
