package com.mtg.cardsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class CardSearchApplication

fun main(args: Array<String>) {
	runApplication<CardSearchApplication>(*args)
}
