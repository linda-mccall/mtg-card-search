package com.mtg.cardsearch.model.response

data class CardResponse (

        val name: String,
        val text: String?=null,
        val cardManaCost: Short,
        val power: Short?=null,
        val toughness: Short?=null,
        val rarity: String,
        var types: List<String> = emptyList(),
        var subtypes: List<String> = emptyList(),
        var legalities: List<String> = emptyList(),
        var rulings: List<String> = emptyList(),
    )