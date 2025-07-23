package com.lyrun.app.TreinoSQL

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TreinoDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "treino.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_TREINO = "treinos"
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date" // yyyy-MM-dd
        const val COLUMN_EXERCISE = "exercise"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_TREINO (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT,
                $COLUMN_EXERCISE TEXT
            );
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TREINO")
        onCreate(db)
    }

    fun insertTreino(date: String, exercise: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, date)
            put(COLUMN_EXERCISE, exercise)
        }
        return db.insert(TABLE_TREINO, null, values)
    }

    fun getTreinosByDate(date: String): List<String> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_TREINO,
            arrayOf(COLUMN_EXERCISE),
            "$COLUMN_DATE = ?",
            arrayOf(date),
            null, null, null
        )

        val exercises = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                exercises.add(getString(getColumnIndexOrThrow(COLUMN_EXERCISE)))
            }
            close()
        }
        return exercises
    }

    fun deleteTreinosByDate(date: String) {
        val db = writableDatabase
        db.delete(TABLE_TREINO, "$COLUMN_DATE = ?", arrayOf(date))
    }
}
