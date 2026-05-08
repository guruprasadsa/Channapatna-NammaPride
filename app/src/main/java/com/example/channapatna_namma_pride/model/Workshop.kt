package com.example.channapatna_namma_pride.model

/**
 * Represents a toy workshop / maker studio.
 */
data class Workshop(
    val id: String = "",
    val name: String = "",
    val artisanName: String = "",
    val description: String = "",
    val address: String = "",
    val distance: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

