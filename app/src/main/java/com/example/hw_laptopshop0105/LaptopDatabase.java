package com.example.hw_laptopshop0105;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LaptopDatabase extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "LapTopDB";

    public LaptopDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table Laptop (lapId varchar(200) primary key, lapName varchar(200),price int,image BLOB)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "Drop table if exists Laptop";
        db.execSQL(query);
        onCreate(db);
    }

    public void addLap(String id, String name, int price, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Insert into Laptop values(?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, id);
        statement.bindString(2, name);
        statement.bindLong(3, price);
        statement.bindBlob(4, image);

        statement.executeInsert();


    }

    public List<Laptop> getLaps() {
        List<Laptop> list = new ArrayList<Laptop>();
        String query = "Select * from Laptop";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            // Book b = new Book(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            Laptop b = new Laptop(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getBlob(3));
            list.add(b);
        }
        cursor.close();
        return list;

    }

    public int update(String id, String name, int price, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lapName", name);
        values.put("price", price);
        values.put("image", image);
        return db.update("Laptop", values, "lapId=?", new String[]{String.valueOf(id)});
    }

    public void deleteLap(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Laptop WHERE lapId='" + String.valueOf(ID) + "' ");
        db.close();

    }

    public Laptop getLapByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM Laptop Where lapId='" + String.valueOf(id) + "'", null);
        if (cursor != null) {
            cursor.moveToNext();
            return new Laptop(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getBlob(3));
        }
        return null;
    }


}
