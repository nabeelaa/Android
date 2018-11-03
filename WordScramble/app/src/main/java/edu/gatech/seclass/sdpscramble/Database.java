package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {

    private static Database instance;

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "sdpscramble_info";
    // Table names
    private static final String TABLE_IN_PROGRESS = "scramble_in_progress";

    // Common column names
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IDENTIFIER = "identifier";
    private static final String KEY_PHRASE = "scrambled_phrase";
    private static final String KEY_IN_PROGRESS_PHRASE = "in_progress_phrase";

    // Scramble_in_progress table create statement
    private static final String CREATE_TABLE_SCRAMBLE_IN_PROGRESS = "CREATE TABLE "
            + TABLE_IN_PROGRESS + "(" + KEY_IDENTIFIER + " TEXT," + KEY_USERNAME + " TEXT,"
            + KEY_PHRASE + " TEXT," + KEY_IN_PROGRESS_PHRASE + " TEXT)";

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    }

    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    This is a constructor for using in-memory database for testing.
    public Database(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_SCRAMBLE_IN_PROGRESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_SCRAMBLE_IN_PROGRESS);

        // create new tables
        onCreate(db);
    }


    public void updateScrambleProgress (String scrambleId, String playerUsername,
                                        String scrambledPhrase, String inProgressPhrase) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("REPLACE", scrambledPhrase);
        String scrambledPhraseReplaced = scrambledPhrase.replace("'", "''");
        String inProgressProgressReplaced = inProgressPhrase.replace("'", "''");
        Log.d("REPLACE", scrambledPhraseReplaced);

        // Query strings for select, update or insert
        String selection = KEY_USERNAME + " = '" + playerUsername + "' AND " + KEY_IDENTIFIER + " = '" + scrambleId +"'";
        String update = "UPDATE " + TABLE_IN_PROGRESS + " SET " + KEY_IN_PROGRESS_PHRASE + " = '" + inProgressProgressReplaced
                + "' WHERE " + KEY_USERNAME + " = '"
                + playerUsername + "' AND " + KEY_IDENTIFIER + " = '" + scrambleId + "'";
        String insert = "INSERT INTO " + TABLE_IN_PROGRESS + " VALUES ('" + scrambleId + "' , '"
                + playerUsername + "' , '" + scrambledPhraseReplaced + "', '" + inProgressProgressReplaced + "')";

        // If selection returns result, then update. Else, insert new in progress entry.
        // Sqlite does not suppor "IF EXISTS" syntax
        if (DatabaseUtils.queryNumEntries(db, TABLE_IN_PROGRESS, selection) > 0) {
            db.execSQL(update);
        }
        else {
            db.execSQL(insert);
        }
    }

    public ArrayList<String> retrieveScramblesInProgress (String playerUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String query = "SELECT * FROM " + TABLE_IN_PROGRESS + " WHERE " + KEY_USERNAME + " = '"
                + playerUsername + "'";

        cursor = db.rawQuery(query, null);
        ArrayList<String> listOfScrambleInProgress = new ArrayList<String >();

        // Iterate returned rows.
        if (cursor.moveToFirst()) {
            do {
                listOfScrambleInProgress.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listOfScrambleInProgress;
    }

    public ArrayList<String> returnInProgressPhrase (String scrambleId, String playerUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        ArrayList<String> inProgPhrasePair = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_IN_PROGRESS + " WHERE " + KEY_USERNAME + " = '"
                + playerUsername + "' AND " + KEY_IDENTIFIER + " = '" + scrambleId + "'";

        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Add scrambledPhrase to list
                inProgPhrasePair.add(cursor.getString(2));
                // Add inProgressPhrase to list
                inProgPhrasePair.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        return inProgPhrasePair;
    }

    public void deleteInProgressRecord (String scrambleId, String playerUsername) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_IN_PROGRESS, KEY_USERNAME + " =? AND " + KEY_IDENTIFIER + " =?",
                new String[] {playerUsername, scrambleId});
    }

}
