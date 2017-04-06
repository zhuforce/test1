package com.zf.sqlcipherTest;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBOpenHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(this);

        final Button insertBtn = (Button) findViewById(R.id.btn_insert);
        final Button selectBtn = (Button) findViewById(R.id.btn_select);
        final TextView contentTv = (TextView) findViewById(R.id.tv_content);

        mDBHelper = new DBOpenHelper(getApplicationContext(), "user.db", null, 1);
        final SQLiteDatabase database = mDBHelper.getWritableDatabase("key123");

        long defaultPageSize = database.getPageSize();
        Log.e("test", "defaultPageSize = " + defaultPageSize);
        database.setPageSize(16384);
        long pageSize = database.getPageSize();
        Log.e("test", "PageSize = " + pageSize);

//        File databaseFile = getDatabasePath("user.db");
//        final SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, "123", null);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", "insert start");
                insertData(database);
                Log.e("test", "insert end");
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentTv.append("\n select start");
                Log.e("test", "select start");
                long starTime = System.currentTimeMillis();
                Cursor cursor = database.rawQuery("SELECT * FROM UserInfo", null);
                List<UserEntity> userList = new ArrayList<>();
                try {
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        UserEntity user = readEntity(cursor, 0);
                        userList.add(user);
                    }
                } finally {
                    cursor.close();
                }
                long endTime = System.currentTimeMillis();
                long consumeTime = endTime - starTime;
                Log.e("test", "select end");
                contentTv.append("\n select result user size = " + userList.size() + ",consume = " + consumeTime + "ms");
            }
        });

    }

    public UserEntity readEntity(Cursor cursor, int offset) {
        UserEntity entity = new UserEntity( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getInt(offset + 1), // peerId
                cursor.getInt(offset + 2), // gender
                cursor.getString(offset + 3), // mainName
                cursor.getString(offset + 4), // pinyinName
                cursor.getString(offset + 5), // realName
                cursor.getString(offset + 6), // avatar
                cursor.getString(offset + 7), // phone
                cursor.getString(offset + 8), // email
                cursor.getInt(offset + 9), // departmentId
                cursor.getInt(offset + 10), // status
                cursor.getInt(offset + 11), // created
                cursor.getInt(offset + 12), // updated
                cursor.getString(offset + 13) //shortphone
        );
        return entity;
    }


    public void insertData(SQLiteDatabase database) {
        try {
            String sql = "INSERT INTO UserInfo ( aaaa, bbbb, cccc, dddd, eeee, ffff, gggg, hhhh, iiii, jjjj, kkkk, llll, mmmm, nnnn ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            database.beginTransaction();
            SQLiteStatement stmt = database.compileStatement(sql);

            for (int index = 0; index < 14000; index++) {
                stmt.bindLong(1, Long.valueOf(index + ""));
                stmt.bindDouble(2, index);
                stmt.bindDouble(3, 1);
                stmt.bindString(4, "tom");
                stmt.bindString(5, "lucy");
                stmt.bindString(6, "force");
                stmt.bindString(7, "http");
                stmt.bindString(8, "0201111");
                stmt.bindString(9, "email");
                stmt.bindDouble(10, 222);
                stmt.bindDouble(11, 1);
                stmt.bindDouble(12, 333);
                stmt.bindDouble(13, 444);
                stmt.bindString(14, "short");

                stmt.execute();
                stmt.clearBindings();
            }
        } finally {
            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }
}
