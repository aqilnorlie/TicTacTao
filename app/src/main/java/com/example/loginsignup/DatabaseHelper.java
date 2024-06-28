package com.example.loginsignup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
        int hostId;
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM hosts WHERE hostPhoneNum = ? AND hostPassword = ? ",
                new String[]{String.valueOf(phone_num), password});
        if (cursor.moveToFirst()) {
            hostId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            cursor.close();
            return hostId;
        } else {
            cursor.close();
            return -1; // Indicates authentication failure
        }
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

    public boolean insertNickname(int hostID, String nickname){
        SQLiteDatabase db_write, db_read;
        Cursor cursor;
        ContentValues values;
        int count;

        db_read = this.getReadableDatabase();
        cursor = db_read.rawQuery("SELECT * FROM players WHERE hostID = ? AND playerNickname = ?" , new String[]{String.valueOf(hostID), nickname});

        count = cursor.getCount();
        if (count > 0){
            cursor.close();
            return false;
        }else{
            db_write = this.getWritableDatabase();
            values = new ContentValues();
            values.put("playerNickname", nickname);
            values.put("hostID", hostID);

            db_write.insert("players", null, values);
            db_write.close();
            return true;

        }

    }

    public List<String> getAllNicknames(int hostID) {
        List<String> nicknames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT playerNickname FROM players WHERE hostID = ?", new String[]{String.valueOf(hostID)});

        if (cursor.moveToFirst()) {
            do {
                nicknames.add(cursor.getString(cursor.getColumnIndexOrThrow("playerNickname")));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return nicknames;
    }


//    public boolean checkNicknamePlayer(String player1, String player2){
//        SQLiteDatabase db;
//        Cursor cursor;
//        int count;
//
//        db = this.getReadableDatabase();
//        cursor = db.rawQuery("SELECT * FROM players WHERE hostID = ? AND playerNickname = ?" , new String[]{String.valueOf(hostID), nickname});
//
//
//    }


}
