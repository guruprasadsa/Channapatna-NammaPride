package com.example.channapatna_namma_pride.model

/**
 * Detailed information about a Channapatna artisan.
 */
data class Artisan(
    val id: String = "",
    val name: String = "",
    val workshopName: String = "",
    val experience: String = "",
    val bio: String = "",
    val specialties: List<String> = emptyList(),
    val location: String = "",
    val phone: String = "",
    val imageUrl: String = ""
)
