package com.example.channapatna_namma_pride.repository

import com.example.channapatna_namma_pride.model.Artisan
import com.example.channapatna_namma_pride.model.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Repository for artisan related operations against Firestore.
 */
class ArtisanRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val artisansCollection = firestore.collection("artisans")

    /**
     * Fetches artisan details by ID.
     */
    suspend fun getArtisanById(artisanId: String): Resource<Artisan> {
        return try {
            val document = artisansCollection.document(artisanId).get().await()
            if (document.exists()) {
                val artisan = document.toObject(Artisan::class.java)
                if (artisan != null) {
                    val isKannada = java.util.Locale.getDefault().language == "kn"
                    val nameField = if (isKannada) "name_kn" else "name_en"
                    val bioField = if (isKannada) "bio_kn" else "bio_en"
                    val workshopField = if (isKannada) "workshopName_kn" else "workshopName_en"
                    val specialtyField = if (isKannada) "specialties_kn" else "specialties_en"

                    val localizedName = document.getString(nameField) ?: artisan.name
                    val localizedBio = document.getString(bioField) ?: artisan.bio
                    val localizedWorkshop = document.getString(workshopField) ?: artisan.workshopName
                    val localizedSpecialties = document.get(specialtyField) as? List<String> ?: artisan.specialties

                    Resource.Success(artisan.copy(
                        id = document.id,
                        name = localizedName,
                        bio = localizedBio,
                        workshopName = localizedWorkshop,
                        specialties = localizedSpecialties
                    ))
                } else {
                    Resource.Error(messageId = com.example.channapatna_namma_pride.R.string.error_failed_to_parse)
                }
            } else {
                Resource.Error(
                    message = "Artisan not found.",
                    messageId = com.example.channapatna_namma_pride.R.string.error_artisan_not_found
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = "An unexpected error occurred: ${e.message ?: "Unknown error"}",
                messageId = com.example.channapatna_namma_pride.R.string.error_unexpected,
                exception = e
            )
        }
    }
}
