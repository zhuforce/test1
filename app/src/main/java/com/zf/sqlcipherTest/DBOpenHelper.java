package com.zf.sqlcipherTest;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by force on 2017/3/25.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'UserInfo' (" +
                "'aaaa' INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "'bbbb' INTEGER NOT NULL UNIQUE," +
                "'cccc' INTEGER NOT NULL ," +
                "'dddd' TEXT NOT NULL ," +
                "'eeee' TEXT NOT NULL ," +
                "'ffff' TEXT NOT NULL ," +
                "'gggg' TEXT NOT NULL ," +
                "'hhhh' TEXT NOT NULL ," +
                "'iiii' TEXT NOT NULL ," +
                "'jjjj' INTEGER NOT NULL ," +
                "'kkkk' INTEGER NOT NULL ," +
                "'llll' INTEGER NOT NULL ," +
                "'mmmm' INTEGER NOT NULL ," +
                "'nnnn' TEXT NOT NULL );");
    }
}
