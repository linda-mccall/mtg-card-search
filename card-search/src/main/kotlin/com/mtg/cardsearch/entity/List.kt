package com.mtg.cardsearch.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.AllArgsConstructor

@Entity
@AllArgsConstructor
class List (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?=null,

        @Column(nullable = false)
        var name: String,

        @ManyToOne(cascade = [CascadeType.DETACH])
        @JsonIgnore
        @JoinColumn(name  = "person_id")
        var user: User,

        @ManyToMany(mappedBy = "lists")
        var cards: Set<Card>
    )