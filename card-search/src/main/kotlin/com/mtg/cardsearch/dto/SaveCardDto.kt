package com.mtg.cardsearch.dto

import java.beans.ConstructorProperties

data class SaveCardDto

@ConstructorProperties("listId", "cardId")
constructor(val listId: Int, val cardId: Int)