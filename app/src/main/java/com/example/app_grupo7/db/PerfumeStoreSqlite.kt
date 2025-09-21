package com.example.app_grupo7.perfume.store

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.app_grupo7.perfume.db.PerfumeDbHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class PerfumeEntity(
    val id: Long? = null,
    val nombre: String,
    val precio: Int,
    val imageUri: String? = null
)

class PerfumeStoreSqlite(context: Context) {

    private val dbHelper = PerfumeDbHelper(context.applicationContext)
    private val state = MutableStateFlow<List<PerfumeEntity>>(emptyList())

    val items: Flow<List<PerfumeEntity>> = state

    init {
        refresh()
    }

    fun insert(nombre: String, precio: Int, imageUri: String?) {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("nombre", nombre)
            put("precio", precio)
            put("image_uri", imageUri)
        }
        db.insert("perfumes", null, cv)
        refresh()
    }

    fun update(id: Long, nombre: String, precio: Int, imageUri: String?) {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("nombre", nombre)
            put("precio", precio)
            put("image_uri", imageUri)
        }
        db.update("perfumes", cv, "id = ?", arrayOf(id.toString()))
        refresh()
    }

    fun delete(id: Long) {
        val db = dbHelper.writableDatabase
        db.delete("perfumes", "id = ?", arrayOf(id.toString()))
        refresh()
    }

    private fun refresh() {
        val db = dbHelper.readableDatabase
        val out = mutableListOf<PerfumeEntity>()
        db.rawQuery(
            "SELECT id, nombre, precio, image_uri FROM perfumes ORDER BY id DESC",
            null
        ).use { c ->
            while (c.moveToNext()) {
                out += PerfumeEntity(
                    id = c.getLong(0),
                    nombre = c.getString(1),
                    precio = c.getInt(2),
                    imageUri = if (!c.isNull(3)) c.getString(3) else null
                )
            }
        }
        state.value = out
    }
}
