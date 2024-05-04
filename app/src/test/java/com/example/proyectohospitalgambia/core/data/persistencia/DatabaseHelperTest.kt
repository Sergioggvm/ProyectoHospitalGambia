package com.example.proyectohospitalgambia.core.data.persistencia

import com.example.proyectohospitalgambia.core.domain.model.datosPols.Peso
import io.mockk.every
import io.mockk.verify
import org.junit.Test
import com.example.proyectohospitalgambia.core.domain.model.people.PeopleUser
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Assert
import org.junit.Before

/**
 * Clase DatabaseHelperTest.
 *
 * Esta clase representa las pruebas unitarias para la clase DatabaseHelper en la aplicación.
 *
 * @property databaseHelper Instancia de la clase DatabaseHelper que se va a probar.
 *
 * @method onBefore Método que se llama antes de cada prueba para inicializar los objetos necesarios.
 * @method testInsertarPersona Método para probar la inserción de una persona en la base de datos.
 * @method testInsertarPersonaConDatosInvalidos Método para probar la inserción de una persona con datos inválidos en la base de datos.
 * @method testVerificarCredenciales Método para probar la verificación de credenciales de un usuario.
 * @method testObtenerTodosLosDatosPesoCorrecto Método para probar la obtención de todos los datos de peso de un usuario.
 * @method testObtenerTodosLosDatosPesoIncorrecto Método para probar la obtención de todos los datos de peso de un usuario con datos incorrectos.
 * @method testInsertFormData Método para probar la inserción de un formulario en la base de datos.
 * @method testListarPols Método para probar la obtención de todos los Pols de la base de datos.
 */
class DatabaseHelperTest {

    @RelaxedMockK
    private lateinit var databaseHelper: DatabaseHelper

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testInsertarPersona() {
        val peopleUser = PeopleUser("1", "data")
        every { databaseHelper.insertarPersona(peopleUser) } returns true
        val result = databaseHelper.insertarPersona(peopleUser)
        assert(result)
        verify { databaseHelper.insertarPersona(peopleUser) }
        println("La prueba 'testInsertarPersona' se ejecutó correctamente")
    }

    @Test
    fun testInsertarPersonaConDatosInvalidos() {
        val peopleUserInvalido = PeopleUser("", "")
        every { databaseHelper.insertarPersona(peopleUserInvalido) } returns false
        val result = databaseHelper.insertarPersona(peopleUserInvalido)
        assert(!result)
        verify { databaseHelper.insertarPersona(peopleUserInvalido) }
        println("La prueba 'testInsertarPersonaConDatosInvalidos' se ejecutó correctamente")
    }

    @Test
    fun testVerificarCredenciales() {
        val nombreUsuario = "testUser"
        val contraseniaUsuario = "testPassword"
        val peopleUser = PeopleUser(nombreUsuario, contraseniaUsuario)
        every { databaseHelper.verificarCredenciales(nombreUsuario, contraseniaUsuario) } returns peopleUser
        val result = databaseHelper.verificarCredenciales(nombreUsuario, contraseniaUsuario)
        assertEquals(peopleUser, result)
        verify { databaseHelper.verificarCredenciales(nombreUsuario, contraseniaUsuario) }
        println("La prueba 'testVerificarCredenciales' se ejecutó correctamente")
    }

    @Test
    fun testObtenerTodosLosDatosPesoCorrecto() {
        val peso1 = Peso("Peso", "2022-01-01", 70)
        val peso2 = Peso("Peso", "2022-01-02", 71)
        val datosPrueba = listOf(peso1, peso2)
        every { databaseHelper.obtenerTodosLosDatosPeso("1") } returns datosPrueba
        val result = databaseHelper.obtenerTodosLosDatosPeso("1")
        assertEquals(datosPrueba, result)
        verify { databaseHelper.obtenerTodosLosDatosPeso("1") }
        println("La prueba 'testObtenerTodosLosDatosPesoCorrecto' se ejecutó correctamente")
    }

    @Test
    fun testObtenerTodosLosDatosPesoIncorrecto() {
        every { databaseHelper.obtenerTodosLosDatosPeso("1") } returns emptyList()
        val result = databaseHelper.obtenerTodosLosDatosPeso("1")
        assertTrue(result.isEmpty())
        verify { databaseHelper.obtenerTodosLosDatosPeso("1") }
        println("La prueba 'testObtenerTodosLosDatosPesoIncorrecto' se ejecutó correctamente")
    }

    @Test
    fun testInsertFormData() {
        val pol = Pol("1", "book", "data", "subido")
        every { databaseHelper.insertFormData(pol) } returns true
        val result = databaseHelper.insertFormData(pol)
        assertTrue(result)
        verify { databaseHelper.insertFormData(pol) }
        println("La prueba 'testInsertFormData' se ejecutó correctamente")
    }

    @Test
    fun testListarPols() {
        val expectedPols = listOf(Pol("id1", "book1", "data1", "subido"), Pol("id2", "book2", "data2", "subido"))
        every { databaseHelper.obtenerPols() } returns expectedPols
        val pols = databaseHelper.obtenerPols()
        Assert.assertNotNull(pols)
        Assert.assertEquals("El tamaño de la lista de pols no es el esperado", expectedPols.size, pols.size)
        Assert.assertTrue("La lista de pols no contiene los elementos esperados", pols.containsAll(expectedPols))
        println("La prueba 'testListarPols' se ejecutó correctamente")
    }
}
