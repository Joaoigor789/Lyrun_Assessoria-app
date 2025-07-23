package com.lyrun.app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "lyrun.db"
        private const val DB_VERSION = 1

        private const val TABELA_USUARIO = "usuarios"
        private const val COL_ID = "id"
        private const val COL_EMAIL = "email"
        private const val COL_SENHA = "senha"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABELA_USUARIO (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_EMAIL TEXT UNIQUE,
                $COL_SENHA TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABELA_USUARIO")
        onCreate(db)
    }

    fun inserirUsuario(email: String, senha: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_EMAIL, email)
        cv.put(COL_SENHA, senha)
        val result = db.insert(TABELA_USUARIO, null, cv)
        db.close()
        return result != -1L
    }

    fun verificarUsuario(email: String, senha: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABELA_USUARIO,
            arrayOf(COL_ID),
            "$COL_EMAIL = ? AND $COL_SENHA = ?",
            arrayOf(email, senha),
            null, null, null
        )
        val existe = cursor.count > 0
        cursor.close()
        db.close()
        return existe
    }
}
