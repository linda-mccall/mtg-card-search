package com.mtg.cardsearch.entity

import jakarta.persistence.*

@Entity
class Rarity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?=null,

        @Column(nullable = false)
        var name: String
    )