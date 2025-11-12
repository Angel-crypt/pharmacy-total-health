package models

import java.util.UUID

data class Recipe(
    val id : String = UUID.randomUUID().toString().substring(0,8),
    val clientName : String,
    val medicine : String,
    val quantity : Int,
    val totalAmount : Int
)

