package com.example.channapatna_namma_pride.model

/**
 * Represents a verified Channapatna toy fetched from Firestore.
 */
data class Toy(
    val id: String = "",
    val name: String = "",
    val artisanName: String = "",
    val artisanId: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val location: String = "Channapatna, Karnataka",
    val giTagNumber: String = ""
)

