package com.example.proyectohospitalgambia.core.data.persistencia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Bloque companion object para definir constantes que serán usadas en toda la clase.
    // Son como los valores estáticos en Java
    companion object {
        private const val DATABASE_NAME = "federation"
        private const val DATABASE_VERSION = 4

        // Nombres de las tablas
        const val TABLE_DUS = "dus"
        const val TABLE_INSTITUTIONS = "institutions"
        const val TABLE_PEOPLE = "people"
        const val TABLE_PERSONAL_DOCS = "personal_docs"
        const val TABLE_POLS = "pols"

        // Nombres de las columnas de la tabla 'dus'
        const val KEY_DUS_ID = "id"
        const val KEY_DUS_DATA = "data"

        // Nombres de las columnas de la tabla 'institutions'
        const val KEY_INSTITUTIONS_ID = "id"
        const val KEY_INSTITUTIONS_DATA = "data"

        // Nombres de las columnas de la tabla 'people'
        const val KEY_PEOPLE_ID = "id"
        const val KEY_PEOPLE_DATA = "data"

        // Nombres de las columnas de la tabla 'personal_docs'
        const val KEY_PERSONAL_DOCS_ID = "id"
        const val KEY_PERSONAL_DOCS_FEDACCT = "fedacct"
        const val KEY_PERSONAL_DOCS_POL = "pol"
        const val KEY_PERSONAL_DOCS_DOCUMENT = "document"
        const val KEY_PERSONAL_DOCS_DATA = "data"

        // Nombres de las columnas de la tabla 'pols'
        const val KEY_POLS_ID = "id"
        const val KEY_POLS_BOOK = "book"
        const val KEY_POLS_DATA = "data"
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("Database", "onCreate: Creating database")
        val createDusTable = ("CREATE TABLE " + TABLE_DUS + "("
                + KEY_DUS_ID + " TEXT PRIMARY KEY NOT NULL,"
                + KEY_DUS_DATA + " TEXT" + ")")
        db.execSQL(createDusTable)

        val createInstitutionsTable = ("CREATE TABLE " + TABLE_INSTITUTIONS + "("
                + KEY_INSTITUTIONS_ID + " TEXT PRIMARY KEY NOT NULL,"
                + KEY_INSTITUTIONS_DATA + " TEXT" + ")")
        db.execSQL(createInstitutionsTable)

        val createPeopleTable = ("CREATE TABLE " + TABLE_PEOPLE + "("
                + KEY_PEOPLE_ID + " TEXT PRIMARY KEY NOT NULL,"
                + KEY_PEOPLE_DATA + " TEXT" + ")")
        db.execSQL(createPeopleTable)

        val createPersonalDocsTable = ("CREATE TABLE " + TABLE_PERSONAL_DOCS + "("
                + KEY_PERSONAL_DOCS_ID + " TEXT PRIMARY KEY NOT NULL,"
                + KEY_PERSONAL_DOCS_FEDACCT + " TEXT,"
                + KEY_PERSONAL_DOCS_POL + " TEXT,"
                + KEY_PERSONAL_DOCS_DOCUMENT + " BLOB,"
                + KEY_PERSONAL_DOCS_DATA + " TEXT,"
                + "FOREIGN KEY (" + KEY_PERSONAL_DOCS_FEDACCT + ") REFERENCES " + TABLE_PEOPLE + "(" + KEY_PEOPLE_ID + "),"
                + "FOREIGN KEY (" + KEY_PERSONAL_DOCS_POL + ") REFERENCES " + TABLE_POLS + "(" + KEY_POLS_ID + ")" + ")")
        db.execSQL(createPersonalDocsTable)

        val createPolsTable = ("CREATE TABLE " + TABLE_POLS + "("
                + KEY_POLS_ID + " TEXT PRIMARY KEY NOT NULL,"
                + KEY_POLS_BOOK + " TEXT,"
                + KEY_POLS_DATA + " TEXT,"
                + "FOREIGN KEY (" + KEY_POLS_BOOK + ") REFERENCES " + TABLE_PEOPLE + "(" + KEY_PEOPLE_ID + ")" + ")")
        db.execSQL(createPolsTable)

        Log.d("Database", "onCreate: Finish database")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DUS")
        onCreate(db)
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INSTITUTIONS")
        onCreate(db)
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PEOPLE")
        onCreate(db)
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONAL_DOCS")
        onCreate(db)
        db.execSQL("DROP TABLE IF EXISTS $TABLE_POLS")
        onCreate(db)

    }

    fun insertarPersona(id: String, data: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_PEOPLE_ID, id)
            put(KEY_PEOPLE_DATA, data)
        }
        val newRowId = db.insert(TABLE_PEOPLE, null, values)
        return newRowId != -1L
    }

    fun listarUsuarios() {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PEOPLE", null)

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndex(KEY_PEOPLE_ID)
                val dataIndex = cursor.getColumnIndex(KEY_PEOPLE_DATA)

                do {
                    val id = cursor.getString(idIndex)
                    val data = cursor.getString(dataIndex)
                    Log.d("DatabaseHelper", "ID: $id, Data: $data")
                } while (cursor.moveToNext())
            }
        }
    }

    // En la clase DatabaseHelper
    fun verificarCredenciales(nombreUsuario: String, contraseniaUsuario: String): String? {
        val db = readableDatabase
        val selection = "${DatabaseHelper.KEY_PEOPLE_DATA} LIKE ? AND ${DatabaseHelper.KEY_PEOPLE_DATA} LIKE ?"
        val selectionArgs = arrayOf("%\"name\":\"$nombreUsuario\"%", "%\"password\":\"$contraseniaUsuario\"%")
        val cursor = db.query(
            DatabaseHelper.TABLE_PEOPLE,
            arrayOf(DatabaseHelper.KEY_PEOPLE_ID),
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var usuarioId: String? = null
        if (cursor.moveToFirst()) {
            usuarioId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_PEOPLE_ID))
        }
        cursor.close()
        db.close()

        return usuarioId
    }

}
