package com.mtg.cardsearch.dto

import java.beans.ConstructorProperties

data class LoginDto

@ConstructorProperties("email", "password")
constructor(val email: String, val password: String)