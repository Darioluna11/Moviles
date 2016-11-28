package com.example.valora_tu_compra_gto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IrvinSF on 16/11/2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE productos(id integer primary key, nombre text, precio integer, lugar text, descripcion text, categoria text, fotos text)");
    }

    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists productos");
        db.execSQL("create table productos(id integer primary key, nombre text, precio integer, lugar text, descripcion text, categoria text,fotos text)");
    }
}
