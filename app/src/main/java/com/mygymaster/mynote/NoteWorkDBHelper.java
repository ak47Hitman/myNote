package com.mygymaster.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logvinov on 28.04.2015.
 */
public class NoteWorkDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "noteDB.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "notes";
    public static final String NOTE_ID_COLUMN = BaseColumns._ID;
    public static final String NOTE_NAME_COLUMN = "note_name";
    public static final String NOTE_DATE_COLUMN = "date";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + NOTE_NAME_COLUMN
            + " text not null unique, " + NOTE_DATE_COLUMN + " text not null);";

    public NoteWorkDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF IT EXIST " + DATABASE_TABLE);
        // Создаём новую таблицу
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTE_NAME_COLUMN, note.getName());
        values.put(NOTE_DATE_COLUMN, note.getDate());

        // Inserting Row
        db.insert(DATABASE_TABLE, null, values);
        db.close(); // Closing database connection
    }

    Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { NOTE_ID_COLUMN,
                        NOTE_NAME_COLUMN, NOTE_DATE_COLUMN }, NOTE_ID_COLUMN + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return note;
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(Integer.parseInt(cursor.getString(0)));
                note.setName(cursor.getString(1));
                note.setDate(cursor.getString(2));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return noteList;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTE_NAME_COLUMN, note.getName());
        values.put(NOTE_DATE_COLUMN, note.getDate());

        // updating row
        return db.update(DATABASE_TABLE, values, NOTE_ID_COLUMN + " = ?",
                new String[] { String.valueOf(note.getID()) });
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, NOTE_ID_COLUMN + " = ?",
                new String[] { String.valueOf(note.getID()) });
        db.close();
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public Cursor getCursor() {
        String selectQuery = "SELECT " + NoteWorkDBHelper.NOTE_ID_COLUMN +", " + NoteWorkDBHelper.NOTE_NAME_COLUMN + ", " + NoteWorkDBHelper.NOTE_DATE_COLUMN + " FROM " + NoteWorkDBHelper.DATABASE_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(selectQuery, null);
    }
}