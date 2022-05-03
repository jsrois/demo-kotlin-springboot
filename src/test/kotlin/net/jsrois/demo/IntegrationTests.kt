package net.jsrois.demo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {

    @Autowired
    lateinit var api: MockMvc

    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun `returns the existing books`() {

        bookRepository.saveAll(
            listOf(
                Book(1, "The Lord Of The Rings", "J.R.R. Tolkien"),
                Book(2, "La guerra de los lugares", "Raquel Rolnik")
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
        bookRepository.deleteAll()
        val book = Book("Mujeres, Raza y Clase", "Angela Y. Davis")

        api.perform(
            post("/api/books")
                .content(jacksonObjectMapper().writeValueAsString(book))
        )
            .andExpect(status().isOk)

        val existingBooks = bookRepository.findAll()
        assertThat(existingBooks, hasSize(1))
        assertThat(existingBooks.first().title, equalTo("Mujeres, Raza y Clase"))
        assertThat(existingBooks.first().author, equalTo("Angela Y. Davis"))




    }
}
