package com.example.app_grupo7.perfume.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PerfumeDbHelper(context: Context) : SQLiteOpenHelper(
    context, "perfumes.db", null, /* ↑ sube a 2 si ya se creó con el esquema malo */ 2
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE perfumes(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                precio INTEGER NOT NULL,
                image_uri TEXT
            );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Migración simple para este caso de clase:
        db.execSQL("DROP TABLE IF EXISTS perfumes")
        onCreate(db)
    }
}
