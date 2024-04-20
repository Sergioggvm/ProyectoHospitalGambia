package com.example.proyectohospitalgambia.core.data.persistencia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.proyectohospitalgambia.core.domain.model.datosPols.ActividadFisica
import com.example.proyectohospitalgambia.core.domain.model.datosPols.ActividadesSociales
import com.example.proyectohospitalgambia.core.domain.model.datosPols.Estado
import com.example.proyectohospitalgambia.core.domain.model.datosPols.GlucosaSangre
import com.example.proyectohospitalgambia.core.domain.model.datosPols.Osat
import com.example.proyectohospitalgambia.core.domain.model.datosPols.Peso
import com.example.proyectohospitalgambia.core.domain.model.datosPols.PresionSanguinea
import com.example.proyectohospitalgambia.core.domain.model.datosPols.Sueno
import com.example.proyectohospitalgambia.core.domain.model.datosPols.ValorEnergetico
import com.example.proyectohospitalgambia.core.domain.model.people.PeopleUser
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import org.json.JSONObject

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Bloque companion object para definir constantes que serán usadas en toda la clase.
    // Son como los valores estáticos en Java
    companion object {
        private const val DATABASE_NAME = "federation"
        private const val DATABASE_VERSION = 15

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
        const val KEY_POLS_SUBIDO = "subido"
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
                + KEY_POLS_SUBIDO + " TEXT,"
                + "FOREIGN KEY (" + KEY_POLS_BOOK + ") REFERENCES " + TABLE_PEOPLE + "(" + KEY_PEOPLE_ID + ")" + ")")
        db.execSQL(createPolsTable)

        Log.d("Database", "onCreate: Finish database")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DUS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INSTITUTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PEOPLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONAL_DOCS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_POLS")
        onCreate(db)

    }

    fun insertarPersona(peopleUser: PeopleUser): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_PEOPLE_ID, peopleUser.id)
            put(KEY_PEOPLE_DATA, peopleUser.data)
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

    fun verificarCredenciales(nombreUsuario: String, contraseniaUsuario: String): PeopleUser? {
        val db = readableDatabase
        val selection = "${DatabaseHelper.KEY_PEOPLE_DATA} LIKE ? AND ${DatabaseHelper.KEY_PEOPLE_DATA} LIKE ?"
        val selectionArgs = arrayOf("%\"name\":\"$nombreUsuario\"%", "%\"password\":\"$contraseniaUsuario\"%")
        val cursor = db.query(
            DatabaseHelper.TABLE_PEOPLE,
            arrayOf(DatabaseHelper.KEY_PEOPLE_ID, DatabaseHelper.KEY_PEOPLE_DATA),
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var usuario: PeopleUser? = null
        cursor.use { // Utilizamos use para asegurar que el cursor se cierre correctamente al finalizar
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PEOPLE_ID)
                val dataIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PEOPLE_DATA)
                if (idIndex != -1 && dataIndex != -1) { // Verificamos que los índices de las columnas sean válidos
                    val id = cursor.getString(idIndex)
                    val data = cursor.getString(dataIndex)
                    usuario = PeopleUser(id, data)
                } else {
                    // Las columnas no fueron encontradas en el cursor
                    // Puede ser un error en los nombres de las columnas
                    // o las columnas no están presentes en la tabla
                    Log.e("VerificarCredenciales", "Las columnas no fueron encontradas en el cursor.")
                }
            }
        }

        db.close()

        return usuario
    }

    fun insertFormData(pol: Pol): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_POLS_ID, pol.idPol)
            put(KEY_POLS_BOOK, pol.book)
            put(KEY_POLS_DATA, pol.data)
            put(KEY_POLS_SUBIDO, pol.isSubido)
        }
        val newRowId = db.insert(TABLE_POLS, null, values)
        db.close()
        return newRowId != -1L
    }

    fun listarPols() {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS", null)

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndex(KEY_POLS_ID)
                val bookIndex = cursor.getColumnIndex(KEY_POLS_BOOK)
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)
                val subidoIndex = cursor.getColumnIndex(KEY_POLS_SUBIDO)

                do {
                    val id = cursor.getString(idIndex)
                    val book = cursor.getString(bookIndex)
                    val data = cursor.getString(dataIndex)
                    val subido = cursor.getString(subidoIndex)
                    Log.d("DatabaseHelper", "ID: $id, Book: $book, Data: $data, Subido: $subido")
                } while (cursor.moveToNext())
            }
        }
    }


    fun actualizarDatosPersona(peopleUser: PeopleUser): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_PEOPLE_DATA, peopleUser.data)
        }
        val whereClause = "$KEY_PEOPLE_ID = ?"
        val whereArgs = arrayOf(peopleUser.id)
        val rowsAffected = db.update(TABLE_PEOPLE, values, whereClause, whereArgs)
        db.close()
        return rowsAffected > 0
    }

    fun usuarioExiste(nombreUsuario: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PEOPLE,
            arrayOf(KEY_PEOPLE_ID),
            "$KEY_PEOPLE_DATA LIKE ?",
            arrayOf("%\"name\":\"$nombreUsuario\"%"),
            null,
            null,
            null
        )

        val existe = cursor.moveToFirst()
        cursor.close()
        db.close()

        return existe
    }


    // --------------------- OBTENER LOS DATOS PARA LAS GRAFICAS ---------------------

    fun obtenerTodosLosSuenos(idUsuario: String): List<Sueno> {
        val listaSuenos = mutableListOf<Sueno>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%Sueno%' AND $KEY_POLS_BOOK = ?", arrayOf(idUsuario))

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    val sueno = Sueno(
                        fechaRealizacion = jsonObject.getString("FechaRealizacion"),
                        horasSueno = jsonObject.getInt("HorasSueno"),
                        calidadSueno = jsonObject.getString("CalidadSueno"),
                        notas = jsonObject.getString("Notas")
                    )
                    listaSuenos.add(sueno)
                } while (cursor.moveToNext())
            }
        }

        return listaSuenos
    }

    fun obtenerTodosLosDatosPresionSanguinea(idUsuario: String): List<PresionSanguinea> {
        val listaPresionSanguinea = mutableListOf<PresionSanguinea>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%PresionSanguinea%' AND $KEY_POLS_BOOK = ?", arrayOf(idUsuario))

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if(jsonObject.has("Sistolico") && jsonObject.has("Diastolico") && jsonObject.has("FrecuenciaCardiaca")) {
                        val presionSanguinea = PresionSanguinea(
                            fechaRealizacion = jsonObject.getString("FechaInsercion"), // Ignoramos la fecha TODO SI SE PILLA LA FECHA PETA LA APLICACIÓN!!!!!!!!!!!!!!!!!
                            sistolico = jsonObject.getInt("Sistolico"),
                            diastolico = jsonObject.getInt("Diastolico"),
                            frecuenciaCardiaca = jsonObject.getInt("FrecuenciaCardiaca")
                        )
                        listaPresionSanguinea.add(presionSanguinea)
                    }
                } while (cursor.moveToNext())
            }
        }
        // Log de todos los datos
        listaPresionSanguinea.forEach { presion ->
            Log.d("TodosLosDatosPresion", "Sistolico: ${presion.sistolico}, Diastolico: ${presion.diastolico}, Frecuencia Cardiaca: ${presion.frecuenciaCardiaca}")
        }

        return listaPresionSanguinea
    }

    fun obtenerTodosLosDatosPeso(idUsuario: String): List<Peso> {
        val listaPeso = mutableListOf<Peso>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%Peso%' AND $KEY_POLS_BOOK = ?", arrayOf(idUsuario))

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if(jsonObject.has("kg")) {
                        val peso = Peso(
                            fechaRealizacion = jsonObject.getString("FechaInsercion"), // Ignoramos la fecha
                            kg = jsonObject.getInt("kg")
                        )
                        listaPeso.add(peso)
                    }
                } while (cursor.moveToNext())
            }
        }

        return listaPeso
    }

    fun obtenerTodosLosDatosGlucemia(idUsuario: String): List<GlucosaSangre> {
        val listaGlucemia = mutableListOf<GlucosaSangre>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%GlucosaSangre%' AND $KEY_POLS_BOOK = ?", arrayOf(idUsuario))

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if(jsonObject.has("Glucosa")) {
                        val glucemia = GlucosaSangre(
                            fechaRealizacion = jsonObject.getString("FechaInsercion"),
                            glucosa = jsonObject.getInt("Glucosa")
                        )
                        listaGlucemia.add(glucemia)
                    }
                } while (cursor.moveToNext())
            }
        }

        return listaGlucemia
    }

    fun obtenerTodosLosDatosOsat(idUsuario: String): List<Osat> {
        val listaOsat = mutableListOf<Osat>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%Osat%' AND $KEY_POLS_BOOK = ?", arrayOf(idUsuario))

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if(jsonObject.has("Osat")) {
                        val osat = Osat(
                            fechaRealizacion = jsonObject.getString("FechaInsercion"),
                            presionSanguinea = jsonObject.getInt("Osat")
                        )
                        listaOsat.add(osat)
                    }
                } while (cursor.moveToNext())
            }
        }

        return listaOsat
    }

    fun obtenerTodosLosDatosActividadFisica(idUsuario: String): List<ActividadFisica> {
        val listaActividadFisica = mutableListOf<ActividadFisica>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%ActividadFisica%' AND $KEY_POLS_BOOK = ?", arrayOf(idUsuario))

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if(jsonObject.has("Aerobico") && jsonObject.has("Anaerobico") && jsonObject.has("Pasos")) {
                        val actividadFisica = ActividadFisica(
                            fechaRealizacion = jsonObject.getString("FechaRealizacion"),
                            aerobico = jsonObject.getInt("Aerobico"),
                            anaerobico = jsonObject.getInt("Anaerobico"),
                            pasos = jsonObject.getInt("Pasos")
                        )
                        listaActividadFisica.add(actividadFisica)
                    }
                } while (cursor.moveToNext())
            }
        }

        return listaActividadFisica
    }

    fun obtenerTodosLosDatosActividadesSociales(idUsuario: String): List<ActividadesSociales> {
        val listaActividadesSociales = mutableListOf<ActividadesSociales>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%ActividadSociales%' AND $KEY_POLS_BOOK = ?", arrayOf(idUsuario))

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if(jsonObject.has("MinutosActividadesSociales")) {
                        val actividadesSociales = ActividadesSociales(
                            fechaRealizacion = jsonObject.getString("FechaRealizacion"),
                            minutosActividad = jsonObject.getInt("MinutosActividadesSociales"),
                            notas = jsonObject.getString("Notas")
                        )
                        listaActividadesSociales.add(actividadesSociales)
                    }
                } while (cursor.moveToNext())
            }
        }

        return listaActividadesSociales
    }

    fun obtenerTodosLosDatosEstado(idUsuario: String): List<Estado> {
        val listaEstado = mutableListOf<Estado>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%Estado%' AND $KEY_POLS_BOOK = ?", arrayOf(idUsuario))

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if(jsonObject.has("EstadoAnimo") && jsonObject.has("Energia")) {
                        val estado = Estado(
                            fechaRealizacion = jsonObject.getString("FechaRealizacion"),
                            estadoAnimo = jsonObject.getString("EstadoAnimo"),
                            energia = jsonObject.getString("Energia"),
                            notas = jsonObject.getString("Notas")
                        )
                        listaEstado.add(estado)
                    }
                } while (cursor.moveToNext())
            }
        }

        return listaEstado
    }

    fun obtenerTodosLosDatosValorEnergetico(idUsuario: String): List<ValorEnergetico> {
        val listaValorEnergetico = mutableListOf<ValorEnergetico>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%ValorEnergetico%' AND $KEY_POLS_BOOK = ?", arrayOf(idUsuario))

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if(jsonObject.has("KcalManana") && jsonObject.has("KcalTarde") && jsonObject.has("KcalNoche") && jsonObject.has("KcalTotal")) {
                        val valorEnergetico = ValorEnergetico(
                            fechaRealizacion = jsonObject.getString("FechaRealizacion"),
                            kcalManana = jsonObject.getInt("KcalManana"),
                            kcalTarde = jsonObject.getInt("KcalTarde"),
                            kcalNoche = jsonObject.getInt("KcalNoche"),
                            kcalTotal = jsonObject.getInt("KcalTotal"),
                            notas = jsonObject.getString("Notas")
                        )
                        listaValorEnergetico.add(valorEnergetico)
                    }
                } while (cursor.moveToNext())
            }
        }

        return listaValorEnergetico
    }


    // --------------------- OBTENER EL ULTIMO DATO PARA LOS TEXTOS ---------------------

    fun obtenerUltimaPresionSanguinea(idUsuario: String): PresionSanguinea? {
        val listaPresiones = obtenerTodosLosDatosPresionSanguinea(idUsuario)
        val ultimaPresion = listaPresiones.lastOrNull()

        // Log de la última presión obtenida
        if (ultimaPresion != null) {
            Log.d("UltimaPresionSanguinea", "Sistolico: ${ultimaPresion.sistolico}, Diastolico: ${ultimaPresion.diastolico}, Frecuencia Cardiaca: ${ultimaPresion.frecuenciaCardiaca}")
        } else {
            Log.d("UltimaPresionSanguinea", "No se encontraron datos de presión sanguínea para el usuario $idUsuario")
        }

        return ultimaPresion
    }



}
