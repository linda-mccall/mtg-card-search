package com.mtg.cardsearch.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Card (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?=null,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = true)
        var text: String?=null,

        @Column(nullable = false)
        var cardManaCost: Short,

        @Column(nullable = true)
        var power: Short?=null,

        @Column(nullable = true)
        var toughness: Short?=null,

        @Column(nullable = false)
        var digitalOnly: Boolean,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name  = "rarity_id")
        var rarity: Rarity,

        @ManyToMany(mappedBy = "cards")
        var types: Set<Type>,

        @ManyToMany(mappedBy = "cards")
        var subtypes: Set<Subtype>,

        @ManyToMany(mappedBy = "cards")
        var legalities: Set<Legality>,

        @JsonIgnore
        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(name = "List_Card",
                joinColumns = [JoinColumn(name = "card_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "list_id", referencedColumnName = "id")])
        var lists: Set<List>

)
