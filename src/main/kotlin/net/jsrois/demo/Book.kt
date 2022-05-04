package net.jsrois.demo

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Book(
    @GeneratedValue(strategy = IDENTITY)
    @Id
    var id: Long = 0,
    var title: String,
    var author: String
)

interface BookRepository : JpaRepository<Book, Long>