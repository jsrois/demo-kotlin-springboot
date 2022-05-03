package net.jsrois.demo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Book(
    @GeneratedValue(strategy = IDENTITY)
    @Id
    val id: Long? = null,
    val title: String,
    val author: String
)
