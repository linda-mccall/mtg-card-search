package com.mtg.cardsearch.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder

@Entity(name = "person")
@AllArgsConstructor
class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?=null,

        @Column(nullable = false)
        var email: String,

        @Column(nullable = false)
        var password: String,

        @JsonIgnore
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
        var lists: Set<List>


    )