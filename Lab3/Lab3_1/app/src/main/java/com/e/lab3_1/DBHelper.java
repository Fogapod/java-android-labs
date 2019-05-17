package com.e.lab3_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "students";

    DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);

        String[] names = {
                "Исаков Мечислав Николаевич",
                "Миронов Ростислав Проклович",
                "Дроздов Вальтер Борисович",
                "Калашников Глеб Макарович",
                "Белозёров Пантелей Федорович",
                "Ситников Виссарион Романович",
                "Ковалёв Лавр Ефимович",
                "Орлов Флор Иринеевич"
        };

        Random rnd = new Random();

        for (int i = 0; i < 5; i++) {
            putUser(db, names[rnd.nextInt(names.length)]);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                + TABLE_NAME
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "full_name TEXT,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void putUser(SQLiteDatabase db, String name) {
        ContentValues newValues = new ContentValues();
        newValues.put("full_name", name);

        db.insert(TABLE_NAME, null, newValues);
    }

    void updateLast(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT seq FROM sqlite_sequence where name='" + TABLE_NAME + "'", null);
        c.moveToFirst();

        String lastInserted = c.getString(0);

        ContentValues newValues = new ContentValues();
        newValues.put("full_name", "Иванов Иван Иванович");

        db.update(TABLE_NAME, newValues, "id = ?", new String[]{lastInserted});

        c.close();
    }

    Cursor getStudents(SQLiteDatabase db) {
        return db.query("students", null, null, null, null, null, null);
    }
}
