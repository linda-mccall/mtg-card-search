package com.mtg.cardsearch.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Legality (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?=null,

        @Column(nullable = false)
        var name: String,

        @JsonIgnore
        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(name = "Card_Legality",
                joinColumns = [JoinColumn(name = "legality_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "card_id", referencedColumnName = "id")])
        var cards: Set<Card>
    )