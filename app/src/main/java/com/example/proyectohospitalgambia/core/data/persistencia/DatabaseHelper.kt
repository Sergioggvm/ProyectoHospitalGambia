package com.example.proyectohospitalgambia.core.data.persistencia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper.Companion.DATABASE_NAME
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper.Companion.DATABASE_VERSION
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper.Companion.TABLE_DUS
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper.Companion.TABLE_INSTITUTIONS
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper.Companion.TABLE_PEOPLE
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper.Companion.TABLE_PERSONAL_DOCS
import com.example.proyectohospitalgambia.core.data.persistencia.DatabaseHelper.Companion.TABLE_POLS
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
import org.mindrot.jbcrypt.BCrypt


/**
 * Clase DatabaseHelper que extiende de SQLiteOpenHelper.
 *
 * Esta clase es responsable de la creación, actualización y operaciones de la base de datos.
 *
 * @property DATABASE_NAME Nombre de la base de datos.
 * @property DATABASE_VERSION Versión de la base de datos.
 * @property TABLE_DUS Nombre de la tabla 'dus'.
 * @property TABLE_INSTITUTIONS Nombre de la tabla 'institutions'.
 * @property TABLE_PEOPLE Nombre de la tabla 'people'.
 * @property TABLE_PERSONAL_DOCS Nombre de la tabla 'personal_docs'.
 * @property TABLE_POLS Nombre de la tabla 'pols'.
 */
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Bloque companion object para definir constantes que serán usadas en toda la clase.
    // Son como los valores estáticos en Java
    companion object {
        private const val DATABASE_NAME = "federation"
        private const val DATABASE_VERSION = 20

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

    /**
     * Método onCreate que se llama cuando se crea la base de datos por primera vez.
     *
     * @param db La base de datos.
     */
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

    /**
     * Método onUpgrade que se llama cuando se actualiza la base de datos.
     *
     * @param db La base de datos.
     * @param oldVersion La versión antigua de la base de datos.
     * @param newVersion La nueva versión de la base de datos.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DUS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INSTITUTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PEOPLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONAL_DOCS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_POLS")
        onCreate(db)

    }

    /**
     * Método para insertar una nueva persona en la base de datos.
     *
     * @param peopleUser El objeto PeopleUser que representa a la persona.
     * @return Retorna true si la inserción fue exitosa, false en caso contrario.
     */
    fun insertarPersona(peopleUser: PeopleUser): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_PEOPLE_ID, peopleUser.id)
            put(KEY_PEOPLE_DATA, peopleUser.data)
        }
        val newRowId = db.insert(TABLE_PEOPLE, null, values)
        return newRowId != -1L
    }

    /**
     * Método para listar todos los usuarios en la base de datos.
     */
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

    /**
     * Método para verificar las credenciales de un usuario.
     *
     * @param nombreUsuario El nombre del usuario.
     * @param contraseniaUsuario La contraseña del usuario.
     * @return Retorna un objeto PeopleUser si las credenciales son correctas, null en caso contrario.
     */
    fun verificarCredenciales(nombreUsuario: String, contraseniaUsuario: String): PeopleUser? {
        val db = readableDatabase
        val selection = "${KEY_PEOPLE_DATA} LIKE ?"
        val selectionArgs = arrayOf("%\"name\":\"$nombreUsuario\"%")
        val cursor = db.query(
            TABLE_PEOPLE,
            arrayOf(KEY_PEOPLE_ID, KEY_PEOPLE_DATA),
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var usuario: PeopleUser? = null
        cursor.use { // Utilizamos use para asegurar que el cursor se cierre correctamente al finalizar
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndex(KEY_PEOPLE_ID)
                val dataIndex = cursor.getColumnIndex(KEY_PEOPLE_DATA)
                if (idIndex != -1 && dataIndex != -1) { // Verificamos que los índices de las columnas sean válidos
                    val id = cursor.getString(idIndex)
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    val contraseniaAlmacenada = jsonObject.getString("password")

                    // Verificar si la contraseña proporcionada coincide con la contraseña almacenada
                    if (BCrypt.checkpw(contraseniaUsuario, contraseniaAlmacenada)) {
                        usuario = PeopleUser(id, data)
                    } else {
                        Log.e(
                            "VerificarCredenciales",
                            "Contraseña incorrecta para el usuario: $nombreUsuario"
                        )
                    }
                } else {
                    // Las columnas no fueron encontradas en el cursor
                    // Puede ser un error en los nombres de las columnas
                    // o las columnas no están presentes en la tabla
                    Log.e(
                        "VerificarCredenciales",
                        "Las columnas no fueron encontradas en el cursor."
                    )
                }
            } else {
                Log.e(
                    "VerificarCredenciales",
                    "No se encontró ningún usuario con el nombre: $nombreUsuario"
                )
            }
        }

        db.close()

        return usuario
    }

    /**
     * Método para insertar un nuevo formulario en la base de datos.
     *
     * @param pol El objeto Pol que representa al formulario.
     * @return Retorna true si la inserción fue exitosa, false en caso contrario.
     */
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

    /**
     * Método para listar todos los formularios en la base de datos.
     */
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


    /**
     * Método para actualizar los datos de una persona en la base de datos.
     *
     * @param peopleUser El objeto PeopleUser que representa a la persona con los datos actualizados.
     * @return Retorna true si la actualización fue exitosa, false en caso contrario.
     */
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

    /**
     * Método para verificar si un usuario existe en la base de datos.
     *
     * @param nombreUsuario El nombre del usuario.
     * @return Retorna true si el usuario existe, false en caso contrario.
     */
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


    /**
     * Método para obtener todos los formularios de la base de datos.
     *
     * @return Retorna una lista de objetos Pol.
     */
    fun obtenerPols(): List<Pol> {
        val polsList = mutableListOf<Pol>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_POLS", null)

        cursor.use { cursor ->
            // Verificar si el cursor tiene columnas
            if (cursor.columnCount > 0) {
                // Obtener los índices de las columnas
                val idIndex = cursor.getColumnIndex(KEY_POLS_ID)
                val bookIndex = cursor.getColumnIndex(KEY_POLS_BOOK)
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)
                val subidoIndex = cursor.getColumnIndex(KEY_POLS_SUBIDO)

                // Iterar sobre el cursor
                while (cursor.moveToNext()) {
                    // Obtener los valores de cada columna por su índice
                    val id = cursor.getString(idIndex)
                    val book = cursor.getString(bookIndex)
                    val data = cursor.getString(dataIndex)
                    val subido = cursor.getString(subidoIndex)

                    // Crear un objeto Pol con los datos obtenidos de la base de datos
                    val pol = Pol(id, book, data, subido)
                    polsList.add(pol)
                }
            }
        }

        db.close()

        return polsList
    }

    /**
     * Método para actualizar el estado de subido de un formulario en la base de datos.
     *
     * @param idPol El id del formulario.
     * @param nuevoEstado El nuevo estado de subido.
     * @return Retorna true si la actualización fue exitosa, false en caso contrario.
     */
    fun actualizarEstadoSubido(idPol: String, nuevoEstado: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_POLS_SUBIDO, nuevoEstado)
        }
        val whereClause = "$KEY_POLS_ID = ?"
        val whereArgs = arrayOf(idPol)
        val rowsAffected = db.update(TABLE_POLS, values, whereClause, whereArgs)
        db.close()
        return rowsAffected > 0
    }


    // --------------------- OBTENER LOS DATOS PARA LAS GRAFICAS ---------------------

    /**
     * Método para obtener todos los datos de sueño de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna una lista de objetos Sueno.
     */
    fun obtenerTodosLosSuenos(idUsuario: String): List<Sueno> {
        val listaSuenos = mutableListOf<Sueno>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%Sueno%' AND $KEY_POLS_BOOK = ?",
            arrayOf(idUsuario)
        )

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    val sueno = Sueno(
                        fechaRealizacion = jsonObject.getString("FechaInsercion"),
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

    /**
     * Método para obtener todos los datos de presión sanguínea de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna una lista de objetos PresionSanguinea.
     */
    fun obtenerTodosLosDatosPresionSanguinea(idUsuario: String): List<PresionSanguinea> {
        val listaPresionSanguinea = mutableListOf<PresionSanguinea>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%PresionSanguinea%' AND $KEY_POLS_BOOK = ?",
            arrayOf(idUsuario)
        )

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if (jsonObject.has("Sistolico") && jsonObject.has("Diastolico") && jsonObject.has(
                            "FrecuenciaCardiaca"
                        )
                    ) {
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
            Log.d(
                "TodosLosDatosPresion",
                "Sistolico: ${presion.sistolico}, Diastolico: ${presion.diastolico}, Frecuencia Cardiaca: ${presion.frecuenciaCardiaca}"
            )
        }

        return listaPresionSanguinea
    }

    /**
     * Método para obtener todos los datos de peso de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna una lista de objetos Peso.
     */
    fun obtenerTodosLosDatosPeso(idUsuario: String): List<Peso> {
        val listaPeso = mutableListOf<Peso>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%Peso%' AND $KEY_POLS_BOOK = ?",
            arrayOf(idUsuario)
        )

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if (jsonObject.has("kg")) {
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

    /**
     * Método para obtener todos los datos de glucemia de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna una lista de objetos GlucosaSangre.
     */
    fun obtenerTodosLosDatosGlucemia(idUsuario: String): List<GlucosaSangre> {
        val listaGlucemia = mutableListOf<GlucosaSangre>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%GlucosaSangre%' AND $KEY_POLS_BOOK = ?",
            arrayOf(idUsuario)
        )

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if (jsonObject.has("Glucosa")) {
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

    /**
     * Método para obtener todos los datos de Osat de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna una lista de objetos Osat.
     */
    fun obtenerTodosLosDatosOsat(idUsuario: String): List<Osat> {
        val listaOsat = mutableListOf<Osat>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%Osat%' AND $KEY_POLS_BOOK = ?",
            arrayOf(idUsuario)
        )

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if (jsonObject.has("Osat")) {
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

    /**
     * Método para obtener todos los datos de actividad física de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna una lista de objetos ActividadFisica.
     */
    fun obtenerTodosLosDatosActividadFisica(idUsuario: String): List<ActividadFisica> {
        val listaActividadFisica = mutableListOf<ActividadFisica>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%ActividadFisica%' AND $KEY_POLS_BOOK = ?",
            arrayOf(idUsuario)
        )

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if (jsonObject.has("Aerobico") && jsonObject.has("Anaerobico") && jsonObject.has(
                            "Pasos"
                        )
                    ) {
                        val actividadFisica = ActividadFisica(
                            fechaRealizacion = jsonObject.getString("FechaInsercion"),
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

    /**
     * Método para obtener todos los datos de actividades sociales de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna una lista de objetos ActividadesSociales.
     */
    fun obtenerTodosLosDatosActividadesSociales(idUsuario: String): List<ActividadesSociales> {
        val listaActividadesSociales = mutableListOf<ActividadesSociales>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%ActividadSociales%' AND $KEY_POLS_BOOK = ?",
            arrayOf(idUsuario)
        )

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if (jsonObject.has("MinutosActividadesSociales")) {
                        val actividadesSociales = ActividadesSociales(
                            fechaRealizacion = jsonObject.getString("FechaInsercion"),
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

    /**
     * Método para obtener todos los datos de estado de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna una lista de objetos Estado.
     */
    fun obtenerTodosLosDatosEstado(idUsuario: String): List<Estado> {
        val listaEstado = mutableListOf<Estado>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%Estado%' AND $KEY_POLS_BOOK = ?",
            arrayOf(idUsuario)
        )

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if (jsonObject.has("EstadoAnimo") && jsonObject.has("Energia")) {
                        val estado = Estado(
                            fechaRealizacion = jsonObject.getString("FechaInsercion"),
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

    /**
     * Método para obtener todos los datos de valor energético de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna una lista de objetos ValorEnergetico.
     */
    fun obtenerTodosLosDatosValorEnergetico(idUsuario: String): List<ValorEnergetico> {
        val listaValorEnergetico = mutableListOf<ValorEnergetico>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_POLS WHERE $KEY_POLS_DATA LIKE '%ValorEnergetico%' AND $KEY_POLS_BOOK = ?",
            arrayOf(idUsuario)
        )

        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val dataIndex = cursor.getColumnIndex(KEY_POLS_DATA)

                do {
                    val data = cursor.getString(dataIndex)
                    val jsonObject = JSONObject(data)
                    if (jsonObject.has("KcalManana") && jsonObject.has("KcalTarde") && jsonObject.has(
                            "KcalNoche"
                        ) && jsonObject.has("KcalTotal")
                    ) {
                        val valorEnergetico = ValorEnergetico(
                            fechaRealizacion = jsonObject.getString("FechaInsercion"),
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

    // --------------------- OBTENER EL ÚLTIMO DATO PARA CADA TIPO DE INFORMACIÓN ---------------------

    /**
     * Método para obtener el último dato de sueño de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna un objeto Sueno.
     */
    fun obtenerUltimoSueno(idUsuario: String): Sueno? {
        val listaSuenos = obtenerTodosLosSuenos(idUsuario)
        return listaSuenos.lastOrNull()
    }

    /**
     * Método para obtener el último dato de presión sanguínea de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna un objeto PresionSanguinea.
     */
    fun obtenerUltimaPresionSanguinea(idUsuario: String): PresionSanguinea? {
        val listaPresiones = obtenerTodosLosDatosPresionSanguinea(idUsuario)
        return listaPresiones.lastOrNull()
    }

    /**
     * Método para obtener el último dato de peso de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna un objeto Peso.
     */
    fun obtenerUltimoPeso(idUsuario: String): Peso? {
        val listaPesos = obtenerTodosLosDatosPeso(idUsuario)
        return listaPesos.lastOrNull()
    }

    /**
     * Método para obtener el último dato de glucemia de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna un objeto GlucosaSangre.
     */
    fun obtenerUltimaGlucemia(idUsuario: String): GlucosaSangre? {
        val listaGlucemias = obtenerTodosLosDatosGlucemia(idUsuario)
        return listaGlucemias.lastOrNull()
    }

    /**
     * Método para obtener el último dato de Osat de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna un objeto Osat.
     */
    fun obtenerUltimoOsat(idUsuario: String): Osat? {
        val listaOsats = obtenerTodosLosDatosOsat(idUsuario)
        return listaOsats.lastOrNull()
    }

    /**
     * Método para obtener el último dato de actividad física de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna un objeto ActividadFisica.
     */
    fun obtenerUltimaActividadFisica(idUsuario: String): ActividadFisica? {
        val listaActividadesFisicas = obtenerTodosLosDatosActividadFisica(idUsuario)
        return listaActividadesFisicas.lastOrNull()
    }

    /**
     * Método para obtener el último dato de actividades sociales de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna un objeto ActividadesSociales.
     */
    fun obtenerUltimasActividadesSociales(idUsuario: String): ActividadesSociales? {
        val listaActividadesSociales = obtenerTodosLosDatosActividadesSociales(idUsuario)
        return listaActividadesSociales.lastOrNull()
    }

    /**
     * Método para obtener el último dato de estado de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna un objeto Estado.
     */
    fun obtenerUltimoEstado(idUsuario: String): Estado? {
        val listaEstados = obtenerTodosLosDatosEstado(idUsuario)
        return listaEstados.lastOrNull()
    }

    /**
     * Método para obtener el último dato de valor energético de la base de datos.
     *
     * @param idUsuario El id del usuario.
     * @return Retorna un objeto ValorEnergetico.
     */
    fun obtenerUltimoValorEnergetico(idUsuario: String): ValorEnergetico? {
        val listaValoresEnergeticos = obtenerTodosLosDatosValorEnergetico(idUsuario)
        return listaValoresEnergeticos.lastOrNull()
    }

}
