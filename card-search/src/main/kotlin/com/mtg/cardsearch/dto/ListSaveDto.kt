package com.mtg.cardsearch.dto

import java.beans.ConstructorProperties

data class ListSaveDto

@ConstructorProperties("listId", "listName")
constructor(val listId: Int?, val listName: String)