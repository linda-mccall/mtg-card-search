package com.mtg.cardsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class CardSearchApplication

fun main(args: Array<String>) {
	val dataSourceUrl = System.getenv("SPRING_DATASOURCE_URL")
	println("Data Source URL: $dataSourceUrl")
	runApplication<CardSearchApplication>(*args)
}
