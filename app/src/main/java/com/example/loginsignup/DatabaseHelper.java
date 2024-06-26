package com.example.loginsignup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String name = "tictactoaDB";

    public DatabaseHelper(@Nullable Context context) {
        super(context, name,  null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE hosts (id INTEGER PRIMARY KEY AUTOINCREMENT, hostPhoneNum INTEGER, " +
                "hostUsername TEXT, hostPassword TEXT)");
        db.execSQL("CREATE TABLE \"players\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"playerNickname\"\tTEXT,\n" +
                "\t\"playerWinPoint\"\tINTEGER,\n" +
                "\t\"playerLosePoint\"\tINTEGER,\n" +
                "\t\"playerDrawPoint\"\tINTEGER,\n" +
                "\t\"hostID\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"hostID\") REFERENCES hosts(id)\n" +
                ")");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists hosts");
        onCreate(db);

    }

    public boolean register(String username, String password, int phone_number){
        long ins;
        SQLiteDatabase db;
        ContentValues values;

        db = this.getWritableDatabase();
        values = new ContentValues();

        values.put("hostUsername", username);
        values.put("hostPassword", password);
        values.put("hostPhoneNum", phone_number);

        ins = db.insert("hosts", null, values);
        db.close();
        return ins != -1;

    }

    public int authentication(int phone_num, String password){
        SQLiteDatabase db;
        Cursor cursor;
        int count;
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM hosts WHERE hostPhoneNum = ? AND hostPassword = ? ",
                new String[]{String.valueOf(phone_num), password});
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public boolean checkPhoneNumExist(int phone_num){
        SQLiteDatabase db;
        Cursor cursor;
        int count;

        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM hosts WHERE hostPhoneNum = ?", new String[]{String.valueOf(phone_num)});

        count = cursor.getCount();
        cursor.close();

        return count <= 0;
    }



}
