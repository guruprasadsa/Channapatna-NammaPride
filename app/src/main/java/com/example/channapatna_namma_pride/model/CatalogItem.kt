package com.example.channapatna_namma_pride.model

/**
 * Represents a toy item in the catalog.
 * Maps directly to the Firestore "toys" collection documents.
 */
data class CatalogItem(
    val id: String = "",
    val name: String = "",
    val artisanName: String = "",
    val artisanId: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val location: String = "",
    val giTagNumber: String = "",
    val category: String = "Classic"
)
