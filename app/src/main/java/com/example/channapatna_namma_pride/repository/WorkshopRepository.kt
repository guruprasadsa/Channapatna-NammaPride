package com.example.channapatna_namma_pride.repository

import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.model.Workshop
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repository for workshop / artisan location data.
 * Currently serves static mock data; ready to swap in Firestore calls.
 */
class WorkshopRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val workshopsCollection = firestore.collection("workshops")

    /**
     * Fetches all workshops from Firestore.
     */
    suspend fun getWorkshops(): Resource<List<Workshop>> {
        return try {
            val snapshot = workshopsCollection.get().await()
            val workshops = snapshot.documents.mapNotNull { doc ->
                val workshop = doc.toObject(Workshop::class.java)
                if (workshop != null) {
                    val isKannada = java.util.Locale.getDefault().language == "kn"
                    val nameField = if (isKannada) "name_kn" else "name_en"
                    val artisanField = if (isKannada) "artisanName_kn" else "artisanName_en"
                    val descField = if (isKannada) "description_kn" else "description_en"
                    val addressField = if (isKannada) "address_kn" else "address_en"

                    val localizedName = doc.getString(nameField) ?: workshop.name
                    val localizedArtisan = doc.getString(artisanField) ?: workshop.artisanName
                    val localizedDesc = doc.getString(descField) ?: workshop.description
                    val localizedAddress = doc.getString(addressField) ?: workshop.address

                    workshop.copy(
                        id = doc.id,
                        name = localizedName,
                        artisanName = localizedArtisan,
                        description = localizedDesc,
                        address = localizedAddress
                    )
                } else null
            }
            if (workshops.isNotEmpty()) {
                Resource.Success(workshops)
            } else {
                Resource.Error(
                    message = "No workshops found.",
                    messageId = com.example.channapatna_namma_pride.R.string.error_no_workshops
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
