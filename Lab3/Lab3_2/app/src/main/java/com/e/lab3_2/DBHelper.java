package com.e.lab3_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
            String[] nameParts = names[rnd.nextInt(names.length)].split(" ");
            putUser(db, nameParts[0], nameParts[1], nameParts[2]);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                + TABLE_NAME
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT,"
                + "surname TEXT,"
                + "patronymic TEXT,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("wadaw", String.valueOf(oldVersion) + " " + String.valueOf(newVersion));
        if (newVersion == 2) {
            db.execSQL("DROP TABLE " + TABLE_NAME + ";");
            db.execSQL("CREATE TABLE "
                    + TABLE_NAME
                    + "("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "patronymic TEXT,"
                    + "date DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ");"
            );
        }
    }

    void putUser(SQLiteDatabase db, String name, String surname, String patronymic) {
        ContentValues newValues = new ContentValues();
        newValues.put("name", name);
        newValues.put("surname", surname);
        newValues.put("patronymic", patronymic);

        db.insert(TABLE_NAME, null, newValues);
    }

    void updateLast(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT seq FROM sqlite_sequence where name='" + TABLE_NAME + "'", null);
        c.moveToFirst();

        String lastInserted = c.getString(0);

        ContentValues newValues = new ContentValues();
        newValues.put("name", "Иванов");
        newValues.put("surname", "Иван");
        newValues.put("patronymic", "Иванович");

        db.update(TABLE_NAME, newValues, "id = ?", new String[]{lastInserted});

        c.close();
    }

    Cursor getStudents(SQLiteDatabase db) {
        return db.query("students", null, null, null, null, null, null);
    }
}
