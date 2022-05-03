package net.jsrois.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/books")
class BookController(@Autowired val bookRepository: BookRepository) {

    @GetMapping
    fun allBooks(): List<Book> {
        return bookRepository.findAll()
    }

    @PostMapping
    fun addBook(book: Book): Book {
        return bookRepository.save(book)
    }

}