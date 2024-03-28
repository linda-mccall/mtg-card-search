package com.mtg.cardsearch.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Subtype (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?=null,

        @Column(nullable = false)
        var name: String,

        @JsonIgnore
        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(name = "Card_Subtype",
                joinColumns = [JoinColumn(name = "subtype_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "card_id", referencedColumnName = "id")])
        var cards: Set<Card>
    )