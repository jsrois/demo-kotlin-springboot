package net.jsrois.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class BookController(@Autowired val bookRepository: BookRepository) {

    @GetMapping
    fun allBooks(): List<Book> {
        return bookRepository.findAll()
    }

    @PostMapping
    fun addBook(@RequestBody book: Book): Book {
        return bookRepository.save(book)
    }

    @PutMapping("/{id}")
    fun editBook(@PathVariable id: Long, @RequestBody book: Book): Book {
        return bookRepository.save(book)
    }

}