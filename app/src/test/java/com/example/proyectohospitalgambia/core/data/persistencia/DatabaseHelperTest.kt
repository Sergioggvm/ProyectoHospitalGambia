package com.example.proyectohospitalgambia.core.data.persistencia

import io.mockk.every
import io.mockk.verify
import org.junit.Test
import com.example.proyectohospitalgambia.core.domain.model.people.PeopleUser
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before

class DatabaseHelperTest {

    @RelaxedMockK
    private lateinit var databaseHelper: DatabaseHelper

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testInsertarPersona() {
        // Crear un objeto PeopleUser para la prueba
        val peopleUser = PeopleUser("1", "data")

        // Configurar el comportamiento del mock
        // Aquí estamos diciendo que cuando el método insertarPersona() sea llamado con el objeto peopleUser, debe devolver true
        every { databaseHelper.insertarPersona(peopleUser) } returns true

        // Llamar al método que estamos probando
        val result = databaseHelper.insertarPersona(peopleUser)

        // Verificar que el resultado es el esperado
        assert(result)

        // Verificar que el método insertarPersona() fue llamado con el objeto peopleUser
        verify { databaseHelper.insertarPersona(peopleUser) }
    }
}
