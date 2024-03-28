package com.mtg.cardsearch.entity

import jakarta.persistence.*

@Entity
class CardRuling (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?=null,

        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name  = "card_id")
        var card: Card,

        @Column(nullable = false)
        var rulingText: String
    )