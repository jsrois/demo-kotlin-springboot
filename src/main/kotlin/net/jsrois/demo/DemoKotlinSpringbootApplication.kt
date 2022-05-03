package net.jsrois.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoKotlinSpringbootApplication

fun main(args: Array<String>) {
    runApplication<DemoKotlinSpringbootApplication>(*args)
}
