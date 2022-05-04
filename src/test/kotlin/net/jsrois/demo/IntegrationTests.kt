package net.jsrois.demo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {

    @Autowired
    lateinit var api: MockMvc

    @Autowired
    lateinit var bookRepository: BookRepository


    @BeforeEach
    internal fun setUp() {
        bookRepository.deleteAll()
    }

    @Test
    fun `returns the existing books`() {

        bookRepository.saveAll(
            listOf(
                Book(title= "The Lord Of The Rings", author =  "J.R.R. Tolkien"),
                Book(title= "La guerra de los lugares", author =  "Raquel Rolnik")
            )
        )

        api.perform(get("/api/books"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[*]", hasSize<Int>(2)))
            .andExpect(jsonPath("$[0].title", equalTo("The Lord Of The Rings")))
            .andExpect(jsonPath("$[0].author", equalTo("J.R.R. Tolkien")))
            .andExpect(jsonPath("$[1].title", equalTo("La guerra de los lugares")))
            .andExpect(jsonPath("$[1].author", equalTo("Raquel Rolnik")))

    }

    @Test
    fun `allows to create a new book`() {

        val book = Book(title = "Mujeres, Raza y Clase", author = "Angela Y. Davis")

        api.perform(
            post("/api/books")
                .content(jacksonObjectMapper().writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        val existingBooks = bookRepository.findAll()

        assertThat(existingBooks, hasSize(1))
        assertThat(existingBooks.first().title, equalTo("Mujeres, Raza y Clase"))
        assertThat(existingBooks.first().author, equalTo("Angela Y. Davis"))
    }

    @Test
    internal fun `allows to edit an existing book`() {

        val book = bookRepository.save(Book(1, "The left hand of darkness", "Ursula K. LeGuin"))

        with(book) {

            val updatedBook = Book(id, "La mano izquierda de la oscuridad", author)

            api.perform(
                put("/api/books/${id}")
                    .content(jacksonObjectMapper().writeValueAsString(updatedBook))
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk)
        }

        bookRepository.findAll().let {
            assertThat(it, hasSize(1))
            assertThat(it.first().title, equalTo("La mano izquierda de la oscuridad"))
            assertThat(it.first().author, equalTo("Ursula K. LeGuin"))
        }


    }
}
